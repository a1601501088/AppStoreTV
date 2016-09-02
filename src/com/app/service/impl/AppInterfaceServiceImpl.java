package com.app.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.app.dao.AppInterfaceMapper;
import com.app.model.AppInterface;
import com.app.service.IAppInterfaceService;
import com.basicframe.common.service.impl.BaseServiceImpl;


@Service("appInterfaceServ")
public class AppInterfaceServiceImpl extends BaseServiceImpl<AppInterface> implements IAppInterfaceService {

	@Resource
	public void setMapper(AppInterfaceMapper appInterfaceMapper){
		super.setSqlMapper(appInterfaceMapper);
	}
	
}
