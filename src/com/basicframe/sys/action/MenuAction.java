package com.basicframe.sys.action;


import com.basicframe.common.action.BaseAction;
import com.basicframe.common.exception.BusException;
import com.basicframe.common.log.LogGPUtil;
import com.basicframe.sys.model.Menu;
import com.basicframe.sys.model.OperatorLog;
import com.basicframe.sys.service.IMenuService;
import com.basicframe.utils.PromptUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * <p>Description: 菜单action</p>
 *
 * <p>Copyright: Copyright (c) 2011</p>
 *
 * <p>Company: </p>
 *
 * @author 唐颖杰
 * @version 1.0
 */
@Controller
public class MenuAction extends BaseAction {
	
	private Log logger = LogFactory.getLog(MenuAction.class);
	
	@Resource
	private IMenuService menuServ;
	
	
	/**
	 * 跳转到menu.jsp页面
	 * @author tyj
	 * @param request
	 * @return String
	 * @date Oct 22, 2010
	 * @modify
	 */
	@RequestMapping("/system/menu/menu")
	public String findMenuPage(HttpServletRequest request){
		return directReturn("/system/menu/menu.jsp");
	}
	
	/**
	 * 获取用户菜单树
	 * @author tyj
	 * @param request
	 * @return String
	 * @date Oct 22, 2010
	 * @modify
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/system/menu/sys_menu")
	public String findSysMenuTree(HttpServletRequest request){
		try {
			//获取用户所有的菜单
			Map<String, List<?>> map = (Map<String, List<?>>)request.getSession().getAttribute("super_user_Map");
			List<Menu> mlist = (List<Menu>)map.get("super_user_menu");
			//绑定树到页面
			request.setAttribute("mlist", mlist);
		} catch (Exception e) {
			return errorReturn(logger, e, "/system/login.jsp", request);
		}
		return directReturn("/system/left.jsp");
	}
	
	/**
	 * 获取所有菜单树
	 * @author tyj
	 * @param request
	 * @return String
	 * @date Oct 22, 2010
	 * @modify
	 */
	@RequestMapping("/system/menu/sys_menu_all")
	public String findAllSysmenuTree(HttpServletRequest request){
		try {
			//获取用户所有的菜单
			List<Menu> mlist = (List<Menu>)menuServ.queryAll();
			//绑定树到页面
			request.setAttribute("mlist", mlist);
		} catch (Exception e) {
			return errorReturn(logger, e, "/system/login.jsp", request);
		}
		return directReturn("/system/menu/menu_tree.jsp");
	}
	
	/**
	 * 添加菜单
	 * @author tyj
	 * @param pid 菜单ID
	 * @param request
	 * @param request
	 * @date Mar 5, 2011
	 * @modify
	 */
	@RequestMapping("/system/menu/menu_add")
	public void addmenu(int pid, HttpServletRequest request, HttpServletResponse response){
		Menu menu = new Menu();
		try {
			//图片上传
			//String menuPic = uploadImages(request, "file", 0, "");
			//页面获取参数
			String menuName = request.getParameter("menuName");
			String menuUrl = request.getParameter("menuUrl");
			//String isOpen = request.getParameter("isOpen");
			String isDisabled = request.getParameter("isDisabled");
			String isModify = request.getParameter("isModify");
			String isRemove = request.getParameter("isRemove");
			String menuOrder = request.getParameter("menuOrder");
			//对象附值
			menu.setParentId(pid);
			menu.setMenuName(menuName);
			/*if(menuPic != ""){
				menu.setmenuPic(menuPic);
			}else{
				menu.setmenuPic("/images/icons/item.gif");
			}*/
			menu.setMenuPic("");
			menu.setMenuUrl(menuUrl);
			menu.setMenuOrder(Integer.parseInt(menuOrder));
			menu.setIsDisabled(isDisabled);
			menu.setIsOpen("0");
			menu.setIsModify(isModify);
			menu.setIsRemove(isRemove);
			//操作值
			String operatorValue = "菜单名："+menuName+";菜单URL:"+menuUrl+";是否禁用："+isDisabled+";是否可修改："+ isModify +";是否可删除："+isDisabled;
			//操作动作
			String operatorAction = "新增菜单";
			//构建日志
			OperatorLog log = LogGPUtil.buildLog(request, operatorValue, operatorAction);
			//添加菜单
			menuServ.create(menu, log);
			// 页面提示信息
			succAjaxReturn(PromptUtil.ADD_SUCCESS, response);
		} catch (Exception e) {
			// 业务异常、系统异常包装错误返回
			errAjaxReturn(logger, e, response);
		}
	}
	
