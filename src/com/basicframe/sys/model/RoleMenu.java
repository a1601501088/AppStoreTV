package com.basicframe.sys.model;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

/**
 * <p>Description: 角色可管理的实体对象</p>
 *
 * <p>Copyright: Copyright (c) 2011</p>
 *
 * <p>Company: xmp</p>
 *
 * @author 唐颖杰
 * @version 1.0
 */
@Alias("roleMenu")
public class RoleMenu implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4044783475932604272L;
	/**
	 * 角色ID
	 */
	private int roleId;
	/**
	 * 模块ID
	 */
	private int menuId;
	
	
	
	public int getRoleId() {
		return roleId;
	}
	
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	
	public int getMenuId() {
		return menuId;
	}
	
	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}
	
	
}
