package com.basicframe.sys.model;

import org.apache.ibatis.type.Alias;

/**
 * <p>Description: 登录日志VO</p>
 *
 * <p>Copyright: Copyright (c) 2011</p>
 *
 * <p>Company: xmp</p>
 *
 * @author 唐颖杰
 * @version 1.0
 */
@Alias("loginLog")
public class LoginLog {
	
	/**
	 * 登录ID
	 */
	private String logId;
	/**
	 * 登录者名称
	 */
	private String loginName;
	/**
	 * 登录状态
	 */
	private String loginStatus;
	/**
	 * 登录IP
	 */
	private String loginIP;
	/**
	 * 登录时间
	 */
	private String loginTime;
	/**
	 * 备注
	 */
	private String remark;
	
	
	
	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	public String getLoginName() {
		return loginName;
	}
	
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
	public String getLoginIP() {
		return loginIP;
	}
	
	public void setLoginIP(String loginIP) {
		this.loginIP = loginIP;
	}
	
	public String getLoginTime() {
		return loginTime;
	}
	
	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}
	
	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getLoginStatus() {
		return loginStatus;
	}

	public void setLoginStatus(String loginStatus) {
		this.loginStatus = loginStatus;
	}

	
}
