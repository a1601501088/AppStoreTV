package com.app.model;

public class IndexItemInfo {
    private int indexItemId;
    private String oldIndexId;//记录老的id

    public String getOldIndexId() {
        return oldIndexId;
    }

    public void setOldIndexId(String oldIndexId) {
        this.oldIndexId = oldIndexId;
    }

    private String indexItemName;

    private String stauts;

    private String imageUrl;

    private String exectePackge;

    private String execteActivity;

    private String exectePara;

    public int getIndexItemId() {
        return indexItemId;
    }

    public void setIndexItemId(int indexItemId) {
        this.indexItemId = indexItemId;
    }

    public String getIndexItemName() {
        return indexItemName;
    }

    public void setIndexItemName(String indexItemName) {
        this.indexItemName = indexItemName == null ? null : indexItemName.trim();
    }

    public String getStauts() {
        return stauts;
    }

    public void setStauts(String stauts) {
        this.stauts = stauts == null ? null : stauts.trim();
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl == null ? null : imageUrl.trim();
    }

    public String getExectePackge() {
        return exectePackge;
    }

    public void setExectePackge(String exectePackge) {
        this.exectePackge = exectePackge == null ? null : exectePackge.trim();
    }

    public String getExecteActivity() {
        return execteActivity;
    }

    public void setExecteActivity(String execteActivity) {
        this.execteActivity = execteActivity == null ? null : execteActivity.trim();
    }

    public String getExectePara() {
        return exectePara;
    }

    public void setExectePara(String exectePara) {
        this.exectePara = exectePara == null ? null : exectePara.trim();
    }
}