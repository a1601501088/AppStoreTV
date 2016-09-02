package com.ws.model;

import java.util.List;

public class UserDetInfoRsp {
	private int result;
	private String description;
	private String transactionID;
	private List<UserDet[]> userDetList;
	private String userID;
	private int userIDType;
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTransactionID() {
		return transactionID;
	}
	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}

	public List<UserDet[]> getUserDetList() {
		return userDetList;
	}

	public void setUserDetList(List<UserDet[]> userDetList) {
		this.userDetList = userDetList;
	}

	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public int getUserIDType() {
		return userIDType;
	}
	public void setUserIDType(int userIDType) {
		this.userIDType = userIDType;
	}
	@Override
	public String toString() {
		return "UserDetInfoRsp [description=" + description + ", result="
				+ result + ", transactionID=" + transactionID
				+ ", userDetList=" + userDetList + ", userID=" + userID
				+ ", userIDType=" + userIDType + "]";
	}
	
}
