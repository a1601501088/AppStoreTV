package com.basicframe.sys.service;

import com.basicframe.common.exception.BusException;
import com.basicframe.common.service.IBaseService;
import com.basicframe.sys.model.LoginLog;

/**
 * <p>Description: 登录日志接口</p>
 *
 * <p>Copyright: Copyright (c) 2011</p>
 *
 * <p>Company: xmp</p>
 *
 * @author 唐颖杰
 * @version 1.0
 */
public interface ILoginLogService extends IBaseService<LoginLog> {
	
	
	
	/**
	 * 创建登陆日志
	 * 
	 * @param vo
	 * 			登陆日志VO
	 * @throws BusException
	 * 			业务异常
	 * @author 唐颖杰
	 * @date： 2011-8-20
	 * @modify：
	 */
	public void createLog(LoginLog vo) throws BusException ;
	
	/**
	 * 清除登录日志
	 * 
	 * @author tyj
	 * @throws BusException
	 * @date Dec 31, 2010
	 * @modify
	 */
	public void truncateLoginLog() throws BusException ;
	
}
