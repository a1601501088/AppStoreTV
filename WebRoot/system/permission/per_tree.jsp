<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
  <head>
	<title></title>
	<meta http-equiv=Content-Type content="text/html; charset=utf-8" />
	<%@ include file="/system/head.jsp" %>
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
		padding: 0px;
		margin:0px;
	  }
	  A:link, A:visited, A:hover, A:active{
        text-decoration: NONE;
        color: #01439a;
      }
	  .lefttab{
		background:url(../../images/system/bg_left2.gif) no-repeat left top; border:1px #FFF solid; 
	  }
	  .lefttab{
		margin:0px; padding:0px; height:30px; width:100%; text-align:center; font-weight: bold;padding-top: 5px;
	  }
	  
	</style>
  </head>
  <body>
	<div id="spanTitle" class="lefttab">
		<a href="#" >权限菜单</a>
	</div>
	<div class="columncontent" id="treeviewarea">
	  <script language="JavaScript" type="text/javascript">
	    var tree = new MzTreeView("tree");
        tree.setIconPath("<c:url value='/images/system/icons/ic/'/>");
        tree.N['0_1'] = "T:权限菜单; url:per_list.do ";
	    <c:if test="${!empty list}">
 	    <c:forEach items="${list}" var="menu">
 	    tree.N['1_${menu.menuId}'] = "T:${menu.menuName}; url:per_list.do?menuId=${menu.menuId}; ";
        </c:forEach>
 	    </c:if>
		tree.wordLine = false;
		tree.setTarget("rightFrame");
 	    document.getElementById('treeviewarea').innerHTML = tree.toString();
       	tree.expandAll();
 	  </script>
	</div>
  </body>
</html>

