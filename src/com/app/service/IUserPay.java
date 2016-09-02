package com.app.service;

import com.app.model.UserPay;
import com.basicframe.common.service.IBaseService;

public interface IUserPay extends IBaseService<UserPay>{
      int create2(UserPay userPay ) throws Exception;
      UserPay queryLatelyByUserId(String userId ) throws Exception;

      boolean payLog(String userId ,String appId,boolean isApp, String userToken, String payType, String commodiy, String shareType,String status) throws Exception;
}
