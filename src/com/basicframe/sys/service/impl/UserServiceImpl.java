package com.basicframe.sys.service.impl;

import com.basicframe.common.exception.BusException;
import com.basicframe.common.service.impl.BaseServiceImpl;
import com.basicframe.sys.dao.UserMapper;
import com.basicframe.sys.dao.UserRoleMapper;
import com.basicframe.sys.model.LoginLog;
import com.basicframe.sys.model.OperatorLog;
import com.basicframe.sys.model.User;
import com.basicframe.sys.model.UserRole;
import com.basicframe.sys.service.IUserService;
import com.basicframe.utils.DateTool;
import com.basicframe.utils.SystemParmDefine;
import com.basicframe.utils.ToolBox;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service("userServ")
public class UserServiceImpl extends BaseServiceImpl<User> implements IUserService {

	
	@Resource
	private UserMapper userMapper;
	@Resource
	private UserRoleMapper userRoleMapper;
	
	@Resource
	public void setMapper(UserMapper userMapper){
		super.setSqlMapper(userMapper);
	}
	
	public User createLogin(User user, LoginLog log) throws BusException { 
		//检测参数是否为空
		ToolBox.isNull(user);
		try {
			//登录验证
			user = userMapper.login(user);
			if(user == null){
				log.setLoginStatus("登陆失败");
				throw new BusException(BusException.ADMIN_LOGIN_ERROR);
			}
			if(SystemParmDefine.ADMIN_STATUS_STOP.equals(String.valueOf(user.getStatus()))){
				log.setLoginStatus("己被冻结");
				throw new BusException(BusException.ADMIN_LOGIN_STATUS_ERROR);
			}
			User u = new User();
			u.setUserId(user.getUserId());
			u.setLastLoginIP(log.getLoginIP());
			u.setLastLoginTime(DateTool.instance.getCurrentDateString());
			//更新,如果更新失败则抛出异常
			userMapper.updateLogin(u);
			log.setLoginStatus("登录成功");
		} catch (Exception e) {
			//日志,异常处理
			getLogger().error(e);
			log.setLoginStatus("系统错误");
			BusException.handleException(e);
		} finally {
			//创建日志
			logGPUtil.createLog(log);
		}
		return user;
	}

	public User queryById(int userId) throws BusException {
		User user = null;
		try {
			//查询管理员信息
			user = userMapper.selectById(userId);
			//如果查询数据为空,则抛出异常
			if(user == null){
				throw new BusException(BusException.ADMIN_NOT_EXISTS);
			}
		} catch (Exception e) {
			//日志,异常处理
			getLogger().error(e);
			BusException.handleException(e);
		}
		return user;
	}

	public void create(User user, OperatorLog log) throws BusException {
		//检测入参
		ToolBox.isNull(user);
		try {
			User nuser = userMapper.selectByName(user.getUserName());
			if(nuser != null){
				throw new BusException(BusException.LOGIN_NAME_IS_EXISTS);
			}
			User ruser = userMapper.selectByRealName(user.getRealName());
			if(ruser != null){
				throw new BusException(BusException.REAL_NAME_IS_EXISTS);
			}
			//创建管理员
			userMapper.insert(user);
			//新值
			log.setOperatorValue("登录名:"+user.getUserName()+";姓名:"+user.getRealName()+";性别:"+user.getSex());
			//操作动作
			log.setOperatorAction("增加管理员");
		} catch (Exception e) {
			getLogger().error(e);
			BusException.handleException(e);
		} finally {
			//创建日志
			logGPUtil.createLog(log);
		}
	}
	
	public void modifyStatus(User user, OperatorLog log) throws BusException {
		//检测入参
		ToolBox.isNull(user);
		ToolBox.isNull(user.getUserId());
		ToolBox.isNull(user.getStatus());
		try {
			//值
			log.setOperatorValue("管理员ID："+user.getUserId()+"；管理员状态："+user.getStatus());
			//操作动作
			log.setOperatorAction("修改管理员状态");
			userMapper.updateStatus(user);
		} catch (Exception e) {
			//日志,异常处理
			getLogger().error(e);
			BusException.handleException(e);
		} finally {
			//创建日志
			logGPUtil.createLog(log);
		}
	}

	public void modify(User user, List<UserRole> list, OperatorLog log) throws BusException {
		//检测入参
		ToolBox.isNull(user);
		ToolBox.isNull(user.getUserId());
		ToolBox.isNull(user.getRealName());
		try {
			User ruser = userMapper.selectByRealName(user.getRealName());
			User ouser = userMapper.selectById(user.getUserId());
			if(ruser != null){
				if(ouser.getUserId() != ruser.getUserId()){
					throw new BusException(BusException.REAL_NAME_IS_EXISTS);
				}
			}
			log.setOperatorValue("用户名:"+ouser.getUserName()+";姓名:"+user.getRealName()+";性别:"+user.getSex());
			//操作动作
			log.setOperatorAction("修改管理员");
			
			ouser.setRealName(user.getRealName());
			ouser.setSex(user.getSex());
			ouser.setBirthday(user.getBirthday());
			ouser.setDepartments(user.getDepartments());
			ouser.setDuties(user.getDuties());
			ouser.setEmail(user.getEmail());
			ouser.setOfficePhone(user.getOfficePhone());
			ouser.setHomePhone(user.getHomePhone());
			ouser.setMobilePhone(user.getMobilePhone());
			if(user.getPassword() != null && !"".equals(user.getPassword())){
				userMapper.updatePassword(user);
			}
			//修改管理员信息
			userMapper.update(ouser);
			//删除用户角色关系
			userRoleMapper.deleteByUserId(user.getUserId());
			//批量插入
			//userRoleMapper.batchInsert(list, UserRoleMapper.class);
			//因为mybatis的批量操作影响整个事物，所以改为循环插入的方式
			for(UserRole vo : list){
				userRoleMapper.insert(vo);
			}
		} catch (Exception e) {
			getLogger().error(e);
			BusException.handleException(e);
		} finally {
			//创建日志
			logGPUtil.createLog(log);
		}
	}

	public void modifyPassword(User user, OperatorLog log) throws BusException {
		//检测入参
		ToolBox.isNull(user);
		ToolBox.isNull(user.getUserId());
		ToolBox.isNull(user.getPassword());
		try {
			log.setOperatorValue("管理员ID："+user.getUserId());
			//操作动作
			log.setOperatorAction("修改密码");
			userMapper.updatePassword(user);
		} catch (Exception e) {
			//日志,异常处理
			getLogger().error(e);
			BusException.handleException(e);
		} finally {
			logGPUtil.createLog(log);
		}
	}


}
