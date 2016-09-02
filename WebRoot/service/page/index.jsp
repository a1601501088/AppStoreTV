<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/5/30
  Time: 16:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <meta name="page-view-size" content="1280*720">

    <link rel="stylesheet" type="text/css" href="css/index.css" media="all">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/jquery-easyui-1.4.1/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/jquery-easyui-1.4.1/themes/icon.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/only.js"></script>
    <!--引入Jquery主文件-->
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.4.1/jquery.min.js"></script>
    <!--引入JqueryEasyUI主文件-->
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
    <script src="${pageContext.request.contextPath}/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript">
        $(function () {

            //订购列表数据
            dataList = ${dataList};

            dataList = eval(dataList);
            //var dataList="";
            // 页面行总条数
            pageLength = 9;

            // 页码索引
            pageIndex = 0;

            // 数据总数
            total = dataList.length;
            //alert(total);
            // 总页数
            pageTotal = Math.ceil(total / pageLength);
        })
        $(document).ready(function () {



            $(".main div a").focusin(function () {
                $(".main div a").css("z-index", "0");
                $(".main div a").removeClass("border");
                $(this).addClass("border");
            });
            $(".main div a").first().focus();
            if (total > 0 && total != 'undefind') {
                $("#content").css("display", "block");
            } else {
                $("#content").css("display", "none");
            }

        });

        function initPage() {
            showPageNum();
            showData();
            //初始化焦点
            //document.getElementById("orderquery").focus();
        }



        // 显示数据
        function showData() {
            for (var i = 0; i < pageLength; i++) {
                var tempIndex = pageIndex * pageLength + i;
                if (tempIndex < dataList.length) {
                    document.getElementById("serviceName_" + i).innerHTML = dataList[tempIndex].productName;
                    document.getElementById("monthFee_" + i).innerHTML = "";
                    var tempDate = dataList[tempIndex].expiredTime;

                    if (dataList[tempIndex].subscriptionExtend == 0) {

                        //document.getElementById("button_" + i).style.display = "none";
                        //tempDate = "自动续订";

                        //document.getElementById("buttonblue_cancelOrderImage_" + i).src = "image/cancelorder.png";
                        $("#buttonblue_"+i).css("display","block");
                    } else if (dataList[tempIndex].subscriptionExtend == 1) {
                        //tempDate = "没有自动续订";
                        // $(document.getElementById("buttonblue_cancelOrderImage_" + i)).hidden();
                        $(document.getElementById("buttonblue_cancelOrderImage_" + i)).parent().parent().remove();
                        //$(document.getElementById("buttonblue_cancelOrderImage_" + i)).parent().removeAttr("onclick");
                    }
                    else if (null != tempDate) {
                        document.getElementById("buttonblue_" + i).style.display = "none";
                        tempDate = tempDate.substring(0, 4) + "-" + tempDate.substring(4, 6) + "-" + tempDate.substring(6, 8)
                        document.getElementById("button_cancelOrderImage_" + i).src = "images/cancelordergray.png";
                        document.getElementById("button_" + i).style.display = "none";
                    }

                    document.getElementById("orderTime_" + i).innerHTML = dataList[tempIndex].expiredTime.substring(0, 4) + "-" +
                            dataList[tempIndex].expiredTime.substring(4, 6) + "-" + dataList[tempIndex].expiredTime.substring(6, 8);
                    if (dataList[tempIndex].subscriptionExtend == 0) {

                        //document.getElementById("button_" + i).style.display = "none";
                        //tempDate = "自动续订";
                        document.getElementById("orderTime_" + i).innerHTML = "自动续订";
                    }
                }
                else {
                    document.getElementById("serviceName_" + i).innerHTML = "";
                    document.getElementById("monthFee_" + i).innerHTML = "";
                    document.getElementById("orderTime_" + i).innerHTML = "";
                    $("#buttonblue_"+i).css("display","none");
                }
            }
        }

        function cancelOrder(obj) {
            var id = obj.id;
            var re = /\D*/g;
            var idNumber = id.replace(re, "");
            var tempIndex = pageIndex * pageLength + parseInt(idNumber);

            //取消订购
            window.location.href = "${pageContext.request.contextPath}/service/page/delOrderSingle.jsp?productID=" + dataList[tempIndex].productID + "&productName=" + escape(dataList[tempIndex].productName);

        }

        // 显示页码
        function showPageNum() {
            document.getElementById("t_page").innerHTML = pageTotal;
            if (null == dataList || dataList.length == 0) {
                document.getElementById("c_page").innerHTML = 0;
                return;
            }
            document.getElementById("c_page").innerHTML = pageIndex + 1;
        }

        // 上一页
        function pageUp() {
            if (pageIndex == 0) {
                return 0;
            }
            else {
                pageIndex--;
                showPageNum();
                showData();
            }
        }

        // 下一页
        function pageDown() {
            if (pageIndex == pageTotal - 1) {
                return 0;
            }
            else {
                pageIndex++;
                showPageNum();
                showData();
            }
        }

        //批量取消订购
        function toBatchCancelOrder() {
            var productIdList = "";
            for (var i = 0; i < dataList.length; i++) {
                var productID = dataList[i].productID;
                productIdList += "," + productID;
            }
            productIdList = productIdList.substr(1, productIdList.length);
            //全部取消订购
            window.location.href = "${pageContext.request.contextPath}/service/page/delOrderSingle.jsp?productIdList=" + productIdList + "&productName=" + escape("全部应用");


        }

        //消费清单
        function exchange() {

            $("#content").css("display", "block");
            window.location.href = "${pageContext.request.contextPath}/service/page/userDets.jsp";

        }
        function tovchipchange() {

            $("#content").css("display", "block");
            window.location.href = "${pageContext.request.contextPath}/service/page/vChip.jsp";
        }
    </script>


