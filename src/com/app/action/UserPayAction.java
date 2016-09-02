package com.app.action;

import com.app.service.IUserPay;
import com.basicframe.common.action.BaseAction;
import com.basicframe.utils.page.Pagination;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;


@Controller
public class UserPayAction extends BaseAction{
	private Logger logger = Logger.getLogger("log");
	private Log log = LogFactory.getLog(UserPayAction.class);
	@Resource
	private IUserPay userPayServ ;

	@RequestMapping("/system/app/userpay_list")
	public String findList(HttpServletRequest request){
		try {
			String userId = request.getParameter("userId");
			String currentPage = request.getParameter("page");
			//参数
			Map<String, Object> map = new HashMap<String, Object>();
			if(userId != null && !"".equals(userId)){
				map.put("userId", "%"+userId+"%");
			}
			//构建分页对象
			Pagination pagination = findPageList(map, userPayServ, currentPage, 15);
			//绑定到页面
			request.setAttribute("pagination", pagination);
			request.setAttribute("userId", userId);
		} catch (Exception e) {
			//业务异常、系统异常包装错误返回
			return errorReturn(log, e, "/system/main.jsp", request);
		}
		return directReturn("/system/app/userpay_list.jsp");
	}
	
	
}
