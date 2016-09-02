package com.ws.model;

public class UserDet {
	private String description;
	private int fee;
	private int productFlag;
	private String productID;
	private String productName;
	private String SPID;
	private String SPName;
	private String orderTime;
	private int orderType;
	private int orderMethod;
	private String subscriptionID;
	private int purchaseType;
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getFee() {
		return fee;
	}
	public void setFee(int fee) {
		this.fee = fee;
	}
	public int getProductFlag() {
		return productFlag;
	}
	public void setProductFlag(int productFlag) {
		this.productFlag = productFlag;
	}
	public String getProductID() {
		return productID;
	}
	public void setProductID(String productID) {
		this.productID = productID;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getSPID() {
		return SPID;
	}
	public void setSPID(String sPID) {
		SPID = sPID;
	}
	public String getSPName() {
		return SPName;
	}
	public void setSPName(String sPName) {
		SPName = sPName;
	}
	public String getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}
	public int getOrderType() {
		return orderType;
	}
	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}
	public int getOrderMethod() {
		return orderMethod;
	}
	public void setOrderMethod(int orderMethod) {
		this.orderMethod = orderMethod;
	}
	public String getSubscriptionID() {
		return subscriptionID;
	}
	public void setSubscriptionID(String subscriptionID) {
		this.subscriptionID = subscriptionID;
	}
	public int getPurchaseType() {
		return purchaseType;
	}
	public void setPurchaseType(int purchaseType) {
		this.purchaseType = purchaseType;
	}
	@Override
	public String toString() {
		return "UserDet [SPID=" + SPID + ", SPName=" + SPName
				+ ", description=" + description + ", fee=" + fee
				+ ", orderMethod=" + orderMethod + ", orderTime=" + orderTime
				+ ", orderType=" + orderType + ", productFlag=" + productFlag
				+ ", productID=" + productID + ", productName=" + productName
				+ ", purchaseType=" + purchaseType + ", subscriptionID="
				+ subscriptionID + "]";
	}

	
	
}
