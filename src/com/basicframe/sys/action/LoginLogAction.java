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
import com.basicframe.sys.service.ILoginLogService;
import com.basicframe.utils.page.Pagination;

/**
 * <p>Description: 登录日志action</p>
 *
 * <p>Copyright: Copyright (c) 2011</p>
 *
 * <p>Company: </p>
 *
 * @author 唐颖杰
 * @version 1.0
 */
@Controller
public class LoginLogAction extends BaseAction {
	
	
	private Log logger = LogFactory.getLog(LoginLogAction.class);
	@Resource
	private ILoginLogService loginLogServ;
	
	
	/**
	 * 登录日志
	 * @author tyj
	 * @param page
	 * @param request
	 * @return String
	 * @date Jan 3, 2011
	 * @modify
	 */
	@RequestMapping("/system/log/loginLog_list")
	public String findLoginLogList(String page, HttpServletRequest request) {
		try {
			//分页查询
			String currentPage = request.getParameter("page");
			String loginName = request.getParameter("loginName");
			String loginStatus = request.getParameter("loginStatus");
			//参数
			Map<String, Object> map = new HashMap<String, Object>();
			if(loginName != null && loginName != ""){
				map.put("loginName", "%"+loginName+"%");
			}
			if(loginStatus != null && loginStatus != ""){
				map.put("loginStatus", "%"+loginStatus+"%");
			}
			//构建分页对象
			Pagination pagination = findPageList(map, loginLogServ, currentPage, 15);
			//绑定到页面
			request.setAttribute("loginName", loginName);
			request.setAttribute("loginStatus", loginStatus);
			request.setAttribute("pagination", pagination);
		} catch (Exception e) {
			//业务异常、系统异常包装错误返回
			return errorReturn(logger, e, "/system/main.jsp", request);
		}
		return directReturn("/system/log/loginLog_list.jsp");
	}
	
	
	
}
