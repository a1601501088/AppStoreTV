package com.app.dao;


import com.app.model.AppInfo;
import com.app.model.UserApp;
import com.basicframe.common.dao.SqlMapper;

import java.util.List;

/**
 * <p>Description: 应用Mapper接口</p>
 *
 * <p>Copyright: Copyright (c) 2011</p>
 *
 * <p>Company: </p>
 *
 * @author 唐颖杰
 * @version 1.0
 */
public interface AppInfoMapper extends SqlMapper<AppInfo> {
	
	AppInfo queryApp(UserApp app);
	List<AppInfo> queryAppByPackgeName(String packgeName) throws Exception;

	List<AppInfo> queryAppById(String appId);

	int updateDownloadNmber(AppInfo appInfo) throws Exception;
}
