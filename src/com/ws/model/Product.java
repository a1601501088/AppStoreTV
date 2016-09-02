package com.ws.model;

public class Product {
	private String changingPolicyDesc;
	private int subscriptionExtend;
	private String expiredTime;
	private String productID;
	private String productName;
	private int purchaseType;
	public String getChangingPolicyDesc() {
		return changingPolicyDesc;
	}
	public void setChangingPolicyDesc(String changingPolicyDesc) {
		this.changingPolicyDesc = changingPolicyDesc;
	}
	public int getSubscriptionExtend() {
		return subscriptionExtend;
	}
	public void setSubscriptionExtend(int subscriptionExtend) {
		this.subscriptionExtend = subscriptionExtend;
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
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getPurchaseType() {
		return purchaseType;
	}
	public void setPurchaseType(int purchaseType) {
		this.purchaseType = purchaseType;
	}
	@Override
	public String toString() {
		return "Product [changingPolicyDesc=" + changingPolicyDesc
				+ ", expiredTime=" + expiredTime + ", productID=" + productID
				+ ", productName=" + productName + ", purchaseType="
				+ purchaseType + ", subscriptionExtend=" + subscriptionExtend
				+ "]";
	}
	
	
}
