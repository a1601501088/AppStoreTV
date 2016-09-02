package com.app.model;

public class UserAppVip {
    private String userId;

    private String userToken;

    private String appName;

    private String appPackge;

    private String appPace;

    private String appPrice;

    private String appTime;

    private String appCrite;

    private String appExpire;

    private String appId;
    
    private String appDay ;
    
    private String vipStatus ;
    
    private String payPat ;
        
    public UserAppVip(){}
    public UserAppVip(String appId ,String appName ,String userId ,String tooken){
    	this.appId = appId ;
    	this.appName = appName ;
    	this.userId = userId ;
    	this.userToken = tooken ;
    }
    
    public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
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

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppPackge() {
        return appPackge;
    }

    public void setAppPackge(String appPackge) {
        this.appPackge = appPackge;
    }

    public String getAppPace() {
        return appPace;
    }

    public void setAppPace(String appPace) {
        this.appPace = appPace;
    }

    public String getAppPrice() {
        return appPrice;
    }

    public void setAppPrice(String appPrice) {
        this.appPrice = appPrice;
    }

    public String getAppTime() {
        return appTime;
    }

    public void setAppTime(String appTime) {
        this.appTime = appTime;
    }

    public String getAppCrite() {
        return appCrite;
    }

    public void setAppCrite(String appCrite) {
        this.appCrite = appCrite;
    }

    public String getAppExpire() {
        return appExpire;
    }

    public void setAppExpire(String appExpire) {
        this.appExpire = appExpire;
    }
	public String getAppDay() {
		return appDay;
	}
	public void setAppDay(String appDay) {
		this.appDay = appDay;
	}
	public String getVipStatus() {
		return vipStatus;
	}
	public void setVipStatus(String vipStatus) {
		this.vipStatus = vipStatus;
	}
	public String getPayPat() {
		return payPat;
	}
	public void setPayPat(String payPat) {
		this.payPat = payPat;
	}
    
}