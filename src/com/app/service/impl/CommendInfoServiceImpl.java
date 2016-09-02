package com.app.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.app.dao.CommendInfoMapper;
import com.app.model.CommendInfo;
import com.app.service.ICommendInfoService;
import com.basicframe.common.service.impl.BaseServiceImpl;


@Service("commendInfoServ")
public class CommendInfoServiceImpl extends BaseServiceImpl<CommendInfo> implements ICommendInfoService {

	//@Resource
	//private CommendInfoMapper commendInfoMapper;
	
	@Resource
	public void setMapper(CommendInfoMapper commendInfoMapper){
		super.setSqlMapper(commendInfoMapper);
	}
	
}
