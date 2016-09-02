<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/6/12
  Time: 11:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>消费清单</title>
    <meta name="page-view-size" content="1280*720">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/service/css/userDets.css"
          media="all">
    <style type="text/css">

        .border1 {
            box-shadow: 0px 0px 60px #fff;
            border: 1px solid #000;
            outline: 2px solid #f3ec50;
            box-sizing: border-box;

        }
        .unborder1 {

             border: 0px solid #244274 ;
            outline: 0px solid #244274;
            box-sizing: border-box;

        }
    </style>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.4.1/jquery.min.js"></script>
    <script type="text/javascript">


        function dd(year, month) {
            //本月第一天 1-31
            var relativeDate = new Date(year, month, 1);
            //获得当前月份0-11
            var relativeMonth = relativeDate.getMonth();
            //获得当前年份4位年
            var relativeYear = relativeDate.getFullYear();

            //当为12月的时候年份需要加1
            //月份需要更新为0 也就是下一年的第一个月
            if (relativeMonth == 11) {
                relativeYear++;
                relativeMonth = 0;
            } else {
                //否则只是月份增加,以便求的下一月的第一天
                relativeMonth++;
            }
            //一天的毫秒数
            var millisecond = 1000 * 60 * 60 * 24;
            //下月的第一天
            var nextMonthDayOne = new Date(relativeYear, relativeMonth, 1);
            //返回得到上月的最后一天,也就是本月总天数
            return new Date(nextMonthDayOne.getTime() - millisecond).getDate();
        };

        $(function () {

            //初始化5个月份
           init();
            getData(0);


            //按钮获取焦点

            $("div a").focusin(function () {
               var   id =  document.activeElement.id;
                alert(id!='t1'&&id!='t2')
                $("div a").removeClass("border1");
                if(id!='t1'&&id!='t2'){
                  //  $("div a").css("z-index", "0");

                    $(this).addClass("border1");
                }else {
                    $(this).addClass("unborder1");
                }

            })
            $("#userdet").focus();

            $("#t1").keyup(function (event) {
                if (39==event.keyCode){
                    $("#mon4").focus();
                }else if(37==event.keyCode){
                    $("#mon3").focus();
                }
            });
            $("#t2").keyup(function (event) {
                if (39==event.keyCode){
                    $("#tag_a1").focus();
                }else if(37==event.keyCode){
                    $("#userdet").focus();
                }
            })


        });

        function init() {


            var $tagTime = $(".tag_time");
            var size = $tagTime.size();
            for (var i = 0; i < size; i++) {
                var tagTime = $tagTime.get(i);
                tagTime.innerHTML = getTime(-i).substr(0, 4) + "." + getTime(-i).substr(4, 2) + "月";
            }
        }
        function getData(index) {
            var arr = getTime(-index * 1).split("=");

            var option = {
                "spId": "${spId}",
                "userId": "${userId}",
                "userIdType": "${userIdType}",
                "userToken": "${userToken}",
                "beginTime": arr[0],
                "endTime": arr[1]

            };
            var url = "${pageContext.request.contextPath}/service/getUserDets.do";
            $.ajax({
                url: url,
                data: option,
                dataType: 'json',
                type: 'post',
                success: function (data) {
                    dataList = data.data.userDetList[0];

                    showTotalFee(dataList);
                    showData(dataList);
                }
            });

        }
        var dataList = "";
        function format(str) {
            if (str.toString().length == 1) {
                str = "0" + str;
            }
            return str;
        }

        function getTime(monthNum) {
            //本月第一天 1-31
            var relativeDate = new Date();
            //获得当前月份0-11
            var relativeMonth = relativeDate.getMonth()+1;
            //获得当前年份4位年
            var relativeYear = relativeDate.getFullYear();



            if (monthNum!=0){
                var num = relativeMonth + monthNum;
                if (num<=0){
                    relativeYear --;
                    relativeMonth = 12+num;
                }else {
                    relativeMonth = num;
                }
            }
            var beginTime = relativeYear+"" + format( relativeMonth) + "01";
            var endTime = relativeYear +""+format( relativeMonth)+"" + dd(relativeYear,relativeMonth-1);
            return beginTime + "=" + endTime;
        }

        function queryExcharge(index) {
            $("div[id^='time_']").html("");
            $("div[id^='name_']").html("");
            $("div[id^='type_']").html("");
            $("div[id^='fee_']").html("");
            $("#totalFee").html("");
            $("#c_page").html("0");
            $("#t_page").html("0");
            pageIndex = 0;
            getData(index);

        }

        // 页面行总条数
        var pageLength = 8;

        // 页码索引
        var pageIndex = 0;
        function showData(dataList) {
            //订购列表数据
            console.info(dataList);

            // 数据总数
            var total = dataList.length;

            // 总页数
            var pageTotal = Math.ceil(total / pageLength);

            if (pageIndex < 0) {
                pageIndex = 0;
            }
            if (pageIndex >= pageTotal) {
                pageIndex = pageTotal - 1;
            }
            $("#t_page").html(pageTotal);
            $("#c_page").html(pageIndex + 1);

            for (var i = 0; i < pageLength; i++) {
                var tempIndex = pageIndex * pageLength + i;

               if (tempIndex > total) continue;


                var orderTime = dataList[tempIndex*1].orderTime;
                $("#time_" + i).html(orderTime.substr(0, 4) + "-" + orderTime.substr(4, 2) + "-" + orderTime.substr(6, 2));
                $("#name_" + i).html(dataList[tempIndex].productName);
                var orderType = "";
                switch (dataList[tempIndex].orderType * 1) {
                    case 1:
                        orderType = "宽带托收";
                        break;
                    case 2:
                        orderType = "短信代收";
                        break;
                    case 3:
                        orderType = "IVR语音";
                        break;
                    case 4:
                        orderType = "TV币";
                        break;
                    case 5:
                        orderType = "电信积分";
                    case 7:
                        orderType = "微信支付";
                        break;
                }
                $("#type_" + i).html(orderType);
                $("#fee_" + i).html(dataList[tempIndex].fee*1/100+"元");
            }


        }


        /**
         * 总金额
         * @param dataList
         */
        function showTotalFee(dataList) {

            var total = dataList.length;
            var totalFee = 0;
            for (var i = 0; i < total; i++) {
                totalFee += dataList[i].fee * 1;
            }

            $("#totalFee").html(totalFee*1/100);

        }

        function pageUp() {
            pageIndex--;
            $("div[id^='time_']").html("");
            $("div[id^='name_']").html("");
            $("div[id^='type_']").html("");
            $("div[id^='fee_']").html("");
            showData(dataList)
        }

        function pageDown() {
            pageIndex++;
            $("div[id^='time_']").html("");
            $("div[id^='name_']").html("");
            $("div[id^='type_']").html("");
            $("div[id^='fee_']").html("");
            showData(dataList)
        }

        //-------------
        //已订包月
        function toOrderQuery() {
            var url = "${pageContext.request.contextPath}/service/queryOrderInfo.do?spId=${spId}&userId=${userId}&userIdType=${userIdType}&userToken=${userToken}";
            window.location.href = url;
        }
        //设置单锁
        function tovchipchange() {
            window.location.href = "${pageContext.request.contextPath}/service/page/vChip.jsp";
        }
    </script>
