package com.app.model;

import org.apache.ibatis.type.Alias;

@Alias("appInfo")
public class AppInfo {
	
	private int appGread;
	
	private int appId;

	private int classId;

	private String appName;

	private String appShortName;

	private String appIcon;

	private String appVersion;

	private String appUrl;

	private String appImg;

	private String appDesc;

	private String appSize;

	private int appCount;

	private int appLevel;

	private String createTime;

	private String updateTime;

	private String className;


	private String provider;

	private String price;

	private String freeFrequency;

	private String chargeType;

	private String freeTime;

	private String packge;

	private String jsonData;

	private String showName;

	private String productId;

	private String spId;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getSpId() {
		return spId;
	}

	public void setSpId(String spId) {
		this.spId = spId;
	}


	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	private String appCode;

	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getFreeFrequency() {
		return freeFrequency;
	}

	public void setFreeFrequency(String freeFrequency) {
		this.freeFrequency = freeFrequency;
	}

	public String getChargeType() {
		return chargeType;
	}

	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}

	public String getFreeTime() {
		return freeTime;
	}

	public void setFreeTime(String freeTime) {
		this.freeTime = freeTime;
	}

	public String getPackge() {
		return packge;
	}

	public void setPackge(String packge) {
		this.packge = packge;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public int getAppId() {
		return appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}

	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppShortName() {
		return appShortName;
	}

	public void setAppShortName(String appShortName) {
		this.appShortName = appShortName;
	}

	public String getAppIcon() {
		return appIcon;
	}

	public void setAppIcon(String appIcon) {
		this.appIcon = appIcon;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public String getAppUrl() {
		return appUrl;
	}

	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}

	public String getAppImg() {
		return appImg;
	}

	public void setAppImg(String appImg) {
		this.appImg = appImg;
	}

	public String getAppDesc() {
		return appDesc;
	}

	public void setAppDesc(String appDesc) {
		this.appDesc = appDesc;
	}

	public String getAppSize() {
		return appSize;
	}

	public void setAppSize(String appSize) {
		this.appSize = appSize;
	}

	public int getAppCount() {
		return appCount;
	}

	public void setAppCount(int appCount) {
		this.appCount = appCount;
	}

	public int getAppLevel() {
		return appLevel;
	}

	public void setAppLevel(int appLevel) {
		this.appLevel = appLevel;
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

	public int getAppGread() {
		return appGread;
	}

	public void setAppGread(int appGread) {
		this.appGread = appGread;
	}

	@Override
	public String toString() {
		return "AppInfo{" +
				"appGread=" + appGread +
				", appId=" + appId +
				", classId=" + classId +
				", appName='" + appName + '\'' +
				", appShortName='" + appShortName + '\'' +
				", appIcon='" + appIcon + '\'' +
				", appVersion='" + appVersion + '\'' +
				", appUrl='" + appUrl + '\'' +
				", appImg='" + appImg + '\'' +
				", appDesc='" + appDesc + '\'' +
				", appSize='" + appSize + '\'' +
				", appCount=" + appCount +
				", appLevel=" + appLevel +
				", createTime='" + createTime + '\'' +
				", updateTime='" + updateTime + '\'' +
				", className='" + className + '\'' +
				", jsonDate='" + jsonData + '\'' +
				", provider='" + provider + '\'' +
				", price='" + price + '\'' +
				", freeFrequency='" + freeFrequency + '\'' +
				", chargeType='" + chargeType + '\'' +
				", freeTime='" + freeTime + '\'' +
				", packge='" + packge + '\'' +
				", jsonData='" + jsonData + '\'' +
				", showName='" + showName + '\'' +
				", appCode='" + appCode + '\'' +
				'}';
	}
}
