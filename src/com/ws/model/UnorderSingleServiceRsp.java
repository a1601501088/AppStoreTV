package com.ws.model;

public class UnorderSingleServiceRsp {
	private String contentID;
	private String description;
	private String expiredTime;
	private String productID;
	private String result;
	private String serviceID;
	private String transactionID;
	public String getContentID() {
		return contentID;
	}
	public void setContentID(String contentID) {
		this.contentID = contentID;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getExpiredTime() {
		return expiredTime;
	}
	public void setExpiredTime(String expiredTime) {
		this.expiredTime = expiredTime;
	}
	public String getProductID() {
		return productID;
	}
	public void setProductID(String productID) {
		this.productID = productID;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getServiceID() {
		return serviceID;
	}
	public void setServiceID(String serviceID) {
		this.serviceID = serviceID;
	}
	public String getTransactionID() {
		return transactionID;
	}
	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}


	@Override
	public String toString() {
		return "UnorderSingleServiceRsp{" +
				"contentID='" + contentID + '\'' +
				", description='" + description + '\'' +
				", expiredTime='" + expiredTime + '\'' +
				", productID='" + productID + '\'' +
				", result='" + result + '\'' +
				", serviceID='" + serviceID + '\'' +
				", transactionID='" + transactionID + '\'' +
				'}';
	}
}
