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
  		$("#tip4").html("");
  		$("#tip5").html("");
  		$("#tip6").html("");
  		$.dialog({
  			id: "forAddInfo",
  			title: "添加",
  			content: document.getElementById("forAddInfo"),
  			ok: function(){
  				$("#tip4").html("");
		  		$("#tip5").html("");
		  		$("#tip6").html("");
  				var t4 = $("#acommendType").val();
				var t5 = $("#acommendName").val();
				var t6 = $("#acommendUrl").val();
				if(t4 == null){
  					$("#tip4").html("请选择类型");
  					return false;
  				}
				if(t5.length == 0){
  					$("#tip5").html("请输入应用名称");
  					return false;
  				}
				if(t6.length == 0){
  					$("#tip6").html("请输入应用ID");
  					return false;
  				}
  				$.dialog.list["forAddInfo"].close();
  				document.addForm.action="commend_add.do";
	        	document.addForm.submit();
  			},
  			init: function () {
  				$("#acommendType option").remove();
				$.post("<c:url value='/system/app/appClass_all.do' />",function(data){
					if(data!=null){
						for(var i in data){
							var ddl = $("#acommendType");
							ddl.append("<option value="+data[i].classId+">"+data[i].className+"</option>");
						}
					}
				},"json");
		    },
    		cancelVal: '&nbsp;关闭&nbsp;',
    		cancel: true 
  		});
	}
	
	//修改
  	function showInfoEdit(form){
  		$("#tip1").html("");
  		$("#tip2").html("");
  		$("#tip3").html("");
  		var id = getCheckedValue(form);
  		if(isNotNull(id)){
  			$.dialog({
	  			id: "forEditInfo",
	  			title: "修改",
	  			content: document.getElementById("forEditInfo"),
	  			ok: function(){
	  				$("#tip1").html("");
			  		$("#tip2").html("");
			  		$("#tip3").html("");
	  				var t1 = $("#commendType").val();
					var t2 = $("#commendName").val();
					var t3 = $("#commendUrl").val();
					if(t1 == null){
	  					$("#tip1").html("请选择类型");
	  					return false;
	  				}
					if(t2.length == 0){
	  					$("#tip2").html("请输入应用名称");
	  					return false;
	  				}
					if(t3.length == 0){
	  					$("#tip3").html("请输入应用ID");
	  					return false;
	  				}
	  				$.dialog.list["forEditInfo"].close();
	  				document.editForm.action="commend_edit.do?commendId="+id;
	        		document.editForm.submit();
	  			},
	  			init: function () {
	  				$("#commendType option").remove();
	  				$.post("<c:url value='/system/app/appClass_all.do' />",function(data){
						if(data!=null){
							for(var i in data){
								var ddl = $("#commendType");
								ddl.append("<option value="+data[i].classId+">"+data[i].className+"</option>");
							}
						}
					},"json");
					$.post("<c:url value='/system/app/commend_view.do' />",{commendId : id},function(data){
						if(data!=null){
							$("#commendType").val(data.commendType),
							$("#commendName").val(data.commendName),
							$("#commendUrl").val(data.commendUrl)
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
				window.location.href="<c:url value='/system/app/commend_delete.do?commendId="+ id +"'/>";
			});
		}	
	}
	
	</script>
	</head>
  <body>
  <form action="<c:url value='commend_list.do'/>" name="form" id="form" method="post">
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
					
						<pg:button type="button" action="commend_edit" onclick="showInfoEdit(this.form);" value="修 改" />
						
					</div>
					<div style="float: right; white-space: nowrap;">
				  		类别名：<input name="className" type="text" value="${className}" />
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
				<td width="10%"><b>类别名称</b></td>
				<td width="30%"><b>应用名称</b></td>
				<td width="30%"><b>应用ID</b></td>
				<td width="10%"><b>图片预览</b></td>
				<td width="10%"><b>创建时间</b></td>
				</tr>
				<c:if test="${!empty pagination.list}">
   				<c:forEach items="${pagination.list}" var="item">
				<tr style="background-color:#F9FBFC">
				<td>${item.commendId}</td>
				<td><input type="checkbox" id="id" name="id" value="${item.commendId}" /></td>
				<td>${item.className}</td>
				<td>${item.commendName}</td>
				<td>${item.commendUrl}</td>
				<td><c:if test="${item.commendImg != '' && item.commendImg != null}"><a href="<c:url value='${item.commendImg}'/>" target=""/></c:if>预览</td>
				<td>${item.createTime}</td>
				</tr>
				 </c:forEach>
   		  		</c:if>
				<tr>
				<td colspan="7" align="center" id="dg1_PageBar">
				<pg:zPage baseurl="commend_list.do?className=${className}" pagination="${pagination}"/>
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
  <form action="" method="post" id="editForm" name="editForm" enctype="multipart/form-data">
    <table width="100%" border="0" align="center" cellpadding="4" cellspacing="4" >
	  <tr>
	    <td align="right">类别：</td>
	    <td align="left">
			<select id="commendType" name="commendType"></select>&nbsp;<span style="color: red;" id="tip1"></span>
	    </td>
	  </tr>
	  <tr>
	    <td align="right">应用名称：</td>
	    <td align="left">
			<input type="text" id="commendName" name="commendName" value=""/>&nbsp;<span style="color: red;" id="tip2"></span>
	    </td>
	  </tr>
	  <tr>
	    <td align="right">：应用ID</td>
	    <td align="left">
	    	<input type="text" id="commendUrl" name="commendUrl" size="60" value=""/>&nbsp;<span style="color: red;" id="tip3"></span>
	    </td>
	  </tr>
	  <tr>
	    <td align="right">上传图片：</td>
	    <td align="left">
	    	<input type="file" id="file" name="file" />
	    </td>
	  </tr>
	  </table>
	</form>
	</div>
	
	<!-- 添加信息弹出层 -->
  <div id="forAddInfo" style="display: none;">
  <form action="" method="post" id="addForm" name="addForm" enctype="multipart/form-data">
    <table width="100%" border="0" align="center" cellpadding="4" cellspacing="4" >
      <tr>
	    <td align="right">类别：</td>
	    <td align="left">
			<select id="acommendType" name="acommendType"></select>&nbsp;<span style="color: red;" id="tip4"></span>
	    </td>
	  </tr>
	  <tr>
	    <td align="right">应用名称：</td>
	    <td align="left">
			<input type="text" id="acommendName" name="acommendName" value=""/>&nbsp;<span style="color: red;" id="tip5"></span>
	    </td>
	  </tr>
	  <tr>
	    <td align="right">应用ID：</td>
	    <td align="left">
	    	<input type="text" id="acommendUrl" name="acommendUrl" size="60" value=""/>&nbsp;<span style="color: red;" id="tip6"></span>
	    </td>
	  </tr>
	  <tr>
	    <td align="right">上传图片：</td>
	    <td align="left">
	    	<input type="file" id="afile" name="afile" />
	    </td>
	  </tr>
	  </table>
	</form>
	</div>
	
  </body>
</html>

