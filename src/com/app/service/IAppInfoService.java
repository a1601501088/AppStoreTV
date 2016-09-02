package com.app.service;

import com.app.model.AppInfo;
import com.app.model.UserApp;
import com.basicframe.common.service.IBaseService;

import java.util.List;

public interface IAppInfoService extends IBaseService<AppInfo>{

    AppInfo queryApp(UserApp app);
    List<AppInfo> queryAppByPackgeName(String packgeName) throws Exception;

    List<AppInfo> queryAppById(String appId);

    int updateDownloadNmber(AppInfo appId)throws Exception;
}
