package com.app.service.impl;

import com.app.dao.AppClassMapper;
import com.app.model.AppClass;
import com.app.service.IAppClassService;
import com.basicframe.common.dao.SqlMapper;
import com.basicframe.common.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service("appClassServ")
public class AppClassServiceImpl extends BaseServiceImpl<AppClass> implements IAppClassService {

	//@Resource
	//private AppClassMapper appClassMapper;


	@Resource
	public void setMapper(AppClassMapper appClassMapper){
		super.setSqlMapper(appClassMapper);
	}
}
