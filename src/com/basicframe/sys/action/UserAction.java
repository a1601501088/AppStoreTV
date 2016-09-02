package com.basicframe.sys.action;

import java.util.ArrayList;
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
import com.basicframe.common.log.LogGPUtil;
import com.basicframe.sys.model.LoginLog;
import com.basicframe.sys.model.Menu;
import com.basicframe.sys.model.OperatorLog;
import com.basicframe.sys.model.Permissions;
import com.basicframe.sys.model.Role;
import com.basicframe.sys.model.User;
import com.basicframe.sys.model.UserRole;
import com.basicframe.sys.service.IMenuService;
import com.basicframe.sys.service.IPermissionsService;
import com.basicframe.sys.service.IRoleService;
import com.basicframe.sys.service.IUserRoleService;
import com.basicframe.sys.service.IUserService;
import com.basicframe.utils.CommonUtil;
import com.basicframe.utils.DateTool;
import com.basicframe.utils.PromptUtil;
import com.basicframe.utils.ToolBox;
import com.basicframe.utils.page.Pagination;

/**
 * <p>Description: 用户action</p>
 *
 * <p>Copyright: Copyright (c) 2011</p>
 *
 * <p>Company: </p>
 *
 * @author 唐颖杰
 * @version 1.0
 */
@Controller
public class UserAction extends BaseAction {
	
	private Log logger = LogFactory.getLog(UserAction.class);
	
	@Resource
	private IUserService userServ;
	@Resource
	private IUserRoleService userRoleServ;
	@Resource
	private IRoleService roleServ;
	@Resource
	private IPermissionsService perServ;
	@Resource
	private IMenuService menuServ;
	
	
	/**
	 * 管理员登录
	 * @author tyj
	 * @param request
	 * @param response
	 * @return 登录成功重定向到后台主页
	 * @date Aug 29, 2010
	 * @modify
	 */
	@RequestMapping("/system/login")
	public String login(HttpServletRequest request) {
		try {
			//获取保存在session中的验证码
			//String checkCode = (String)request.getSession().getAttribute("checkCode");
			//清除session
			request.getSession().removeAttribute("checkCode");
			//获取用户输入的验证码
			//String valCode = request.getParameter("checkCode");
			//获取登录名和密码
			String pass = ToolBox.getMD5String("123456");
			System.out.println("pass:"+pass);
			String loginName = request.getParameter("loginName");
			String password = ToolBox.getMD5String(request.getParameter("password"));
			//判断验证码是否正确
			/*if(!valCode.equalsIgnoreCase(checkCode)){
				throw new BusException("验证码错误！");
			}*/
			// 创建日志对象
			LoginLog log = new LoginLog();
			log.setLoginName(loginName);
			log.setLoginIP(request.getRemoteAddr());
			log.setLoginTime(DateTool.instance.getCurrentDateString());
			log.setRemark("");
			//登录验证
			User super_user = new User();
			super_user.setUserName(loginName);
			super_user.setPassword(password);
			super_user = userServ.createLogin(super_user, log);
			//设置登录Session
			request.getSession().setAttribute("super_user", super_user);
			//获取用户模块
			List<Menu> mlist = menuServ.queryUserMenu(super_user.getUserId());
			//获取用户权限
			List<Permissions> pList = perServ.queryUserPermissions(super_user.getUserId());
			//存放到Map
			Map<String, List<?>> map = new HashMap<String, List<?>>();
			map.put("super_user_menu", mlist);
			map.put("super_user_permissions", pList);
			//将Map存放到Session
			request.getSession().setAttribute("super_user_Map", map);
		} catch (Exception e) {
			//业务异常、系统异常包装错误返回
			return errorReturn(logger, e, "login.jsp", request);
		}
		//重定向到后台main页面
		return redirectReturn("index.jsp");
	}
	
	/**
	 * 刷新权限
	 * @author tyj
	 * @param request
	 * @date Jan 4, 2011
	 * @modify
	 */
	@RequestMapping("/system/user/permissions_ref")
	public String refreshPermissions(HttpServletRequest request){
		try {
			User super_user = (User)request.getSession().getAttribute("super_user");
			if(super_user != null){
				//获取用户模块
				List<Menu> mlist = menuServ.queryUserMenu(super_user.getUserId());
				//获取用户权限
				List<Permissions> pList = perServ.queryUserPermissions(super_user.getUserId());
				//存放到Map
				Map<String, List<?>> map = new HashMap<String, List<?>>();
				map.put("super_user_menu", mlist);
				map.put("super_user_permissions", pList);
				//将Map存放到Session
				request.getSession().setAttribute("super_user_Map", map);
			}
		} catch (Exception e) {
			//业务异常、系统异常包装错误返回
			return errorReturn(logger, e, "/system/login.jsp", request);
		}
		//重定向到后台main页面
		return redirectReturn("/system/index.jsp");
	}
	
