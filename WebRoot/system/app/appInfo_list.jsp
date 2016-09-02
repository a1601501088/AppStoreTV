<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/tag/pages.tld" prefix="pg" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <%@ include file="/system/head.jsp" %>
    <script type="text/javascript" src="<c:url value='/js/MzTreeView10.js'/>"></script>
    <script type="text/javascript">

        //添加
        function showInfoAdd() {
            $("#tip10").html("");
            $("#tip11").html("");
            $("#tip12").html("");
            $("#tip13").html("");
            $("#tip14").html("");
            $("#tip15").html("");
            $("#tip16").html("");
            $("#tip17").html("");
            $("#tip31").html("");
            $("#tip45").html("");
            $.dialog({
                id: "forAddInfo",
                title: "添加",
                content: document.getElementById("forAddInfo"),
                ok: function () {
                    $("#tip10").html("");
                    $("#tip11").html("");
                    $("#tip12").html("");
                    $("#tip13").html("");
                    $("#tip14").html("");
                    $("#tip15").html("");
                    $("#tip16").html("");
                    $("#tip17").html("");
                    $("#tip31").html("");
                    $("#tip45").html("");
                    var t10 = $("#aclassId").val();
                    if (t10 == null) {
                        $("#tip10").html("请选择类型");
                        return false;
                    }
                    var t11 = $("#aappName").val();
                    if (t11.length == 0) {
                        $("#tip11").html("请输入应用名称");
                        return false;
                    }
                    var t12 = $("#aappShortName").val();
                    if (t12.length == 0) {
                        $("#tip12").html("请输入应用简短名称");
                        return false;
                    }
                    var t13 = $("#aappVersion").val();
                    if (t13.length == 0) {
                        $("#tip13").html("请输入应用版本");
                        return false;
                    }
                    var t14 = $("#aappUrl").val();
                    if (t14.length == 0) {
                        $("#tip14").html("请输入应用URL");
                        return false;
                    }
                    var t15 = $("#aappSize").val();
                    if (t15.length == 0) {
                        $("#tip15").html("请输入应用大小");
                        return false;
                    }
                    var t16 = $("#aappCount").val();
                    /*if(t16.length == 0){
                     $("#tip16").html("请输入应用下载次数");
                     return false;
                     }*/
                    if (!valzs(t16)) {
                        $("#tip16").html("请输入整数");
                        return false;
                    }
                    var t17 = $("#aappLevel").val();
                    if (t17.length == 0) {
                        $("#tip17").html("请输入应用等级");
                        return false;
                    }
                    if (t17 != 1 && t17 != 2 && t17 != 3 && t17 != 4 && t17 != 5) {
                        $("#tip17").html("应用等级只能为1到5的数字");
                        return false;
                    }
                    /*var t31 = $("#aappUrl").val();
                     if(t31.length == 0){
                     $("#tip31").html("请输入json数据");
                     return false;
                     }
                     var t45 = $("#aprovider").val();
                     if (t45.length ==0){
                     $("#tip45").html("请输入提供商");
                     return false;
                     }*/
                    var t58 = $("#achargeType").val();
                    if (t58.length == 0) {
                        $("#tip58").html("请明确是否收费");
                        return false;
                    }
                    if (t58 != '1' && t58 != '0') {
                        $("#tip58").html("必须填1或0");
                        return false;
                    }
                    if (t58 == '1') {//收费
                        var t56 = $("#aprice").val();
                        if (t56.length == 0) {
                            $("#tip56").html("请输入价格");
                            return false;
                        }
                        if (!valzs(t56)) {
                            $("#tip56").html("请输入整数");
                            return false;
                        }
                    }

                    var t57 = $("#afreeFrequency").val();
                    if (!t57.length == 0) {
                        if (isNaN(t57)) {
                            $("#tip57").html("必须为数字");
                            return false;
                        }
                        if (t57 * 1 < 0) {
                            $("#tip57").html("免费次数不能为负数");
                            return false;
                        }
                    }


                    /*var t59 = $("#apackge").val();
                     if(t59.length == 0){
                     $("#tip59").html("请输入所属专区名");
                     return false;
                     }*/
                    var t60 = $("#ashowName").val();
                    if (t60.length == 0) {
                        $("#tip60").html("请输入显示名");
                        return false;
                    }
                    var t61 = $("#aappCode").val();
                    if (t61.length == 0) {
                        $("#tip61").html("请输入appCode");
                        return false;
                    }
                    if (isNaN(t61)) {
                        $("#tip61").html("appCode必须为数字");
                        return false;
                    }


                    $.dialog.list["forAddInfo"].close();
                    document.addForm.action = "appInfo_add.do";
                    document.addForm.submit();
                },
                init: function () {
                    $("#aclassId option").remove();
                    $.post("<c:url value='/system/app/appClass_all.do' />", function (data) {
                        if (data != null) {
                            for (var i in data) {
                                var ddl = $("#aclassId");
                                ddl.append("<option value=" + data[i].classId + ">" + data[i].className + "</option>");
                            }
                        }
                    }, "json");
                },
                cancelVal: '&nbsp;关闭&nbsp;',
                cancel: true
            });
        }

        //修改
        function showInfoEdit(form) {
            $("#tip1").html("");
            $("#tip2").html("");
            $("#tip3").html("");
            $("#tip4").html("");
            $("#tip5").html("");
            $("#tip6").html("");
            $("#tip7").html("");
            $("#tip8").html("");
            $("#tip44").html("");
            $("#tip46").html("");
            $("#tip48").html("");
            $("#tip49").html("");
            $("#tip50").html("");
            $("#tip51").html("");
            $("#tip52").html("");
            $("#tip53").html("");
            $("#tip54").html("");
            $("#tip55").html("");
            var id = getCheckedValue(form);
            if (isNotNull(id)) {
                $.dialog({
                    id: "forEditInfo",
                    title: "修改",
                    content: document.getElementById("forEditInfo"),
                    ok: function () {
                        $("#tip1").html("");
                        $("#tip2").html("");
                        $("#tip3").html("");
                        $("#tip4").html("");
                        $("#tip5").html("");
                        $("#tip6").html("");
                        $("#tip7").html("");
                        $("#tip8").html("");
                        $("#tip44").html("");
                        $("#tip46").html("");
                        $("#tip48").html("");
                        $("#tip49").html("");
                        $("#tip50").html("");
                        $("#tip51").html("");
                        $("#tip52").html("");
                        $("#tip53").html("");
                        $("#tip54").html("");
                        $("#tip55").html("");
                        var t1 = $("#classId").val();
                        if (t1 == null) {
                            $("#tip1").html("请选择类型");
                            return false;
                        }

                        var t2 = $("#appName").val();
                        if (t2.length == 0) {
                            $("#tip2").html("请输入应用名称");
                            return false;
                        }

                        var t3 = $("#appShortName").val();
                        if (t3.length == 0) {
                            $("#tip3").html("请输入应用简短名称");
                            return false;
                        }
                        var t4 = $("#appVersion").val();
                        if (t4.length == 0) {
                            $("#tip4").html("请输入应用版本");
                            return false;
                        }
                        var t5 = $("#appUrl").val();
                        if (t5.length == 0) {
                            $("#tip5").html("请输入应用URL");
                            return false;
                        }

                        var t6 = $("#appSize").val();
                        if (t6.length == 0) {
                            $("#tip6").html("请输入应用大小");
                            return false;
                        }
                        var t7 = $("#appCount").val();
                        if (t7.length == 0) {
                            $("#tip7").html("请输入应用下载次数");
                            return false;
                        }
                        if (!valzs(t7)) {
                            $("#tip7").html("请输入整数");
                            return false;
                        }
                        var t8 = $("#appLevel").val();
                        if (t8.length == 0) {
                            $("#tip8").html("请输入应用等级");
                            return false;
                        }

                        if (t8 != 1 && t8 != 2 && t8 != 3 && t8 != 4 && t8 != 5) {
                            $("#tip8").html("应用等级只能为1到5的数字");
                            return false;
                        }
                        /*var t44 = $("#jsonData").val();
                         if(t44.length == 0){
                         $("#tip44").html("请输入jsonData");
                         return false;
                         }
                         var t46 = $("#provider").val();
                         if(t46.length == 0){
                         $("#tip46").html("请输入提供商");
                         return false;
                         }*/
                        var t50 = $("#chargeType").val();
                        if (t50.length == 0) {
                            $("#tip50").html("请明确是否收费");
                            return false;
                        }
                        if (t50 != '1' && t50 != '0') {
                            $("#tip50").html("必须填1或0");
                            return false;
                        }

                        if (t50 == '1') {
                            var t48 = $("#price").val();
                            if (t48.length == 0) {
                                $("#tip48").html("请输入价格");
                                return false;
                            }
                            if (!valzs(t48)) {
                                $("#tip48").html("请输入整数");
                                return false;
                            }
                        }

                        var t49 = $("#freeFrequency").val();
                        if (!t49.length == 0) {
                            if (isNaN(t49)) {
                                $("#tip49").html("必须为数字");
                                return false;
                            }
                            if (t49 * 1 < 0) {
                                $("#tip49").html("免费次数不能为负数");
                                return false;
                            }
                        }


                        var t53 = $("#appCode").val();
                        if (t53.length == 0) {
                            $("#tip53").html("请输入appCode");
                            return false;
                        }
                        if (isNaN(t53)) {
                            $("#tip53").html("appCode必须为数字");
                            return false;
                        }
                        $.dialog.list["forEditInfo"].close();
                        document.editForm.action = "appInfo_edit.do?appId=" + id;
                        document.editForm.submit();
                    },
                    init: function () {
                        $("#classId option").remove();
                        $.post("<c:url value='/system/app/appClass_all.do' />", function (data) {
                            if (data != null) {
                                for (var i in data) {
                                    var ddl = $("#classId");
                                    ddl.append("<option value=" + data[i].classId + ">" + data[i].className + "</option>");
                                }
                            }
                        }, "json");
                        $.post("<c:url value='/system/app/appInfo_view.do' />", {appId: id}, function (data) {
                            if (data != null) {
                                console.info(data)
                                $("#classId").val(data.classId),
                                        $("#appName").val(data.appName),
                                        $("#appShortName").val(data.appShortName),
                                        $("#appUrl").val(data.appUrl),
                                        $("#appDesc").val(data.appDesc),
                                        $("#appVersion").val(data.appVersion),
                                        $("#appSize").val(data.appSize),
                                        $("#appLevel").val(data.appLevel),
                                        $("#appCount").val(data.appCount),
                                        $("#jsonData").val(data.jsonData),
                                        $("#provider").val(data.provider),
                                        $("#price").val(data.price),
                                        $("#freeFrequency").val(data.freeFrequency),
                                        $("#chargeType").val(data.chargeType),
                                        $("#packge").val(data.packge),
                                        $("#showName").val(data.showName),
                                        $("#appCode").val(data.appCode),
                                        $("#spId").val(data.spId),
                                        $("#productId").val(data.productId)
                            }
                        }, "json");
                    },
                    cancelVal: '&nbsp;关闭&nbsp;',
                    cancel: true
                });
            }
        }

        //删除
        function showInfoDelete(form) {
            var id = getCheckedValue(form);
            if (isNotNull(id)) {
                $.dialog.confirm('警告：您确认删除吗？', function () {
                    window.location.href = "<c:url value='/system/app/appInfo_delete.do?appId="+ id +"'/>";
                });
            }
        }

    </script>
