package com.ws.model;

public class LimitationModRsp {
	private String description;
	private String result;
	private String transactionID;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	@Override
	public String toString() {
		return "LimitationModRsp [description=" + description + ", result="
				+ result + ", transactionID=" + transactionID + "]";
	}

}
