package com.app.service;

import com.app.model.UserApp;
import com.app.model.UserAppVip;
import com.app.model.UserPay;
import com.basicframe.common.exception.BusException;
import com.basicframe.common.service.IBaseService;

import java.util.List;
import java.util.Map;

public interface IUserAppVip  extends IBaseService<UserAppVip>{

    UserAppVip queryApp(UserApp app)throws Exception;

    List<UserAppVip> queryAppList(Map<String,Object> map) throws Exception;
      Map<String,String> wrapMon( UserApp userApp,UserAppVip uav)throws Exception;
    void updateUserPay(UserPay userApp) throws Exception;

    UserAppVip queryAppVipInfo(String userId, String appId);

    boolean vip(String userId, String userToken, String appId) throws BusException;
}