</head>
<body onload="initPage()" class="main">

<div style="left: 520px; width: 600px; top: 63px; text-align: left; position: absolute; font-weight: bold; color: rgb(100, 149, 237); font-size: 22px;"
     align="right">
    <label style="font-size: 22px;">(&nbsp;用户帐号:${userId}&nbsp;)</label>
</div>
<!-- 包月订购-->
<div id="payment" style="position: absolute; left: 55px; top: 125px; height: 50px;">
    <a href="#">
        <img src="image/btn_trans.gif" height="53" border="0" width="158">
    </a>
</div>
<!-- 链接消费清单-->
<div id="exchange" style="position: absolute; left: 55px; top: 180px; height: 50px;">
    <a href="#" onclick="exchange()">
        <img src="image/btn_trans.gif" height="53" border="0" width="158">
    </a>
</div>
<!-- 童锁-->
<div id="exmonexchange" style="position: absolute; left: 55px; top: 235px; height: 50px;">
    <a href="#" onclick="tovchipchange()">
        <img src="image/btn_trans.gif" height="50" border="0" width="158">
    </a>
</div>
<!--  中间内容 -->
<div id="content" class="main">


    <!-- 当前页  -->
    <div style="position: absolute; top: 645px; left: 1040px; font-weight: bold; color: rgb(100, 149, 237); font-size: 22px;">
        <span id="c_page">0</span>/<span id="t_page"></span></div>


    <div id="serviceName_0"
         style="position: absolute; left: 260px; top: 175px; width: 300px; height: 40px; color: white; overflow: hidden; font-size: 24px; font-weight: bold;"></div>
    <div id="serviceName_1"
         style="position: absolute; left: 260px; top: 225px; width: 300px; height: 40px; color: white; overflow: hidden; font-size: 24px; font-weight: bold;"></div>
    <div id="serviceName_2"
         style="position: absolute; left: 260px; top: 275px; width: 300px; height: 40px; color: white; overflow: hidden; font-size: 24px; font-weight: bold;"></div>
    <div id="serviceName_3"
         style="position: absolute; left: 260px; top: 325px; width: 300px; height: 40px; color: white; overflow: hidden; font-size: 24px; font-weight: bold;"></div>
    <div id="serviceName_4"
         style="position: absolute; left: 260px; top: 375px; width: 300px; height: 40px; color: white; overflow: hidden; font-size: 24px; font-weight: bold;"></div>
    <div id="serviceName_5"
         style="position: absolute; left: 260px; top: 425px; width: 300px; height: 40px; color: white; overflow: hidden; font-size: 24px; font-weight: bold;"></div>
    <div id="serviceName_6"
         style="position: absolute; left: 260px; top: 475px; width: 300px; height: 40px; color: white; overflow: hidden; font-size: 24px; font-weight: bold;"></div>
    <div id="serviceName_7"
         style="position: absolute; left: 260px; top: 525px; width: 300px; height: 40px; color: white; overflow: hidden; font-size: 24px; font-weight: bold;"></div>
    <div id="serviceName_8"
         style="position: absolute; left: 260px; top: 575px; width: 300px; height: 40px; color: white; overflow: hidden; font-size: 24px; font-weight: bold;"></div>

    <div id="monthFee_0"
         style="position: absolute; left: 620px; top: 175px; width: 160px; text-align: center; height: 40px; color: white; font-size: 24px; font-weight: bold;"></div>
    <div id="monthFee_1"
         style="position: absolute; left: 620px; top: 225px; width: 160px; text-align: center; height: 40px; color: white; font-size: 24px; font-weight: bold;"></div>
    <div id="monthFee_2"
         style="position: absolute; left: 620px; top: 275px; width: 160px; text-align: center; height: 40px; color: white; font-size: 24px; font-weight: bold;"></div>
    <div id="monthFee_3"
         style="position: absolute; left: 620px; top: 325px; width: 160px; text-align: center; height: 40px; color: white; font-size: 24px; font-weight: bold;"></div>
    <div id="monthFee_4"
         style="position: absolute; left: 620px; top: 375px; width: 160px; text-align: center; height: 40px; color: white; font-size: 24px; font-weight: bold;"></div>
    <div id="monthFee_5"
         style="position: absolute; left: 620px; top: 425px; width: 160px; text-align: center; height: 40px; color: white; font-size: 24px; font-weight: bold;"></div>
    <div id="monthFee_6"
         style="position: absolute; left: 620px; top: 475px; width: 160px; text-align: center; height: 40px; color: white; font-size: 24px; font-weight: bold;"></div>
    <div id="monthFee_7"
         style="position: absolute; left: 620px; top: 525px; width: 160px; text-align: center; height: 40px; color: white; font-size: 24px; font-weight: bold;"></div>
    <div id="monthFee_8"
         style="position: absolute; left: 620px; top: 575px; width: 160px; text-align: center; height: 40px; color: white; font-size: 24px; font-weight: bold;"></div>

    <div id="orderTime_0"
         style="position: absolute; left: 810px; top: 175px; width: 160px; height: 23px; color: white; text-align: center; font-size: 24px; font-weight: bold;"></div>
    <div id="orderTime_1"
         style="position: absolute; left: 810px; top: 225px; width: 160px; height: 23px; color: white; text-align: center; font-size: 24px; font-weight: bold;"></div>
    <div id="orderTime_2"
         style="position: absolute; left: 810px; top: 275px; width: 160px; height: 23px; color: white; text-align: center; font-size: 24px; font-weight: bold;"></div>
    <div id="orderTime_3"
         style="position: absolute; left: 810px; top: 325px; width: 160px; height: 23px; color: white; text-align: center; font-size: 24px; font-weight: bold;"></div>
    <div id="orderTime_4"
         style="position: absolute; left: 810px; top: 375px; width: 160px; height: 23px; color: white; text-align: center; font-size: 24px; font-weight: bold;"></div>
    <div id="orderTime_5"
         style="position: absolute; left: 810px; top: 425px; width: 160px; height: 23px; color: white; text-align: center; font-size: 24px; font-weight: bold;"></div>
    <div id="orderTime_6"
         style="position: absolute; left: 810px; top: 475px; width: 160px; height: 23px; color: white; text-align: center; font-size: 24px; font-weight: bold;"></div>
    <div id="orderTime_7"
         style="position: absolute; left: 810px; top: 525px; width: 160px; height: 23px; color: white; text-align: center; font-size: 24px; font-weight: bold;"></div>
    <div id="orderTime_8"
         style="position: absolute; left: 810px; top: 575px; width: 160px; height: 23px; color: white; text-align: center; font-size: 24px; font-weight: bold;"></div>


    <div id="buttonblue_0" style="position: absolute; left: 1010px; top: 170px; width: 163px; text-align: center;">
        <a href="#" id="cancelOrder_0" style="font-weight: bold;" onclick="cancelOrder(this)">
            <img id="buttonblue_cancelOrderImage_0" src="image/cancelorder.png" height="45" border="0" width="163">
        </a>
    </div>


    <div id="buttonblue_1" style="position: absolute; left: 1010px; top: 220px; width: 163px; text-align: center;">
        <a href="#" id="cancelOrder_1" style="font-weight: bold;" onclick="cancelOrder(this)">
            <img id="buttonblue_cancelOrderImage_1" src="image/cancelorder.png" height="45" border="0" width="163">
        </a>
    </div>


    <div id="buttonblue_2" style="position: absolute; left: 1010px; top: 270px; width: 163px; text-align: center;">
        <a href="#" id="cancelOrder_2" style="font-weight: bold;" onclick="cancelOrder(this)">
            <img id="buttonblue_cancelOrderImage_2" src="image/cancelorder.png" height="45" border="0" width="163">
        </a>
    </div>


    <div id="buttonblue_3" style="position: absolute; left: 1010px; top: 320px; width: 163px; text-align: center;">
        <a href="#" id="cancelOrder_3" style="font-weight: bold;" onclick="cancelOrder(this)">
            <img id="buttonblue_cancelOrderImage_3" src="image/cancelorder.png" height="45" border="0" width="163">
        </a>
    </div>


    <div id="buttonblue_4" style="position: absolute; left: 1010px; top: 370px; width: 163px; text-align: center;">
        <a href="#" id="cancelOrder_4" style="font-weight: bold;" onclick="cancelOrder(this)">
            <img id="buttonblue_cancelOrderImage_4" src="image/cancelorder.png" height="45" border="0" width="163">
        </a>
    </div>


    <div id="buttonblue_5" style="position: absolute; left: 1010px; top: 420px; width: 163px; text-align: center;">
        <a href="#" id="cancelOrder_5" style="font-weight: bold;" onclick="cancelOrder(this)">
            <img id="buttonblue_cancelOrderImage_5" src="image/cancelorder.png" height="45" border="0" width="163">
        </a>
    </div>


    <div id="buttonblue_6" style="position: absolute; left: 1010px; top: 470px; width: 163px; text-align: center;">
        <a href="#" id="cancelOrder_6" style="font-weight: bold;" onclick="cancelOrder(this)">
            <img id="buttonblue_cancelOrderImage_6" src="image/cancelorder.png" height="45" border="0" width="163">
        </a>
    </div>


    <div id="buttonblue_7" style="position: absolute; left: 1010px; top: 520px; width: 163px; text-align: center;">
        <a href="#" id="cancelOrder_7" style="font-weight: bold;" onclick="cancelOrder(this)">
            <img id="buttonblue_cancelOrderImage_7" src="image/cancelorder.png" height="45" border="0" width="163">
        </a>
    </div>


    <div id="buttonblue_8" style="position: absolute; left: 1010px; top: 570px; width: 163px; text-align: center;">
        <a href="#" id="cancelOrder_8" style="font-weight: bold;" onclick="cancelOrder(this)">
            <img id="buttonblue_cancelOrderImage_8" src="image/cancelorder.png" height="45" border="0" width="163">
        </a>
    </div>


    <div id="batchCancel" style="position: absolute; left: 723px; top: 638px; height: 40px;">
        <a href="#" onclick="toBatchCancelOrder()">
            <img src="image/pl_btn.png" height="37" border="0" width="150">
        </a>
    </div>

    <div id="pageUp" style="position: absolute; left: 923px; top: 638px; height: 40px;">
        <a href="#" onclick="pageUp()">
            <img src="${pageContext.request.contextPath}/service/image/btn_trans.gif" height="37" border="0"
                 width="100">
        </a>
    </div>

    <div id="pageDown" style="position: absolute; left: 1086px; top: 638px; height: 40px;">
        <a href="#" onclick="pageDown()">
            <img src="${pageContext.request.contextPath}/service/image/btn_trans.gif" height="37" border="0"
                 width="100">
        </a>
    </div>
</div>

<div id="win" style="background: #0000C2;">
</div>
</body>
</html>
