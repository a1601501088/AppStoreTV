package com.basicframe.sys.service;

import com.basicframe.common.exception.BusException;
import com.basicframe.common.service.IBaseService;
import com.basicframe.sys.model.OperatorLog;

/**
 * <p>Description: 操作日志接口</p>
 *
 * <p>Copyright: Copyright (c) 2011</p>
 *
 * <p>Company: xmp</p>
 *
 * @author 唐颖杰
 * @version 1.0
 */
public interface IOperatorLogService extends IBaseService<OperatorLog> {
	
	/**
	 * 创建操作日志
	 * @param vo
	 * 			操作日志VO
	 * @throws BusException
	 * 			业务异常
	 * @author 唐颖杰
	 * @date： 2011-8-20
	 * @modify：
	 */
	public void createLog(OperatorLog vo) throws BusException ;

}