</head>
<body>

<div style="left: 530px; width: 600px; top: 63px; text-align: left; position: absolute; font-weight: bold; color: rgb(100, 149, 237); font-size: 22px;"
     align="right">
    (&nbsp;用户帐号:${userId}&nbsp;)
</div>


<!-- 包月订购-->
<div class="tag_border" id="order" style="position: absolute; left: 55px; top: 120px; height: 50px;">
    <a href="#" onclick="toOrderQuery()">
        <img src="${pageContext.request.contextPath}/service/image/btn_trans.gif" height="50" width="158" border="0">
    </a>
</div>
<!-- 消费清单-->
<div class="tag_border" id="exchange" style="position: absolute; left: 55px; top: 175px; height: 50px;">
    <a href="#" id="userdet" onclick="exchange()">
        <img src="${pageContext.request.contextPath}/service/image/btn_trans.gif" height="50" width="158" border="0">
    </a>
</div>


<!-- 童锁-->
<div class="tag_border" id="exmonexchange" style="position: absolute; left: 55px; top: 230px; height: 50px;">
    <a href="#" onclick="tovchipchange()">
        <img src="${pageContext.request.contextPath}/service/image/btn_trans.gif" height="50" width="158" border="0">
    </a>
</div>
<!-- 时间显示 -->
<!-- 当月 -->

    <div class="tag_time"
         style="left: 240px; top: 110px; position: absolute; text-align: center; color: rgb(255, 255, 255); width: 127px; height: 42px; font-weight: bold; font-size: 24px; line-height: 42px; vertical-align: middle;"
         align="left">2016.06月

    </div>

    <div class="tag_time"
         style="left: 400px; top: 110px; position: absolute; text-align: center; color: rgb(255, 255, 255); width: 127px; height: 42px; font-weight: bold; font-size: 24px; line-height: 42px; vertical-align: middle;"
         align="left">2016.05月

    </div>

    <div class="tag_time"
         style="left: 560px; top: 110px; position: absolute; text-align: center; color: rgb(255, 255, 255); width: 127px; height: 42px; font-weight: bold; font-size: 24px; line-height: 42px; vertical-align: middle;"
         align="left">2016.04月

    </div>

    <div class="tag_time"
         style="left: 720px; top: 110px; position: absolute; text-align: center; color: rgb(255, 255, 255); width: 127px; height: 42px; font-weight: bold; font-size: 24px; line-height: 42px; vertical-align: middle;"
         align="left">2016.03月
    </div>

    <div class="tag_time"
         style="left: 880px; top: 110px; position: absolute; text-align: center; color: rgb(255, 255, 255); width: 127px; height: 42px; font-weight: bold; font-size: 24px; line-height: 42px; vertical-align: middle;"
         align="left">2016.02月

    </div>

    <div class="tag_time"
         style="left: 1040px; top: 110px; position: absolute; text-align: center; color: rgb(255, 255, 255); width: 127px; height: 42px; font-weight: bold; font-size: 24px; line-height: 42px; vertical-align: middle;"
         align="left">2016.01月

    </div>


