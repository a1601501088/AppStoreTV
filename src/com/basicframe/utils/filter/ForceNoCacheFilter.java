package com.basicframe.utils.filter;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
/**
 * Title: Filter缓存
 * Description: 使用Filter指定浏览器是否缓存
 * @Copyright: Copyright (c) 2010
 * @author: tyj
 * @version: 1.0
 * @time: 2010.03.05
 */
public class ForceNoCacheFilter implements Filter {
	
	FilterConfig fc;

	public void destroy() {
		this.fc = null;
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) 
		throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) res;
		for (Enumeration<?> e = this.fc.getInitParameterNames(); e.hasMoreElements();) {
			String headerName = (String) e.nextElement();
			response.addHeader(headerName, this.fc.getInitParameter(headerName));
		}
		chain.doFilter(req, response);
	}

	public void init(FilterConfig fc) throws ServletException {
		this.fc = fc;
	}
}
