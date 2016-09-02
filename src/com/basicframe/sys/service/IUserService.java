package com.basicframe.sys.service;

import java.util.List;

import com.basicframe.common.exception.BusException;
import com.basicframe.common.service.IBaseService;
import com.basicframe.sys.model.LoginLog;
import com.basicframe.sys.model.OperatorLog;
import com.basicframe.sys.model.User;
import com.basicframe.sys.model.UserRole;

/**
 * <p>Description: 用户接口</p>
 *
 * <p>Copyright: Copyright (c) 2011</p>
 *
 * <p>Company: xmp</p>
 *
 * @author 唐颖杰
 * @version 1.0
 */
public interface IUserService extends IBaseService<User> {
	
	
	/**
	 * 管理员登录
	 * 
	 * @param user
	 * 			用户实体对象(需要的参数有1：loginName,2：password)
	 * @param log
	 * 			登陆日志对象
	 * @throws BusException 
	 * 				业务异常
	 * @author 唐颖杰
	 * @date 2011-8-22
	 * @modify
	 */
	public User createLogin(User user, LoginLog log) throws BusException ;
	
	/**
	 * 修改管理员信息
	 * 
	 * @param user 
	 * 				管理员实体对象
	 * @param log 
	 * 				操作日志实体对象
	 * @throws BusException
	 * 				业务异常
	 * @author 唐颖杰
	 * @date 2011-8-22
	 * @modify
	 */
	public void modify(User user, List<UserRole> list, OperatorLog log) throws BusException ;
	
	/**
	 * 修改管理员状态
	 * 
	 * @param user 
	 * 			用户实体对象
	 * @param log
	 * 			操作日志对象
	 * @throws BusException
	 * 			业务异常
	 * @author 唐颖杰
	 * @date 2011-8-22
	 * @modify
	 */
	public void modifyStatus(User user, OperatorLog log) throws BusException;
	
	/**
	 * 修改管理员密码
	 * 
	 * @param user
	 * 			用户实体对象(需要的参数有1：password,2：userId)
	 * @param log
	 * 			操作日志对象
	 * @throws BusException
	 * 			业务异常
	 * @author 唐颖杰
	 * @date 2011-8-22
	 * @modify
	 */
	public void modifyPassword(User user, OperatorLog log) throws BusException;
		

}
