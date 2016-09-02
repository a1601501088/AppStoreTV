package com.app.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.app.dao.AppPackgeMapper;
import com.app.dao.PackgeInfoMapper;
import com.app.model.PackgeInfo;
import com.app.service.IPackgeInfo;
import com.basicframe.common.service.IBaseService;
import com.basicframe.common.service.impl.BaseServiceImpl;

@Service("packgeServ")
public class PackgeInfoService extends BaseServiceImpl<PackgeInfo> implements IPackgeInfo{

	@Resource
	private AppPackgeMapper appPackgeMapper ;
	
	@Resource
	public void setMapper(PackgeInfoMapper packgeInfoMapper) {
		super.setSqlMapper(packgeInfoMapper);
	}

	@Override
	public List<PackgeInfo> queryByUser(String userId) {
		return null;
	}

	@Override
	public PackgeInfo queryByApp(String appId) throws Exception {
		return appPackgeMapper.selectByAppId(appId);
		
	}


	
}
