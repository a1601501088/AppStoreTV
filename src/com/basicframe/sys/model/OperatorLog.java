package com.basicframe.sys.model;

import org.apache.ibatis.type.Alias;

/**
 * <p>Description: 操作日志VO</p>
 *
 * <p>Copyright: Copyright (c) 2011</p>
 *
 * <p>Company: xmp</p>
 *
 * @author 唐颖杰
 * @version 1.0
 */
@Alias("operatorLog")
public class OperatorLog {

	/**
	 * 日志ID
	 */
	private String logID;
	/**
	 * 日志类型
	 */
	private String logType;
	/**
	 * 操作者ID
	 */
	private String operatorID;
	/**
	 * 操作者名称
	 */
	private String operatorName;
	/**
	 * 操作类型
	 */
	private String operatorType;
	/**
	 * 操作动作
	 */
	private String operatorAction;
	/**
	 * 操作前的值
	 */
	private String operatorValue;
	/**
	 * 操作IP
	 */
	private String operatorIP;
	/**
	 * 操作时间
	 */
	private String operatorTime;
	/**
	 * 操作备注
	 */
	private String operatorRemark;
	
	
	
	public String getLogID() {
		return logID;
	}
	
	public void setLogID(String logID) {
		this.logID = logID;
	}
	
	public String getLogType() {
		return logType;
	}
	
	public void setLogType(String logType) {
		this.logType = logType;
	}
	
	public String getOperatorID() {
		return operatorID;
	}
	
	public void setOperatorID(String operatorID) {
		this.operatorID = operatorID;
	}
	
	public String getOperatorName() {
		return operatorName;
	}
	
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	
	public String getOperatorType() {
		return operatorType;
	}
	
	public void setOperatorType(String operatorType) {
		this.operatorType = operatorType;
	}
	
	public String getOperatorAction() {
		return operatorAction;
	}
	
	public void setOperatorAction(String operatorAction) {
		this.operatorAction = operatorAction;
	}
	
	public String getOperatorValue() {
		return operatorValue;
	}

	public void setOperatorValue(String operatorValue) {
		this.operatorValue = operatorValue;
	}

	public String getOperatorIP() {
		return operatorIP;
	}
	
	public void setOperatorIP(String operatorIP) {
		this.operatorIP = operatorIP;
	}
	
	public String getOperatorTime() {
		return operatorTime;
	}
	
	public void setOperatorTime(String operatorTime) {
		this.operatorTime = operatorTime;
	}
	
	public String getOperatorRemark() {
		return operatorRemark;
	}
	
	public void setOperatorRemark(String operatorRemark) {
		this.operatorRemark = operatorRemark;
	}
	
}
