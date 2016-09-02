<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/6/8
  Time: 17:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>童锁</title>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/service/css/vChip.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.4.1/jquery.min.js"></script>
    <script type="text/javascript">
        $(function () {
            $(".pass").first().addClass("border").focus();
            $(".pass,a").focusin(function () {
                $(".pass,a").css("z-index", "0");
                $(".pass,a").removeClass("border");
                $(this).addClass("border");
            })
            $(".pass").focus(function () {
                $("#tip1,#tip2").css("display", "none");
            });
        })
        function vchipConfirm() {

            var $pass = $(".pass");
            var $tip2 = $("#tip2")
            var $tip1 = $("#tip1")
            if ($pass[0].value.length != 0) {
                if ($pass[0].value.length != 4) {
                    $tip1.text("格式错误").css("display", "inline");
                    return false;
                }
            }

            if ($pass[2].value.length != 4) {
                $tip2.text("格式错误").css("display", "inline");
                return false;
            }
            if ($pass[1].value != $pass[2].value) {
                $tip2.text("密码不一至").css("display", "inline");
                return false;
            }
            var newPassword = $pass[1].value;
            var password = $pass[0].value;

            var url = "${pageContext.request.contextPath}/service/modLimitation.do";
            $.ajax({
                url: url,
                data: {
                    "spId": "${spId}",
                    "userId": "${userId}",
                    "userIdType": "${userIdType}",
                    "userToken": "${userToken}",
                    "newPassword": newPassword,
                    "password": password
                },
                dataType: 'json',
                type: 'post',
                success: function (data) {
                    if (data.code == '200') {
                        $("#tip2").text("修改成功").css({"display": "inline", "color": "green"});
                        $(".pass").text("");
                        setTimeout(function () {
                            var url = "${pageContext.request.contextPath}/service/queryOrderInfo.do?spId=${spId}&userId=${userId}&userIdType=${userIdType}&userToken=${userToken}";
                            window.location.href = url;
                        }, 3000);
                    } else {
                        if ('童锁密码错误' == data.data.description) {
                            $("#tip1").text("密码错误").css("display", "inline");
                        } else {
                            $("#tip1").text("修改失败").css("display", "inline");
                        }
                    }
                }
            });

        }
        function vchipretrun() {
            var url = "${pageContext.request.contextPath}/service/queryOrderInfo.do?spId=${spId}&userId=${userId}&userIdType=${userIdType}&userToken=${userToken}";
            window.location.href = url;
        }
    </script>
</head>
<body>
<div id="content">

    <input class="pass" type="text"> <span id="tip1">密码错误</span>

    <input class="pass" type="password">

    <input class="pass" type="password"> <span id="tip2">密码不一至</span>

    <div id="vchipConfirm">
        <a href="#" onclick="return vchipConfirm()">
            <img src="${pageContext.request.contextPath}/service/image/btn_trans.gif" height="37" border="0"
                 width="100">
        </a>
    </div>

    <div id="vchipretrun">
        <a href="#" onclick="vchipretrun()">
            <img src="${pageContext.request.contextPath}/service/image/btn_trans.gif" height="37" border="0"
                 width="100">
        </a>
    </div>
</div>
</body>
</html>
