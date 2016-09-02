<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page isErrorPage="true"%>
<%@ page import="java.io.*,java.util.*"%>
<%
	response.setStatus(HttpServletResponse.SC_OK);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>HTTP Status 500 - Error report</title>
		<link rel="stylesheet" type="text/css" href="<c:url value='/css/style.css'/>" />
		<style>
<!--
body {
	color: #555;
	margin: 1px;
	padding: 1px;
}

body,td,p {
	font-family: Tahoma, Arial, sans-serif;
	font-size: 14px;
}

h1,h2,h3,b {
	background-color: #F4F6F3;
	padding: 2px 6px;
	margin: 0;
}

H1 {
	font-family: "微软雅黑", "黑体", Arial, sans-serif;
	font-size: 20px;
	color: #336699;
	background-color: #E6F0F7;
	padding: 3px 60px 3px 6px;
}

H2 {
	font-size: 16px;
}

H3 {
	font-size: 14px;
	background-color: #F4F6F3;
}


</style>
	</head>
	<body>
		程序发生了错误，有可能该页面正在调试或者是设计上的缺陷.
		<br />
		你可以选择
		<br /> 
		<a href=<%=request.getContextPath() + "/forum/new.jsp"%>>反馈</a>  提醒我...
		或者
		<br />
		<a href="javascript:history.go(-1)">返回上一页</a>
		<hr width=100% />
		<h1>
			<font color="#DB1260">JSP Error Page</font>
		</h1>

		<p>
			An exception was thrown:
			<b> <%=exception.getClass()%>:<%=exception.getMessage()%></b>
		</p>
		<%
			System.out.println("Header....");
			Enumeration<String> e = request.getHeaderNames();
			String key;
			while (e.hasMoreElements()) {
				key = e.nextElement();
				System.out.println(key + "=" + request.getHeader(key));
			}
			System.out.println("Attribute....");
			e = request.getAttributeNames();
			while (e.hasMoreElements()) {
				key = e.nextElement();
				System.out.println(key + "=" + request.getAttribute(key));
			}

			System.out.println("Parameter....");
			e = request.getParameterNames();
			while (e.hasMoreElements()) {
				key = e.nextElement();
				System.out.println(key + "=" + request.getParameter(key));
			}
		%>
		<%=request.getAttribute("javax.servlet.forward.request_uri")%><br />
		<%=request.getAttribute("javax.servlet.forward.servlet_path")%>
		<p style="margin-top: 10px; margin-bottom:10px; font-weight:600; font-size: 15px;">
		With the following stack trace:
		</p>
		<pre>
	<%
		exception.printStackTrace();
		ByteArrayOutputStream ostr = new ByteArrayOutputStream();
		exception.printStackTrace(new PrintStream(ostr));
		out.print(ostr);
	%>
	</pre>
	<hr width=100% />
	</body>
</html>
