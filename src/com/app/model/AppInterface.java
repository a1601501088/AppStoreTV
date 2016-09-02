package com.app.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.ibatis.type.Alias;

@Alias("appInterface")
public class AppInterface implements Serializable {

	private static final long serialVersionUID = 7203126793968364466L;
	private String transactionId;
	private String spId;
	private String userId;
	private String userToken;
	/*解密密钥，格式：n:2,其中0<=n<=加解密字段最小长度的整数
	如果填值，加解密字段为密文
	如果不填值，加解密字段为明文*/
	private String key;
	private String productId;
	/*扣费金额，单位：分
	此值不大于产品定价费用*/
	private String price;
	private String productName;
	private String backUrl;
	/*SP系统订购消息接收地址。如果该字段不为空，订购接口机主动将订购支付结果以http的方式同时告知SP系统的该地址
	url中不允许出现特殊字符“$”*/
	private String notifyUrl;
	/*标志位，可扩展：
	“VAS” ：增值业务
	“EPG” ：广电EPG */
	private String optFlag;
	/*当optFlag取值“EPG”时生效
	0：包月支付
	1：按次m元/n小时 （m-0.00两位小数 n-0整型） 
	2：产品包时段支付*/
	private String purchaseType;
	/*当optFlag取值“EPG”时生效
	栏目ID(purchaseType=1必填)*/
	private String categoryId;
	/*当optFlag取值“EPG”时生效
	内容ID(purchaseType=1必填， contentType为1,2,3时，contentID填频道ID)*/
	private String contentId;
	/*当optFlag取值“EPG”时生效
	0：vod（普通节目）；1：channel（频道）；2：tvod（回看）；(purchaseType=1必填)*/
	private String contentType;
	private String result;
	private String resultDesc;
	private String createTime;
	private String updateTime;
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getSpId() {
		return spId;
	}
	public void setSpId(String spId) {
		this.spId = spId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserToken() {
		return userToken;
	}
	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getBackUrl() {
		return backUrl;
	}
	public void setBackUrl(String backUrl) {
		this.backUrl = backUrl;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	public String getOptFlag() {
		return optFlag;
	}
	public void setOptFlag(String optFlag) {
		this.optFlag = optFlag;
	}
	public String getPurchaseType() {
		return purchaseType;
	}
	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getContentId() {
		return contentId;
	}
	public void setContentId(String contentId) {
		this.contentId = contentId;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getResultDesc() {
		return resultDesc;
	}
	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	
	
	
}