	/**
	 * 管理员列表
	 * @author tyj
	 * @param page
	 * @param request
	 * @return
	 * @date Jan 4, 2011
	 * @modify
	 */
	@RequestMapping("/system/user/user_list")
	public String findUserList(String page, HttpServletRequest request){
		try {
			String userName = request.getParameter("userName");
			String currentPage = request.getParameter("page");
			//参数
			Map<String, Object> map = new HashMap<String, Object>();
			if(userName != null && userName != ""){
				map.put("userName", "%"+userName+"%");
			}
			//构建分页对象
			Pagination pagination = findPageList(map, userServ, currentPage, 15);
			//查询所有角色
			List<Role> rList = roleServ.queryAll();
			//查询所有用户角色关联
			List<UserRole> urList = userRoleServ.queryAll();
			request.setAttribute("pagination", pagination);
			request.setAttribute("rList", rList);
			request.setAttribute("urList", urList);
			request.setAttribute("userName", userName);
		} catch (Exception e) {
			//业务异常、系统异常包装错误返回
			return errorReturn(logger, e, "/system/index.jsp", request);
		}
		return directReturn("/system/user/user_list.jsp");
	}
	
	/**
	 * 增加管理员
	 * @author tyj
	 * @param request
	 * @return
	 * @date Jan 4, 2011
	 * @modify
	 */
	@RequestMapping("/system/user/user_add")
	public String addUser(HttpServletRequest request){
		try {
			//构建对象
			User user = new User();
			user.setUserName(CommonUtil.getStr(request.getParameter("userName"), true, true));
			user.setPassword(ToolBox.getMD5String(request.getParameter("password")));
			user.setRealName(CommonUtil.getStr(request.getParameter("realName"), true, true));
			user.setSex(Integer.parseInt(CommonUtil.getStr(request.getParameter("sex"), true, true)));
			if(request.getParameter("birthday").length() > 0){
				user.setBirthday(CommonUtil.getStr(request.getParameter("birthday"), true, true));
			}
			user.setDepartments(CommonUtil.getStr(request.getParameter("departments"), true, true));
			user.setDuties(CommonUtil.getStr(request.getParameter("duties"), true, true));
			user.setEmail(CommonUtil.getStr(request.getParameter("email"), true, true));
			user.setOfficePhone(CommonUtil.getStr(request.getParameter("officePhone"), true, true));
			user.setHomePhone(CommonUtil.getStr(request.getParameter("homePhone"), true, true));
			user.setMobilePhone(CommonUtil.getStr(request.getParameter("mobilePhone"), true, true));
			user.setCreateTime(DateTool.instance.getCurrentDateString());
			user.setLastLoginTime(null);
			user.setLastLoginIP("");
			user.setLoginTimes(0);
			user.setStatus(1);
			//操作值
			String operatorValue = "";
			//操作动作
			String operatorAction = "";
			//构建日志
			OperatorLog log = LogGPUtil.buildLog(request, operatorValue, operatorAction);
			//增加管理员
			userServ.create(user, log);
		} catch (Exception e) {
			//异常外理
			return errorReturn(logger, e, "user_list.do", request);
		}
		return successReturn(PromptUtil.ADD_SUCCESS, "user_list.do", request);
	}
	
	/**
	 * 修改管理员信息跳转
	 * @author tyj
	 * @param request
	 * @param UserID
	 * @return
	 * @date Jan 5, 2011
	 * @modify
	 */
	@RequestMapping("/system/user/user_view")
	public void findUserById(int id, HttpServletRequest request, HttpServletResponse response){
		try {
			User user = userServ.queryById(id);
			List<Role> list = roleServ.queryUserRole(id);
			String nameStr = "";
			String idStr = "";
			if(list != null && list.size() > 0){
				for(int i = 0; i < list.size(); i++){
					nameStr += list.get(i).getRoleName() + ",";
					idStr += list.get(i).getRoleId() + ",";
				}
				nameStr = nameStr.substring(0, nameStr.length() - 1);
				idStr = idStr.substring(0, idStr.length() - 1);
			}
			user.setRoleNameList(nameStr);
			List<Role> allList = roleServ.queryAll();
			StringBuffer sb = new StringBuffer();
			sb.append("<table width=\"100%\" border=\"1\" cellpadding=\"3\" cellspacing=\"0\" bordercolor=\"#eeeeee\">");
			for(int i = 0; i < allList.size(); i++){
				sb.append("<tr><td height=\"20\" align=\"left\">");
				String flag = "";
				for(int j = 0; j < list.size(); j++){
					if(allList.get(i).getRoleId() == list.get(j).getRoleId()){
						flag = "checked ='checked'";
					}
				}
				sb.append("<input type='checkbox' name='pck' value='"+allList.get(i).getRoleId()+"' "+flag+" />"+allList.get(i).getRoleName());
				
				sb.append("</td></tr>");
			}
			sb.append("</table>");
			user.setRoleIdList(sb.toString());
			//绑定到页面
			ajaxJson(user, response);
		} catch (Exception e) {
			//业务异常、系统异常包装错误返回
			errorReturn(logger, e, "/system/main.jsp", request);
		}
	}
	
