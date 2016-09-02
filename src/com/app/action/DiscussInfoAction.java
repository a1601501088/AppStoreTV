package com.app.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.service.IAppInfoService;
import com.app.service.IDiscussInfoService;
import com.basicframe.common.action.BaseAction;
import com.basicframe.common.exception.BusException;

/**
 * <p>Description: 评论action</p>
 *
 * <p>Copyright: Copyright (c) 2015</p>
 *
 * <p>Company: </p>
 *
 * @author 唐颖杰
 * @version 1.0
 */
@Controller
public class DiscussInfoAction extends BaseAction {
	
	@Resource
	private IDiscussInfoService discussInfoServ;
	
	@Resource
	private IAppInfoService appInfoServ;
	
	
	 /** 提供给前台用的接口
	 * @author tyj
	 * @param request
	 * @param response
	 * @date Mar 5, 2015
	 * @modify
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/app/discuss/add")
	public void interfaceAddDiscussInfo(HttpServletRequest request, HttpServletResponse response){
		JSONObject json = new JSONObject();
		Map<String, String> map = new HashMap<String, String>(); 
		try {
			String appId = request.getParameter("appId");
			String appLevel = request.getParameter("appLevel");
			discussInfoServ.create(appId, appLevel);
			map.put("code", "200");
			map.put("message", "评论成功");
		} catch (BusException e){
			map.put("code", "400");
			map.put("message", e.getExceptionCode());
		} catch (Exception e) {
			map.put("code", "500");
			map.put("message", "查询错误");
		}
		json.put("meta", map);
		//绑定到页面
		ajaxJson(json, response);
	}
	
	
}
