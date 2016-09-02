package com.app.service;

import com.app.model.AppInfo;
import com.app.model.PackgeInfo;
import com.app.model.UserApp;
import com.app.model.UserPackgeVip;
import com.basicframe.common.service.IBaseService;

import java.util.Map;

public interface IUserApp extends IBaseService<UserApp>{

	UserApp queryApp(UserApp app) ;
	
	UserApp queryUserApp(UserApp userApp);
	
	PackgeInfo queryPackgeByApp(String appId);
	
	UserPackgeVip queryUPVip(String userId ,String packgeName);

	UserApp queryAppByBean(UserApp app);

	 Map<String,Object> validate(AppInfo appInfo,  UserApp app)throws Exception;
}
