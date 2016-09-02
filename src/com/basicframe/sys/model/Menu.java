package com.basicframe.sys.model;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

/**
 * <p>Description: 菜单实体对象</p>
 *
 * <p>Copyright: Copyright (c) 2011</p>
 *
 * <p>Company: xmp</p>
 *
 * @author 唐颖杰
 * @version 1.0
 */
@Alias("menu")
public class Menu implements Serializable{
	
	private static final long serialVersionUID = -7782699187797104995L;
	/**
	 * 菜单ID
	 */
	private int menuId;
	/**
	 * 菜单名
	 */
	private String menuName;
	/**
	 * 菜单图标
	 */
	private String menuPic;
	/**
	 * 菜单URL
	 */
	private String menuUrl;
	/**
	 * 排序
	 */
	private int menuOrder;
	/**
	 * 父菜单ID
	 */
	private int parentId;
	/**
	 * 是否使用
	 */
	private String isDisabled;
	/**
	 * 是否可以修改
	 */
	private String isModify;
	/**
	 * 是否可以删除
	 */
	private String isRemove;
	/**
	 * 是否打开
	 */
	private String isOpen;
	
	
	
	public int getMenuId() {
		return menuId;
	}
	
	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}
	
	public String getMenuName() {
		return menuName;
	}
	
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	
	public String getMenuPic() {
		return menuPic;
	}
	
	public void setMenuPic(String menuPic) {
		this.menuPic = menuPic;
	}
	
	public String getMenuUrl() {
		return menuUrl;
	}
	
	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}
	
	public int getMenuOrder() {
		return menuOrder;
	}
	
	public void setMenuOrder(int menuOrder) {
		this.menuOrder = menuOrder;
	}
	
	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getIsDisabled() {
		return isDisabled;
	}
	
	public void setIsDisabled(String isDisabled) {
		this.isDisabled = isDisabled;
	}
	
	public String getIsModify() {
		return isModify;
	}
	
	public void setIsModify(String isModify) {
		this.isModify = isModify;
	}
	
	public String getIsRemove() {
		return isRemove;
	}
	
	public void setIsRemove(String isRemove) {
		this.isRemove = isRemove;
	}
	
	public String getIsOpen() {
		return isOpen;
	}
	
	public void setIsOpen(String isOpen) {
		this.isOpen = isOpen;
	}
	
	
}
