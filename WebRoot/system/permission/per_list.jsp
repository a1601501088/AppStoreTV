<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tag/pages.tld" prefix="pg"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
	<title>权限列表</title>
	<meta http-equiv=Content-Type content="text/html; charset=utf-8" />
	<%@ include file="/system/head.jsp" %>
	<script type="text/javascript">
	
	//增加权限信息
  	function showPermissionInfoAdd(form){
  	
  		$.dialog({
  			id: "forAddPerInfoId",
  			title: "增加权限",
  			content: document.getElementById("forAddPerInfo"),
  			ok: function(){
  				$.dialog.list["forAddPerInfoId"].close();
  				document.addPerForm.action="per_add.do";
        		document.addPerForm.submit();
  			},
    		cancelVal: '&nbsp;关闭&nbsp;',
    		cancel: true 
  		});

	}
	
	//修改权限信息
  	function showPermissionInfoEdit(form){
  		var id = getCheckedValue(form);
  		var menuId = $("#menuId").val();
  		if(isNotNull(id)){
  			$.dialog({
	  			id: "forEditPerInfo",
	  			title: "修改权限",
	  			content: document.getElementById("forEditPerInfo"),
	  			ok: function(){
	  				$.dialog.list["forEditPerInfo"].close();
	  				document.editPerForm.action="per_edit.do?id="+id+"&menuId="+ menuId;
	        		document.editPerForm.submit();
	  			},
	  			init: function () {
			        $.post("<c:url value='/system/permission/per_view.do' />",{id : id},function(data){
						if(data!=null){
							$("#e_perName").val(data.perName),
							$("#e_perAction").val(data.perAction),
							$("#e_perRemark").val(data.perRemark)
						}
					},"json");
			    },
	    		cancelVal: '&nbsp;关闭&nbsp;',
	    		cancel: true 
	  		});
  		}
	}
	
	//删除
	function deletePermission(form){
		var id = getCheckedValue(form);
		var menuId = $("#menuId").val();
  		if(isNotNull(id)){
			$.dialog.confirm('警告：您确认要删除吗？',function(){
				window.location.href="<c:url value='/system/permission/per_delete.do?id="+ id +"&menuId="+ menuId +"'/>";
			});
		}
	}
	
	</script>
  </head>
  <body>
  
	<form action="<c:url value='/system/permission/per_list.do'/>" name="form" id="form" method="post">
    <table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
	<tr valign="top">
		<td>
		<table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
			<tr>
				<td height="30" valign="middle" class="blockTd"><img src="<c:url value='/images/system/role.gif'/>" /> 权限列表</td>
			</tr>
			<tr>
				<td style="padding:0 8px 4px;">
				<div style="float: left; white-space: nowrap;">
					<pg:button type="button" action="per_add" onclick="showPermissionInfoAdd(this.form);" value="新 增" />
					<pg:button type="button" action="per_view" onclick="showPermissionInfoEdit(this.form);" value="修 改" />
					<pg:button type="button" action="per_delete" onclick="deletePermission(this.form);" value="删 除" />
				</div>
				<div style="float: right; white-space: nowrap;">
           	</div>
			</td>
		</tr>
		<tr>
			<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
			<table width="100%" cellpadding="2" cellspacing="0" class="dataTable" id="dg1">
			<tr class="dataTableHead">
			  <td width="8%">序号</td>
			  <td width="6%"><input type="checkbox" id="chkAll" name="chkAll" onclick="checkall(this.form);"/></td>
		      <td width="12%">权限名称</td>
		      <td width="59%">权限动作</td>
		      <td width="15%">所属菜单</td>
	        </tr>
			<c:if test="${!empty pagination.list}">
  			<c:forEach items="${pagination.list}" var="per">
			<tr style="background-color:#F9FBFC">
			  <td>${per.perId}</td>
			  <td><input type="checkbox" id="id" name="id" value="${per.perId}" /></td>
			  <td>${per.perName}</td>
			  <td title="${per.perAction}">${per.perAction}</td>
			  <td>${per.menuId}</td>
		    </tr>
			</c:forEach>
  		  	</c:if>
			<tr>
			  <td colspan="5" align="center" id="dg1_PageBar">
				<pg:zPage baseurl="per_list.do" pagination="${pagination}"/>
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
    
    
    <!-- 增加权限 -->
	<div id="forAddPerInfo" style="display: none;">
	<form name="addPerForm" id="addPerForm" method="post">
      <table width="600" cellpadding="3" cellspacing="0" >
      	  <tr>
            <td><input type="hidden" id="menuId" name="menuId" value="${menuId}" /></td>
            <td></td>
          </tr>
          <tr>
            <td width="91" height="28" align="right">权限名称：</td>
            <td width="209" height="28" align="left">
              <input type="text" id="perName" name="perName" maxlength="16" size="65"/>
            </td>
          </tr>
          <tr>
            <td width="91" height="28" align="right">权限动作：</td>
            <td height="28" align="left">
              <textarea cols="85" name="perAction" id="perAction"></textarea>
            </td>
          </tr>
          <tr>
            <td width="91" height="28" align="right">权限描述： </td>
            <td height="28" align="left">
              <textarea rows="2" cols="85" name="perRemark" id="perRemark"></textarea>
            </td>
          </tr>
        </table>
    </form>
	</div>
    
    <!-- 修改权限 -->
	<div id="forEditPerInfo" style="display: none;">
	<form name="editPerForm" id="editPerForm" method="post">
      <table width="600" cellpadding="3" cellspacing="0" >
      	  <tr>
            <td><input type="hidden" id="menuId" name="menuId" value="${id}" /></td>
            <td></td>
          </tr>
          <tr>
            <td width="91" height="28" align="right">权限名称：</td>
            <td width="209" height="28" align="left">
              <input type="text" id="e_perName" name="e_perName" maxlength="16" size="65"/>
            </td>
          </tr>
          <tr>
            <td width="91" height="28" align="right">权限动作：</td>
            <td height="28" align="left">
              <textarea cols="85" name="e_perAction" id="e_perAction"></textarea>
            </td>
          </tr>
          <tr>
            <td width="91" height="28" align="right">权限描述： </td>
            <td height="28" align="left">
              <textarea rows="2" cols="85" name="e_perRemark" id="e_perRemark"></textarea>
            </td>
          </tr>
        </table>
    </form>
	</div>
	
	
  </body>
</html>

