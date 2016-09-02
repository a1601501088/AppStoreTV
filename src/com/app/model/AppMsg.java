package com.app.model;

import org.apache.ibatis.type.Alias;

@Alias("appMsg")
public class AppMsg {
	
	private int msgId;
	
	private String msgName;
	
	private String createTime;

	
	public int getMsgId() {
		return msgId;
	}

	public void setMsgId(int msgId) {
		this.msgId = msgId;
	}

	public String getMsgName() {
		return msgName;
	}

	public void setMsgName(String msgName) {
		this.msgName = msgName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
}
