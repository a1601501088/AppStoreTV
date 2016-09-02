package com.ws.service;

import com.ws.model.*;

import java.util.Map;

/**
 * Created by Administrator on 2016/5/23.
 */
public interface WSOrderInfoService {
    GetOrderInfoRsp queryOrderInfo(String spId, String userId, int userIdType, String userToken ) throws Exception;

    UnorderSingleServiceRsp unorderSingleService(String spId, String userId, int userIdType, String userToken,String productId) throws  Exception;

    BatchUnorderServiceRsp batchUnorderService(String spId, String userId, int userIdType, String userToken,String productIdList)throws Exception;

    Map<String,Object> postMG(String spId, String userId, String userIdType, String productId, String userToken, String expiredTime)throws Exception;
    UserDetInfoRsp getUserDets(UserDetInfoReq req ) throws Exception;

    AccountPwdChangRsp changeAccountPwd(AccountPwdChangReq req ) throws Exception;
}

