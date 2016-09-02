package com.app.service.impl;

import javax.annotation.Resource;

import com.app.model.UserApp;
import org.springframework.stereotype.Service;

import com.app.dao.AppInfoMapper;
import com.app.model.AppInfo;
import com.app.service.IAppInfoService;
import com.basicframe.common.service.impl.BaseServiceImpl;

import java.util.List;


@Service("appInfoServ")
public class AppInfoServiceImpl extends BaseServiceImpl<AppInfo> implements IAppInfoService {

	@Resource
	private AppInfoMapper appInfoMapper;

	@Resource
	public void setMapper(AppInfoMapper appInfoMapper){
		super.setSqlMapper(appInfoMapper);
	}



	/**
	 * 查出是否是付费app  ， true为付费app应用
	 * @param app
	 */
	@Override
	public AppInfo queryApp(UserApp app) {

		AppInfo appInfo = appInfoMapper.queryApp(app);
		return appInfo;
	}

	@Override
	public List<AppInfo> queryAppByPackgeName(String packgeName) throws Exception {
		return appInfoMapper.queryAppByPackgeName(packgeName);
	}

	@Override
	public List<AppInfo> queryAppById(String appId) {

		return appInfoMapper.queryAppById(appId);
	}

	/**
	 *  更新  根据appId更新 下载次数加1
	 * @return
	 * @throws Exception
     */
	@Override
	public int updateDownloadNmber(AppInfo appInfo) throws Exception {

		int count = appInfoMapper.updateDownloadNmber(appInfo);
		return count;
	}
}
