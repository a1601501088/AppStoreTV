<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'goodsDetail.jsp' starting page</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />


  </head>
  
<body style="background-color:#0653A5;text-align:center;">
 <div >
<!--<img src="<%=basePath%>/images/system/loading.gif" /> -->
<input id="result" type="hidden" value="${result}"/>
<input id="desc" type="hidden" value="${desc}"/>
<input id="transationId" type="hidden" value="${transactionId}"/>
<script>
	var  result = "0";
	var  desc="支付成功";
	var transationId="";
    function funFromjs(){
		transationId=document.getElementById("transationId").value;
		result=document.getElementById("result").value;
		desc=document.getElementById("desc").value;
		AppFunction.mySubmit(transationId,result,desc);
    	
    }

    var aTag = document.getElementById('welcome');

    
    </script>
</div>
</body>

</html>