<!--焦点-->
<div style="width: 1;height: 70;border: 0px;left: 230px; top: 113px; position: absolute;" align="left">
    <a id="t2" style="background: #00000000;width: 1;height: 70;border: 0px;"></a>
</div>
<div style="left: 235px; top: 103px; position: absolute;" align="left">
    <a id="tag_a1"  href="#" onclick="queryExcharge(0)">
        <img src="${pageContext.request.contextPath}/service/image/btn_trans.gif" height="48" width="156" border="0">
    </a>
</div>

<div style="left: 390px; top: 103px; position: absolute;" align="left">
    <a href="#" onclick="queryExcharge('1')">
        <img src="${pageContext.request.contextPath}/service/image/btn_trans.gif" height="48" width="160" border="0">
    </a>
</div>

<div style="left: 554px; top: 103px; position: absolute;" align="left">
    <a href="#" id="mon2"  onclick="queryExcharge('2')">
        <img src="${pageContext.request.contextPath}/service/image/btn_trans.gif" height="48" width="156" border="0">
    </a>
</div>

<div style="left: 712px; top: 103px; position: absolute;" align="left">
    <a href="#" id="mon3" onclick="queryExcharge('3')">
        <img src="${pageContext.request.contextPath}/service/image/btn_trans.gif" height="48" width="156" border="0">
    </a>
</div>
<%--焦点跳板--%>
<div style="width: 2;height: 2;border: 0px;left: 868px; top: 113px; position: absolute;" align="left">
    <a id="t1" style="background: #344EA3;width: 2;height: 2;border: 0px;"></a>
