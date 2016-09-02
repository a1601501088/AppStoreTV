package com.app.model;

import org.apache.ibatis.type.Alias;

@Alias("discussInfo")
public class DiscussInfo {
	
	private int discussId;
	
	private int appId;
	
	private int appLevel;
	
	private String createTime;

	
	public int getDiscussId() {
		return discussId;
	}

	public void setDiscussId(int discussId) {
		this.discussId = discussId;
	}

	public int getAppId() {
		return appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}

	public int getAppLevel() {
		return appLevel;
	}

	public void setAppLevel(int appLevel) {
		this.appLevel = appLevel;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	
}
