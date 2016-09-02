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
			$(".add_tip").text("");
			$(".add").val("");
			$.dialog({
				id: "forAddInfo",
				title: "添加",
				content: document.getElementById("forAddInfo"),
				ok: function(){
					$(".add_tip").text("");

					var $add = $(".add");
					for (var i = 0;i<$add.size();i++){
						if ($add[i].value==''){
							$(".add_tip")[i].innerText="不能为空";
							return false;
						}
						if (i==2){
							var status_value = $add[i].value;
							if(status_value!="1"&&status_value!="0"){
								$(".add_tip")[i].innerText="必须为1或0";
								return false;
							}
						}
					}


					$.dialog.list["forAddInfo"].close();
					document.addForm.action="packgeinfo_add.do";
					document.addForm.submit();

				},
				cancelVal: '&nbsp;关闭&nbsp;',
				cancel: true
			});
		}

		//修改
		function showInfoEdit(form){
			$(".edit_tip").text("");
			$(".edit").val("");
			var id = getCheckedValue(form);
			if(isNotNull(id)){
				$.dialog({
					id: "forEditInfo",
					title: "修改",
					content: document.getElementById("forEditInfo"),
					ok: function(){
						$(".edit_tip").text("");
						var $edit = $(".edit");
						for (var i = 0;i<$edit.size();i++){
							if ($edit[i].value==''){
								$(".edit_tip")[i].innerText="不能为空";
								return false;
							}
							if (i==2){
								var status_value = $edit[i].value;
								if(status_value!="1"&&status_value!="0"){
									$(".edit_tip")[i].innerText="必须为1或0";
									return false;
								}
							}
						}
						$.dialog.list["forEditInfo"].close();
						document.editForm.action="packgeInfo_edit.do?packgeId="+id;
						document.editForm.submit();
					},
					init: function () {
						$.post("${pageContext.request.contextPath}/system/app/packgeInfo_view.do",{'packgeId' : id},function(data){
							if(data!=null){
								var $edit = $(".edit");
								data = eval("("+data+")");
								$edit[0].value =data.packgeName;
								$edit[1].value =data.packgeType;
								$edit[2].value =data.packgeStatus;
								$edit[3].value =data.packgePrice;
								$edit[4].value =data.packgeUrl;
								$edit[5].value =data.spId;
								$edit[6].value =data.productId;
							};
						});
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
					window.location.href="<c:url value='/system/app/packgeInfo_delete.do?packgeId="+ id +"'/>";
				});
			}
		}

	</script>
</head>
<body>
<form action="<c:url value='packgeInfo_list.do'/>" name="form" id="form" method="post">
	<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
		<tr valign="top">
			<td>
				<table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
					<tr>
						<td height="30" valign="middle" class="blockTd"><img src="<c:url value='/images/system/role.gif'/>" /> 列表</td>
					</tr>
					<tr>
						<td style="padding:0 8px 4px;">
							<div style="float: left; white-space: nowrap; ">
								<pg:button type="button" action="appClass_add" onclick="showInfoAdd();" value="新 增" />
								<pg:button type="button" action="appClass_edit" onclick="showInfoEdit(this.form);" value="修 改" />
								<pg:button type="button" action="appClass_delete" onclick="showInfoDelete(this.form);" value="删 除" />
							</div>
							<div style="float: right; white-space: nowrap;">
								专区名称：<input name="packgeName" type="text" value="${packgeName}" />
								<input type="button" class="inputButton" name="Submitbutton" id="Submitbutton" value="查询" onclick="form.submit();" />
							</div>
						</td>
					</tr>
					<tr>
						<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
							<table width="100%" cellpadding="2" cellspacing="0" class="dataTable" id="dg1">
								<tr class="dataTableHead">
									<td width="5%">专区ID</td>
									<td width="2%"><input type="checkbox" id="chkAll" name="chkAll" onclick="checkall(this.form);"/></td>
									<td width="10%"><b>专区名称</b></td>
									<td width="8%"><b>专区类型</b></td>
									<td width="8%"><b>专区状态</b></td>
									<td width="8%"><b>包月价格(元)</b></td>
									<td width="19%"><b>专区URL</b></td>
									<td width="10%"><b>创建时间</b></td>
									<td width="10%"><b>SP_ID</b></td>
									<td width="10%"><b>产品ID</b></td>
								</tr>
								<c:if test="${!empty pagination.list}">

									<c:forEach items="${pagination.list}" var="item">
										<tr style="background-color:#F9FBFC">
											<td>${item.packgeId}</td>
											<td><input type="checkbox" id="id" name="id" value="${item.packgeId}" /></td>
											<td>${item.packgeName}</td>
											<td>${item.packgeType}</td>
											<td>
												<c:choose>
													<c:when test="${item.packgeStatus=='1'}">
														生效
													</c:when>
													<c:otherwise>
														失效
													</c:otherwise>
												</c:choose>
											</td>
											<td>${item.packgePrice}</td>
											<td>${item.packgeUrl}</td>
											<td>${item.packgeCritme}</td>
											<td>${item.spId}</td>
											<td>${item.productId}</td>
										</tr>
									</c:forEach>
								</c:if>
								<tr>
									<td colspan="10" align="center" id="dg1_PageBar">
										<pg:zPage baseurl="query_appvip.do?className=${className}" pagination="${pagination}"/>
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
				<td align="right">专区名称：</td>
				<td align="left">
					<input type="text" size="20" class="edit" name="packgeName" />&nbsp;<span style="color: red;" class="edit_tip" ></span></td>
			</tr>
			<tr>
				<td align="right">专区类型：</td>
				<td align="left">
					<input type="text" size="20" class="edit" name="packgeType" />&nbsp;<span style="color: red;" class="edit_tip"></span></td>
			</tr>
			<tr>
				<td align="right">专区状态(1生效0失效)：</td>
				<td align="left">
					<input type="text" size="20" class="edit" name="packgeStatus" />&nbsp;<span style="color: red;" class="edit_tip"></span></td>
			</tr>
			<tr>
				<td align="right">包月价格(元)：</td>
				<td align="left">
					<input type="text" size="20" class="edit" name="packgePrice" />&nbsp;<span style="color: red;" class="edit_tip"></span></td>
			</tr>
			<tr>
				<td align="right">专区URL：</td>
				<td align="left">
					<input type="text" size="20" class="edit" name="packgeUrl" />&nbsp;<span style="color: red;" class="edit_tip"></span></td>
			</tr>
			<tr>
				<td align="right">SP_ID：</td>
				<td align="left">
					<input type="text" size="20" class="edit" name="spId" />&nbsp;<span style="color: red;" class="edit_tip"></span></td>
			</tr>
			<tr>
				<td align="right">产品ID：</td>
				<td align="left">
					<input type="text" size="20" class="edit" name="productId" />&nbsp;<span style="color: red;" class="edit_tip"></span></td>
			</tr>
		</table>
	</form>
</div>

<!-- 添加信息弹出层 -->
<div id="forAddInfo" style="display: none;">
	<form action="" method="post" id="addForm" name="addForm">
		<table width="100%" border="0" align="center" cellpadding="4" cellspacing="4" >
			<tr>
				<td align="right">专区名称：</td>
				<td align="left">
					<input type="text" size="20" class="add" name="packgeName" />&nbsp;<span style="color: red;" class="add_tip" ></span></td>
			</tr>
			<tr>
				<td align="right">专区类型：</td>
				<td align="left">
					<input type="text" size="20" class="add" name="packgeType" />&nbsp;<span style="color: red;" class="add_tip"></span></td>
			</tr>
			<tr>
				<td align="right">专区状态(1生效0失效)：</td>
				<td align="left">
					<input type="text" size="20" class="add" name="packgeStatus" />&nbsp;<span style="color: red;" class="add_tip"></span></td>
			</tr>
			<tr>
				<td align="right">专区价格(元)：</td>
				<td align="left">
					<input type="text" size="20" class="add" name="packgePrice" />&nbsp;<span style="color: red;" class="add_tip"></span></td>
			</tr>
			<tr>
				<td align="right">专区URL：</td>
				<td align="left">
					<input type="text" size="20" class="add" name="packgeUrl" />&nbsp;<span style="color: red;" class="add_tip"></span></td>
			</tr>
			<tr>
				<td align="right">SP_ID：</td>
				<td align="left">
					<input type="text" size="20" class="add" name="spId" />&nbsp;<span style="color: red;" class="add_tip"></span></td>
			</tr>
			<tr>
				<td align="right">产品ID：</td>
				<td align="left">
					<input type="text" size="20" class="add" name="productId" />&nbsp;<span style="color: red;" class="add_tip"></span></td>
			</tr>
		</table>
	</form>
</div>

</body>
</html>

