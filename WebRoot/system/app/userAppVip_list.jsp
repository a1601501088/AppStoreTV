<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tag/pages.tld" prefix="pg"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <%@ include file="/system/head.jsp" %>
    <script type="text/javascript" src="<c:url value='/js/MzTreeView10.js'/>"></script>
    <script type="text/javascript">

        //添加
        function showInfoAdd(){
            $("#tipInfo3").html("");
            $("#tipInfo4").html("");
            $.dialog({
                id: "forAddInfo",
                title: "添加",
                content: document.getElementById("forAddInfo"),
                ok: function(){
                    $("#tipInfo3").html("");
                    $("#tipInfo4").html("");
                    var className3 = $("#aclassName").val();
                    var classOrder4 = $("#aclassOrder").val();
                    if(className3.length == 0){
                        $("#tipInfo3").html("请输入类别名称");
                        return false;
                    }
                    if(classOrder4.length == 0){
                        $("#tipInfo4").html("请输入类排序号");
                        return false;
                    }
                    if(!valzs(classOrder4)){
                        $("#tipInfo4").html("请输入整数");
                        return false;
                    }
                    $.dialog.list["forAddInfo"].close();
                    document.addForm.action="appClass_add.do";
                    document.addForm.submit();
                },
                cancelVal: '&nbsp;关闭&nbsp;',
                cancel: true
            });
        }

        //修改
        function showInfoEdit(form){
            $("#tipInfo1").html("");
            $("#tipInfo2").html("");
            var id = getCheckedValue(form);
            if(isNotNull(id)){
                $.dialog({
                    id: "forEditInfo",
                    title: "修改",
                    content: document.getElementById("forEditInfo"),
                    ok: function(){
                        $("#tipInfo1").html("");
                        $("#tipInfo2").html("");
                        var className1 = $("#className").val();
                        var classOrder2 = $("#classOrder").val();
                        if(className1.length == 0){
                            $("#tipInfo1").html("请输入类别名称");
                            return false;
                        }
                        if(classOrder2.length == 0){
                            $("#tipInfo2").html("请输入类排序号");
                            return false;
                        }
                        if(!valzs(classOrder2)){
                            $("#tipInfo2").html("请输入整数");
                            return false;
                        }
                        $.dialog.list["forEditInfo"].close();
                        document.editForm.action="appClass_edit.do?classId="+id;
                        document.editForm.submit();
                    },
                    init: function () {
                        $.post("<c:url value='/system/app/appClass_view.do' />",{classId : id},function(data){
                            if(data!=null){
                                $("#className").val(data.className),
                                        $("#classOrder").val(data.classOrder)
                            }
                        },"json");
                    },
                    cancelVal: '&nbsp;关闭&nbsp;',
                    cancel: true
                });
            }
        }

        //删除
        function showInfoDelete(form){
            var id = getCheckedValue(form);
            if(isNotNull(id)){
                $.dialog.confirm('警告：您确认删除吗？',function(){
                    window.location.href="<c:url value='/system/app/appClass_delete.do?classId="+ id +"'/>";
                });
            }
        }

    </script>
</head>
<body>
<form action="<c:url value='query_appvip.do'/>" name="form" id="form" method="post">
    <table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
        <tr valign="top">
            <td>
                <table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
                    <tr>
                        <td height="30" valign="middle" class="blockTd"><img src="<c:url value='/images/system/role.gif'/>" /> 列表</td>
                    </tr>
                    <tr>
                        <td style="padding:0 8px 4px;">
                            <div style="float: left; white-space: nowrap; display: none;">
                                <pg:button type="button" action="appClass_add" onclick="showInfoAdd();" value="新 增" />
                                <pg:button type="button" action="appClass_edit" onclick="showInfoEdit(this.form);" value="修 改" />
                                <pg:button type="button" action="appClass_delete" onclick="showInfoDelete(this.form);" value="删 除" />
                            </div>
                            <div style="float: right; white-space: nowrap;">
                                用户ID：<input name="userId" type="text" value="${userId}" />
                                <input type="button" class="inputButton" name="Submitbutton" id="Submitbutton" value="查询" onclick="form.submit();" />
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
                            <table width="100%" cellpadding="2" cellspacing="0" class="dataTable" id="dg1">
                                <tr class="dataTableHead">
                                    <td width="15%">用户ID</td>
                                    <%--<td width="5%"><input type="checkbox" id="chkAll" name="chkAll" onclick="checkall(this.form);"/></td>--%>
                                    <td width="25%"><b>应用名称</b></td>
                                    <td width="10%"><b>包月价格(元)</b></td>
                                    <td width="25%"><b>创建时间</b></td>
                                    <td width="25%"><b>过期时间</b></td>
                                </tr>
                                <c:if test="${!empty pagination.list}">

                                    <c:forEach items="${pagination.list}" var="item">
                                        <tr style="background-color:#F9FBFC">
                                            <td>${item.userId}</td>
                                            <%--<td><input type="checkbox" id="id" name="id" value="${item.userId}" /></td>--%>
                                            <td>${item.appName}</td>
                                            <td>${item.appPrice}</td>
                                            <td>${item.appCrite}</td>
                                            <td>${item.appExpire}</td>
                                        </tr>
                                    </c:forEach>
                                </c:if>
                                <tr>
                                    <td colspan="6" align="center" id="dg1_PageBar">
                                        <pg:zPage baseurl="query_appvip.do?userId=${userId}" pagination="${pagination}"/>
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
    <form action="" method="post" id="editForm" name="editForm">
        <table width="100%" border="0" align="center" cellpadding="4" cellspacing="4" >
            <tr>
                <td align="right">类别名：</td>
                <td align="left">
                    <input type="text" id="className" name="className" value=""/>&nbsp;<span style="color: red;" id="tipInfo1"></span></td>
            </tr>
            <tr>
                <td align="right">排序号：</td>
                <td align="left">
                    <input type="text" size="10" id="classOrder" name="classOrder" value=""/>&nbsp;<span style="color: red;" id="tipInfo2"></span></td>
            </tr>
        </table>
    </form>
</div>

<!-- 添加信息弹出层 -->
<div id="forAddInfo" style="display: none;">
    <form action="" method="post" id="addForm" name="addForm">
        <table width="100%" border="0" align="center" cellpadding="4" cellspacing="4" >
            <tr>
                <td align="right">类别名：</td>
                <td align="left">
                    <input type="text" id="aclassName" name="aclassName" />&nbsp;<span style="color: red;" id="tipInfo3"></span></td>
            </tr>
            <tr>
                <td align="right">排序号：</td>
                <td align="left">
                    <input type="text" size="10" id="aclassOrder" name="aclassOrder" />&nbsp;<span style="color: red;" id="tipInfo4"></span></td>
            </tr>
        </table>
    </form>
</div>

</body>
</html>

