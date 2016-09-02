<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tag/pages.tld" prefix="pg"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
	<title>菜单管理</title>
	<meta http-equiv=Content-Type content="text/html; charset=utf-8" />
	<%@ include file="/system/head.jsp" %>
	<script type="text/javascript">
	
	$.ajaxSetup({
		async : false  
	});  

	
	function reloadHtml(id){
		window.parent.leftFrame.location.reload();
		window.location.href="<c:url value='/system/menu/menu_view.do?id="+id+"' />";
	}
	
	//修改菜单信息
  	function showEditMenuInfo(id){
  		$.dialog.confirm("你确认要修改吗？", function () {
			$.post("<c:url value='/system/menu/menu_edit.do'/>",{
	    		id:id,
	    		menuName:$("#menuName").val(),
	    		menuOrder:$("#menuOrder").val(),
	    		menuUrl:$("#menuUrl").val(),
	    		isDisabled:$("#isDisabled").val(),
	    		isModify:$("#isModify").val(),
	    		isRemove:$("#isRemove").val()
	    	},function(data){
	    		$.dialog.tips(data.message);
				if(data.status=="success"){
				    setTimeout("reloadHtml("+id+");",1000);
				}
			},"json");
		 });
	}
	
	//添加菜单信息
  	function showAddMenuInfo(id){
  		$.dialog({
  			title:'添加菜单',
  			content: document.getElementById("addMenuInfo"),
  			ok: function(){
  				var flag = false;
       			$.post("<c:url value='/system/menu/menu_add.do' />",{
		    		pid:id,
		    		menuName:$("#menuName2").val(),
		    		menuOrder:$("#menuOrder2").val(),
		    		menuUrl:$("#menuUrl2").val(),
		    		isDisabled:$("#isDisabled2").val(),
		    		isModify:$("#isModify2").val(),
		    		isRemove:$("#isRemove2").val()
		    	},function(data){
		    		$.dialog.tips(data.message);
					if(data.status=="success"){
						if(data.status=="success"){
						    setTimeout("reloadHtml("+id+");",1000);
						}
					}
					if(data.status=="error"){
						flag = true;
					}
				},"json");
       			if(flag){
       				return false;
       			}
    		},
    		cancelVal: '&nbsp;关闭&nbsp;',
    		cancel: true 
  		});
	}
	
	//删除角色
	function deleteMenu(id){
		$.dialog.confirm('警告：删除该菜单同时也会删除该菜单下所有权限，您确认删除吗？',function(){
			$.post("<c:url value='/system/menu/menu_delete.do' />",{id : id},function(data){
				$.dialog.tips(data.message);
				if(data.status=="success"){
					if(data.status=="success"){
					    setTimeout("reloadHtml(1);",1000);
					}
				}
			},"json");
		});
	}
	
	</script> 
  </head>
  <body>
  
  <form action="<c:url value='/system/menu/menu_edit.do?id=${menu.menuId}'/>" method="post" name="editForm">
  <table width="100%" bmenuOrder="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
		<tr valign="top">
			<td>
			<table width="100%" bmenuOrder="0" cellspacing="0" cellpadding="6" class="blockTable">
				<tr>
					<td height="30" class="blockTd"><img src="<c:url value='/images/system/role.gif'/>" align="absbottom" /> 菜单管理</td>
				</tr>
				<tr>
					<td style="padding:0 8px 4px;"></td>
			</tr>
			<tr>
				<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
                    <pg:button type="button" value=" 新 增 " action="menu_add" onclick="showAddMenuInfo(${menu.menuId});" />
                    <pg:button type="button" value=" 保 存 " action="menu_edit" onclick="showEditMenuInfo(${menu.menuId});"/>
                    <pg:button type="button" value=" 删 除 " action="menu_delete" onclick="deleteMenu(${menu.menuId});" />
                    <div style="height: 8px;"></div>
					<table width="100%" bmenuOrder="1" cellpadding="3" cellspacing="0" bmenuOrdercolor="#eeeeee">
				      <tr>
				        <td width="105" height="25" class="td-edit-height">菜单名称：</td>
				        <td width="952" height="25" colspan="2">
				          <input type="text" id="menuName" name="menuName" value="${menu.menuName}" style="width: 200px;"/>
				        </td>
			          </tr>
				      <tr>
					    <td width="105" height="25" class="td-edit-height">链接地址：</td>
					    <td height="25" colspan="2">
					    <input name="menuUrl" type="text" id="menuUrl" value="${menu.menuUrl}" size="60" />
					    </td>
				      </tr>
				      <tr>
				        <td width="105" height="25" class="td-edit-height">菜单排序：</td>
				        <td height="25" colspan="2">
				        <input type="text" id="menuOrder" name="menuOrder" value="${menu.menuOrder}" style="width: 48px;" />
				        </td>
			          </tr>
			          <tr>
			            <td width="105" height="25" class="td-edit-height">是否使用：</td>
			            <td height="25" colspan="2">
			              <select name="isDisabled" id="isDisabled">
			                <option value="0" ${menu.isDisabled == 0 ? "selected='selected'" : ""}>&nbsp;&nbsp;否&nbsp;&nbsp;</option>
			                <option value="1" ${menu.isDisabled == 1 ? "selected='selected'" : ""}>&nbsp;&nbsp;是&nbsp;&nbsp;</option>
			              </select>
			            </td>
		              </tr>
			          <tr>
			            <td width="105" height="25" class="td-edit-height">是否可以修改：</td>
			            <td height="25" colspan="2">
			              <select name="isModify" id="isModify">
			                <option value="0" ${menu.isModify == "0" ? "selected='selected'" : ""}>&nbsp;&nbsp;否&nbsp;&nbsp;</option>
			                <option value="1" ${menu.isModify == "1" ? "selected='selected'" : ""}>&nbsp;&nbsp;是&nbsp;&nbsp;</option>
			              </select>
			            </td>
		              </tr>
			          <tr>
			            <td width="105" height="25" class="td-edit-height">是否可以删除：</td>
			            <td height="25" colspan="2">
			              <select name="isRemove" id="isRemove">
			                <option value="0" ${menu.isRemove == "0" ? "selected='selected'" : ""}>&nbsp;&nbsp;否&nbsp;&nbsp;</option>
			                <option value="1" ${menu.isRemove == "1" ? "selected='selected'" : ""}>&nbsp;&nbsp;是&nbsp;&nbsp;</option>
			              </select>
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
    
    
    <div id="addMenuInfo" style="display: none;">
      <form action="<c:url value='/system/menu/menu_add.do' />" method="post" id="addForm" name="addForm" >
		<table width="460">
	      <tr>
	        <td width="120" height="30" align="right">菜单名称：</td>
	        <td align="left"><input type="text" id="menuName2" name="menuName2" style="width: 200px;"/>
	        </td>
          </tr>
	      <tr>
		    <td height="30" align="right">链接地址：</td>
		    <td align="left"><input name="menuUrl2" type="text" id="menuUrl2" value="" style="width: 300px;" /></td>
	      </tr>
	      <tr>
	        <td height="30" align="right">栏目排序：</td>
	        <td align="left"><input type="text" id="menuOrder2" name="menuOrder2" value="0" style="width: 48px;"  /></td>
          </tr>
          <tr>
            <td height="30" align="right">是否使用：</td>
            <td align="left">
              <select name="isDisabled2" id="isDisabled2" >
                <option value="1">&nbsp;&nbsp;是&nbsp;&nbsp;</option>
                <option value="0">&nbsp;&nbsp;否&nbsp;&nbsp;</option>
              </select>
            </td>
          </tr>
          <tr>
            <td height="30" align="right">是否可以修改：</td>
            <td align="left">
              <select name="isModify2" id="isModify2" >
                <option value="1">&nbsp;&nbsp;是&nbsp;&nbsp;</option>
                <option value="0">&nbsp;&nbsp;否&nbsp;&nbsp;</option>
              </select>
            </td>
          </tr>
          <tr>
            <td height="30" align="right">是否可以删除：</td>
            <td align="left">
              <select name="isRemove2" id="isRemove2"  >
                <option value="1">&nbsp;&nbsp;是&nbsp;&nbsp;</option>
                <option value="0">&nbsp;&nbsp;否&nbsp;&nbsp;</option>
              </select>
            </td>
          </tr>
		</table>
		</form>
    </div>
    
    
  </body>
</html>

