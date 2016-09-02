package com.basicframe.sys.model;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

/**
 * <p>Description: 用户实体对象</p>
 *
 * <p>Copyright: Copyright (c) 2011</p>
 *
 * <p>Company: xmp</p>
 *
 * @author 唐颖杰
 * @version 1.0
 */
@Alias("user")
public class User implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6581712050560028052L;
	/**
	 * 用户ID
	 */
	private int userId;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 真实姓名
	 */
	private String realName;
	/**
	 * 性别
	 */
	private int sex;
	/**
	 * 生日
	 */
	private String birthday;
	/**
	 * 部门
	 */
	private String departments;
	/**
	 * 职位
	 */
	private String duties;
	/**
	 * 邮箱地址
	 */
	private String email;
	/**
	 * 办公室电话
	 */
	private String officePhone;
	/**
	 * 家庭电话
	 */
	private String homePhone;
	/**
	 * 手机号码
	 */
	private String mobilePhone;
	/**
	 * 管理员状态 0：禁用 1：正常
	 */
	private int status;
	/**
	 * 创建时间
	 */
	private String createTime;
	/**
	 * 最后登录时间
	 */
	private String lastLoginTime;
	/**
	 * 最后登录IP
	 */
	private String lastLoginIP;
	/**
	 * 登录次数
	 */
	private int loginTimes;
	/**
	* 管理员角色名称
	*/
	private String roleNameList;
	/**
	* 管理员角色ID
	*/
	private String roleIdList;
	
	
	
	public String getRoleNameList() {
		return roleNameList;
	}

	public void setRoleNameList(String roleNameList) {
		this.roleNameList = roleNameList;
	}

	public String getRoleIdList() {
		return roleIdList;
	}

	public void setRoleIdList(String roleIdList) {
		this.roleIdList = roleIdList;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getRealName() {
		return realName;
	}
	
	public void setRealName(String realName) {
		this.realName = realName;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}
	
	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getDepartments() {
		return departments;
	}

	public void setDepartments(String departments) {
		this.departments = departments;
	}

	public String getDuties() {
		return duties;
	}

	public void setDuties(String duties) {
		this.duties = duties;
	}

	public String getOfficePhone() {
		return officePhone;
	}

	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}

	public String getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	public String getLastLoginTime() {
		return lastLoginTime;
	}
	
	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	
	public String getLastLoginIP() {
		return lastLoginIP;
	}
	
	public void setLastLoginIP(String lastLoginIP) {
		this.lastLoginIP = lastLoginIP;
	}
	
	public int getLoginTimes() {
		return loginTimes;
	}
	
	public void setLoginTimes(int loginTimes) {
		this.loginTimes = loginTimes;
	}
	
}
