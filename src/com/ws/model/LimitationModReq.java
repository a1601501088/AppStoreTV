package com.ws.model;

public class LimitationModReq {
	
	private String limitation;
	private String timeStamp;
	private String transactionID;
	private String userID;
	private String userToken;
	public String getLimitation() {
		return limitation;
	}
	public void setLimitation(String limitation) {
		this.limitation = limitation;
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
		return "LimitationModReq [limitation=" + limitation + ", timeStamp="
				+ timeStamp + ", transactionID=" + transactionID + ", userID="
				+ userID + ", userToken=" + userToken + "]";
	}
	
	

}
