package com.basicframe.sys.service;

import java.util.List;

import com.basicframe.common.service.IBaseService;
import com.basicframe.sys.model.Permissions;

/**
 * <p>Description: 权限接口</p>
 *
 * <p>Copyright: Copyright (c) 2011</p>
 *
 * <p>Company: </p>
 *
 * @author 唐颖杰
 * @version 1.0
 */
public interface IPermissionsService extends IBaseService<Permissions> {
	
	/**
	 * 查询用户权限
	 * 
	 * @param userID 
	 * 			用户ID
	 * @return list 权限列表
	 * @author 唐颖杰
	 * @date 2011-8-22
	 * @modify
	 */
	public List<Permissions> queryUserPermissions(int userId);
	
}