</div>

<div style="left: 874px; top: 103px; position: absolute;" align="left">
    <a href="#" id="mon4" onclick="queryExcharge('4')">
        <img src="${pageContext.request.contextPath}/service/image/btn_trans.gif" height="48" width="150" border="0">
    </a>
</div>
<div style="left: 1026px; top: 103px; position: absolute;" align="left">
    <a href="#" onclick="queryExcharge('5')">
        <img src="${pageContext.request.contextPath}/service/image/btn_trans.gif" height="48" width="158" border="0">
    </a>
</div>


<!--  中间内容 -->

<div id="time_0"
     style="position: absolute; left: 240px; top: 220px; width: 200px; text-align: center; font-size: 24px; font-weight: bold; height: 40px; color: white;"></div>
<div id="time_1"
     style="position: absolute; left: 240px; top: 270px; width: 200px; text-align: center; font-size: 24px; font-weight: bold; height: 40px; color: white;"></div>
<div id="time_2"
     style="position: absolute; left: 240px; top: 320px; width: 200px; text-align: center; font-size: 24px; font-weight: bold; height: 40px; color: white;"></div>
<div id="time_3"
     style="position: absolute; left: 240px; top: 370px; width: 200px; text-align: center; font-size: 24px; font-weight: bold; height: 40px; color: white;"></div>
<div id="time_4"
     style="position: absolute; left: 240px; top: 420px; width: 200px; text-align: center; font-size: 24px; font-weight: bold; height: 40px; color: white;"></div>
<div id="time_5"
     style="position: absolute; left: 240px; top: 470px; width: 200px; text-align: center; font-size: 24px; font-weight: bold; height: 40px; color: white;"></div>
<div id="time_6"
     style="position: absolute; left: 240px; top: 520px; width: 200px; text-align: center; font-size: 24px; font-weight: bold; height: 40px; color: white;"></div>
<div id="time_7"
     style="position: absolute; left: 240px; top: 570px; width: 200px; text-align: center; font-size: 24px; font-weight: bold; height: 40px; color: white;"></div>


<div id="name_0"
     style="position: absolute; left: 490px; top: 220px; width: 300px; height: 40px; font-size: 24px; font-weight: bold; text-align: center; color: white; overflow: hidden;"></div>
<div id="name_1"
     style="position: absolute; left: 490px; top: 270px; width: 300px; height: 40px; font-size: 24px; font-weight: bold; text-align: center; color: white; overflow: hidden;"></div>
<div id="name_2"
     style="position: absolute; left: 490px; top: 320px; width: 300px; height: 40px; font-size: 24px; font-weight: bold; text-align: center; color: white; overflow: hidden;"></div>
<div id="name_3"
     style="position: absolute; left: 490px; top: 370px; width: 300px; height: 40px; font-size: 24px; font-weight: bold; text-align: center; color: white; overflow: hidden;"></div>
<div id="name_4"
     style="position: absolute; left: 490px; top: 420px; width: 300px; height: 40px; font-size: 24px; font-weight: bold; text-align: center; color: white; overflow: hidden;"></div>
<div id="name_5"
     style="position: absolute; left: 490px; top: 470px; width: 300px; height: 40px; font-size: 24px; font-weight: bold; text-align: center; color: white; overflow: hidden;"></div>
<div id="name_6"
     style="position: absolute; left: 490px; top: 520px; width: 300px; height: 40px; font-size: 24px; font-weight: bold; text-align: center; color: white; overflow: hidden;"></div>
<div id="name_7"
     style="position: absolute; left: 490px; top: 570px; width: 300px; height: 40px; font-size: 24px; font-weight: bold; text-align: center; color: white; overflow: hidden;"></div>


<div id="type_0"
     style="position: absolute; left: 815px; top: 220px; width: 131px; font-size: 24px; font-weight: bold; height: 23px; text-align: center; color: white;"></div>
