<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
  <head>
	<title>管理中心登陆</title>
	<meta http-equiv=Content-Type content="text/html; charset=utf-8" />
	<link rel="icon" href="../pic/favicon.ico" type="image/x-icon" />
	<style type="text/css">
	  td,input,button,select,body {font-family:"lucida Grande",Verdana;font-size:12px;}
	  .welcomeinfo {font:bold 16px "lucida Grande",Verdana;height:39px;margin:0 0 0 118px;}
	  .t_left1 {margin-left:17px;}
	  .nowrap {white-space:nowrap; text-decoration:none}
	  .f_size,.f_sizetd {font-size:12px;}
	  .normal {font-weight:normal}
	  img {border:none}
	  a {text-decoration:none;cursor:pointer;}
	  .level {margin-bottom:6px;margin-left:1px;font:normal 12px "lucida Grande",Verdana;clear:both;}
	  .level_no {margin-top:8px;margin-bottom:17px;}
	  .leftpanel {margin:0 0 14px 0;padding:0;list-style:none;}
	  .left {float:left}
	  .right {float:right}
	  .todaybody {overflow:auto;overflow-x:hidden}
	  ul.tipbook {border-top:1px solid #d8dee5;margin:0 13px;padding:18px 0 0 2px;list-style:none;}
	  ul.tipbook li {height:28px;}
	  .tipstitle_title {font:normal 14px Verdana;margin-bottom:5px;padding-left:13px;padding-top:8px;}
	  .ico_input {border:none;padding:0;margin:0;width:16px;height:16px;}
      .ico_helpmin {background:url(../images/system/help.gif) no-repeat;margin:0 6px 1px 0}
      .ico_bbsmin {background:url(../images/system/bbs.gif) no-repeat;margin:0 6px 1px 0}
	  
	A:link {
		COLOR: #0a4173;
	}
	A:visited {
		COLOR: #0a4173;
	}
	A:hover {
		COLOR: #1274BA;
	}
	A:active {
		COLOR: #1274BA;
	}
	.reverse a:link, .reverse a:visited, .reverse a:active{
		color:#1274BA;
	}
	.reverse:hover{
		color:white; background:#1274BA;
	}
	.emphasize{
		color:#E83820;
	}
	.colordiv{
		line-height:25px; background:#F5FBFF; text-align:left;
	}
	.toptitle, .toptitle a:link, .toptitle a:visited{
		background-image:url(bg_top.gif);
	}
	.toptitle a:hover{
		background-image:url(bg_top2.gif);
	}
	.toptitle_r, .toptitle_r a:link, .toptitle_r a:visited{
		background-image:url(bg_top.gif);
	}
	.toptitle_r a:hover{
		background-image:url(bg_top2.gif);
	}
	.topline{
		background-color:#93b9dc
	}
	.lefttab{
		background-image:url(bg_left.gif);
	}
	.lefttab2{
		background:url(bg_left2.gif) no-repeat left top; border:1px #FFF solid;
	}
	.tabOn{
		background-image:url(tabon.gif);
	}
	.tabOff{
		background-image:url(taboff.gif);
	}
	.tabOn6{
		background-image:url(tabon6.gif);
	}
	.tabOff6{
		background-image:url(taboff6.gif);
	}
	.column{
		border:1px #93b9dc solid;
	}
	.columntitle{
		background:#bfd3e6; border-bottom:1px #93b9dc solid; color:#000000;
	}
	.columntitle span{
		color:#000000;
	}
	.columnsubtitle{
		background:#F5FBFF; border-top:1px #d5e3ef solid; color:#0A4173; 
	}
	.separator{
		background:#eff2f4;
	}
	.colorfocus {
		border: 1px #86a1ba double;
		background-color: #fff8e6;
	}
	.colorblur {
		border: 1px #86a1ba double;
		background-color: #ffffff;
	}
	.button{border:1px #93b9dc solid;background:url(../images/system/button_bg.gif) repeat;}
	.summary-title { background:#f0f6fc; border-top:1px #d5e3ef solid; }
	.tdbg-dark {background: #d5e3ef;}
  </style>
  </head>
  <body class="todaybody">
	<ul class="leftpanel" style="margin-bottom:0;border:none;">
    <li style="padding-right:200px;height:auto;margin-top: 20px;" id="TodayWelcome" class="welcomeinfo t_left1">
	<script>
		var hour = (new Date()).getHours();
		if (hour < 4) {
			hello = "夜深了，";
		}
		else if (hour < 7) {
			hello = "早安，";
		}
		else if (hour < 9) {
			hello = "早上好，"; 
		}
		else if (hour < 12) {
			hello = "上午好，";
		}
		else if (hour < 14) {
			hello = "中午好，";
		}
		else if (hour < 17) {
			hello = "下午好，";
		}
		else if (hour < 19) {
			hello = "您好，";
		}
		else if (hour < 22) {
			hello = "晚上好，";
		}
		else {
			hello = "夜深了，";
		}
		document.write(hello);
	</script>${super_user.realName}<span class="f_size normal addrtitle" id="spanGreeting">！</span><span id="accountType"></span>
	<div class="oneheight"></div>
	<div class="level level_no" style="line-height:26px;">欢迎使用智能机顶盒AppStore管理后台，当前登录IP：<%=request.getRemoteAddr() %>。<br />
    </div>
    </li>
       </ul>
		<div class="clr" style="height: 23px; margin: 15px 0 0 10px">
			<div class="left" style="margin: 2px 0 0 0">
				
			</div>
		</div>
	</body>
</html>