</head>
<body>
<form action="<c:url value='appInfo_list.do'/>" name="form" id="form" method="post">
    <table width="100%" border="0" cellspacing="6" cellpadding="0"
           style="border-collapse: separate; border-spacing: 6px;">
        <tr valign="top">
            <td>
                <table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
                    <tr>
                        <td height="30" valign="middle" class="blockTd"><img
                                src="<c:url value='/images/system/role.gif'/>"/> 列表
                        </td>
                    </tr>
                    <tr>
                        <td style="padding:0 8px 4px;">
                            <div style="float: left; white-space: nowrap;">
                                <pg:button type="button" action="appInfo_add" onclick="showInfoAdd();" value="新 增"/>
                                <pg:button type="button" action="appInfo_edit" onclick="showInfoEdit(this.form);"
                                           value="修 改"/>
                                <pg:button type="button" action="appInfo_delete" onclick="showInfoDelete(this.form);"
                                           value="删 除"/>
                            </div>
                            <div style="float: right; white-space: nowrap;">
                                应用名称：<input name="appName" type="text" value="${appName}"/>
                                <input type="button" class="inputButton" name="Submitbutton" id="Submitbutton"
                                       value="查询" onclick="form.submit();"/>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
                            <table width="200%" cellpadding="2" cellspacing="0" class="dataTable" id="dg1">
                                <tr class="dataTableHead">
                                    <td width="4%">序号</td>
                                    <td width="3%"><input type="checkbox" id="chkAll" name="chkAll"
                                                          onclick="checkall(this.form);"/></td>
                                    <td width="7%"><b>类别名称</b></td>
                                    <td width="10%"><b>应用名称</b></td>
                                    <td width="23%"><b>应用URL</b></td>
                                    <td width="10%"><b>应用描述</b></td>
                                    <td width="6%"><b>拼音简称</b></td>
                                    <td width="6%"><b>应用版本</b></td>
                                    <td width="5%"><b>大小</b></td>
                                    <td width="5%"><b>等级</b></td>
                                    <td width="6%"><b>下载次数</b></td>
                                    <td width="6%"><b>应用图标</b></td>
                                    <td width="6%"><b>应用图片</b></td>

                                    <td width="5%"><b>提供商</b></td>
                                    <td width="5%"><b>价格</b></td>
                                    <td width="7%"><b>免费次数</b></td>
                                    <td width="7%"><b>是否收费</b></td>
                                    <td width="7%"><b>所属专区</b></td>
                                    <td width="7%"><b>显示名称</b></td>
                                    <td width="10%"><b>SP_ID</b></td>
                                    <td width="19%"><b>产品ID</b></td>
                                    <td width="18%"><b>创建时间</b></td>
                                    <td width="18%"><b>更新时间</b></td>
                                </tr>
                                <c:if test="${!empty pagination.list}">
                                    <c:forEach items="${pagination.list}" var="item">
                                        <tr style="background-color:#F9FBFC">
                                            <td>${item.appId}</td>
                                            <td><input type="checkbox" id="id" name="id" value="${item.appId}"/></td>
                                            <td>${item.className}</td>
                                            <td>${item.appName}</td>
                                            <td>${item.appUrl}</td>
                                            <td>${item.appDesc}</td>
                                            <td>${item.appShortName}</td>
                                            <td>${item.appVersion}</td>
                                            <td>${item.appSize}</td>
                                            <td>${item.appLevel}</td>
                                            <td>${item.appCount}</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${empty item.appIcon}">
                                                        预览
                                                    </c:when>
                                                    <c:otherwise>
                                                        <a href="/AppStoreTV${item.appIcon}" target="_blank">预览</a>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${empty item.appImg}">
                                                        预览
                                                    </c:when>
                                                    <c:otherwise>
                                                        <a href="/AppStoreTV${item.appImg}" target="_blank">预览</a>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>${item.provider}</td>
                                            <td>${item.price}</td>
                                            <td>${item.freeFrequency}</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${item.chargeType=='1'}">
                                                        收费
                                                    </c:when>
                                                    <c:otherwise>
                                                       免费
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>${item.packge}</td>
                                            <td>${item.showName}</td>
                                            <td>${item.spId}</td>
                                            <td>${item.productId}</td>
                                            <td>${item.createTime}</td>
                                            <td>${item.updateTime}</td>
                                        </tr>
                                    </c:forEach>
                                </c:if>
                                <tr>
                                    <td colspan="23" align="left" id="dg1_PageBar">
                                        <div style="width: 1100px;">
                                            <pg:zPage baseurl="appInfo_list.do?appName=${appName}"
                                                      pagination="${pagination}"/>
                                        </div>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
