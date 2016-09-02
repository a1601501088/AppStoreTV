package com.basicframe.common.log;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.basicframe.common.exception.BusException;
import com.basicframe.sys.dao.LoginLogMapper;
import com.basicframe.sys.dao.OperatorLogMapper;
import com.basicframe.sys.model.LoginLog;
import com.basicframe.sys.model.OperatorLog;
import com.basicframe.sys.model.User;
import com.basicframe.sys.service.ILoginLogService;
import com.basicframe.sys.service.IOperatorLogService;
import com.basicframe.utils.DateTool;

/**
 * <p>Description: 日志工具类</p>
 *
 * <p>Copyright: Copyright (c) 2011</p>
 *
 * <p>Company: xmp</p>
 *
 * @author 唐颖杰
 * @version 1.0
 */
@Component("logGPUtil")
public class LogGPUtil {
	
	private Log logger = LogFactory.getLog(LogGPUtil.class);
	
	@Resource
	private OperatorLogMapper operaotrLogMapper;
	@Resource
	private LoginLogMapper loginLogMapper;
	@Resource
	private ILoginLogService loginLogServ;
	@Resource
	private IOperatorLogService operatorLogServ;
	
	/**
	 * 日志入库。在同一个事务中。
	 * @param vo日志对象
	 * @throws BusException
	 */
	public void createLog(OperatorLog log) throws BusException {
		createLog(log, true);
	}
	
	/**
	 * 登录日志入库。不在同一个事务中。
	 * @param vo日志对象
	 * @throws BusException
	 */
	public void createLog(LoginLog log) throws BusException {
		createLog(log, false);
	}

	/**
	 * 
	 * @param vo
	 * @param isSynchronized
	 *    日志保存是否在业务事务中.true在同一个事务中，否则不同
	 *    在同一个事务中，日志必须和业务事务一起提交。对于那些日志和业务严格要求得情况下，可以采用此法
	 * @throws BusException
	 */
	public void createLog(OperatorLog log, boolean isSynchronized) throws BusException {
		if (isSynchronized) {
			try {
				//跟业务一起提交
				operaotrLogMapper.insert(log);
			} catch (Exception e) {
				logger.error(e);
				BusException.handleException(e);
			}
		} else {
			try {
				//单独提交
				operatorLogServ.createLog(log);
			} catch (Exception e) {
				logger.error(e);
				BusException.handleException(e);
			}
		}
	}
	
	/**
	 * 
	 * @param vo
	 * @param isSynchronized
	 *    日志保存是否在业务事务中.true在同一个事务中，否则不同
	 *    在同一个事务中，日志必须和业务事务一起提交。对于那些日志和业务严格要求得情况下，可以采用此法
	 * @throws BusException
	 */
	public void createLog(LoginLog log, boolean isSynchronized) throws BusException {
		if (isSynchronized) {
			try {
				//跟业务一起提交
				loginLogMapper.insert(log);
			} catch (Exception e) {
				logger.error(e);
				BusException.handleException(e);
			}
		} else {
			try {
				//单独提交
				loginLogServ.createLog(log);
			} catch (Exception e) {
				logger.error(e);
				BusException.handleException(e);
			}
		}
	}
	
	/**
	 * 构建日志
	 * 
	 * @param request
	 * 				http请求
	 * @param operatorValue
	 * 				操作的值
	 * @return 操作日志实体对象
	 * @throws BusException 业务异常
	 * @author 唐颖杰
	 * @date： 2011-8-20
	 * @modify：
	 */
	public static OperatorLog buildLog(HttpServletRequest request, String operatorValue, String operatorAction) throws BusException {
		OperatorLog log = new OperatorLog();
		User super_user = (User)request.getSession().getAttribute("super_user");
		log.setOperatorID(String.valueOf(super_user.getUserId()));
		log.setOperatorName(super_user.getUserName());
		log.setOperatorAction(operatorAction);
		log.setOperatorValue(operatorValue);
		log.setOperatorType("1");  //此处记录操作人是管理员还是普通用户
		log.setLogType("1");       //此处记录的是日志类型,是正常日志还是异常日志
		log.setOperatorTime(DateTool.instance.getCurrentDateString());
		log.setOperatorIP(request.getRemoteAddr());
		log.setOperatorRemark("");
		return log;
	}

}
