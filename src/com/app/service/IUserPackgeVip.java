package com.app.service;

import com.app.model.UserPackgeVip;
import com.basicframe.common.service.IBaseService;

import java.util.List;
import java.util.Map;

public interface IUserPackgeVip extends IBaseService<UserPackgeVip> {

    UserPackgeVip queryApp(Map<String, Object> map) throws Exception;
    List<UserPackgeVip> queryAppList(Map<String, Object> map) throws Exception;

    UserPackgeVip queryAppById(Map<String, Object> map)throws Exception;

    boolean createVipAndPaylog(String userId, String packgeId, String userToken,String payType,
                         String commodity,String shareType) throws Exception;
}
