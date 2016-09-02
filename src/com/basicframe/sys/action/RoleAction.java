package com.basicframe.sys.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
import com.basicframe.sys.model.Role;
import com.basicframe.sys.model.RoleMenu;
import com.basicframe.sys.model.RolePermissions;
import com.basicframe.sys.service.IMenuService;
import com.basicframe.sys.service.IPermissionsService;
import com.basicframe.sys.service.IRoleMenuService;
import com.basicframe.sys.service.IRolePermissionsService;
import com.basicframe.sys.service.IRoleService;
import com.basicframe.utils.DateTool;
import com.basicframe.utils.PromptUtil;
import com.basicframe.utils.page.Pagination;

/**
 * <p>Description: 角色action</p>
 *
 * <p>Copyright: Copyright (c) 2011</p>
 *
 * <p>Company: </p>
 *
 * @author 唐颖杰
 * @version 1.0
 */
@Controller
public class RoleAction extends BaseAction {
	
	private Log logger = LogFactory.getLog(RoleAction.class);
	
	@Resource
	private IRoleService roleServ;
	@Resource
	private IRoleMenuService roleMenuServ;
	@Resource
	private IMenuService menuServ;
	@Resource
	private IPermissionsService perServ;
	@Resource
	private IRolePermissionsService rolePerServ;
	
	
	/**
	 * 角色列表
	 * @author tyj
	 * @param page 当前页
	 * @param request
	 * @return 返回到role_list.jsp
	 * @date Feb 24, 2011
	 * @modify
	 */
	@RequestMapping("/system/role/role_list")
	public String findRolelList(String page, HttpServletRequest request){
		try {
			String roleName = request.getParameter("roleName");
			String currentPage = request.getParameter("page");
			//参数
			Map<String, Object> map = new HashMap<String, Object>();
			if(roleName != null && roleName != ""){
				map.put("roleName", "%"+roleName+"%");
			}
			//构建分页对象
			Pagination pagination = findPageList(map, roleServ, currentPage, 15);
			//绑定到页面
			request.setAttribute("pagination", pagination);
			request.setAttribute("roleName", roleName);
		} catch (Exception e) {
			//业务异常、系统异常包装错误返回
			return errorReturn(logger, e, "/system/main.jsp", request);
		}
		return directReturn("/system/role/role_list.jsp");
	}
	
	/**
	 * 添加角色
	 * @author tyj
	 * @param request
	 * @date Mar 5, 2011
	 * @modify
	 */
	@RequestMapping("/system/role/role_add")
	public String addRole(HttpServletRequest request){
		Role role = new Role();
		try {
			//页面获取参数
			String roleName = request.getParameter("aRoleName");
			String roleInfo = request.getParameter("aRoleInfo");
			//对象附值
			role.setRoleName(roleName);
			role.setRoleInfo(roleInfo);
			role.setStatus("1");
			role.setCreateTime(DateTool.getDateFromString("yyyy-MM-dd hh24:mm:ss"));
			//操作值
			String operatorValue = "角色名："+role.getRoleName()+"；角色描述："+role.getRoleInfo();
			//操作动作
			String operatorAction = "新增角色";
			//构建日志
			OperatorLog log = LogGPUtil.buildLog(request, operatorValue, operatorAction);
			//添加模块
			roleServ.create(role, log);
		} catch (Exception e) {
			//业务异常、系统异常包装错误返回
			return errorReturn(logger, e, "/system/role/role_list.do", request);
		}
		return successReturn(PromptUtil.ADD_SUCCESS, "/system/role/role_list.do", request);
	}
	
	/**
	 * 显示角色信息
	 * @author tyj
	 * @param id 角色ID
	 * @param request
	 * @param response
	 * @date Mar 5, 2011
	 * @modify
	 */
	@RequestMapping("/system/role/role_view")
	public void findRoleById(int id, HttpServletRequest request , HttpServletResponse response){
		Role role = null;
		try {
			//查询角色信息
			role = roleServ.queryById(id);
			//绑定到页面
			ajaxJson(role, response);
		} catch (Exception e) {
			errorReturn(logger, e, "/system/role/role_list.do", request);
		}
	}
	
