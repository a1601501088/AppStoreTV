package com.basicframe.sys.dao;

import org.springframework.dao.DataAccessException;

import com.basicframe.common.dao.SqlMapper;
import com.basicframe.sys.model.RoleMenu;

/**
 * <p>Description: 角色菜单权限Mapper接口</p>
 *
 * <p>Copyright: Copyright (c) 2011</p>
 *
 * <p>Company: xmp</p>
 *
 * @author 唐颖杰
 * @version 1.0
 */
public interface RoleMenuMapper extends SqlMapper<RoleMenu> {
	
	
	/**
	 * 根据菜单ID删除
	 * @param menuId
	 * 			菜单ID
	 * @return	受影响的行数
	 * @throws DataAccessException
	 * @author 唐颖杰
	 * @date： 2011-8-23
	 * @modify：
	 */
	public int deleteByMenuId(int menuId) throws DataAccessException;
	
}
