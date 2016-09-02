package com.ws.model;

public class BatchUnorderServiceReq {

	private String SPID;

	private String timeStamp;

	private String transactionID;
	
	private String userID;
	
	private int userIDType;
	
	private String userToken;
	
	private String productIDList;

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

	public String getUserToken() {
		return userToken;
	}

	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}

	public String getProductIDList() {
		return productIDList;
	}

	public void setProductIDList(String productIDList) {
		this.productIDList = productIDList;
	}
	
	

}
