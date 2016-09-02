package com.basicframe.sys.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.basicframe.common.service.impl.BaseServiceImpl;
import com.basicframe.sys.dao.RolePermissionsMapper;
import com.basicframe.sys.model.RolePermissions;
import com.basicframe.sys.service.IRolePermissionsService;


@Service("rolePerServ")
public class RolePermissionsServiceImpl extends BaseServiceImpl<RolePermissions> implements IRolePermissionsService {
	
	//@Resource
	//private RolePermissionsMapper rolePermissionsMapper;
	
	@Resource
	public void setMapper(RolePermissionsMapper rolePermissionsMapper){
		super.setSqlMapper(rolePermissionsMapper);
	}

	
}
