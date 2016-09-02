package com.basicframe.sys.service;

import java.util.List;

import com.basicframe.common.service.IBaseService;
import com.basicframe.sys.model.Menu;

/**
 * <p>Description: 菜单接口</p>
 *
 * <p>Copyright: Copyright (c) 2011</p>
 *
 * <p>Company: xmp</p>
 *
 * @author 唐颖杰
 * @version 1.0
 */
public interface IMenuService extends IBaseService<Menu> {

	
	/**
	 * 根据parentId查询菜单
	 * 
	 * @param parentId 
	 * 				parentId
	 * @return 菜单List
	 * @author 唐颖杰
	 * @date： 2011-8-22
	 * @modify：
	 */
	public List<Menu> queryByParentId(int parentId) ;
	
	/**
	 * 查询用户所有的菜单(高级权限)
	 * 
	 * @param roleId 
	 * 				角色ID
	 * @return 用户所有的菜单List
	 * @author 唐颖杰
	 * @date：  2011-8-22
	 * @modify：
	 */
	public List<Menu> queryRoleMenu(int roleId) ;
	
	/**
	 * 查询用户所有的菜单(高级权限)
	 * 
	 * @return 菜单List
	 * @author 唐颖杰
	 * @date：  2011-8-22
	 * @modify：
	 */
	public List<Menu> queryPerMenu() ;
	
	/**
	 * 查询用户所有的模块
	 * 
	 * @param userId 
	 * 				用户ID
	 * @return 模块List
	 * @author 唐颖杰
	 * @date：  2011-8-22
	 * @modify：
	 */
	public List<Menu> queryUserMenu(int userId) ;
	
	
}

