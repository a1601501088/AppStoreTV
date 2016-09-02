package com.basicframe.sys.dao;

import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.basicframe.common.dao.SqlMapper;
import com.basicframe.sys.model.RolePermissions;

/**
 * <p>Description: 角色权限Mapper接口</p>
 *
 * <p>Copyright: Copyright (c) 2011</p>
 *
 * <p>Company: xmp</p>
 *
 * @author 唐颖杰
 * @version 1.0
 */
public interface RolePermissionsMapper extends SqlMapper<RolePermissions> {
	
	
	/**
	 * 根据菜单ID删除(删除角色操作权限)
	 * @param menuId
	 * 			菜单ID
	 * @return	受影响的行数
	 * @throws DataAccessException
	 * @author 唐颖杰
	 * @date： 2011-8-23
	 * @modify：
	 */
	public int deleteByMenuId(int menuId) throws DataAccessException;
	
	/**
	 * 根据角色ID删除
	 * @param map
	 * 			参数（1,roleId  2,mList）
	 * @return  受影响的行数
	 * @throws DataAccessException
	 * @author 唐颖杰
	 * @date： 2011-8-23
	 * @modify：
	 */
	public int deleteByRoleId(Map<String, Object> map) throws DataAccessException;
	
	/**
	 * 根据ID删除
	 * 	
	 * @param id
	 * 			权限ID
	 * @return
	 * 			int
	 * @throws DataAccessException
	 */
	public int deleteById(int perId) throws DataAccessException;
	
}
