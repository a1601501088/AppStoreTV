package com.basicframe.utils.filter;

import com.basicframe.sys.model.Permissions;
import com.basicframe.sys.model.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
/**
 * 安全检查Filter
 * @author: tyj
 * @version: V1.0
 * @date: Aug 31, 2010
 */
public class SystemFilter implements Filter {
	
	
	public void init(FilterConfig arg0) throws ServletException {}

	/**
	 * 权限验证Filter
	 */
	@SuppressWarnings("unchecked")
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		//取得根目录所对应的绝对路径
		String currentURL = request.getRequestURI();
		//如果是/login.jsp,/login.do请求就不判断
		if (currentURL.indexOf("/login.jsp") == -1) {
			if (currentURL.indexOf("/login.do") == -1) {
				User super_user = (User)request.getSession().getAttribute("super_user");
				//如果没有登录则返回到登录页面
				if (super_user == null){
					response.sendRedirect(request.getContextPath() + "/system/login.jsp");
					return;
				}
				if (currentURL.indexOf(".do") == -1 && currentURL.indexOf(".jsp") == -1){
					response.sendRedirect(request.getContextPath() + "/system/index.jsp");
					return;
				}
				//不需要验证的URL
				String[] url = {"error.jsp","esc.jsp","tip.jsp","demo.jsp","index.jsp","left.jsp","security.jsp","main.jsp",
						"top.jsp","separator.jsp","sys_menu.do","login_out.do"};
				for(int i = 0; i < url.length; i++){
					if(currentURL.indexOf(url[i]) != -1){
						chain.doFilter(request, response);
						return;
					}
				}
				//权限验证
				Map<String, List<?>> map = (Map<String, List<?>>)request.getSession().getAttribute("super_user_Map");
				if(map != null){
					List<Permissions> permissions = (List<Permissions>)map.get("super_user_permissions");
					for(int i = 0; i < permissions.size(); i++){
						String[] str = permissions.get(i).getPerAction().split(",");

						for(int j = 0; j< str.length; j++){
							//System.out.println("权限"+i+":"+str[j]+";currentURL:"+currentURL);
							if(currentURL.indexOf(str[j]) != -1){
								chain.doFilter(request, response);
								return;
							}
						}
					}
				}
				//没有权限时跳转的页面
				response.sendRedirect(request.getContextPath() + "/system/security.jsp");
				return;
			}
		}
		chain.doFilter(request, response);
	}

	public void destroy() {}

}
