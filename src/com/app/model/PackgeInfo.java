package com.app.model;

import java.util.List;

public class PackgeInfo {
    
	private String packgeId;

    private String packgeType;

    private String packgeCritme;

    private String packgeUrl;

    private String packgeName;

    private String packgeOrde;

    private String packgeTerm;

    private String packgeStatus;

    private String packgePrice;
 
    private String packgePace ;

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

    private List<AppInfo> appInfos;

    public List<AppInfo> getAppInfos() {
        return appInfos;
    }

    public void setAppInfos(List<AppInfo> appInfos) {
        this.appInfos = appInfos;
    }

    public String getPackgePace() {
		return packgePace;
	}

	public void setPackgePace(String packgePace) {
		this.packgePace = packgePace;
	}

	public String getPackgeId() {
        return packgeId;
    }

    public void setPackgeId(String packgeId) {
        this.packgeId = packgeId;
    }

    public String getPackgeType() {
        return packgeType;
    }

    public void setPackgeType(String packgeType) {
        this.packgeType = packgeType;
    }

    public String getPackgeCritme() {
        return packgeCritme;
    }

    public void setPackgeCritme(String packgeCritme) {
        this.packgeCritme = packgeCritme;
    }

    public String getPackgeUrl() {
        return packgeUrl;
    }

    public void setPackgeUrl(String packgeUrl) {
        this.packgeUrl = packgeUrl;
    }

    public String getPackgeName() {
        return packgeName;
    }

    public void setPackgeName(String packgeName) {
        this.packgeName = packgeName;
    }

    public String getPackgeOrde() {
        return packgeOrde;
    }

    public void setPackgeOrde(String packgeOrde) {
        this.packgeOrde = packgeOrde;
    }

    public String getPackgeTerm() {
        return packgeTerm;
    }

    public void setPackgeTerm(String packgeTerm) {
        this.packgeTerm = packgeTerm;
    }

    public String getPackgeStatus() {
        return packgeStatus;
    }

    public void setPackgeStatus(String packgeStatus) {
        this.packgeStatus = packgeStatus;
    }

    public String getPackgePrice() {
        return packgePrice;
    }

    public void setPackgePrice(String packgePrice) {
        this.packgePrice = packgePrice;
    }
}