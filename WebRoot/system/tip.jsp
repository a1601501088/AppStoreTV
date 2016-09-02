<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
	<title>信息提示</title>
	<meta http-equiv=Content-Type content="text/html; charset=utf-8" />
	<%@ include file="/system/head.jsp" %>
	<script type="text/javascript">
		$(function(){
			$.dialog({
			    content: '${Msg}',
			    icon: 'warning',
			    width: 200,
			    ok: function () {
			    	window.location.href="<c:url value='${Url}'/>";
			    }
			});
		});
    </script>
  </head>
  
  <body>
	 
  </body>
</html>
