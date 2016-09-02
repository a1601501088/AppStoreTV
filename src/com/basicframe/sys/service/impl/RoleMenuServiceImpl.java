package com.basicframe.sys.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.basicframe.common.service.impl.BaseServiceImpl;
import com.basicframe.sys.dao.RoleMenuMapper;
import com.basicframe.sys.model.RoleMenu;
import com.basicframe.sys.service.IRoleMenuService;

@Service("roleMenuServ")
public class RoleMenuServiceImpl extends BaseServiceImpl<RoleMenu> implements IRoleMenuService {
	
	//@Resource
	//private RoleMenuMapper roleMenuMapper;
	
	@Resource
	public void setMapper(RoleMenuMapper roleMenuMapper){
		super.setSqlMapper(roleMenuMapper);
	}
	
	
	
	
}
