package com.basicframe.sys.dao;

import org.springframework.dao.DataAccessException;

import com.basicframe.common.dao.SqlMapper;
import com.basicframe.sys.model.User;

/**
 * <p>Description: 用户Mapper接口</p>
 *
 * <p>Copyright: Copyright (c) 2011</p>
 *
 * <p>Company: xmp</p>
 *
 * @author 唐颖杰
 * @version 1.0
 */
public interface UserMapper extends SqlMapper<User> {
	
	/**
	 * 登陆验证
	 * 
	 * @param user
	 * 			实体参数(1:Name,2:password)
	 * @return user
	 * @throws DataAccessException
	 * @author 唐颖杰
	 * @date： 2011-8-20
	 * @modify：
	 */
	public User login(User user) throws DataAccessException;
	
	/**
	 * 更新密码
	 * 
	 * @param user
	 * 			实体对象(1:passwrod,2:userId)
	 * @return 受影响的行数
	 * @throws DataAccessException
	 * @author 唐颖杰
	 * @date： 2011-8-20
	 * @modify：
	 */
	public int updatePassword(User user) throws DataAccessException;
	
	/**
	 * 更新状态
	 * 
	 * @param status
	 * 			状态
	 * @param user
	 * 			实体对象
	 * @return 受影响的行数
	 * @throws DataAccessException
	 * @author 唐颖杰
	 * @date： 2011-8-20
	 * @modify：
	 */
	public int updateStatus(User user) throws DataAccessException;
	
	/**
	 * 更新登陆信息
	 * 
	 * @param user
	 * 			实体对象(1:lastLoginTime,2:lastLoginIP,3:userId)
	 * @return 受影响的行数
	 * @throws DataAccessException
	 * @author 唐颖杰
	 * @date： 2011-8-20
	 * @modify：
	 */
	public int updateLogin(User user) throws DataAccessException;
	
	/**
	 * 
	 * @param realName
	 * 				姓名
	 * @return 用户对象
	 * @throws DataAccessException
	 * @author 唐颖杰
	 * @date： 2011-8-23
	 * @modify：
	 */
	public User selectByRealName(String realName) throws DataAccessException;

}
