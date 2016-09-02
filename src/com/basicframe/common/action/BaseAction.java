package com.basicframe.common.action;

import com.basicframe.common.exception.BusException;
import com.basicframe.common.exception.CoreException;
import com.basicframe.common.service.IBaseService;
import com.basicframe.utils.SystemParmDefine;
import com.basicframe.utils.page.Pagination;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Description: base action</p>
 *
 * <p>Copyright: Copyright (c) 2011</p>
 *
 * <p>Company: </p>
 *
 * @author 唐颖杰
 * @version 1.0
 */
public class BaseAction {

	//private Log logger = LogFactory.getLog(BaseAction.class);
	
	public static final String VIEW = "view";
	public static final String LIST = "list";
	public static final String STATUS = "status";
	public static final String WARN = "warn";
	public static final String SUCCESS = "success";
	public static final String ERROR = "error";
	public static final String MESSAGE = "message";
	public static final String CONTENT = "content";
	
	
	/**
	 * 重定向跳转
	 * 
	 * @param url
	 * 			需要跳转的url
	 * @return 	跳转到url
	 * @author 唐颖杰
	 * @date 2011-8-21
	 * @modify
	 */
	protected String redirectReturn(String url){
		return "redirect:" + url;
	}
	
	/**
	 * 直接跳转(不跳转到任何处理成功页面)
	 * 
	 * @param url
	 * 			需要跳转的地址
	 * @return 	跳转到指定的地址
	 * @author 唐颖杰
	 * @date Oct 14, 2010
	 * @modify
	 */
	protected String directReturn(String url){
		return url;
	}
	
	/**
	 * 成功返回
	 * 
	 * @param desc
	 * 			描述信息
	 * @param url
	 * 			点击确认按钮后跳转的地址
	 * @param request
	 * @author 唐颖杰
	 * @date Feb 28, 2011
	 * @modify
	 */
	protected String successReturn(String desc, String url, HttpServletRequest request){
		request.setAttribute("Msg", desc);
		request.setAttribute("Url", url);
		return "/system/tip.jsp";
	}
	
	/**
	 * 错误返回
	 * 
	 * @param logger 
	 * 			文本日志
	 * @param e 
	 * 			异常信息
	 * @param url
	 * 			点击确认按钮后跳转的地址
	 * @param request
	 * 			http请求
	 * @author 唐颖杰
	 * @date 2011-8-21
	 * @modify
	 */
	protected String errorReturn(Log logger, Exception e, String url, HttpServletRequest request){
		logger.error(e);
		e.printStackTrace();
		//获取异常信息
		String errorInfo = "";
		if(e instanceof BusException){
			BusException ex = (BusException)e;
			if(ex!=null){
				errorInfo = ex.getExceptionCode();
			}
		}else{
			errorInfo = BusException.SYSTEM_ERROR;
		}
		request.setAttribute("Msg", errorInfo);
		request.setAttribute("Url", url);
		return "/system/tip.jsp";
	}
	
	protected String setErrorReturn(Log logger, Exception e, String url, HttpServletRequest request){
		logger.error(e);
		e.printStackTrace();
		//获取异常信息
		String errorInfo = "";
		if(e instanceof BusException){
			BusException ex = (BusException)e;
			if(ex!=null){
				errorInfo = ex.getExceptionCode();
			}
		}else{
			errorInfo = BusException.SYSTEM_ERROR;
		}
		request.setAttribute("Msg", errorInfo);
		return url;
	}
	
	/**
	 * 处理成功后返回（AJAX返回）
	 * 
	 * @param promptCode 
	 * 				描述代码
	 * @author 唐颖杰
	 * @date：2011-8-21
	 * @modify：
	 */
	public void succAjaxReturn(String promptCode, HttpServletResponse response){
		ajaxJsonSuccessMessage(promptCode, response);
	}
	
	/**
	 * AJAX 异常处理（jquery json,post提交方式异常）
	 * 
	 * @param log
	 * 			文本日志
	 * @param e
	 * 			异常信息
	 * @param response
	 * 			http请求
	 * @author 唐颖杰
	 * @date：2011-8-21
	 * @modify：
	 */
	public void errAjaxReturn(Log log ,Exception e, HttpServletResponse response) {
		log.error(e);
		e.printStackTrace();
		String message = "";
		if (e instanceof BusException) {
			BusException be = (BusException) e;
			message = be.getExceptionCode();
		} else if(e instanceof CoreException){ 
			CoreException be = (CoreException) e;
			message = be.getExceptionCode();			
		} else {
			message = SystemParmDefine.SYS_ERROR;
		}
		ajaxJsonErrorMessage(message, response);
	}
	
