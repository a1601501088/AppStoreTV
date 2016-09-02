package com.basicframe.sys.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.basicframe.common.action.BaseAction;
import com.basicframe.sys.service.IOperatorLogService;
import com.basicframe.utils.page.Pagination;


/**
 * <p>Description: 操作日志action</p>
 *
 * <p>Copyright: Copyright (c) 2011</p>
 *
 * <p>Company: </p>
 *
 * @author 唐颖杰
 * @version 1.0
 */
@Controller
public class OperatorLogAction extends BaseAction {
	
	private Log logger = LogFactory.getLog(OperatorLogAction.class);
	
	@Resource
	private IOperatorLogService operatorServ;
	
	/**
	 * 操作日志分页查询
	 * @author tyj
	 * @param page 当前页
	 * @param request 
	 * @return String
	 * @date Oct 28, 2010
	 * @modify
	 */
	@RequestMapping("/system/log/operatorLog_list")
	public String findOperatorLogList(String page, HttpServletRequest request){
		try {
			//分页查询
			String currentPage = request.getParameter("page");
			String operatorName = request.getParameter("operatorName");
			String operatorAction = request.getParameter("operatorAction");
			//参数
			Map<String, Object> map = new HashMap<String, Object>();
			if(operatorName != null && operatorName != ""){
				map.put("operatorName", "%"+operatorName+"%");
			}
			if(operatorAction != null && operatorAction != ""){
				map.put("operatorAction", "%"+operatorAction+"%");
			}
			//构建分页对象
			Pagination pagination = findPageList(map, operatorServ, currentPage, 15);
			//绑定到页面
			request.setAttribute("pagination", pagination);
			request.setAttribute("operatorName", operatorName);
			request.setAttribute("operatorAction", operatorAction);
		} catch (Exception e) {
			//业务异常、系统异常包装错误返回
			return errorReturn(logger, e, "/system/main.jsp", request);
		}
		return directReturn("operatorLog_list.jsp");
	}

}
