package com.basicframe.sys.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.basicframe.common.service.impl.BaseServiceImpl;
import com.basicframe.sys.dao.UserRoleMapper;
import com.basicframe.sys.model.UserRole;
import com.basicframe.sys.service.IUserRoleService;

@Service("userRoleServ")
public class UserRoleServiceImpl extends BaseServiceImpl<UserRole> implements IUserRoleService {
	
	@Resource
	public void setMapper(UserRoleMapper adminRoleMapper){
		super.setSqlMapper(adminRoleMapper);
	}
	
}