	/**
	 * 修改管理员信息
	 * @author tyj
	 * @param request
	 * @return
	 * @date Jan 5, 2011
	 * @modify
	 */
	@RequestMapping("/system/user/user_edit")
	public String editUser(HttpServletRequest request){
		//构建新闻对象
		User editUser = new User();
		int userId = Integer.parseInt(request.getParameter("userId"));
		try {
			editUser.setUserId(userId);
			editUser.setPassword(ToolBox.getMD5String(request.getParameter("e_password")));
			editUser.setRealName(CommonUtil.getStr(request.getParameter("e_realName"), true, true));
			editUser.setSex(Integer.parseInt(request.getParameter("e_sex")));
			if(request.getParameter("e_birthday").length() > 0){
				editUser.setBirthday(CommonUtil.getStr(request.getParameter("e_birthday"), true, true));
			}
			editUser.setDepartments(CommonUtil.getStr(request.getParameter("e_departments"), true, true));
			editUser.setDuties(CommonUtil.getStr(request.getParameter("e_duties"), true, true));
			editUser.setEmail(CommonUtil.getStr(request.getParameter("e_email"), true, true));
			editUser.setOfficePhone(CommonUtil.getStr(request.getParameter("e_officePhone"), true, true));
			editUser.setHomePhone(CommonUtil.getStr(request.getParameter("e_homePhone"), true, true));
			editUser.setMobilePhone(CommonUtil.getStr(request.getParameter("e_mobilePhone"), true, true));
			//用户角色
			String[] pck = (String[])request.getParameterValues("pck");
			List<UserRole> list = new ArrayList<UserRole>();
			if(pck != null){
				for(int i = 0; i < pck.length; i++){
					UserRole ur = new UserRole();
					ur.setUserId(userId);
					ur.setRoleId(Integer.parseInt(pck[i]));
					list.add(ur);
				}
			}
			//操作值
			String operatorValue = "";
			//操作动作
			String operatorAction = "";
			//构建日志
			OperatorLog log = LogGPUtil.buildLog(request, operatorValue, operatorAction);
			//修改管理员信息
			userServ.modify(editUser, list, log);
		} catch (Exception e) {
			//异常外理
			return errorReturn(logger, e, "user_list.do", request);
		}
		return successReturn(PromptUtil.EDIT_SUCCESS, "user_list.do", request);
	}
	
	/**
	 * 修改管理员状态
	 * @author 
	 * @param request
	 * @param response
	 * @return 
	 * @date 2011.1.4
	 */
	@RequestMapping("/system/user/user_status_edit")
	public String editStatus(int id, HttpServletRequest request){
		try {
			User user = userServ.queryById(id);
			user.setStatus(Integer.parseInt(request.getParameter("status")));
			//操作值
			String operatorValue = "";
			//操作动作
			String operatorAction = "";
			//构建日志
			OperatorLog log = LogGPUtil.buildLog(request, operatorValue, operatorAction);
			userServ.modifyStatus(user, log);
		} catch (Exception e) {
			//业务异常、系统异常包装错误返回
			return errorReturn(logger, e, "user_list.do", request);
		}
		return successReturn(PromptUtil.EDIT_STATUS_SUCCESS, "user_list.do", request);
	}
	
	/**
	 * 管理员退出
	 * @author 
	 * @param request
	 * @param response
	 * @return 
	 * @date Aug 29, 2010
	 * @modify
	 */
	@RequestMapping("/system/user/login_out")
	public String outLogin(HttpServletRequest request , HttpServletResponse response) {
		//清除session
		request.getSession().removeAttribute("super_user");
		request.getSession().removeAttribute("super_user_Map");
		//重定向到后台登陆页面
		return redirectReturn("/system/login.jsp");
	}
	
	
}
