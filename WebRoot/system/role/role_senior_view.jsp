<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tag/pages.tld" prefix="pg"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
	<title>角色管理</title>
	<meta http-equiv=Content-Type content="text/html; charset=utf-8" />
	<%@ include file="/system/head.jsp" %>
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/ui.tabs.css'/>" media="print, projection, screen"/>
	<script type="text/javascript" src="<c:url value='/js/MzTreeView10.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/ui.core.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/ui.tabs.js'/>"></script>
	<script type="text/javascript">
		$(function() {
		    $('#rotate > ul').tabs();
		});
    </script>
    <style>
    	.MzTreeViewRow {border:none;width:500px;padding:0px;margin:0px;border-collapse:collapse}
		.MzTreeViewCell0 {border-bottom:1px solid #CCCCCC;padding:0px;margin:0px;}
		.MzTreeViewCell1 {border-bottom:1px solid #CCCCCC;border-left:1px solid #CCCCCC;width:200px;padding:0px;margin:0px;}
    </style>
  </head>
  <body>
  
  <table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
		<tr valign="top">
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
				<tr>
					<td height="30" class="blockTd"><img src="<c:url value='/images/system/role.gif'/>" align="absbottom" /> 角色权限</td>
				</tr>
				<tr>
					<td style="padding:0 8px 4px;"></td>
			</tr>
			<tr>
				<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<table width="600" border="1" cellpadding="3" cellspacing="0" bordercolor="#eeeeee">
					<tr>
					  <td width="100" height="23">角色名称：</td><td width="482">${role.roleName}</td>
				    </tr>
					<tr>
					  <td height="23">角色描述：</td><td>${role.roleInfo}</td>
					</tr>
					<tr>
						<td colspan="2">
						<div id="rotate">
						  <ul>
						      <li><a href="#fragment-1"><span>菜单权限</span></a></li>
						      <li><a href="#fragment-2"><span>操作权限</span></a></li>
						  </ul>
						  <form action="<c:url value='/system/role/role_menu_edit.do?id=${role.roleId}' />" method="post">
						  <div id="fragment-1">
						      <script language="JavaScript" type="text/javascript">
							      var tree = new MzTreeView("tree");
						          tree.setIconPath("<c:url value='/images/system/icons/ic/'/>");
							      ${m_list}
						 	      document.write(tree.toString());
						       	  tree.expandAll();
					 	      </script>
					 	      <br />
					 	      <span style="padding-left: 50px;"><pg:button type="submit" action="role_menu_edit" value=" 提 交 " /></span>
						  </div>
						  </form>
						  <form action="<c:url value='/system/role/role_senior_edit.do?id=${role.roleId}' />" method="post">
						  <div id="fragment-2">
						      ${o_list}
						      <br />
						      <span style="padding-left: 50px;"><pg:button type="submit" action="role_senior_edit" value=" 提 交 " /></span>
						  </div>
						  
						  </form>
					    </div>
						</td>
					</tr>
					</table>
				
				</td>
			</tr>
		</table> 
      	</td>
      </tr>
    </table>
    
  </body>
</html>

