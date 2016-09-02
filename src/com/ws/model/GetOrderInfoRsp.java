package com.ws.model;

import java.util.List;

public class GetOrderInfoRsp {
	private String result;
	private String transactionID;
	private String userID;
	private int userIDType;

	//private List<Product> productList;
	private List<Product[]> productList;


	public List<Product[]> getProductList() {
		return productList;
	}

	public void setProductList(List<Product[]> productList) {
		this.productList = productList;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
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

	@Override
	public String toString() {
		return "GetOrderInfoRsp{" +
				"result='" + result + '\'' +
				", transactionID='" + transactionID + '\'' +
				", userID='" + userID + '\'' +
				", userIDType=" + userIDType +
				", productList=" + productList +
				'}';
	}
}
