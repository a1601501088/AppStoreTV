package com.app.model;

import org.apache.ibatis.type.Alias;

@Alias("commendInfo")
public class CommendInfo {
	
	private int commendId;
	
	private int commendType;
	
	private String commendName;
	
	private String commendUrl;
	
	private String commendImg;
	
	private String createTime;
	
	private String className;

	
	
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getCommendImg() {
		return commendImg;
	}

	public void setCommendImg(String commendImg) {
		this.commendImg = commendImg;
	}

	public String getCommendName() {
		return commendName;
	}

	public void setCommendName(String commendName) {
		this.commendName = commendName;
	}

	public int getCommendId() {
		return commendId;
	}

	public void setCommendId(int commendId) {
		this.commendId = commendId;
	}

	public int getCommendType() {
		return commendType;
	}

	public void setCommendType(int commendType) {
		this.commendType = commendType;
	}

	public String getCommendUrl() {
		return commendUrl;
	}

	public void setCommendUrl(String commendUrl) {
		this.commendUrl = commendUrl;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	
}
