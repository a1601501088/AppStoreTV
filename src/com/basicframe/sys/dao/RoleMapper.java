package com.basicframe.sys.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.basicframe.common.dao.SqlMapper;
import com.basicframe.sys.model.Role;

/**
 * <p>Description: 角色Mapper接口</p>
 *
 * <p>Copyright: Copyright (c) 2011</p>
 *
 * <p>Company: xmp</p>
 *
 * @author 唐颖杰
 * @version 1.0
 */
public interface RoleMapper extends SqlMapper<Role> {
	
	/**
	 * 询用户所拥有的角色
	 * 
	 * @param userId
	 * 			用户ID
	 * 
	 * @return 角色列表
	 * @throws DataAccessException
	 * @author 唐颖杰
	 * @date： 2011-8-23
	 * @modify：
	 */
	public List<Role> selectUserRole(int userId) throws DataAccessException;
	
}
