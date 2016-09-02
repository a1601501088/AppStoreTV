<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
	<title>管理中心left</title>
	<meta http-equiv=Content-Type content="text/html; charset=utf-8" />
	<link type="text/css" rel="stylesheet" href="<c:url value='/css/style.css'/>" />
	<script type="text/javascript" src="<c:url value='/js/MzTreeView10.js'/>"></script>
	<style type="text/css">
	  body{
		scrollbar-face-color: #D7E0EB;
		scrollbar-highlight-color: #E9EDF3;
		scrollbar-shadow-color: #B4C7E4;
		scrollbar-3dlight-color: #F3F7F8;
		scrollbar-arrow-color:  #2B61B4;
		scrollbar-track-color: #EFEFEF;
		scrollbar-darkshadow-color: #DEE3E7;
	  }
	 
      A:link, A:visited, A:hover, A:active{
        font-size: 12px;
        padding-left: 4px;
        text-decoration: NONE;
        color: #01439a;
      }
	  .lefttab{
		background-image:url(<c:url value='/images/system/bg_left.gif'/>);margin:0;
		padding:0; line-height:33px;height:33px; width:100%; text-align:center; font-weight: bold;
	  }
	  .columncontent{
		padding:3px; margin:0px; text-align:left;
	  }
	  .columncontent td{
		font-family:"lucida Grande",Verdana;font-size:12px;
	  }
	</style>
  </head>
  <body>
	<div id="spanTitle" class="lefttab">
		<a href="<c:url value='/system/user/permissions_ref.do'/>" title="点击刷新权限">管理选项</a>
	</div>
	<div class="columncontent" id="treeviewarea">
	  <script language="JavaScript" type="text/javascript">
	    var tree = new MzTreeView("tree");
        tree.setIconPath("<c:url value='/images/system/icons/'/>");
	    <c:if test="${!empty mlist}">
 	    <c:forEach items="${mlist}" var="menu">
 	    tree.N['${menu.parentId}_${menu.menuId}'] = "T:${menu.menuName}; url:<c:url value='${menu.menuUrl}'/>;";
        </c:forEach>
 	    </c:if>
		tree.wordLine = false;
		tree.setTarget("right");
 	    document.getElementById('treeviewarea').innerHTML = tree.toString();
       	tree.expandAll();
 	  </script>
	</div>
  </body>
</html>

