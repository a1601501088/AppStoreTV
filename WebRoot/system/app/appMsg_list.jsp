<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tag/pages.tld" prefix="pg"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
	<title></title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%@ include file="/system/head.jsp" %>
	<script type="text/javascript" src="<c:url value='/js/MzTreeView10.js'/>"></script>
	<script type="text/javascript">
	
	//添加
  	function showInfoAdd(){
  		$("#tip2").html("");
  		$.dialog({
  			id: "forAddInfo",
  			title: "添加",
  			content: document.getElementById("forAddInfo"),
  			ok: function(){
  				$("#tip2").html("");
  				var t2 = $("#amsgName").val();
  				if(t2.length == 0){
  					$("#tip2").html("请输入消息");
  					return false;
  				}
  				$.dialog.list["forAddInfo"].close();
  				document.addForm.action="appMsg_add.do";
	        	document.addForm.submit();
  			},
    		cancelVal: '&nbsp;关闭&nbsp;',
    		cancel: true 
  		});
	}
	
	//修改
  	function showInfoEdit(form){
  		$("#tip1").html("");
  		var id = getCheckedValue(form);
  		if(isNotNull(id)){
  			$.dialog({
	  			id: "forEditInfo",
	  			title: "修改",
	  			content: document.getElementById("forEditInfo"),
	  			ok: function(){
	  				$("#tip1").html("");
	  				var t1 = $("#msgName").val();
	  				if(t1.length == 0){
	  					$("#tip1").html("请输入消息");
	  					return false;
	  				}
	  				$.dialog.list["forEditInfo"].close();
	  				document.editForm.action="appMsg_edit.do?msgId="+id;
	        		document.editForm.submit();
	  			},
	  			init: function () {
			         $.post("<c:url value='/system/app/appMsg_view.do' />",{msgId : id},function(data){
						if(data!=null){
							$("#msgName").val(data.msgName)
						}
					},"json");
			    },
	    		cancelVal: '&nbsp;关闭&nbsp;',
	    		cancel: true 
	  		});
  		}
	}
	
	//删除
	function showInfoDelete(form){
		var id = getCheckedValue(form);
  		if(isNotNull(id)){
			$.dialog.confirm('警告：您确认删除吗？',function(){
				window.location.href="<c:url value='/system/app/appMsg_delete.do?msgId="+ id +"'/>";
			});
		}	
	}
	
	</script>
	</head>
  <body>
  <form action="<c:url value='appMsg_list.do'/>" name="form" id="form" method="post">
  <table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
		<tr valign="top">
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
				<tr>
					<td height="30" valign="middle" class="blockTd"><img src="<c:url value='/images/system/role.gif'/>" /> 列表</td>
				</tr>
				<tr>
					<td style="padding:0 8px 4px;">
					<div style="float: left; white-space: nowrap;">
						<pg:button type="button" action="appMsg_add" onclick="showInfoAdd();" value="新 增" />
						<pg:button type="button" action="appMsg_edit" onclick="showInfoEdit(this.form);" value="修 改" />
						<pg:button type="button" action="appMsg_delete" onclick="showInfoDelete(this.form);" value="删 除" />
					</div>
					<div style="float: right; white-space: nowrap;">
				  		类别名：<input name="msgName" type="text" value="${msgName}" />
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
				<td width="70%"><b>消息内容</b></td>
				<td width="20%"><b>创建时间</b></td>
				</tr>
				<c:if test="${!empty pagination.list}">
   				<c:forEach items="${pagination.list}" var="item">
				<tr style="background-color:#F9FBFC">
				<td>${item.msgId}</td>
				<td><input type="checkbox" id="id" name="id" value="${item.msgId}" /></td>
				<td>${item.msgName}</td>
				<td>${item.createTime}</td>
				</tr>
				 </c:forEach>
   		  		</c:if>
				<tr>
				<td colspan="4" align="center" id="dg1_PageBar">
				<pg:zPage baseurl="appMsg_list.do?msgName=${msgName}" pagination="${pagination}"/>
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
  
  <!-- 修改信息弹出层 -->
  <div id="forEditInfo" style="display: none;">
  <form action="" method="post" id="editForm" name="editForm">
    <table width="100%" border="0" align="center" cellpadding="4" cellspacing="4" >
	  <tr>
	    <td align="right">消息内容：</td>
	    <td align="left">
	       <textarea rows="2" cols="40" id="msgName" name="msgName"></textarea>&nbsp;<span style="color: red;" id="tip1"></span>
	    </td>
	  </tr>
	  </table>
	</form>
	</div>
	
	<!-- 添加信息弹出层 -->
  <div id="forAddInfo" style="display: none;">
  <form action="" method="post" id="addForm" name="addForm">
    <table width="100%" border="0" align="center" cellpadding="4" cellspacing="4" >
	  <tr>
	    <td align="right">消息内容：</td>
	    <td align="left">
	      <textarea rows="2" cols="40" id="amsgName" name="amsgName"></textarea>&nbsp;<span style="color: red;" id="tip2"></span>
	    </td>
	  </tr>
	  </table>
	</form>
	</div>
	
  </body>
</html>

