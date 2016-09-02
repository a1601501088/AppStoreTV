package com.app.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.model.AppMsg;
import com.app.service.IAppMsgService;
import com.basicframe.common.action.BaseAction;
import com.basicframe.common.log.LogGPUtil;
import com.basicframe.sys.model.OperatorLog;
import com.basicframe.utils.DateTool;
import com.basicframe.utils.PromptUtil;
import com.basicframe.utils.ToolBox;
import com.basicframe.utils.page.Pagination;

/**
 * <p>Description: 推送消息action</p>
 *
 * <p>Copyright: Copyright (c) 2015</p>
 *
 * <p>Company: </p>
 *
 * @author 唐颖杰
 * @version 1.0
 */
@Controller
public class AppMsgAction extends BaseAction {
	
	private Log logger = LogFactory.getLog(AppMsgAction.class);
	
	@Resource
	private IAppMsgService appMsgServ;
	
	
	/**
	 * 角色列表
	 * @author tyj
	 * @param page 当前页
	 * @param request
	 * @return 返回到app_list.jsp
	 * @date Feb 24, 2011
	 * @modify
	 */
	@RequestMapping("/system/app/appMsg_list")
	public String findList(String page, HttpServletRequest request){
		try {
			String msgName = request.getParameter("msgName");
			String currentPage = request.getParameter("page");
			//参数
			Map<String, Object> map = new HashMap<String, Object>();
			if(msgName != null && msgName != ""){
				map.put("msgName", "%"+msgName+"%");
			}
			//构建分页对象
			Pagination pagination = findPageList(map, appMsgServ, currentPage, 15);
			//绑定到页面
			request.setAttribute("pagination", pagination);
			request.setAttribute("msgName", msgName);
		} catch (Exception e) {
			//业务异常、系统异常包装错误返回
			return errorReturn(logger, e, "/system/main.jsp", request);
		}
		return directReturn("/system/app/appMsg_list.jsp");
	}
	
	/**
	 * 添加
	 * @author tyj
	 * @param request
	 * @date Mar 5, 2011
	 * @modify
	 */
	@RequestMapping("/system/app/appMsg_add")
	public String add(HttpServletRequest request){
		try {
			//页面获取参数
			String msgName = request.getParameter("amsgName");
			ToolBox.busExcep(msgName);
			//对象附值
			AppMsg msg = new AppMsg();
			msg.setMsgName(msgName);
			msg.setCreateTime(DateTool.instance.getCurrentDateString());
			//操作值
			String operatorValue = "消息："+msg.getMsgName()+"；时间："+msg.getCreateTime();
			//操作动作
			String operatorAction = "新增推送消息";
			//构建日志
			OperatorLog log = LogGPUtil.buildLog(request, operatorValue, operatorAction);
			//添加
			appMsgServ.create(msg, log);
		} catch (Exception e) {
			//业务异常、系统异常包装错误返回
			return errorReturn(logger, e, "/system/app/appMsg_list.do", request);
		}
		return successReturn(PromptUtil.ADD_SUCCESS, "/system/app/appMsg_list.do", request);
	}
	
	/**
	 * 显示角色信息
	 * @author tyj
	 * @param msgId
	 * @param request
	 * @param response
	 * @date Mar 5, 2011
	 * @modify
	 */
	@RequestMapping("/system/app/appMsg_view")
	public void findRoleById(int msgId, HttpServletRequest request , HttpServletResponse response){
		try {
			//查询
			AppMsg msg = appMsgServ.queryById(msgId);
			//绑定到页面
			ajaxJson(msg, response);
		} catch (Exception e) {
			errorReturn(logger, e, "/system/app/appMsg_list.do", request);
		}
	}
	
	/**
	 * 修改
	 * @author tyj
	 * @param msgId 
	 * @param request
	 * @date Mar 5, 2011
	 * @modify
	 */
	@RequestMapping("/system/app/appMsg_edit")
	public String edit(int msgId, HttpServletRequest request){
		try {
			//页面获取参数
			String msgName = request.getParameter("msgName");
			ToolBox.busExcep(msgName);
			//对象附值
			AppMsg msg = new AppMsg();
			msg.setMsgId(msgId);
			msg.setMsgName(msgName);
			//操作值
			String operatorValue = "id："+msg.getMsgId()+"；消息："+msg.getMsgName()+"；时间："+msg.getCreateTime();
			//操作动作
			String operatorAction = "更新推送消息";
			//构建日志
			OperatorLog log = LogGPUtil.buildLog(request, operatorValue, operatorAction);
			//更新
			appMsgServ.modify(msg, log);
		} catch (Exception e) {
			//业务异常、系统异常包装错误返回
			return errorReturn(logger, e, "/system/app/appMsg_list.do", request);
		}
		return successReturn(PromptUtil.EDIT_SUCCESS, "/system/app/appMsg_list.do", request);
	}
	
	/**
	 * 删除
	 * @author tyj
	 * @param request
	 * @date Mar 8, 2015
	 * @modify
	 */
	@RequestMapping("/system/app/appMsg_delete")
	public String delete(int msgId, String page, HttpServletRequest request){
		try {
			//查询
			AppMsg msg = appMsgServ.queryById(msgId);
			ToolBox.isNull(msg);
			//操作值
			String operatorValue = "id："+msg.getMsgId()+"；消息："+msg.getMsgName()+"；时间："+msg.getCreateTime();
			//操作动作
			String operatorAction = "删除推送消息";
			//构建日志
			OperatorLog log = LogGPUtil.buildLog(request, operatorValue, operatorAction);
			//删除
			appMsgServ.remove(msgId, log);
		} catch (Exception e) {
			//业务异常、系统异常包装错误返回
			return errorReturn(logger, e, "/system/app/appMsg_list.do", request);
		}
		return successReturn(PromptUtil.DELETE_SUCCESS, "/system/app/appMsg_list.do", request);
	}
	
	
}
