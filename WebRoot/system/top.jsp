<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
  <head>
	<title>top</title>
	<meta http-equiv=Content-Type content="text/html; charset=utf-8" />
	<%@ include file="/system/head.jsp" %>
  </head>
  <body style="margin:0px;">
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="bluebg" style="background:#3388bb url(../images/system/vistaBlue.jpg) repeat-x left top;">
		<tr>
			<td height="70" valign="bottom">
			  <table height="70" border="0" cellpadding="0" cellspacing="0" style="position:relative;">
				<tr>
					<td style="padding:15;color: white;font-size: 20px;">智能机顶盒AppStore管理后台</td>
				</tr>
			  </table>
			</td>
			<td valign="bottom">
			  <div style="text-align:right; margin:0 20px 15px 0;">当前用户：<b>${super_user.realName}</b>
			    &nbsp;[&nbsp;<a href="<c:url value='/system/user/login_out.do'/>">退出登录</a>]</div>
			</td>
		</tr>
		<tr>
		  <td colspan="3" style="background-color:#93b9dc; height: 2px;"></td>
		</tr>
	</table>
  </body>
</html>