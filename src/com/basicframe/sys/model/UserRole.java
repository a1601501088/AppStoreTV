package com.basicframe.sys.model;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

/**
 * <p>Description: 用户角色关联实体对象</p>
 *
 * <p>Copyright: Copyright (c) 2011</p>
 *
 * <p>Company: xmp</p>
 *
 * @author 唐颖杰
 * @version 1.0
 */
@Alias("userRole")
public class UserRole implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5360379152784300718L;
	/**
	 * 角色ID
	 */
	private int roleId;
	/**
	 * 用户ID
	 */
	private int userId;
	
	
	
	public int getRoleId() {
		return roleId;
	}
	
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	
	
}
