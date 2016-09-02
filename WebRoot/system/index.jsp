<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
  <head>
	<title>应用支撑平台v1.0</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link rel="icon" href="../pic/favicon.ico" type="image/x-icon" />
	<link rel="shortcut icon" href="../pic/favicon.ico" type="image/x-icon" />
	<script type="text/javascript">
	  if(top.location != self.location){
		top.location=self.location;
	  }
	  function changeDisplayMode(){
		if(document.getElementById("bottomframes").cols=="192,7,*"){
			document.getElementById("bottomframes").cols="0,7,*"; 
			document.getElementById("separator").contentWindow.document.getElementById('ImgArrow').src="../images/system/separator2.gif"
		}else{
			document.getElementById("bottomframes").cols="192,7,*"
			document.getElementById("separator").contentWindow.document.getElementById('ImgArrow').src="../images/system/separator1.gif"
		}
	  }
	</script>
  </head>
  <frameset id="mainframes" border="false" framespacing="0" rows="72,*" frameborder="no">
	<frame id="top" name="top" src="top.jsp" scrolling="no" />
	<frameset id="bottomframes" border="false" framespacing="0" frameborder="no" cols="192,7,*">
	  <frame id="left" name="left" marginwidth="0" marginheight="0" src="<c:url value='/system/menu/sys_menu.do'/>" noresize="noresize" scrolling="auto" />
	  <frame id="separator" name="separator" src="separator.jsp" scrolling="no" noresize="noresize" />
	  <frame id="right" name="right" src="main.jsp" />
	</frameset>
  </frameset>
  <noframes>
	<body>
	  <p>此网页使用了框架，但您的浏览器不支持框架。</p>
	</body>
  </noframes>
</html>










