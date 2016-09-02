package com.ws.model;

public class AccountPwdChangReq {
     private String newPassword;
     private String password;
     private String timeStamp;
     private String transactionID;
     private String userID;
     private String userToken;
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getTransactionID() {
		return transactionID;
	}
	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getUserToken() {
		return userToken;
	}
	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}
	@Override
	public String toString() {
		return "AccountPwdChangReq [newPassword=" + newPassword + ", password="
				+ password + ", timeStamp=" + timeStamp + ", transactionID="
				+ transactionID + ", userID=" + userID + ", userToken="
				+ userToken + "]";
	}
     
     
}
