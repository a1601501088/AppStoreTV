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
	
	function showViewInfo(id){
  		/* var id = getCheckedValue(form); */
  		if(isNotNull(id)){
  			$.dialog({
	  			id: "forViewInfo",
	  			title: "详情",
	  			content: document.getElementById("forViewInfo"),
	  			init: function () {
					$.post("<c:url value='/system/app/appInterface_view.do' />",{id : id},function(data){
						if(data!=null){
							var contentType=data.contentType;
							var contentName="";
							if(contentType=="0"){
								contentName="vod（普通节目）";
							}
							if(contentType=="1"){
								contentName="channel（频道）";
							}
							if(contentType=="2"){
								contentName="tvod（回看）";
							}
							$("#transactionId").html(data.transactionId),
							$("#spId").html(data.spId),
							$("#userId").html(data.userId),
							$("#userToken").html(data.userToken),
							$("#key").html(data.key),
							$("#productId").html(data.productId),
							$("#price").html(data.price),
							$("#productName").html(data.productName),
							$("#backUrl").html(data.backUrl),
							$("#notifyUrl").html(data.notifyUrl),
							$("#optFlag").html(data.optFlag!=""?(data.optFlag=="VAS"?"增值业务":"广电EPG"):""),
							$("#purchaseType").html(data.purchaseType),
							$("#categoryId").html(data.categoryId),
							$("#contentId").html(data.contentId),
							$("#contentType").html(contentName),
							$("#result").html(data.result=="0"?"成功":data.result),
							$("#resultDesc").html(data.resultDesc),
							$("#createTime").html(data.createTime),
							$("#updateTime").html(data.updateTime)
						}
					},"json");
			    },
	    		cancelVal: '&nbsp;关闭&nbsp;',
	    		cancel: true 
	  		});
  		}
	}
	
	</script>
	</head>
  <body>
  <form action="<c:url value='appInterface_list.do'/>" name="form" id="form" method="post">
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
						<!-- <pg:button type="button" action="appInterface_view" onclick="showViewInfo(this.form);" value="查 看" /> -->
					</div>
					<div style="float: right; white-space: nowrap;">
				  		关键字：<input name="msgName" type="text"  value="${msgName}" alt="结果    transId 产品标识"/>
            	  		<input type="button" class="inputButton" name="Submitbutton" id="Submitbutton" value="查询" onclick="form.submit();" />
            		</div>
				</td>
			</tr>
			<tr>
				<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
				<table width="100%" cellpadding="2" cellspacing="0" class="dataTable" id="dg1">
				<tr class="dataTableHead">
				<!-- <td width="5%"><input type="checkbox" id="chkAll" name="chkAll" onclick="checkall(this.form);"/></td> -->
				<td width="28%"><b>transId</b></td>
				<td width="5%"><b>用户标识</b></td>
				<td width="5%"><b>SP标识</b></td>
				<td width="15%"><b>产品名称</b></td>
				<td width="5%"><b>价格</b></td>
				<td width="30%"><b>结果</b></td>
				<td width="12%"><b>订购时间</b></td>
				</tr>
				<c:if test="${!empty pagination.list}">
   				<c:forEach items="${pagination.list}" var="item">
				<tr style="background-color:#F9FBFC">
				<%-- <td><input type="checkbox" id="id" name="id" value="${item.transactionId}" /></td> --%>
				<td><a href="#" onclick="showViewInfo('${item.transactionId}')">${item.transactionId}</a></td>
				<td>${item.userId}</td>
				<td>${item.spId}</td>
				<td>${item.productName}</td>
				<td>${item.price}</td>
				<td><c:if test="${item.result=='0'}">成功</c:if><c:if test="${item.result!='0'}">${item.result}</c:if></td>
				<td>${item.createTime}</td>
				</tr>
				 </c:forEach>
   		  		</c:if>
				<tr>
				<td colspan="4" align="center" id="dg1_PageBar">
				<pg:zPage baseurl="appInterface_list.do?msgName=${msgName}" pagination="${pagination}"/>
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
	 <div id="forViewInfo" style="display: none;">
    <table width="100%" border="0" align="center" cellpadding="4" cellspacing="4" >
	  <tr><td><b>transId</b></td><td><span id="transactionId"></span></td><td><b>SP标识</b></td><td><span id="spId"></span></td></tr>
	  	<tr><td><b>用户标识</b></td><td><span id="userId"></span></td><td><b>用户token</b></td><td><span id="userToken"></span></td></tr>
	  	<tr><td><b>key</b></td><td><span id="key"></span></td><td><b>产品标识</b></td><td><span id="productId"></span></td></tr>
	  	<tr><td><b>价格</b></td><td><span id="price"></span></td><td><b>产品名称</b></td><td><span id="productName"></span></td></tr>
	  	<tr><td><b>返回URL</b></td><td><span id="backUrl"></span></td><td><b>通知URL</b></td><td><span id="notifyUrl"></span></td></tr>
	  	<tr><td><b>产品类型</b></td><td><span id="optFlag"></span></td><td><b>支付类型</b></td><td><span id="purchaseType"></span></td></tr>
	  	<tr><td><b>栏目标识</b></td><td><span id="categoryId"></span></td><td><b>内容标识</b></td><td><span id="contentId"></span></td></tr>
	  	<tr><td><b>内容类型</b></td><td><span id="contentType"></span></td><td><b>结果</b></td><td><span id="result"></span></td></tr>
	  	<tr><td><b>更新时间</b></td><td><span id="updateTime"></span></td><td><b>订单时间</b></td><td><span id="createTime"></span></td></tr>
	  	<tr><td><b>结果说明</b></td><td colspan="3"><span id="resultDesc"></span></td></tr>
	  </table>
	</div>
	<!-- 添加信息弹出层 -->
</html>

