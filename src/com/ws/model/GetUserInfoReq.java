package com.ws.model;

public class GetUserInfoReq {

	private String SPID;
	
	private String timeStamp;
	
	private String transactionID;
	
	private String userToken;

	public String getSPID() {
		return SPID;
	}

	public void setSPID(String sPID) {
		SPID = sPID;
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

	public String getUserToken() {
		return userToken;
	}

	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}

	@Override
	public String toString() {
		return "GetUserInfoReq [SPID=" + SPID + ", timeStamp=" + timeStamp
				+ ", transactionID=" + transactionID + ", userToken="
				+ userToken + "]";
	}
	
}
