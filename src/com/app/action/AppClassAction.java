package com.app.action;

import com.app.model.AppClass;
import com.app.service.IAppClassService;
import com.basicframe.common.action.BaseAction;
import com.basicframe.common.log.LogGPUtil;
import com.basicframe.sys.model.OperatorLog;
import com.basicframe.utils.DateTool;
import com.basicframe.utils.PromptUtil;
import com.basicframe.utils.ToolBox;
import com.basicframe.utils.page.Pagination;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Description: 应用类别action</p>
 *
 * <p>Copyright: Copyright (c) 2015</p>
 *
 * <p>Company: </p>
 *
 * @author 唐颖杰
 * @version 1.0
 */
@Controller
public class AppClassAction extends BaseAction {
	
	private Log logger = LogFactory.getLog(AppClassAction.class);
	
	@Resource
	private IAppClassService appClassServ;
	
	
	/**
	 * 列表
	 * @author tyj
	 * @param page 当前页
	 * @param request
	 * @return 返回到appClass_list.jsp
	 * @date Feb 24, 2015
	 * @modify
	 */
	@RequestMapping("/system/app/appClass_list")
	public String findlList(String page, HttpServletRequest request){
		try {
			String className = request.getParameter("className");
			String currentPage = request.getParameter("page");
			//参数
			Map<String, Object> map = new HashMap<String, Object>();
			if(className != null && !"".equals(className)){
				map.put("className", "%"+className+"%");
			}
			//构建分页对象
			Pagination pagination = findPageList(map, appClassServ, currentPage, 15);
			//绑定到页面
			request.setAttribute("pagination", pagination);
			request.setAttribute("className", className);
		} catch (Exception e) {
			//业务异常、系统异常包装错误返回
			return errorReturn(logger, e, "/system/main.jsp", request);
		}
		return directReturn("/system/app/appClass_list.jsp");
	}
	
	/**
	 * 添加
	 * @author tyj
	 * @param request
	 * @date Mar 5, 2015
	 * @modify
	 */
	@RequestMapping("/system/app/appClass_add")
	public String add(HttpServletRequest request){
		try {
			AppClass appClass = new AppClass();
			//页面获取参数
			String className = request.getParameter("aclassName");
			String classOrder = request.getParameter("aclassOrder");
			//对象赋值
			appClass.setClassName(className);
			appClass.setClassOrder(Integer.parseInt(classOrder));
			appClass.setCreateTime(DateTool.instance.getCurrentDateString());
			//操作值
			String operatorValue = "类别名："+appClass.getClassName()+"；时间："+appClass.getCreateTime();
			//操作动作
			String operatorAction = "新增应用类别";
			//构建日志
			OperatorLog log = LogGPUtil.buildLog(request, operatorValue, operatorAction);
			//添加
			appClassServ.create(appClass, log);
		} catch (Exception e) {
			//业务异常、系统异常包装错误返回
			return errorReturn(logger, e, "/system/app/appClass_list.do", request);
		}
		return successReturn(PromptUtil.ADD_SUCCESS, "/system/app/appClass_list.do", request);
	}
	
	/**
	 * 显示信息
	 * @author tyj
	 * @param classId 
	 * @param request
	 * @param response
	 * @date Mar 5, 2015
	 * @modify
	 */
	@RequestMapping("/system/app/appClass_view")
	public void findById(int classId, HttpServletRequest request , HttpServletResponse response){
		try {
			//查询信息
			AppClass appClass = appClassServ.queryById(classId);
			//绑定到页面
			ajaxJson(appClass, response);
		} catch (Exception e) {
			errorReturn(logger, e, "/system/app/appClass_list.do", request);
		}
	}
	
	/**
	 * 修改角色
	 * @author tyj
	 * @param classId
	 * @param request
	 * @date Mar 5, 2011
	 * @modify
	 */
	@RequestMapping("/system/app/appClass_edit")
	public String edit(int classId, HttpServletRequest request){
		try {
			AppClass appClass = new AppClass();
			//页面获取参数
			String className = request.getParameter("className");
			String classOrder = request.getParameter("classOrder");
			//对象附值
			appClass.setClassId(classId);
			appClass.setClassName(className);
			appClass.setClassOrder(Integer.parseInt(classOrder));
			//操作值
			String operatorValue = "id："+ appClass.getClassId()+"；类别名："+appClass.getClassName()+"；时间："+appClass.getCreateTime();
			//操作动作
			String operatorAction = "更新应用类别";
			//构建日志
			OperatorLog log = LogGPUtil.buildLog(request, operatorValue, operatorAction);
			//修改
			appClassServ.modify(appClass, log);
		} catch (Exception e) {
			//业务异常、系统异常包装错误返回
			return errorReturn(logger, e, "/system/app/appClass_list.do", request);
		}
		return successReturn(PromptUtil.EDIT_SUCCESS, "/system/app/appClass_list.do", request);
	}
	
	/**
	 * 删除
	 * @author tyj
	 * @param request
	 * @date Mar 8, 2011
	 * @modify
	 */
	@RequestMapping("/system/app/appClass_delete")
	public String delete(int classId, String page, HttpServletRequest request){
		try {
			//查询角色信息
			AppClass appClass = appClassServ.queryById(classId);
			ToolBox.isNull(appClass);
			//操作值
			String operatorValue = "id："+ appClass.getClassId()+"；类别名："+appClass.getClassName()+"；时间："+appClass.getCreateTime();
			//操作动作
			String operatorAction = "删除应用类别";
			//构建日志
			OperatorLog log = LogGPUtil.buildLog(request, operatorValue, operatorAction);
			//删除
			appClassServ.remove(classId, log);
		} catch (Exception e) {
			//业务异常、系统异常包装错误返回
			return errorReturn(logger, e, "/system/app/appClass_list.do", request);
		}
		return successReturn(PromptUtil.DELETE_SUCCESS, "/system/app/appClass_list.do", request);
	}
	
	/**
	 * 查询所有
	 * @author tyj
	 * @param request
	 * @param response
	 * @date Mar 5, 2015
	 * @modify
	 */
	@RequestMapping("/system/app/appClass_all")
	public void findAll(HttpServletRequest request , HttpServletResponse response){
		try {
			//查询信息
			List<AppClass> appClass = appClassServ.queryAll();
			//绑定到页面
			ajaxJsonList(appClass, response);
		} catch (Exception e) {
			errorReturn(logger, e, "/system/app/appClass_list.do", request);
		}
	}
	
	 /** 提供给前台用的接口
	 * @author tyj
	 * @param request
	 * @param response
	 * @date Mar 5, 2015
	 * @modify
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/app/appclass/queryAll")
	public void interfaceFindAll(HttpServletRequest request , HttpServletResponse response){
		JSONObject json = new JSONObject();
		JSONArray jsonObject = null;
		Map<String, String> map = new HashMap<String, String>(); 
		try {
			//查询信息
			List<AppClass> appClass = appClassServ.queryAll();
			if(appClass != null && appClass.size() > 0){
				map.put("code", "200");
				map.put("message", "查询成功");
				jsonObject = JSONArray.fromObject(appClass);
			}else{
				map.put("code", "400");
				map.put("message", "查询失败");
			}
		} catch (Exception e) {
			map.put("code", "500");
			map.put("message", "查询错误");
		}
		json.put("meta", map);
		json.put("data", jsonObject);
		//绑定到页面
		ajaxJson(json, response);
	}
	
	
}