	/**
	 * 显示菜单
	 * @author tyj
	 * @param id 菜单ID
	 * @param request
	 * @param request
	 * @return menu_view.jsp
	 * @date Mar 5, 2011
	 * @modify
	 */
	@RequestMapping("/system/menu/menu_view")
	public String findmenuById(int id, HttpServletRequest request){
		Menu menu = null;
		try {
			//查询栏目信息
			menu = menuServ.queryById(id);
			//绑定到页面
			request.setAttribute("menu", menu);
		} catch (Exception e) {
			return errorReturn(logger, e, "/system/error.jsp", request);
		}
		return directReturn("/system/menu/menu_view.jsp");
	}
	
	/**
	 * 修改菜单
	 * @author tyj
	 * @param id 菜单ID
	 * @param request
	 * @param request
	 * @date Mar 5, 2011
	 * @modify
	 */
	@RequestMapping("/system/menu/menu_edit")
	public void editmenu(int id, HttpServletRequest request, HttpServletResponse response){
		Menu menu = new Menu();
		try {
			//图片上传
			//String menuPic = uploadImages(request, "file", 0, "");
			//页面获取参数
			String menuName = request.getParameter("menuName");
			String menuUrl = request.getParameter("menuUrl");
			//String isOpen = request.getParameter("isOpen");
			String isDisabled = request.getParameter("isDisabled");
			String isModify = request.getParameter("isModify");
			String isRemove = request.getParameter("isRemove");
			String menuOrder = request.getParameter("menuOrder");
			//对象附值
			menu.setMenuId(id);
			menu.setMenuName(menuName);
			menu.setMenuPic("");
			menu.setMenuUrl(menuUrl);
			menu.setMenuOrder(Integer.parseInt(menuOrder));
			menu.setIsDisabled(isDisabled);
			menu.setIsOpen("0");
			menu.setIsModify(isModify);
			menu.setIsRemove(isRemove);
			//操作值
			String operatorValue = "菜单ID："+ id +"；菜单名："+menuName+"；菜单URL："+menuUrl+"；是否禁用："+isDisabled+"；是否可修改："+ isModify +"；是否可删除："+isDisabled;
			//操作动作
			String operatorAction = "修改菜单";
			//构建日志
			OperatorLog log = LogGPUtil.buildLog(request, operatorValue, operatorAction);
			//修改菜单
			menuServ.modify(menu, log);
			// 页面提示信息
			succAjaxReturn(PromptUtil.EDIT_SUCCESS, response);
		} catch (Exception e) {
			// 业务异常、系统异常包装错误返回
			errAjaxReturn(logger, e, response);
		}
	}

	/**
	 * 删除菜单
	 * @author tyj
	 * @param id 菜单ID
	 * @param request
	 * @param request
	 * @date Mar 8, 2011
	 * @modify
	 */
	@RequestMapping("/system/menu/menu_delete")
	public void deleteMenu(int id, HttpServletRequest request, HttpServletResponse response){
		try {
			Menu menu = menuServ.queryById(id);
			if(menu == null){
				throw new BusException("此菜单不存在");
			}
			//创建日志对象
			//操作值
			String operatorValue = "菜单ID："+id+",名称："+menu.getMenuName()+",url："+menu.getMenuUrl()+",上级菜单ID："+menu.getParentId()+",是否禁用："+menu.getIsDisabled();
			//操作动作
			String operatorAction = "删除菜单";
			//构建日志
			OperatorLog log = LogGPUtil.buildLog(request, operatorValue, operatorAction);
			//删除菜单
			menuServ.remove(id, log);
			// 页面提示信息
			succAjaxReturn(PromptUtil.DELETE_SUCCESS, response);
		} catch (Exception e) {
			// 业务异常、系统异常包装错误返回
			errAjaxReturn(logger, e, response);
		}
	}
	
	
}