<div id="type_1"
     style="position: absolute; left: 815px; top: 270px; width: 131px; font-size: 24px; font-weight: bold; height: 23px; text-align: center; color: white;"></div>
<div id="type_2"
     style="position: absolute; left: 815px; top: 320px; width: 131px; font-size: 24px; font-weight: bold; height: 23px; text-align: center; color: white;"></div>
<div id="type_3"
     style="position: absolute; left: 815px; top: 370px; width: 131px; font-size: 24px; font-weight: bold; height: 23px; text-align: center; color: white;"></div>
<div id="type_4"
     style="position: absolute; left: 815px; top: 420px; width: 131px; font-size: 24px; font-weight: bold; height: 23px; text-align: center; color: white;"></div>
<div id="type_5"
     style="position: absolute; left: 815px; top: 470px; width: 131px; font-size: 24px; font-weight: bold; height: 23px; text-align: center; color: white;"></div>
<div id="type_6"
     style="position: absolute; left: 815px; top: 520px; width: 131px; font-size: 24px; font-weight: bold; height: 23px; text-align: center; color: white;"></div>
<div id="type_7"
     style="position: absolute; left: 815px; top: 570px; width: 131px; font-size: 24px; font-weight: bold; height: 23px; text-align: center; color: white;"></div>


<div id="fee_0"
     style="position: absolute; left: 1020px; top: 220px; width: 130px; height: 23px; font-size: 24px; font-weight: bold; text-align: center; color: white;"></div>
<div id="fee_1"
     style="position: absolute; left: 1020px; top: 270px; width: 130px; height: 23px; font-size: 24px; font-weight: bold; text-align: center; color: white;"></div>
<div id="fee_2"
     style="position: absolute; left: 1020px; top: 320px; width: 130px; height: 23px; font-size: 24px; font-weight: bold; text-align: center; color: white;"></div>
<div id="fee_3"
     style="position: absolute; left: 1020px; top: 370px; width: 130px; height: 23px; font-size: 24px; font-weight: bold; text-align: center; color: white;"></div>
<div id="fee_4"
     style="position: absolute; left: 1020px; top: 420px; width: 130px; height: 23px; font-size: 24px; font-weight: bold; text-align: center; color: white;"></div>
<div id="fee_5"
     style="position: absolute; left: 1020px; top: 470px; width: 130px; height: 23px; font-size: 24px; font-weight: bold; text-align: center; color: white;"></div>
<div id="fee_6"
     style="position: absolute; left: 1020px; top: 520px; width: 130px; height: 23px; font-size: 24px; font-weight: bold; text-align: center; color: white;"></div>
<div id="fee_7"
     style="position: absolute; left: 1020px; top: 570px; width: 130px; height: 23px; font-size: 24px; font-weight: bold; text-align: center; color: white;"></div>

<!-- 上-页 -->
<div class="tag_page" id="pageUp" style="position: absolute; left: 927px; top: 640px; height: 31px;">
    <a href="#" onclick="pageUp()">
        <img src="${pageContext.request.contextPath}/service/image/btn_trans.gif" height="30" width="95" border="0">
    </a>
</div>

<div class="tag_page" id="pageDown" style="position: absolute; left: 1090px; top: 640px; height: 31px;">
    <a href="#" onclick="pageDown()">
        <img src="${pageContext.request.contextPath}/service/image/btn_trans.gif" height="30" width="95" border="0">
    </a>
</div>
<!-- 消费金额  -->
<div style="position: absolute; top: 645px; left: 235px; font-size: 24px; font-weight: bold; color: rgb(255, 255, 255);">
    本月消费总额：<span id="totalFee">0</span>元
</div>
<div style="position: absolute; top: 645px; left: 600px; font-size: 24px; font-weight: bold; color: rgb(255, 255, 255);">

</div>
<!-- 页码  -->
<div style="position: absolute; top: 645px; left: 1035px; font-size: 22px; font-weight: bold; color: rgb(100, 149, 237);">
    <span id="c_page">0</span>/<span id="t_page">0</span></div>


</body>
</html>
