package com.basicframe.sys.model;

import java.util.Date;

import org.apache.ibatis.type.Alias;

/**
 * <p>Description: 角色实体对象</p>
 *
 * <p>Copyright: Copyright (c) 2011</p>
 *
 * <p>Company: xmp</p>
 *
 * @author 唐颖杰
 * @version 1.0
 */
@Alias("role")
public class Role {
	
	/**
	 * 角色ID
	 */
	private int roleId;
	/**
	 * 角色名
	 */
	private String roleName;
	/**
	 * 角色描述
	 */
	private String roleInfo;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	
	
	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}
	
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	public String getRoleInfo() {
		return roleInfo;
	}
	
	public void setRoleInfo(String roleInfo) {
		this.roleInfo = roleInfo;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
