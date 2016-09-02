package com.basicframe.sys.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.basicframe.common.exception.BusException;
import com.basicframe.common.service.impl.BaseServiceImpl;
import com.basicframe.sys.dao.RoleMapper;
import com.basicframe.sys.dao.RoleMenuMapper;
import com.basicframe.sys.dao.RolePermissionsMapper;
import com.basicframe.sys.dao.UserRoleMapper;
import com.basicframe.sys.model.OperatorLog;
import com.basicframe.sys.model.Role;
import com.basicframe.sys.model.RoleMenu;
import com.basicframe.sys.model.RolePermissions;
import com.basicframe.sys.service.IRoleService;
import com.basicframe.utils.ToolBox;


@Service("roleServ")
public class RoleServiceImpl extends BaseServiceImpl<Role> implements IRoleService{
	
	@Resource
	private RoleMapper roleMapper;
	@Resource
	private UserRoleMapper userRoleMapper;
	@Resource
	private RoleMenuMapper roleMenuMapper;
	@Resource
	private RolePermissionsMapper rolePermissionsMapper;
	
	@Resource
	public void setMapper(RoleMapper roleMapper){
		super.setSqlMapper(roleMapper);
	}
	
	public void create(Role role, OperatorLog log) throws BusException {
		try {
			//检测入参
			ToolBox.isNull(role);
			ToolBox.isNull(role.getRoleName());
			ToolBox.isNull(role.getRoleInfo());
			Role r = roleMapper.selectByName(role.getRoleName());
			if(r != null){
				throw new BusException(BusException.ROLE_NAME_IS_EXISTS);
			}
			//创建角色
			roleMapper.insert(role);
		} catch (Exception e) {
			getLogger().error(e);
			BusException.handleException(e);
		} finally {
			//创建日志
			logGPUtil.createLog(log);
		}
	}

	public void modifyMenu(int roleId, List<RoleMenu> list, OperatorLog log) throws BusException {
		try {
			//要修改的角色菜单List(记录操作日志)
			String str = "";
			List<Integer> mList = null;
			if(list != null && list.size() > 0){
				mList = new ArrayList<Integer>();
				for(int i = 0; i< list.size(); i++){
					mList.add(list.get(i).getMenuId());
					str += list.get(i).getMenuId() + ",";
				}
				str = str.substring(0, str.length()-1);
			}
			//值
			log.setOperatorValue("角色ID:"+roleId+";菜单ID:" + str);
			//操作动作
			log.setOperatorAction("修改角色菜单");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("roleId", roleId);
			map.put("mList", mList);
			//删除角色操作权限
			rolePermissionsMapper.deleteByRoleId(map);
			//删除角色菜单权限
			roleMenuMapper.delete(roleId);
			//添加角色菜单权限
			//roleMenuMapper.batchInsert(list, RoleMenuMapper.class);
			//因为mybatis的批量操作影响整个事物，所以改为循环插入的方式
			for(RoleMenu vo : list){
				roleMenuMapper.insert(vo);
			}
		} catch (Exception e) {
			getLogger().error(e);
			BusException.handleException(e);
		} finally {
			//创建日志
			logGPUtil.createLog(log);
		}
	}
	
	public void modifySenior(int roleId, List<RolePermissions> list, OperatorLog log) throws BusException {
		try {
			//要修改的角色菜单List(记录操作日志)
			String str = "";
			if(list != null && list.size() > 0){
				for(int i = 0; i< list.size(); i++){
					str += list.get(i).getPermissionsId() + ",";
				}
				str = str.substring(0, str.length()-1);
			}
			//新值
			log.setOperatorValue("角色ID:"+roleId+";操作权限ID:" + str);
			//操作动作
			log.setOperatorAction("修改角色操作权限");
			//修改权限
			rolePermissionsMapper.delete(roleId);
			//批量插入
			//rolePermissionsMapper.batchInsert(list, RolePermissionsMapper.class);
			//因为mybatis的批量操作影响整个事物，所以改为循环插入的方式
			for(RolePermissions vo : list){
				rolePermissionsMapper.insert(vo);
			}
		} catch (Exception e) {
			getLogger().error(e);
			BusException.handleException(e);
		} finally {
			//创建日志
			logGPUtil.createLog(log);
		}
	}

	public void remove(int id, OperatorLog log) throws BusException {
		//检测入参
		ToolBox.isNull(id);
		try {
			//删除角色
			roleMapper.delete(id);
			//删除用户角色
			userRoleMapper.delete(id);
			//删除角色菜单
			roleMenuMapper.delete(id);
			//删除角色操作权限
			rolePermissionsMapper.delete(id);
		} catch (Exception e) {
			getLogger().error(e);
			BusException.handleException(e);
		} finally {
			//创建日志
			logGPUtil.createLog(log);
		}
	}
	
	public List<Role> queryUserRole(int userId) {
		return roleMapper.selectUserRole(userId);
	}


}
