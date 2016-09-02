package com.basicframe.sys.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.basicframe.common.exception.BusException;
import com.basicframe.common.service.impl.BaseServiceImpl;
import com.basicframe.sys.dao.PermissionsMapper;
import com.basicframe.sys.dao.RolePermissionsMapper;
import com.basicframe.sys.model.OperatorLog;
import com.basicframe.sys.model.Permissions;
import com.basicframe.sys.service.IPermissionsService;


@Service("perServ")
public class PermissionsServiceImpl extends BaseServiceImpl<Permissions> implements IPermissionsService {
	
	@Resource
	private PermissionsMapper permissionsMapper;
	
	@Resource
	private RolePermissionsMapper rolePermissionsMapper;
	
	@Resource
	public void setMapper(PermissionsMapper permissionsMapper){
		super.setSqlMapper(permissionsMapper);
	}

	public List<Permissions> queryUserPermissions(int userId) {
		return permissionsMapper.selectUserPermissions(userId);
	}

	public void remove(int perId, OperatorLog log) throws BusException {
		permissionsMapper.delete(perId);
		rolePermissionsMapper.deleteById(perId);
	}

}
