package com.app.service;

import com.app.model.DiscussInfo;
import com.basicframe.common.exception.BusException;
import com.basicframe.common.service.IBaseService;

public interface IDiscussInfoService extends IBaseService<DiscussInfo>{
	public void create(String appId, String appLevel) throws BusException;
}