</form>

<!-- 修改信息弹出层 -->
<div id="forEditInfo" style="display: none;">
    <form action="" method="post" id="editForm" name="editForm" enctype="multipart/form-data">
        <table width="100%" border="0" align="center" cellpadding="4" cellspacing="4">
            <tr>
                <td align="right">类别：</td>
                <td align="left">
                    <select id="classId" name="classId"></select>&nbsp;<span style="color: red;" id="tip1"></span>
                </td>
            </tr>
            <tr>
                <td align="right">应用名称：</td>
                <td align="left">
                    <input type="text" id="appName" name="appName" size="30" value=""/>&nbsp;<span style="color: red;"
                                                                                                   id="tip2"></span>
                </td>
            </tr>
            <tr>
                <td align="right">拼音简称：</td>
                <td align="left">
                    <input type="text" id="appShortName" name="appShortName" size="30" value=""/>&nbsp;<span
                        style="color: red;" id="tip3"></span>
                </td>
            </tr>
            <tr>
                <td align="right">应用版本：</td>
                <td align="left">
                    <input type="text" id="appVersion" name="appVersion" size="30" value=""/>&nbsp;<span
                        style="color: red;" id="tip4"></span>
                </td>
            </tr>
            <tr>
                <td align="right">下载地址：</td>
                <td align="left">
                    <input type="text" id="appUrl" name="appUrl" size="30" value=""/>&nbsp;<span style="color: red;"
                                                                                                 id="tip5"></span>
                </td>
            </tr>
            <tr>
                <td align="right">应用大小(M)：</td>
                <td align="left">
                    <input type="text" maxlength="10" id="appSize" name="appSize" size="30" value=""/>&nbsp;<span style="color: red;"
                                                                                                   id="tip6"></span>
                </td>
            </tr>
            <tr>
                <td align="right">下载次数：</td>
                <td align="left">
                    <input type="text" maxlength="10" id="appCount" name="appCount" size="30" value=""/>&nbsp;<span style="color: red;"
                                                                                                     id="tip7"></span>
                </td>
            </tr>
            <tr>
                <td align="right">等级：</td>
                <td align="left">
                    <input type="text" maxlength="10" id="appLevel" name="appLevel" size="30" value=""/>&nbsp;<span style="color: red;"
                                                                                                     id="tip8"></span>
                </td>
            </tr>

            <tr>
                <td align="right">JSON数据：</td>
                <td align="left">
                    <input type="text" id="jsonData" name="jsonData" size="30" value=""/>&nbsp;<span style="color: red;"
                                                                                                     id="tip44"></span>
                </td>
            </tr>

            </tr>

            <tr>
                <td align="right">提供商：</td>
                <td align="left">
                    <input type="text" id="provider" name="provider" size="30" value=""/>&nbsp;<span style="color: red;"
                                                                                                     id="tip46"></span>
                </td>
            </tr>
            <tr>
                <td align="right">价格(元)：</td>
                <td align="left">
                    <input type="text" id="price" maxlength="10" name="price" size="30" value=""/>&nbsp;<span style="color: red;"
                                                                                               id="tip48"></span>
                </td>
            </tr>
            <tr>
                <td align="right">免费次数：</td>
                <td align="left">
                    <input type="text" maxlength="10" id="freeFrequency" name="freeFrequency" size="30" value=""/>&nbsp;<span
                        style="color: red;" id="tip49"></span>
                </td>
            </tr>
            <tr>
                <td align="right">收费(1收费0免费)：</td>
                <td align="left">
                    <input type="text" maxlength="1" id="chargeType" name="chargeType" size="30" value=""/>&nbsp;<span
                        style="color: red;" id="tip50"></span>
                </td>
            </tr>
            <tr>
                <td align="right">所属专区：</td>
                <td align="left">
                    <input type="text" id="packge" name="packge" size="30" value=""/>&nbsp;<span style="color: red;"
                                                                                                 id="tip51"></span>
                </td>
            </tr>
            <tr>
                <td align="right">显示名：</td>
                <td align="left">
                    <input type="text" id="showName" name="showName" size="30" value=""/>&nbsp;<span style="color: red;"
                                                                                                     id="tip52"></span>
                </td>
            </tr>
            <tr>
                <td align="right">appCode：</td>
                <td align="left">
                    <input type="text" maxlength="10" id="appCode" name="appCode" size="30" value=""/>&nbsp;<span style="color: red;"
                                                                                                   id="tip53"></span>
                </td>
            </tr>
            <tr>
                <td align="right">SP_ID：</td>
                <td align="left">
                    <input type="text" id="spId" name="spId" size="30" value=""/>&nbsp;<span style="color: red;"
                                                                                             id="tip54"></span>
                </td>
            </tr>
            <tr>
                <td align="right">产品ID：</td>
                <td align="left">
                    <input type="text" id="productId" name="productId" size="30" value=""/>&nbsp;<span
                        style="color: red;" id="tip55"></span>
                </td>
            </tr>
            <tr>
                <td align="right">应用图标：</td>
                <td align="left">
                    <input type="file" id="iconfile" name="iconfile" accept="image/png"/>
                </td>
            </tr>
            <tr>
                <td align="right">应用图片：</td>
                <td align="left">
                    <input type="file" id="imgfile" name="imgfile" accept="image/png"/>
                </td>
            <tr>
                <td align="right">应用描述：</td>
                <td align="left">
                    <textarea rows="1" cols="60" id="appDesc" name="appDesc"></textarea>
                </td>
            </tr>
        </table>
    </form>
