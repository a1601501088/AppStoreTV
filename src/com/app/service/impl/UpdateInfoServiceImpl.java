package com.app.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.app.dao.UpdateInfoMapper;
import com.app.model.UpdateInfo;
import com.app.service.IUpdateInfoService;
import com.basicframe.common.service.impl.BaseServiceImpl;


@Service("updateInfoServ")
public class UpdateInfoServiceImpl extends BaseServiceImpl<UpdateInfo> implements IUpdateInfoService {

	@Resource
	private UpdateInfoMapper updateInfoMapper;
	
	@Resource
	public void setMapper(UpdateInfoMapper updateInfoMapper){
		super.setSqlMapper(updateInfoMapper);
	}

	
	
}
