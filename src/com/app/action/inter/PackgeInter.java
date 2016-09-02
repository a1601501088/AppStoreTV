package com.app.action.inter;

import com.app.model.AppInfo;
import com.app.model.PackgeInfo;
import com.app.service.IAppInfoService;
import com.app.service.IPackgeInfo;
import com.basicframe.common.action.BaseAction;
import net.sf.json.JSONArray;
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
public class PackgeInter extends BaseAction{

	@Resource
	private IPackgeInfo packgeServ;
	@Resource
	private IAppInfoService appInfoServ;
	
	
	/**
	 * 查询所有专区的详细信息(包括专区中所有app信息)
	 */
	@RequestMapping(value = "/queryAllPackge",method = RequestMethod.POST)
	public void queryAll(HttpServletResponse response){
		
		List<PackgeInfo> list  = null ;
		String code = "400" ;
		String message = "fail" ;
		try {
			list =  packgeServ.queryAll();
			code = "200";
			message = "success" ;
		} catch (Exception e) {
			e.printStackTrace();
			code = "500";
			message = "error" ;
		}
		JSONObject result = new JSONObject();

        result.put("code", code);
		result.put("message", message);
		result.put("data", JSONArray.fromObject(list));
		ajaxJson(result.toString(), response);
	}
	
	/**
	 * 查询单个专区的详细信息
	 */
	@RequestMapping(value = "/queryPackgeById",method = RequestMethod.POST)
	public void queryById(@RequestParam("json")String json ,HttpServletRequest request , HttpServletResponse response){
		String code = "400" ;
		String message = "fail" ;
		JSONObject obj = JSONObject.fromObject(json);
		String packgeId = obj.getString("packgeId");
		PackgeInfo vo = null ;
        List<AppInfo> appInfos = null;
		try {
			vo =  packgeServ.queryById(packgeId);
            appInfos = appInfoServ.queryAppByPackgeName(vo.getPackgeName());
            code = "200";
			message = "success" ;
		} catch (Exception e) {
			e.printStackTrace();
			code = "500";
			message = "error" ;
		}
		JSONObject result = new JSONObject();
		result.put("code", code);
		result.put("message", message);
		result.put("result", vo);
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(appInfos);
        result.put("appInfos", jsonArray);
		ajaxJson(result.toString(), response);
	}
	
	/**
	 * 查询APP所属专区
	 */
	@RequestMapping(value = "/queryPackgeByApp",method = RequestMethod.POST)
	public void queryPackgeByApp(@RequestParam("json")String json ,HttpServletRequest request , HttpServletResponse response){
		String code = "400" ;
		String message = "fail" ;
		JSONObject obj = JSONObject.fromObject(json);
		String appId = obj.getString("appId");
		PackgeInfo vo = null ;
		try {
		    vo = packgeServ.queryByApp(appId);
			code = "200";
			message = "success" ;
		} catch (Exception e) {
			e.printStackTrace();
			code = "500";
			message = "error" ;
		}
		JSONObject result = new JSONObject();
		result.put("code", code);
		result.put("message", message);
		result.put("result", vo);
		ajaxJson(result.toString(), response);
	}
}
