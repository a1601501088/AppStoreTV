<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/5/25
  Time: 15:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>自助服务</title>
</head>
<style type="text/css">
    * {
        margin: 0px;
        padding: 0px;
    }

    table td {
        border: solid black 1px;
        align-content: center;
        text-align: center;
    }

    table {
        border-collapse: collapse;
        border: 1px solid black;
        width: 70%;

    }

    #batchde {
        margin-left: 800px;
    }

    h3 {
        font: red;
    }

    #tr1 {
        height: 20px;
        background: beige;
    }

    #table2 {
        width: 100%;
    }

    #tr2 {
        background: burlywood;

    }

    .hd {
        display: none;
    }
</style>
<script type="text/javascript" src="../js/jquery.min.js"></script>
<script type="text/javascript" src="../js/only.js"></script>
<script type="text/javascript">

    function delBtn(btn) {


        var productId = $(btn).attr("productId");
        var userId = "${userId}";
        var userIdType = "${userIdType}";
        /*   var productId = "packageIDa1000000000000000000051";
         var userId = "sunbb";
         var userIdType = "0";*/

        var url = "${pageContext.request.contextPath}/service/delOrderSingleService.do?" + "productId=" + productId + "&userId=" + userId + "&userIdType=" + userIdType + "&spId=${spId}" + "&userToken=${userToken}";
        $.ajax({
            url: url,
            dataType: 'json',
            type: 'post',
            success: function (data) {
                if (data.code == '200') {
                    alert("退订成功");
                    var $tr = $(btn).parent().parent();
                    $tr.fadeOut(1000);

                } else {
                    alert("退订失败");
                }
            }
        });


    }

    $(function () {
        $("#tr1 td").mouseover(function () {
            $(this).css("background", "red");
        });
        $("#tr1 td").mouseout(function () {
            $(this).css("background", "beige");
        });

        $("#batchde").click(function () {

            var productIdList = "";
            var $cBoxList = $(".checkd:checked");
            if ($cBoxList.size() > 0) {
                $cBoxList.each(function (index, dom) {
                    var btn = $(dom).parent().parent().children().last().children()[0];
                    productIdList +=  $(btn).attr("productId")+",";
                });
                var userId = "${userId}";
                var userIdType = "${userIdType}";

                var url = "${pageContext.request.contextPath}/service/batchdelOrderService.do?"+"productIdList=" + productIdList + "&userId=" + userId + "&userIDType=" + userIdType + "&spId=${spId}" + "&userToken=${userToken}";
                $.ajax({
                    url: url,
                    dataType: 'json',
                    type: 'post',
                    success: function (data) {
                        if (data.code == '200') {

                            alert("退订成功");
                            $cBoxList.parent().parent().fadeOut(1000);
                            $(":checkbox").attr("checked", false);
                        } else {
                            alert("退订失败"+data.code);
                        }
                    }
                });
            } else {
                alert("请选择");
            }
        });

        //全选、取消全选的事件

        $("#checkAll").click(function () {
            if ($("#checkAll").attr("checked")) {
                $(":checkbox").attr("checked", true);
            } else {
                $(":checkbox").attr("checked", false);
            }
        });

    });
</script>
<body>

<center>

    <h3>自助服务</h3>
    <table>
        <tbody>
        <tr id="tr1">
            <td>活动专区</td>
            <td>订购查询</td>
            <td>钱包设置</td>
            <td>童锁设置</td>
            <td>消费限额修改</td>
            <td>帮助</td>
        </tr>

        <tr id="tr2">
            <td colspan="6">
                <table id="table2">
                    <tr>
                        <td><input type="checkbox" id="checkAll">全选</td>
                        <td>业务名称</td>
                        <td>包月类型</td>
                        <td>计费描述</td>
                        <td>是否续订</td>
                        <td>过期时间</td>
                        <td>单个退订</td>
                    </tr>

                    <c:forEach items="${orderInfo.productList[0]}" var="product">
                        <tr>
                            <td><input type="checkbox" class="checkd"></td>
                            <td>${product.productName}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${product.purchaseType}='0'">
                                        包月
                                    </c:when>
                                    <c:otherwise>
                                        按次
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>${product.changingPolicyDesc}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${product.subscriptionExtend}='0'">
                                        续订
                                    </c:when>
                                    <c:otherwise>
                                        不续订
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>${product.expiredTime}</td>
                            <td><input type="button" onclick="delBtn(this);" value="单个退订"
                                       userIDType="${orderInfo.userIDType}" class="unorderbtn"
                                       userId="${orderInfo.userID}" productId="${product.productID}"></td>
                        </tr>
                    </c:forEach>


                </table>
                <input type="button" value="批量退订" id="batchde">

            </td>

        </tr>

        </tbody>

    </table>
</center>
</body>
</html>