	/**
	 * 显示角色高级权限信息
	 * @author tyj
	 * @param id 角色ID
	 * @param request
	 * @return 返回到role_senior_view.jsp页面
	 * @date Mar 5, 2011
	 * @modify
	 */
	@RequestMapping("/system/role/role_senior_view")
	public String findRoleSeniorById(int id, HttpServletRequest request){
		Role role = null;
		try {
			//查询角色信息
			role = roleServ.queryById(id);
			/**
			 * 角色操作权限
			 */
			String o_list = "";
			List<Menu> seniorList = menuServ.queryRoleMenu(id);
			//所有角色权限
			List<RolePermissions> rpList = rolePerServ.queryList(id);
			//所有权限
			List<Permissions> pList = perServ.queryAll();
			if(seniorList != null && seniorList.size() > 0){
				Iterator<Menu> it = seniorList.iterator();
				StringBuffer sb = new StringBuffer();
				sb.append("<table width=\"100%\" border=\"1\" cellpadding=\"3\" cellspacing=\"0\" bordercolor=\"#eeeeee\">");
				while(it.hasNext()){
					Menu vo = (Menu)it.next();
					sb.append("<tr><td height=\"23\" width=\"20%\">");
					sb.append(vo.getMenuName()+"： ");
					sb.append("</td><td width=\"80%\">");
					for(int i = 0; i < pList.size(); i++){
						String flag = "";
						if(pList.get(i).getMenuId().equals(String.valueOf(vo.getMenuId()))){
							for(int j = 0; j < rpList.size(); j++){
								if(rpList.get(j).getPermissionsId() == pList.get(i).getPerId()){
									flag = "checked ='checked'";
								}
							}
							sb.append("&nbsp;&nbsp;<input type='checkbox' name='pck' value='"+pList.get(i).getPerId()+"' "+flag+" />"+pList.get(i).getPerName());
						}
					}
					sb.append("</td></tr>");
				}
				sb.append("</table>");
				o_list = sb.toString();
			}
			/**
			 * 角色菜单权限
			 */
			String m_list = "";
			//角色权限模块
			List<RoleMenu> rmlist = roleMenuServ.queryList(id);
			//获取所有的模块
			List<Menu> allList = menuServ.queryAll();
			if(allList != null && allList.size() > 0){
				Iterator<Menu> allit = allList.iterator();
				StringBuffer allsb = new StringBuffer();
				while(allit.hasNext()){
					Menu vo = (Menu)allit.next();
					String flag = "0";
					for(int i = 0; i < rmlist.size(); i++){
						if(rmlist.get(i).getMenuId() == vo.getMenuId()){
							flag = "1";
						}
					}
					allsb.append("tree.N['"+ vo.getParentId() +"_"+ vo.getMenuId() +"'] = 'T:"+ vo.getMenuName() +"; ctrl:sel;checked:"+ flag +"';");
				}
				m_list = allsb.toString();
			}
			//绑定到页面
			request.setAttribute("role", role);
			request.setAttribute("m_list", m_list);
			request.setAttribute("o_list", o_list);
		} catch (Exception e) {
			return errorReturn(logger, e, "/system/role/role_list.do", request);
		}
		return directReturn("/system/role/role_senior_view.jsp");
	}
	
	/**
	 * 修改角色
	 * @author tyj
	 * @param id 角色ID
	 * @param request
	 * @date Mar 5, 2011
	 * @modify
	 */
	@RequestMapping("/system/role/role_edit")
	public String editRole(int id, HttpServletRequest request){
		Role role = new Role();
		try {
			//系统内置角色不能操做
			if(id == 1)throw new BusException(BusException.SYS_ROLE_NOT_OPERATOR);
			//页面获取参数
			String roleName = request.getParameter("roleName");
			String roleInfo = request.getParameter("roleInfo");
			//对象附值
			role.setRoleId(id);
			role.setRoleName(roleName);
			role.setRoleInfo(roleInfo);
			//操作值
			String operatorValue = "角色名："+role.getRoleName()+"；角色描述："+role.getRoleInfo();
			//操作动作
			String operatorAction = "更新角色";
			//构建日志
			OperatorLog log = LogGPUtil.buildLog(request, operatorValue, operatorAction);
			//修改模块
			roleServ.modify(role, log);
		} catch (Exception e) {
			//业务异常、系统异常包装错误返回
			return errorReturn(logger, e, "/system/role/role_list.do", request);
		}
		return successReturn(PromptUtil.EDIT_SUCCESS, "/system/role/role_list.do", request);
	}
	
