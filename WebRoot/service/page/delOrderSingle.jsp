<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/6/8
  Time: 9:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/service/css/delOrderSingle.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/only.js" charset=utf-8></script>
    <!--引入Jquery主文件-->
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.4.1/jquery.min.js"></script>
    <script type="text/javascript">


        $(function () {
            $("#productName").html(getQueryString("productName"));


            $("a").first().addClass("border");
            $("a").first()[0].focus();
            $("a").focusin(function () {
                $("a").css("z-index", "0");
                $("a").removeClass("border");
                $(this).addClass("border");
            });

        })

        function cancelOrder() {

            var productID = getQueryString("productID");
            var url = "";
            var option;

           if (null==productID){//批量退订
               url = "${pageContext.request.contextPath}/service/batchdelOrderService.do";
               var productIdList = getQueryString("productIdList");

               option = {
                   "productIdList": productIdList
               };
           }else {//单个退订
               url = "${pageContext.request.contextPath}/service/delOrderSingleService.do";

               option = {
                   "productID": productID
               };
           }

            $.ajax({
                url: url,
                data: option,
                dataType: 'json',
                type: 'post',
                success: function (data) {
                    console.info(data);
                    if (data.code == 200) {
                       var url = "${pageContext.request.contextPath}/service/queryOrderInfo.do?spId=${spId}&userId=${userId}&userIdType=${userIdType}&userToken=${userToken}";
                        window.location.href = url;
                    }
                }
            });
        }

        function delOrderRetrun() {
            var url = "${pageContext.request.contextPath}/service/queryOrderInfo.do?spId=${spId}&userId=${userId}&userIdType=${userIdType}&userToken=${userToken}";
            window.location.href = url;
        }
    </script>
</head>
<body>

<div>
    <div id="productName"></div>
    <div id="btn_cancelOrder"
         style="position: absolute; left: 440px; top: 542px; height: 65px; width: 210px; ">
        <a href="#" onclick="cancelOrder()">
            <img src="${pageContext.request.contextPath}/service/image/btn_trans.gif" height="37" border="0"
                 width="100">
        </a>
    </div>

    <div id="btn_retrun"
         style="position: absolute; left: 712px; top: 542px; height:68px; width: 211px;">
        <a href="#" onclick="delOrderRetrun()">
            <img src="${pageContext.request.contextPath}/service/image/btn_trans.gif" height="40" border="0"
                 width="105">
        </a>
    </div>
</div>
</body>
</html>
