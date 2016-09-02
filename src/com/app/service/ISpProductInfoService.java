package com.app.service;

import com.app.model.SpProductInfo;
import com.basicframe.common.service.IBaseService;

/**
 * Created by Administrator on 2016/5/19.
 */
public interface ISpProductInfoService extends IBaseService<SpProductInfo> {
    SpProductInfo querySpProductInfo(String spId,String appId,String packgeId,String payType) throws Exception;
}
