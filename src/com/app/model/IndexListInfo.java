package com.app.model;

import java.util.Date;

public class IndexListInfo {
    private long indexId;

    private String indexName;

    private String stauts;

    private String topImageUrl;

    private String topExectePackge;

    private String topExecteActivity;

    private String topExectePara;

    private String bottomImageUrl;

    private String bottomExectePackge;

    private String bottomExecteActivity;

    private String bottomExectePara;

    private Date createTime;

    private String orderNo;

    public long getIndexId() {
        return indexId;
    }

    public void setIndexId(long indexId) {
        this.indexId = indexId;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName == null ? null : indexName.trim();
    }

    public String getStauts() {
        return stauts;
    }

    public void setStauts(String stauts) {
        this.stauts = stauts == null ? null : stauts.trim();
    }

    public String getTopImageUrl() {
        return topImageUrl;
    }

    public void setTopImageUrl(String topImageUrl) {
        this.topImageUrl = topImageUrl == null ? null : topImageUrl.trim();
    }

    public String getTopExectePackge() {
        return topExectePackge;
    }

    public void setTopExectePackge(String topExectePackge) {
        this.topExectePackge = topExectePackge == null ? null : topExectePackge.trim();
    }

    public String getTopExecteActivity() {
        return topExecteActivity;
    }

    public void setTopExecteActivity(String topExecteActivity) {
        this.topExecteActivity = topExecteActivity == null ? null : topExecteActivity.trim();
    }

    public String getTopExectePara() {
        return topExectePara;
    }

    public void setTopExectePara(String topExectePara) {
        this.topExectePara = topExectePara == null ? null : topExectePara.trim();
    }

    public String getBottomImageUrl() {
        return bottomImageUrl;
    }

    public void setBottomImageUrl(String bottomImageUrl) {
        this.bottomImageUrl = bottomImageUrl == null ? null : bottomImageUrl.trim();
    }

    public String getBottomExectePackge() {
        return bottomExectePackge;
    }

    public void setBottomExectePackge(String bottomExectePackge) {
        this.bottomExectePackge = bottomExectePackge == null ? null : bottomExectePackge.trim();
    }

    public String getBottomExecteActivity() {
        return bottomExecteActivity;
    }

    public void setBottomExecteActivity(String bottomExecteActivity) {
        this.bottomExecteActivity = bottomExecteActivity == null ? null : bottomExecteActivity.trim();
    }

    public String getBottomExectePara() {
        return bottomExectePara;
    }

    public void setBottomExectePara(String bottomExectePara) {
        this.bottomExectePara = bottomExectePara == null ? null : bottomExectePara.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }
}