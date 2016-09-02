package com.app.action.inter;

import com.app.model.AppInfo;
import com.app.service.IAppInfoService;
import com.basicframe.common.action.BaseAction;
import com.basicframe.common.exception.BusException;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class AppInfoInter extends BaseAction{

	@Resource
	private IAppInfoService appInfoServ;
	
	/**
	 * 
	 * @param json appId
	 * @param request
	 * @param response
	 * @return 对应的app信息
	 */
	@RequestMapping(value = "/queryApp",method=RequestMethod.POST)
	public void queryAppById(@RequestParam("json")String json ,HttpServletRequest request ,HttpServletResponse response){
		String code = "400";
		String message = "fail";
		AppInfo appInfo = null ;
		try {
			JSONObject jsonObj = JSONObject.fromObject(json);
			String appId = jsonObj.getString("appId");
			appInfo = appInfoServ.queryById(appId); 
			code = "200" ;
			message = "success";
		} catch (BusException e) {
			e.printStackTrace();
			code = "200" ;
			message = "success";
		}
		JSONObject result = new JSONObject();
		result.put("code", code);
		result.put("message", message);
		result.put("result", appInfo);
		ajaxJson(result.toString(), response);


	}


	/**
	 *
	 * @param json appId
	 * @param request
	 * @param response
	 * @return 对应的app信息
	 */
	@RequestMapping(value = "/app/addDownloadNumber",method=RequestMethod.POST)
	public void addDownloadNumber(@RequestParam("json")String json ,HttpServletRequest request ,HttpServletResponse response){
		String code = "400";
		String message = "fail";
		int count = 0 ;
		try {
			JSONObject jsonObj = JSONObject.fromObject(json);
			String appId = jsonObj.getString("appId");
			List<AppInfo> appInfos = appInfoServ.queryAppById(appId);
			if (appInfos.size()>0){
				count = appInfoServ.updateDownloadNmber(appInfos.get(0));
				if (count>0){
					code = "200" ;
					message = "success";
				}else {
					code = "400" ;
					message = "修改失败";
				}

			}else {
				message = "没有这个应用";
			}
		} catch (Exception e) {
			e.printStackTrace();
			code = "200" ;
			message = "success";
		}
		JSONObject result = new JSONObject();
		result.put("code", code);
		result.put("message", message);
		ajaxJson(result.toString(), response);


	}
	
}
