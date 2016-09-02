<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
  <head>
	<title>left</title>
	<meta http-equiv=Content-Type content="text/html; charset=utf-8" />
  </head>
  <frameset cols="172,*" frameborder="no"  framespacing="0">
    <frame src="<c:url value='/system/menu/sys_menu_all.do'/>" id="leftFrame" name="leftFrame" scrolling="auto" noresize="noresize" title="leftFrame" />
    <frame src="<c:url value='/system/menu/menu_view.do?id=1'/>" id="rightFrame" name="rightFrame" title="rightFrame"  scrolling="auto" noresize="noresize"/>
  </frameset>
  <noframes>
    <body>
      <p>此网页使用了框架，但您的浏览器不支持框架。</p>
    </body>
  </noframes>
</html>