	/**
	 * 分页查询
	 * @param map 
	 * 			分页条件参数
	 * @param ibServ
	 * 			需要分页的service接口
	 * @param currentPage
	 * 			当前页
	 * @param pageSize
	 * 			每页显示的条数
	 * @return  Pagination分页对象
	 * @author  唐颖杰
	 * @date： 2011-8-20
	 * @modify：
	 */
	public Pagination findPageList(Map<String, Object> map, IBaseService<?> ibServ, String currentPage, int pageSize) {
		Pagination pagination = null;
		//当前页如果为空时，设置默认值为1
		int page = (currentPage == null || "".equals(currentPage)) ? 1 : Integer.parseInt(currentPage);
		//获取总行数
		int totalRows = ibServ.queryTotalRows(map);
		//计算总页数
		int totalPage = 1;
		if (totalRows < pageSize) {
			totalPage = 1;
		} else {
			if (totalRows % pageSize == 0) {
				totalPage = totalRows / pageSize;
			} else {
				totalPage = totalRows / pageSize + 1;
			}
		}
		// 计算查询开始数据下标
		Long startRow = null;
		if(page <= 0){
			startRow = 0l;
		} else if(page > totalPage){
			startRow = Long.valueOf((totalPage-1) * pageSize);
		}else{
			startRow = Long.valueOf((page - 1) * pageSize);
		}
		map.put("startRow", startRow + 1);
		map.put("endRow", startRow + pageSize);
		//获取数据
		List<?> list = ibServ.queryPageList(map);
		//生成分页算法
		pagination = new Pagination(page, totalRows, pageSize, list);
		return pagination;
	}
	
	// AJAX输出，返回null
	public String ajax(String content, String type, HttpServletResponse response) {
		try {
			response.setContentType(type + ";charset=UTF-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.getWriter().write(content);
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	// AJAX输出文本，返回null
	public String ajaxText(String text, HttpServletResponse response) {
		return ajax(text, "text/plain", response);
	}

	// AJAX输出HTML，返回null
	public String ajaxHtml(String html, HttpServletResponse response) {
		return ajax(html, "text/html", response);
	}

	// AJAX输出XML，返回null
	public String ajaxXml(String xml, HttpServletResponse response) {
		return ajax(xml, "text/xml", response);
	}

	// 根据字符串输出JSON，返回null
	public String ajaxJson(String jsonString, HttpServletResponse response) {
		return ajax(jsonString, "text/html", response);
	}
	
	// 根据Map输出JSON，返回null
	public String ajaxJson(Map<String, String> jsonMap, HttpServletResponse response) {
		JSONObject jsonObject = JSONObject.fromObject(jsonMap);
		return ajax(jsonObject.toString(), "text/html", response);
	}
	
	// 根据Object输出JSON，返回null
	public String ajaxJson(Object obj, HttpServletResponse response) {
		JSONObject jsonObject = JSONObject.fromObject(obj);
		return ajax(jsonObject.toString(), "text/html", response);
	}
	
	// 根据Object输出JSON，返回null
	public String ajaxJsonList(Object obj, HttpServletResponse response) {
		JSONArray jsonObject = JSONArray.fromObject(obj);
		System.out.println(jsonObject.toString());
		return ajax(jsonObject.toString(), "text/html", response);
	}
	
	// 输出JSON警告消息，返回null
	public String ajaxJsonWarnMessage(String message, HttpServletResponse response) {
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put(STATUS, WARN);
		jsonMap.put(MESSAGE, message);
		JSONObject jsonObject = JSONObject.fromObject(jsonMap);
		return ajax(jsonObject.toString(), "text/html", response);
	}
	
	// 输出JSON成功消息，返回null
	public String ajaxJsonSuccessMessage(String message, HttpServletResponse response) {
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put(STATUS, SUCCESS);
		jsonMap.put(MESSAGE, message);
		JSONObject jsonObject = JSONObject.fromObject(jsonMap);
		return ajax(jsonObject.toString(), "text/html", response);
	}
	
	// 输出JSON错误消息，返回null
	public String ajaxJsonErrorMessage(String message, HttpServletResponse response) {
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put(STATUS, ERROR);
		jsonMap.put(MESSAGE, message);
		JSONObject jsonObject = JSONObject.fromObject(jsonMap);
		return ajax(jsonObject.toString(), "text/html", response);
	}
	
	// 设置页面不缓存
	public void setResponseNoCache(HttpServletResponse response) {
		response.setHeader("progma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Cache-Control", "no-store");
		response.setDateHeader("Expires", 0);
	}
	
}
