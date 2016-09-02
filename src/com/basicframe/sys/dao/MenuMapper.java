package com.basicframe.sys.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.basicframe.common.dao.SqlMapper;
import com.basicframe.sys.model.Menu;

/**
 * <p>Description: 系统菜单Mapper接口</p>
 *
 * <p>Copyright: Copyright (c) 2011</p>
 *
 * <p>Company: xmp</p>
 *
 * @author 唐颖杰
 * @version 1.0
 */
public interface MenuMapper extends SqlMapper<Menu> {
	
	
	/**
	 * 根据父类ID查询
	 * 
	 * @param pid
	 * 			父类ID
	 * @return	Menu列表
	 * @throws DataAccessException
	 * @author 唐颖杰
	 * @date： 2011-8-23
	 * @modify：
	 */
	public List<Menu> selectByParentId(int pid) throws DataAccessException;
	
	/**
	 * 查询用户菜单
	 * 	
	 * @param userId
	 * 			用户ID
	 * @return	Menu列表
	 * @throws DataAccessException
	 * @author 唐颖杰
	 * @date： 2011-8-23
	 * @modify：
	 */
	public List<Menu> selectUserMenu(int userId) throws DataAccessException;
	
	/**
	 * 查询可赋权的角色权限菜单
	 * 
	 * @param roleId
	 * 			角色ID
	 * @return	Menu列表
	 * @throws DataAccessException
	 * @author 唐颖杰
	 * @date： 2011-8-23
	 * @modify：
	 */
	public List<Menu> selectRoleMenu(int roleId) throws DataAccessException;
	
	/**
	 * 查询所有可赋权的权限菜单
	 * 
	 * @return	Menu列表
	 * @throws DataAccessException
	 * @author 唐颖杰
	 * @date： 2011-8-23
	 * @modify：
	 */
	public List<Menu> selectPerMenu() throws DataAccessException;
	
}
