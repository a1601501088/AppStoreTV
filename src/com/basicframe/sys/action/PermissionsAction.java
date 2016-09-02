package com.basicframe.sys.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.basicframe.common.action.BaseAction;
import com.basicframe.common.exception.BusException;
import com.basicframe.common.log.LogGPUtil;
import com.basicframe.sys.model.Menu;
import com.basicframe.sys.model.OperatorLog;
import com.basicframe.sys.model.Permissions;
import com.basicframe.sys.service.IMenuService;
import com.basicframe.sys.service.IPermissionsService;
import com.basicframe.utils.PromptUtil;
import com.basicframe.utils.page.Pagination;


/**
 * <p>Description: 权限action</p>
 *
 * <p>Copyright: Copyright (c) 2011</p>
 *
 * <p>Company: </p>
 *
 * @author 唐颖杰
 * @version 1.0
 */
@Controller
public class PermissionsAction extends BaseAction {
	
	private Log logger = LogFactory.getLog(UserAction.class);
	
	@Resource
	private IPermissionsService perServ;
	@Resource
	private IMenuService menuServ;
	
	
	/**
	 * 跳转到per.jsp页面
	 * @author tyj
	 * @param request
	 * @return String
	 * @date Oct 22, 2010
	 * @modify
	 */
	@RequestMapping("/system/permission/per")
	public String findMenuPage(HttpServletRequest request){
		return directReturn("/system/permission/per.jsp");
	}
	
	/**
	 * 权限列表
	 * @author tyj
	 * @param page
	 * @param request
	 * @return per_list.jsp
	 * @date Jan 4, 2011
	 * @modify
	 */
	@RequestMapping("/system/permission/per_list")
	public String findPermissionList(String page, HttpServletRequest request){
		try {
			//获取参数
			String menuId = request.getParameter("menuId");
			//自定义查询
			String currentPage = request.getParameter("page");
			//参数
			Map<String, Object> map = new HashMap<String, Object>();
			if(menuId != null && menuId != ""){
				map.put("menuId", menuId);
			}
			//构建分页对象
			Pagination pagination = findPageList(map, perServ, currentPage, 15);
			//绑定到页面
			request.setAttribute("menuId", menuId);
			request.setAttribute("pagination", pagination);
		} catch (Exception e) {
			//业务异常、系统异常包装错误返回
			return errorReturn(logger, e, "/system/index.jsp", request);
		}
		return directReturn("/system/permission/per_list.jsp");
	}
	
	/**
	 * 显示权限信息
	 * @author tyj
	 * @param id 权限ID
	 * @param request
	 * @param response
	 * @date Mar 5, 2011
	 * @modify
	 */
	@RequestMapping("/system/permission/per_view")
	public void findPermissionById(int id, HttpServletRequest request, HttpServletResponse response){
		Permissions per = null;
		try {
			//查询权限信息
			per = perServ.queryById(id);
			//绑定到页面
			ajaxJson(per, response);
		} catch (Exception e) {
			errorReturn(logger, e, "/system/role/role_list.do", request);
		}
	}
	
	/**
	 * 修改权限
	 * @author tyj
	 * @param id 权限ID
	 * @param request
	 * @date Mar 5, 2011
	 * @modify
	 */
	@RequestMapping("/system/permission/per_edit")
	public String editPermissions(int id, HttpServletRequest request){
		Permissions vo = new Permissions();
		//页面获取参数
		String perName = request.getParameter("e_perName");
		String perAction = request.getParameter("e_perAction");
		String perRemark = request.getParameter("e_perRemark");
		String menuId = request.getParameter("menuId");
		try {
			//对象附值
			vo.setPerId(id);
			vo.setPerName(perName);
			vo.setPerAction(perAction);
			vo.setPerRemark(perRemark);
			//操作值
			String operatorValue = "权限名:"+perName+";权限动作:"+perAction+";菜单ID："+menuId;
			//操作动作
			String operatorAction = "修改权限";
			//构建日志
			OperatorLog log = LogGPUtil.buildLog(request, operatorValue, operatorAction);
			//修改权限
			perServ.modify(vo, log);
		} catch (Exception e) {
			//业务异常、系统异常包装错误返回
			return errorReturn(logger, e, "/system/permission/per_list.do?menuId="+menuId+"", request);
		}
		return successReturn(PromptUtil.EDIT_SUCCESS, "/system/permission/per_list.do?menuId="+menuId+"", request);
	}
	
	/**
	 * 增加权限
	 * @author tyj
	 * @param request
	 * @date Mar 5, 2011
	 * @modify
	 */
	@RequestMapping("/system/permission/per_add")
	public String addPermissions(HttpServletRequest request){
		Permissions vo = new Permissions();
		//页面获取参数
		String perName = request.getParameter("perName");
		String perAction = request.getParameter("perAction");
		String perRemark = request.getParameter("perRemark");
		String menuId = request.getParameter("menuId");
		try {
			if(menuId == null || menuId == ""){
				throw new BusException(BusException.PERMISSIONS_NOT_ADD);
			}
			if(perName == null || perName == ""){
				throw new BusException(BusException.PERNAME_NOT_NULL);
			}
			if(perAction == null || perAction == ""){
				throw new BusException(BusException.PERACTION_NOT_NULL);
			}
			//对象附值
			vo.setPerName(perName);
			vo.setPerAction(perAction);
			vo.setPerRemark(perRemark);
			vo.setMenuId(menuId);
			//操作值
			String operatorValue = "权限名:"+perName+";权限动作:"+perAction+";菜单ID："+menuId;
			//操作动作
			String operatorAction = "新增权限";
			//构建日志
			OperatorLog log = LogGPUtil.buildLog(request, operatorValue, operatorAction);
			//修改权限
			perServ.create(vo, log);
		} catch (Exception e) {
			String url = menuId == "" ? "/system/permission/per_list.do":"/system/permission/per_list.do?menuId="+menuId+"";
			//业务异常、系统异常包装错误返回
			return errorReturn(logger, e, url, request);
		}
		return successReturn(PromptUtil.ADD_SUCCESS, "/system/permission/per_list.do?menuId="+menuId+"", request);
	}
	
	/**
	 * 权限模块
	 * @author tyj
	 * @param request
	 * @date Mar 5, 2011
	 * @modify
	 */
	@RequestMapping("/system/permission/per_tree")
	public String findPermissionMenu(HttpServletRequest request){
		try {
			//查询权限信息
			List<Menu> list = menuServ.queryPerMenu();
			request.setAttribute("list", list);
		} catch (Exception e) {
			errorReturn(logger, e, "/system/role/role_list.do", request);
		}
		return directReturn("/system/permission/per_tree.jsp");
	}
	
	/**
	 * 删除权限(删除权限没有删除角色相应权限??????????????)
	 * @author tyj
	 * @param request
	 * @date Mar 8, 2011
	 * @modify
	 */
	@RequestMapping("/system/permission/per_delete")
	public String deletePermission(int id, HttpServletRequest request){
		String menuId = request.getParameter("menuId");
		try {
			//操作值
			String operatorValue = "权限ID:"+id;
			//操作动作
			String operatorAction = "删除权限";
			//构建日志
			OperatorLog log = LogGPUtil.buildLog(request, operatorValue, operatorAction);
			//删除
			perServ.remove(id, log);
		} catch (Exception e) {
			//业务异常、系统异常包装错误返回
			return errorReturn(logger, e, "/system/permission/per_list.do?menuId="+menuId+"", request);
		}
		return successReturn(PromptUtil.DELETE_SUCCESS, "/system/permission/per_list.do?menuId="+menuId+"", request);
	}
	
	
}
