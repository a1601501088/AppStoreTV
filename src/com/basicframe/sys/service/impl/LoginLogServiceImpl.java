package com.basicframe.sys.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.basicframe.common.exception.BusException;
import com.basicframe.common.service.impl.BaseServiceImpl;
import com.basicframe.sys.dao.LoginLogMapper;
import com.basicframe.sys.model.LoginLog;
import com.basicframe.sys.service.ILoginLogService;
import com.basicframe.utils.ToolBox;

@Service("loginLogServ")
public class LoginLogServiceImpl extends BaseServiceImpl<LoginLog> implements ILoginLogService {
	
	@Resource
	private LoginLogMapper loginLogMapper;
	
	@Resource
	public void setMapper(LoginLogMapper loginLogMapper){
		super.setSqlMapper(loginLogMapper);
	}
	
	
	public void truncateLoginLog() throws BusException {
		// TODO Auto-generated method stub
	}
	
	public void createLog(LoginLog vo) throws BusException {
		//检验参数是否为空
		ToolBox.isNull(vo);
		try {
			//创建登录日志
			loginLogMapper.insert(vo);
		} catch (Exception e) {
			//日志,异常外理
			getLogger().error(e);
			BusException.handleException(e);
		}
	}

	
}
