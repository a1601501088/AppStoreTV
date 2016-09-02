package com.basicframe.sys.dao;

import org.springframework.dao.DataAccessException;

import com.basicframe.common.dao.SqlMapper;
import com.basicframe.sys.model.UserRole;

/**
 * <p>Description: 用户角色Mapper接口</p>
 *
 * <p>Copyright: Copyright (c) 2011</p>
 *
 * <p>Company: xmp</p>
 *
 * @author 唐颖杰
 * @version 1.0
 */
public interface UserRoleMapper extends SqlMapper<UserRole> {
	
	
	/**
	 * 根据用户ID删除
	 * 
	 * @param userId
	 * @return 受影响的行数
	 * @throws DataAccessException
	 * @author 唐颖杰
	 * @date： 2011-8-23
	 * @modify：
	 */
	public int deleteByUserId(int userId) throws DataAccessException;
	
}
