package com.basicframe.sys.service;

import java.util.List;

import com.basicframe.common.exception.BusException;
import com.basicframe.common.service.IBaseService;
import com.basicframe.sys.model.OperatorLog;
import com.basicframe.sys.model.Role;
import com.basicframe.sys.model.RoleMenu;
import com.basicframe.sys.model.RolePermissions;

/**
 * <p>Description: 角色接口</p>
 *
 * <p>Copyright: Copyright (c) 2011</p>
 *
 * <p>Company: xmp</p>
 *
 * @author 唐颖杰
 * @version 1.0
 */
public interface IRoleService extends IBaseService<Role> {
	
	
	/**
	 * 查询用户所属角色
	 *
	 * @param userId 
	 * 			用户ID
	 * @return list 角色列表
	 * @author 唐颖杰
	 * @date 2011-8-22
	 * @modify
	 */
	public List<Role> queryUserRole(int userId) ;
	
	/**
	 * 修改角色菜单权限
	 * 
	 * @param list 
	 * 			角色菜单List
	 * @param log 
	 * 			操作日志实体对象
	 * @throws BusException
	 * 			业务异常
	 * @author 唐颖杰
	 * @date：2011-8-22
	 * @modify：
	 */	
	public void modifyMenu(int roleId, List<RoleMenu> list, OperatorLog log) throws BusException ;
	
	/**
	 * 修改角色(高级)
	 * 
	 * @param role 
	 * 			角色实体对象
	 * @param log 
	 * 			操作日志实体对象
	 * @throws BusException
	 * 			业务异常
	 * @author 唐颖杰
	 * @date：2011-8-22
	 * @modify：
	 */	
	public void modifySenior(int roleId, List<RolePermissions> list, OperatorLog log) throws BusException;
	
}
