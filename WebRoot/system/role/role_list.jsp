<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tag/pages.tld" prefix="pg"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
	<title>角色管理</title>
	<meta http-equiv=Content-Type content="text/html; charset=utf-8" />
	<%@ include file="/system/head.jsp" %>
	<script type="text/javascript" src="<c:url value='/js/MzTreeView10.js'/>"></script>
	<script type="text/javascript">
	
	//添加角色信息
  	function showRoleInfoAdd(){
  		$.dialog({
  			id: "forAddRoleInfo",
  			title: "添加角色",
  			content: document.getElementById("forAddRoleInfo"),
  			ok: function(){
  				$.dialog.list["forAddRoleInfo"].close();
  				document.addRoleForm.action="role_add.do";
	        	document.addRoleForm.submit();
  			},
    		cancelVal: '&nbsp;关闭&nbsp;',
    		cancel: true 
  		});
	}
	
	//修改角色信息
  	function showRoleInfoEdit(form){
  		var id = getCheckedValue(form);
  		if(isNotNull(id)){
  			$.dialog({
	  			id: "forEditRoleInfo",
	  			title: "修改角色",
	  			content: document.getElementById("forEditRoleInfo"),
	  			ok: function(){
	  				$.dialog.list["forEditRoleInfo"].close();
	  				document.roleForm.action="role_edit.do?id="+id;
	        		document.roleForm.submit();
	  			},
	  			init: function () {
			         $.post("<c:url value='/system/role/role_view.do' />",{id : id},function(data){
						if(data!=null){
							$("#roleName").val(data.roleName),
							$("#roleInfo").val(data.roleInfo)
						}
					},"json");
			    },
	    		cancelVal: '&nbsp;关闭&nbsp;',
	    		cancel: true 
	  		});
  		}
	}
	
	//修改角色权限
  	function showRoleSeniorEdit(form){
  		var id = getCheckedValue(form);
  		if(isNotNull(id)){
	        window.location.href="<c:url value='/system/role/role_senior_view.do?id="+ id +"'/>";
  		}
	}
	
	//删除角色
	function deleteRole(form){
		var id = getCheckedValue(form);
  		if(isNotNull(id)){
			$.dialog.confirm('警告：删除该角色同时也会删除该角色下所有权限，您确认删除吗？',function(){
				window.location.href="<c:url value='/system/role/role_delete.do?id="+ id +"'/>";
			});
		}	
	}
	
	</script>
	</head>
  <body>
  <form action="<c:url value='role_list.do'/>" name="form" id="form" method="post">
  <table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
		<tr valign="top">
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
				<tr>
					<td height="30" valign="middle" class="blockTd"><img src="<c:url value='/images/system/role.gif'/>" /> 角色列表</td>
				</tr>
				<tr>
					<td style="padding:0 8px 4px;">
					<div style="float: left; white-space: nowrap;">
						<pg:button type="button" action="role_add" onclick="showRoleInfoAdd();" value="新 增" />
						<pg:button type="button" action="role_edit" onclick="showRoleInfoEdit(this.form);" value="修 改" />
						<pg:button type="button" action="role_delete" onclick="deleteRole(this.form);" value="删 除" />
						<pg:button type="button" action="role_senior_edit" onclick="showRoleSeniorEdit(this.form);" value="权 限" />
					</div>
					<div style="float: right; white-space: nowrap;">
					
				  角色名：<input name="roleName" type="text" value="${roleName}" />
            	  <input type="button" class="inputButton" name="Submitbutton" id="Submitbutton" value="查询" onclick="form.submit();" />
            	</div>
				</td>
			</tr>
			<tr>
				<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
				<table width="100%" cellpadding="2" cellspacing="0" class="dataTable" id="dg1">
				<tr class="dataTableHead">
				<td width="5%">序号</td>
				<td width="5%"><input type="checkbox" id="chkAll" name="chkAll" onclick="checkall(this.form);"/></td>
				<td width="25%"><b>角色名称</b></td>
				<td width="65%"><b>角色描述</b></td>
				</tr>
				<c:if test="${!empty pagination.list}">
   				<c:forEach items="${pagination.list}" var="role">
				<tr style="background-color:#F9FBFC">
				<td>${role.roleId}</td>
				<td><input type="checkbox" id="id" name="id" value="${role.roleId}" /></td>
				<td>${role.roleName}</td>
				<td>${role.roleInfo}</td>
				</tr>
				 </c:forEach>
   		  		</c:if>
				<tr>
				<td colspan="4" align="center" id="dg1_PageBar">
				<pg:zPage baseurl="role_list.do?roleName=${roleName}" pagination="${pagination}"/>
				</td>
				</tr>
				</table>
				</td>
			</tr>
		</table> 
      	</td>
      </tr>
    </table>
  </form>
  
  <!-- 修改角色信息弹出层 -->
  <div id="forEditRoleInfo" style="display: none;">
  <form action="" method="post" id="roleForm" name="roleForm">
    <table width="100%" border="0" align="center" cellpadding="4" cellspacing="4" >
	  <tr>
	    <td align="right">角色名：</td>
	    <td align="left">
	      <input type="text" id="roleName" name="roleName" /></td>
	  </tr>
	  <tr>
	    <td align="right">角色备注：</td>
	    <td align="left">
	      <textarea rows="2" cols="40" id="roleInfo" name="roleInfo"></textarea></td>
	  </tr>
	  </table>
	</form>
	</div>
	
	<!-- 添加角色信息弹出层 -->
  <div id="forAddRoleInfo" style="display: none;">
  <form action="" method="post" id="addRoleForm" name="addRoleForm">
    <table width="100%" border="0" align="center" cellpadding="4" cellspacing="4" >
	  <tr>
	    <td align="right">角色名：</td>
	    <td align="left">
	      <input type="text" id="aRoleName" name="aRoleName" /></td>
	  </tr>
	  <tr>
	    <td align="right">角色备注：</td>
	    <td align="left">
	      <textarea rows="2" cols="40" id="aRoleInfo" name="aRoleInfo"></textarea></td>
	  </tr>
	  </table>
	</form>
	</div>
	
  </body>
</html>

