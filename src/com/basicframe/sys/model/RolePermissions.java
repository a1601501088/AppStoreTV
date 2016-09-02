package com.basicframe.sys.model;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

/**
 * <p>Description: 权限关联实体对象</p>
 *
 * <p>Copyright: Copyright (c) 2011</p>
 *
 * <p>Company: xmp</p>
 *
 * @author 唐颖杰
 * @version 1.0
 */
@Alias("rolePermissions")
public class RolePermissions implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3397206418879689197L;
	/**
	 * 角色ID
	 */
	private int roleId;
	/**
	 * 权限ID
	 */
	private int permissionsId;
	
	
	
	public int getRoleId() {
		return roleId;
	}
	
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	
	public int getPermissionsId() {
		return permissionsId;
	}
	
	public void setPermissionsId(int permissionsId) {
		this.permissionsId = permissionsId;
	}

	
}
