package com.app.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.app.dao.AppMsgMapper;
import com.app.model.AppMsg;
import com.app.service.IAppMsgService;
import com.basicframe.common.service.impl.BaseServiceImpl;


@Service("appMsgServ")
public class AppMsgServiceImpl extends BaseServiceImpl<AppMsg> implements IAppMsgService {

	//@Resource
	//private AppMsgMapper appMsgMapper;
	
	@Resource
	public void setMapper(AppMsgMapper appMsgMapper){
		super.setSqlMapper(appMsgMapper);
	}
	
}
