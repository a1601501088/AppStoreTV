package com.app.dao;

import com.app.model.AppPackge;
import com.app.model.PackgeInfo;
import com.basicframe.common.dao.SqlMapper;

public interface AppPackgeMapper extends SqlMapper<AppPackge>{

	PackgeInfo selectByAppId(String appId);
	
}
