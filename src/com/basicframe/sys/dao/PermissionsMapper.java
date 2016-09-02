package com.basicframe.sys.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.basicframe.common.dao.SqlMapper;
import com.basicframe.sys.model.Permissions;

/**
 * <p>Description: 权限Mapper接口</p>
 *
 * <p>Copyright: Copyright (c) 2011</p>
 *
 * <p>Company: xmp</p>
 *
 * @author 唐颖杰
 * @version 1.0
 */
public interface PermissionsMapper extends SqlMapper<Permissions> {
	
	
	/**
	 * 查询用户权限
	 * 
	 * @param userId
	 * 			用户ID
	 * 
	 * @return	用户权限列表
	 * @throws DataAccessException
	 * @author 唐颖杰
	 * @date： 2011-8-23
	 * @modify：
	 */
	public List<Permissions> selectUserPermissions(int userId) throws DataAccessException;
	
	/**
	 * 根据菜单ID删除
	 * 
	 * @param menuId
	 * 			菜单ID
	 * 
	 * @return 受影响的行数
	 * @throws DataAccessException
	 * @author 唐颖杰
	 * @date： 2011-8-23
	 * @modify：
	 */
	public int deleteByMenuId(int menuId) throws DataAccessException;
	
	
}