	/**
	 * 修改角色菜单权限
	 * @author tyj
	 * @param id 角色ID
	 * @param request
	 * @date Mar 5, 2011
	 * @modify
	 */
	@RequestMapping("/system/role/role_menu_edit")
	public String editRoleMenu(int id, HttpServletRequest request){
		try {
			//系统内置角色不能操做
			//if(id == 1)throw new BusException(BusException.SYS_ROLE_NOT_OPERATOR);
			//页面获取参数
			String[] sel = (String[])request.getParameterValues("sel");
			//对象附值
			List<RoleMenu> list = new ArrayList<RoleMenu>();
			if(sel != null){
				for(int i = 0; i < sel.length; i++){
					RoleMenu rm = new RoleMenu();
					rm.setRoleId(id);
					rm.setMenuId(Integer.parseInt(sel[i]));
					list.add(rm);
				}
			}
			//操作值
			String operatorValue = "";
			//操作动作
			String operatorAction = "";
			//构建日志
			OperatorLog log = LogGPUtil.buildLog(request, operatorValue, operatorAction);
			//修改模块
			roleServ.modifyMenu(id, list, log);
		} catch (Exception e) {
			//业务异常、系统异常包装错误返回
			return errorReturn(logger, e, "/system/role/role_senior_view.do?id="+ id +"", request);
		}
		return successReturn(PromptUtil.EDIT_SUCCESS, "/system/role/role_senior_view.do?id="+ id +"", request);
	}
	
	/**
	 * 修改角色(高级)
	 * @author tyj
	 * @param id 角色ID
	 * @param request
	 * @date Mar 5, 2011
	 * @modify
	 */
	@RequestMapping("/system/role/role_senior_edit")
	public String editRoleSenior(int id, HttpServletRequest request){
		Role role = new Role();
		try {
			//系统内置角色不能操做
			//if(id == 1)throw new BusException(BusException.SYS_ROLE_NOT_OPERATOR);
			//页面获取参数
			String roleName = request.getParameter("roleName");
			String roleInfo = request.getParameter("roleInfo");
			String[] pck = (String[])request.getParameterValues("pck");
			//对象附值
			role.setRoleId(id);
			role.setRoleName(roleName);
			role.setRoleInfo(roleInfo);
			List<RolePermissions> list = new ArrayList<RolePermissions>();
			if(pck != null){
				for(int i = 0; i < pck.length; i++){
					RolePermissions pp = new RolePermissions();
					pp.setRoleId(id);
					pp.setPermissionsId(Integer.parseInt(pck[i]));
					list.add(pp);
				}
			}
			//操作值
			String operatorValue = "";
			//操作动作
			String operatorAction = "";
			//构建日志
			OperatorLog log = LogGPUtil.buildLog(request, operatorValue, operatorAction);
			//修改模块
			roleServ.modifySenior(id, list, log);
		} catch (Exception e) {
			//业务异常、系统异常包装错误返回
			return errorReturn(logger, e, "/system/role/role_senior_view.do?id="+ id +"", request);
		}
		return successReturn(PromptUtil.EDIT_SUCCESS, "/system/role/role_senior_view.do?id="+ id +"", request);
	}
	
	/**
	 * 删除角色
	 * @author tyj
	 * @param request
	 * @date Mar 8, 2011
	 * @modify
	 */
	@RequestMapping("/system/role/role_delete")
	public String deleteRole(int id, String page, HttpServletRequest request){
		try {
			//系统内置角色不能操做
			if(id == 1)throw new BusException(BusException.SYS_ROLE_NOT_OPERATOR);
			///操作值
			String operatorValue = "角色ID："+id;
			//操作动作
			String operatorAction = "删除角色";
			//构建日志
			OperatorLog log = LogGPUtil.buildLog(request, operatorValue, operatorAction);
			//删除
			roleServ.remove(id, log);
		} catch (Exception e) {
			//业务异常、系统异常包装错误返回
			return errorReturn(logger, e, "/system/role/role_list.do", request);
		}
		return successReturn(PromptUtil.DELETE_SUCCESS, "/system/role/role_list.do", request);
	}
	
	
}
