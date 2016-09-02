package com.app.model;

import java.util.Date;

public class SpProductInfo {
    private String spProductId;

    private String productId;

    private String spId;

    private String payType;

    private String productName;

    private String packgeId;

    private String appId;

    private Date createTime;

    private String remark;


    public String getSpProductId() {
        return spProductId;
    }

    public void setSpProductId(String spProductId) {
        this.spProductId = spProductId == null ? null : spProductId.trim();
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    public String getSpId() {
        return spId;
    }

    public void setSpId(String spId) {
        this.spId = spId == null ? null : spId.trim();
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType == null ? null : payType.trim();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public String getPackgeId() {
        return packgeId;
    }

    public void setPackgeId(String packgeId) {
        this.packgeId = packgeId == null ? null : packgeId.trim();
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}