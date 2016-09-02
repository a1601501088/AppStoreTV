package com.basicframe.sys.model;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

/**
 * <p>Description: 权限实体对象</p>
 *
 * <p>Copyright: Copyright (c) 2011</p>
 *
 * <p>Company: xmp</p>
 *
 * @author 唐颖杰
 * @version 1.0
 */
@Alias("permissions")
public class Permissions implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5893686299092919593L;
	/**
	 * 权限ID
	 */
	private int perId;
	/**
	 * 权限名称
	 */
	private String perName;
	/**
	 * 权限动作
	 */
	private String perAction;
	/**
	 * 权限描述
	 */
	private String perRemark;
	/**
	 * 系统模块
	 */
	private String menuId;
	
	
	
	public int getPerId() {
		return perId;
	}

	public void setPerId(int perId) {
		this.perId = perId;
	}

	public String getPerName() {
		return perName;
	}

	public void setPerName(String perName) {
		this.perName = perName;
	}

	public String getPerAction() {
		return perAction;
	}

	public void setPerAction(String perAction) {
		this.perAction = perAction;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getPerRemark() {
		return perRemark;
	}

	public void setPerRemark(String perRemark) {
		this.perRemark = perRemark;
	}
	
}