</div>

<!-- 添加信息弹出层 -->
<div id="forAddInfo" style="display: none;">
    <form action="" method="post" id="addForm" name="addForm" enctype="multipart/form-data">
        <table width="100%" border="0" align="center" cellpadding="4" cellspacing="4">
            <tr>
                <td align="right">类别：</td>
                <td align="left">
                    <select id="aclassId" name="aclassId"></select>&nbsp;<span style="color: red;" id="tip10"></span>
                </td>
            </tr>
            <tr>
                <td align="right">应用名称：</td>
                <td align="left">
                    <input type="text" id="aappName" name="aappName" size="30" value=""/>&nbsp;<span style="color: red;"
                                                                                                     id="tip11"></span>
                </td>
            </tr>
            <tr>
                <td align="right">拼音简称：</td>
                <td align="left">
                    <input type="text" id="aappShortName" name="aappShortName" size="30" value=""/>&nbsp;<span
                        style="color: red;" id="tip12"></span>
                </td>
            </tr>
            <tr>
                <td align="right">应用版本：</td>
                <td align="left">
                    <input type="text" id="aappVersion" name="aappVersion" size="30" value=""/>&nbsp;<span
                        style="color: red;" id="tip13"></span>
                </td>
            </tr>
            <tr>
                <td align="right">下载地址：</td>
                <td align="left">
                    <input type="text" id="aappUrl" name="aappUrl" size="30" value=""/>&nbsp;<span style="color: red;"
                                                                                                   id="tip14"></span>
                </td>
            </tr>
            <tr>
                <td align="right">应用大小(M)：</td>
                <td align="left">
                    <input type="text" maxlength="10" id="aappSize" name="aappSize" size="30" value=""/>&nbsp;<span style="color: red;"
                                                                                                     id="tip15"></span>
                </td>
            </tr>
            <tr>
                <td align="right">下载次数：</td>
                <td align="left">
                    <input type="text" maxlength="10" id="aappCount" name="aappCount" size="30" value=""/>&nbsp;<span
                        style="color: red;" id="tip16"></span>
                </td>
            </tr>
            <tr>
                <td align="right">等级：</td>
                <td align="left">
                    <input type="text" maxlength="10" id="aappLevel" name="aappLevel" size="30" value=""/>&nbsp;<span
                        style="color: red;" id="tip17"></span>
                </td>
            </tr>
            <tr>
                <td align="right">JSON数据：</td>
                <td align="left">
                    <input type="text" id="jjsonData" name="jjsonData" size="30" value=""/>&nbsp;<span
                        style="color: red;" id="tip31"></span>
                </td>
            </tr>
            <tr>
                <td align="right">提供商：</td>
                <td align="left">
                    <input type="text" id="aprovider" name="aprovider" size="30" value=""/>&nbsp;<span
                        style="color: red;" id="tip45"></span>
                </td>
            </tr>

            <tr>
                <td align="right">价格(元)：</td>
                <td align="left">
                    <input type="text" maxlength="10"  id="aprice" name="aprice" size="30" value=""/>&nbsp;<span style="color: red;"
                                                                                                 id="tip56"></span>
                </td>
            </tr>
            <tr>
                <td align="right">免费次数：</td>
                <td align="left">
                    <input type="text" maxlength="10" id="afreeFrequency" name="afreeFrequency" size="30" value=""/>&nbsp;<span
                        style="color: red;" id="tip57"></span>
                </td>
            </tr>
            <tr>
                <td align="right">收费(1收费0免费)：</td>
                <td align="left">
                    <input type="text" id="achargeType" name="achargeType" maxlength="1" size="30" value=""/>&nbsp;<span
                        style="color: red;" id="tip58"></span>
                </td>
            </tr>
            <tr>
                <td align="right">所属专区：</td>
                <td align="left">
                    <input type="text" id="apackge" name="apackge" size="30" value=""/>&nbsp;<span style="color: red;"
                                                                                                   id="tip59"></span>
                </td>
            </tr>
            <tr>
                <td align="right">显示名：</td>
                <td align="left">
                    <input type="text" id="ashowName" name="ashowName" size="30" value=""/>&nbsp;<span
                        style="color: red;" id="tip60"></span>
                </td>
            </tr>
            <tr>
                <td align="right">appCode：</td>
                <td align="left">
                    <input type="text" id="aappCode" name="aappCode" size="30" value=""/>&nbsp;<span style="color: red;"
                                                                                                     id="tip61"></span>
                </td>
            </tr>
            <tr>
                <td align="right">SP_ID：</td>
                <td align="left">
                    <input type="text" id="aspId" name="aspId" size="30" value=""/>&nbsp;<span style="color: red;"
                                                                                               id="tip62"></span>
                </td>
            </tr>
            <tr>
                <td align="right">产品ID：</td>
                <td align="left">
                    <input type="text" id="aproductId" name="aproductId" size="30" value=""/>&nbsp;<span
                        style="color: red;" id="tip63"></span>
                </td>
            </tr>
            <tr>
                <td align="right">应用图标：</td>
                <td align="left">
                    <input type="file" id="aiconfile" name="aiconfile" accept="image/png"/>
                </td>
            </tr>
            <tr>
                <td align="right">应用图片：</td>
                <td align="left">
                    <input type="file" id="aimgfile" name="aimgfile" accept="image/png"/>
                </td>
            </tr>
            <tr>
                <td align="right">应用描述：</td>
                <td align="left">
                    <textarea rows="1" cols="60" id="aappDesc" name="aappDesc"></textarea>
                </td>
            </tr>
        </table>
    </form>
</div>

</body>
</html>

