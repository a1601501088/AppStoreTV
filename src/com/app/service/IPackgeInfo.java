package com.app.service;

import com.app.model.PackgeInfo;
import com.basicframe.common.service.IBaseService;

import java.util.List;

public interface IPackgeInfo extends IBaseService<PackgeInfo>{

	List<PackgeInfo> queryByUser(String userId);
	
	PackgeInfo queryByApp(String appId) throws Exception;
}
