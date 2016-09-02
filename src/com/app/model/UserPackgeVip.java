package com.app.model;

public class UserPackgeVip {

    private String userId;

    private String userToken;

    private String packgeName;

    private String packgePace;

    private String packgePrice;

    private String vipStatus;

    private String packgeId;

    private String packgeUrl;

    private String packgeOrde;

    private String packgeExpire;

    private String payPat;
    private PackgeInfo packgeInfo;

    public PackgeInfo getPackgeInfo() {
        return packgeInfo;
    }

    public void setPackgeInfo(PackgeInfo packgeInfo) {
        this.packgeInfo = packgeInfo;
    }

    public UserPackgeVip() {
    }

    public UserPackgeVip(String userId, String packgeName) {
        this.userId = userId;
        this.packgeName = packgeName;
    }

    public String getPackgeId() {
        return packgeId;
    }

    public void setPackgeId(String packgeId) {
        this.packgeId = packgeId;
    }

    public String getVipStatus() {
        return vipStatus;
    }

    public void setVipStatus(String vipStatus) {
        this.vipStatus = vipStatus;
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

    public String getPackgeName() {
        return packgeName;
    }

    public void setPackgeName(String packgeName) {
        this.packgeName = packgeName;
    }

    public String getPackgePace() {
        return packgePace;
    }

    public void setPackgePace(String packgePace) {
        this.packgePace = packgePace;
    }

    public String getPackgePrice() {
        return packgePrice;
    }

    public void setPackgePrice(String packgePrice) {
        this.packgePrice = packgePrice;
    }

    public String getPackgeUrl() {
        return packgeUrl;
    }

    public void setPackgeUrl(String packgeUrl) {
        this.packgeUrl = packgeUrl;
    }

    public String getPackgeOrde() {
        return packgeOrde;
    }

    public void setPackgeOrde(String packgeOrde) {
        this.packgeOrde = packgeOrde;
    }

    public String getPackgeExpire() {
        return packgeExpire;
    }

    public void setPackgeExpire(String packgeExpire) {
        this.packgeExpire = packgeExpire;
    }

    public String getPayPat() {
        return payPat;
    }

    public void setPayPat(String payPat) {
        this.payPat = payPat;
    }


}