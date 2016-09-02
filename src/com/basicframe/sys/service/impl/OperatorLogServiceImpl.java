package com.basicframe.sys.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.basicframe.common.exception.BusException;
import com.basicframe.common.service.impl.BaseServiceImpl;
import com.basicframe.sys.dao.OperatorLogMapper;
import com.basicframe.sys.model.OperatorLog;
import com.basicframe.sys.service.IOperatorLogService;
import com.basicframe.utils.ToolBox;

@Service("operatorLogServ")
public class OperatorLogServiceImpl extends BaseServiceImpl<OperatorLog> implements IOperatorLogService {

	@Resource
	private OperatorLogMapper operatorLogMapper;
	
	@Resource
	public void setMapper(OperatorLogMapper operatorLogMapper){
		super.setSqlMapper(operatorLogMapper);
	}
	
	public void createLog(OperatorLog vo) throws BusException {
		//检验参数是否为空
		ToolBox.isNull(vo);
		ToolBox.isNull(vo.getOperatorID());
		ToolBox.isNull(vo.getOperatorName());
		try {
			//创建操作日志
			operatorLogMapper.insert(vo);
		} catch (Exception e) {
			//日志,异常外理
			getLogger().error(e);
			BusException.handleException(e);
		}
	}
	
}
