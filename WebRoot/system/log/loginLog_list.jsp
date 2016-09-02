<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<%@ taglib uri="/WEB-INF/tag/pages.tld" prefix="pg"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
	<title>登录日志</title>
	<meta http-equiv=Content-Type content="text/html; charset=utf-8" />
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/style_sys.css'/>" />
  </head>
  <body>

  <form action="<c:url value='loginLog_list.do'/>" name="form" id="form" method="post">
  <table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
		<tr valign="top">
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
				<tr>
					<td height="30" valign="middle" class="blockTd"><img src="<c:url value='/images/system/role.gif'/>" /> 登录日志列表</td>
				</tr>
				<tr>
					<td style="padding:0 8px 4px;">
					<div style="float: left; white-space: nowrap;">
					  登录名：
					  <input type="text" id="loginName" name="loginName" value="${loginName}"/>
					  登录状态：
					  <input type="text" id="loginStatus" name="loginStatus" value="${loginStatus}"/>
					  登录时间：
					  <input type="text" id="operatorTime" name="operatorTime" value="${operatorTime}"/> 至 
		      		  <input type="text" id="operatorTime" name="operatorTime" value="${operatorTime}"/>
	            	  <input type="button" class="inputButton" name="Submitbutton" id="Submitbutton" value="查询" onclick="form.submit();" />
					</div>
					<div style="float: right; white-space: nowrap;">
            	</div>
				</td>
			</tr>
			<tr>
				<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
				<table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
				<tr class="dataTableHead">
					<td width="8%">序号</td>
					<td width="15%">登录名</td>
			    	<td width="20%">登录状态</td>
			    	<td width="15%">登录IP</td>
			   		<td width="42%">登录时间</td>
				</tr>
				<c:if test="${!empty pagination.list}">
   				<c:forEach items="${pagination.list}" var="loginLog">
				<tr style="background-color:#F9FBFC">
					<td>${loginLog.logId}</td>
				    <td>${loginLog.loginName}</td>
				    <td>${loginLog.loginStatus}</td>
				    <td>${loginLog.loginIP}</td>
				    <td>${fn:substring(loginLog.loginTime,0,19)}</td>
				</tr>
				 </c:forEach>
   		  		</c:if>
				<tr>
				<td colspan="5" align="center" id="dg1_PageBar">
				<pg:zPage baseurl="loginLog_list.do?loginName=${loginName}&loginStatus=${loginStatus}" pagination="${pagination}"/>
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
  
  </body>
</html>

