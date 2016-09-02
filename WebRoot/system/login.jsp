<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>管理中心登陆</title>
    <meta http-equiv=Content-Type content="text/html; charset=utf-8"/>
    <style type="text/css">
        body {
            margin: 0px
        }

        p {
            margin: 0px
        }

        body {
            color: #000;
            background-color: #fff
        }

        body {
            font-size: 12px;
            line-height: 150%;
            font-family: "verdana", "arial", "helvetica", "sans-serif"
        }

        table {
            font-size: 12px;
            line-height: 150%;
            font-family: "verdana", "arial", "helvetica", "sans-serif"
        }

        input {
            font-size: 12px;
            font-family: "verdana", "arial", "helvetica", "sans-serif"
        }

        select {
            font-size: 12px;
            font-family: "verdana", "arial", "helvetica", "sans-serif"
        }

        textarea {
            font-size: 12px;
            font-family: "verdana", "arial", "helvetica", "sans-serif"
        }

        a:link {
            color: #036;
            text-decoration: none
        }

        a:visited {
            color: #036;
            text-decoration: none
        }

        a:hover {
            color: #f60;
            text-decoration: underline
        }

        a.menuchild:link {
            color: #036;
            text-decoration: none
        }

        a.menuchild:visited {
            color: #036;
            text-decoration: none
        }

        a.menuchild:hover {
            color: #f60;
            text-decoration: underline
        }

        a.menuparent:link {
            color: #000;
            text-decoration: none
        }

        a.menuparent:visited {
            color: #000;
            text-decoration: none
        }

        a.menuparent:hover {
            color: #f60;
            text-decoration: none
        }

        table.position {
            width: 100%
        }

        tr.position {
            height: 25px;
            background-color: #f4f7fc
        }

        td.position {
            border-right: #adceff 1px solid;
            padding-left: 20px;
            border-bottom: #adceff 1px solid
        }

        table.listtable {
            width: 98%;
            background-color: #b1ceee
        }

        tr.listheadertr {
            font-weight: bold;
            height: 25px;
            background-color: #ebf4fd;
            text-align: center
        }

        tr.listtr {
            height: 25px;
            background-color: #fff;
            text-align: center
        }

        tr.listalternatingtr {
            height: 25px;
            background-color: #fffdf0;
            text-align: center
        }

        tr.listfootertr {
            height: 30px;
            background-color: #ebf4fd;
            text-align: center
        }

        table.edittable {
            width: 98%;
            background-color: #b1ceee
        }

        tr.editheadertr {
            height: 25px;
            background-color: #ebf4fd
        }

        td.editheadertd {
            padding-left: 50px;
            font-weight: bold
        }

        tr.edittr {
            height: 30px
        }

        td.editlefttd {
            width: 150px;
            background-color: #fffdf0;
            text-align: center
        }

        td.editrighttd {
            padding-left: 10px;
            background-color: #fff
        }

        tr.editfootertr {
            height: 40px;
            background-color: #ebf4fd
        }

        td.editfootertd {
            padding-left: 150px
        }
    </style>
    <script type="text/javascript">
        if (top.location != self.location) {
            top.location = self.location;
        }
        //去除空格
        function replaces(str) {
            str = str.replace(/\s+/g, "");
            return str;
        }
        //登录验证
        function loginValidate() {
            var loginName = replaces(document.getElementById("loginName").value);
            if (loginName == null || loginName == "") {
                alert("请输入用户名");
                document.getElementById("loginName").focus();
                return false;
            }
            var password = replaces(document.getElementById("password").value);
            if (password == null || password == "") {
                alert("请输入密码");
                document.getElementById("password").focus();
                return false;
            }
        }
    </script>
</head>
<body style="background-color:#002779;">
<table border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
        <td align="center" height="600">
            <table width="468" border="0" align="center" cellpadding="0"
                   cellspacing="0">
                <tr>
                    <td>
                        <img height="23" src="../images/system/login_1.jpg" width="468"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <img height="147" src="../images/system/login_2.jpg" width="468"/>
                    </td>
                </tr>
            </table>
            <table width="468" border="0" align="center" cellpadding="0"
                   cellspacing="0" bgcolor="#ffffff">
                <tr>
                    <td width="16">
                        <img height="122" src="../images/system/login_3.jpg" width="16"/>
                    </td>
                    <td align="center">
                        <form name="loginForm" action="<c:url value='/system/login.do'/>" method="post">
                            <table cellspacing="0" cellpadding="0" width="230" border="0">
                                <tr>
                                    <td width="5"></td>
                                    <td width="56"></td>
                                    <td></td>
                                </tr>
                                <tr>
                                    <td></td>
                                    <td height="20" colspan="2">${errorInfo}</td>
                                </tr>
                                <tr>
                                    <td></td>
                                    <td height="35">
                                        用户名：
                                    </td>
                                    <td height="35">
                                        <input style="border-right: #CCC 1px solid; border-top: #CCC 1px solid; border-left: #CCC 1px solid; border-bottom: #CCC 1px solid; height: 20px; line-height: 20px;"
                                               maxlength="30" size="24" id="loginName" name="loginName"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>&nbsp;</td>
                                    <td height="35">口&nbsp;&nbsp;&nbsp;令：</td>
                                    <td height="35">
                                        <input style="border-right: #CCC 1px solid; border-top: #CCC 1px solid; border-left: #CCC 1px solid; border-bottom: #CCC 1px solid; height: 20px; line-height: 20px;"
                                               type="password" maxlength="30" size="24" id="password" name="password"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="3"></td>
                                </tr>
                                <tr>
                                    <td>&nbsp;</td>
                                    <td height="30">&nbsp;</td>
                                    <td height="30" align="left"><input type="image" src="../images/system/bt_login.gif"
                                                                        onclick="return loginValidate();"/></td>
                                </tr>
                            </table>
                        </form>
                    </td>
                    <td width="16">
                        <img height="122" src="../images/system/login_4.jpg" width="16"/>
                    </td>
                </tr>
            </table>
            <table cellspacing="0" cellpadding="0" width="468" border="0">
                <tr>
                    <td>
                        <img height="16" src="../images/system/login_5.jpg" width="468"/>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
</body>
</html>

