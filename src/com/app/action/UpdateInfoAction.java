package com.app.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.model.UpdateInfo;
import com.app.service.IUpdateInfoService;
import com.basicframe.common.action.BaseAction;

/**
 * <p>Description: 升级action</p>
 *
 * <p>Copyright: Copyright (c) 2015</p>
 *
 * <p>Company: </p>
 *
 * @author 唐颖杰
 * @version 1.0
 */
@Controller
public class UpdateInfoAction extends BaseAction {
	
	@Resource
	private IUpdateInfoService updateInfoServ;
	
	
	 /** 提供给前台用的接口
	 * @author tyj
	 * @param request
	 * @param response
	 * @date Mar 5, 2015
	 * @modify
	 */
	@RequestMapping("/app/update/query")
	public void interfaceUpdateInfo(HttpServletRequest request, HttpServletResponse response){
		JSONObject json = new JSONObject();
		Map<String, String> map = new HashMap<String, String>(); 
		UpdateInfo info = null;
		try {
			info = updateInfoServ.queryById(1);
			map.put("code", "200");
			map.put("message", "查询成功");
		} catch (Exception e) {
			map.put("code", "500");
			map.put("message", "查询错误");
		}
		json.put("meta", map);
		json.put("data", info);
		//绑定到页面
		ajaxJson(json.toString(), response);
	}
	
	
}
