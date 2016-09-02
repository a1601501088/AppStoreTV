package com.app.model;

public class UserPay {
	/**
	 * 唯一标识
	 */
	private String payId ;
	/**
	 * 用户ID
	 */
    private String userId;
	/**
	 * 用户tooken
	 */
    private String userToken;
	/**
	 * 商品名字
	 */
    private String payName;
	/**
	 * 购买时间
	 */
    private String payTime;
	/**
	 * 购买价格
	 */
    private String payPice;
	/**
	 * 支付类型1.成功，2.失败
	 */
    private String payType;
	/**
	 * 购买类型 1.App，2.专区
	 */
	private String shareType ;
	/**
	 * 到期时间,购买时间加上一个月
	 */
	private String expireTime ;
	/**
	 * 购买次数
	 */
	private String payPat ;
	/**
	 * 商品ID
	 */
	private String commodity ;
	/**
	 * 支付成功或失败状态
	 */
	private String payStatus;


	public String getShareType() {
		return shareType;
	}

	public void setShareType(String shareType) {
		this.shareType = shareType;
	}

	public String getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}

	public String getPayPat() {
		return payPat;
	}

	public void setPayPat(String payPat) {
		this.payPat = payPat;
	}

	public String getCommodity() {
		return commodity;
	}

	public void setCommodity(String commodity) {
		this.commodity = commodity;
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

    public String getPayName() {
        return payName;
    }

    public void setPayName(String payName) {
        this.payName = payName;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getPayPice() {
        return payPice;
    }

    public void setPayPice(String payPice) {
        this.payPice = payPice;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

	public String getPayId() {
		return payId;
	}

	public void setPayId(String payId) {
		this.payId = payId;
	}


	public void setPayStatus(String payStatus) {

		this.payStatus = payStatus;
	}

	public String getPayStatus() {
		return payStatus;
	}
}