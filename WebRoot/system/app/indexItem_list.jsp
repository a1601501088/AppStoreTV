<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
            $(".add_tip").text("");
            $(".add").val("");
            $.dialog({
                id: "forAddInfo",
                title: "添加",
                content: document.getElementById("forAddInfo"),
                ok: function(){
                    $(".add_tip").text("");

                    var $add = $(".add");
                    for (var i = 0;i<$add.size();i++){
                        if ($add[i].value==''){
                            $(".add_tip")[i].innerText="不能为空";
                            return false;
                        }

                        if (i==1){
                            var status_value = $add[i].value;
                            if(status_value!="1"&&status_value!="0"){
                                $(".add_tip")[i].innerText="必须为1或0";
                                return false;
                            }
                        }
                    }
                    $.dialog.list["forAddInfo"].close();
                    document.addForm.action="indexItem_add.do";
                    document.addForm.submit();
                },
                cancelVal: '&nbsp;关闭&nbsp;',
                cancel: true
            });
        }

        //修改
        function showInfoEdit(form){

            $(".edit_tip").text("");
            var id = getCheckedValue(form);
            if(isNotNull(id)){
                $.dialog({
                    id: "forEditInfo",
                    title: "修改",
                    content: document.getElementById("forEditInfo"),
                    ok: function(){
                        $(".edit_tip").text("");
                        var $edit = $(".edit");
                        for (var i = 0;i<$edit.size();i++){
                            if ($edit[i].value==''){
                                $(".edit_tip")[i].innerText="不能为空";
                                return false;
                            }
                            if (i==1){
                                var status_value = $edit[i].value;
                                if(status_value!="1"&&status_value!="0"){
                                    $(".edit_tip")[i].innerText="必须为1或0";
                                    return false;
                                }
                            }
                        }

                        $.dialog.list["forEditInfo"].close();
                        document.editForm.action="indexItem_edit.do?oldIndexId="+id;
                        document.editForm.submit();
                    },
                    init: function () {
                        $.post("${pageContext.request.contextPath}/system/app/indexItem_view.do",{"indexItemId" : id},function(data){
                            if(data!=null){
                                data = eval("("+data+")");
                                console.info(data)
                                var $edit =  $(".edit");


                                $edit[0].value = data.indexItemName;
                                $edit[1].value = data.stauts;

                                $edit[2].value = data.execteActivity;

                                $("#exectePackge")[0].value =data.exectePackge;
                                $("#exectePara")[0].value =data.exectePara;

                            }
                        });
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
                    window.location.href="<c:url value='/system/app/indexItem_delete.do?indexItemId="+ id +"'/>";
                });
            }
        }

    </script>
</head>
<body>
<form action="<c:url value='indexItem_list.do'/>" name="form" id="form" method="post" enctype="multipart/form-data">
    <table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
        <tr valign="top">
            <td>
                <table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
                    <tr>
                        <td height="30" valign="middle" class="blockTd"><img src="<c:url value='/images/system/role.gif'/>" /> 列表</td>
                    </tr>
                    <tr>
                        <td style="padding:0px 8px 4px;">
                            <div style="float: left; white-space: nowrap;">
                                <pg:button type="button" action="indexItem_add" onclick="showInfoAdd();" value="新 增" />
                                <pg:button type="button" action="indexItem_edit" onclick="showInfoEdit(this.form);" value="修 改" />
                                <pg:button type="button" action="indexItem_delete" onclick="showInfoDelete(this.form);" value="删 除" />
                            </div>
                            <div style="float: right; white-space: nowrap;">
                                名称：<input name="indexItemName" type="text" value="${indexItemName}" />
                                <input type="button" class="inputButton" name="Submitbutton" id="Submitbutton" value="查询" onclick="form.submit();" />
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
                            <table width="100%" cellpadding="2" cellspacing="0" class="dataTable" id="dg1">
                                <tr class="dataTableHead">
                                    <td width="4%">序号</td>
                                    <td width="3%"><input type="checkbox" id="chkAll" name="chkAll" onclick="checkall(this.form);"/></td>
                                    <td width="10%"><b>名称</b></td>
                                    <td width="4%"><b>状态</b></td>
                                    <td width="15%"><b>启动包名</b></td>
                                    <td width="25%"><b>启动类名</b></td>
                                    <td width="20%"><b>携带数据</b></td>
                                    <td width="10%"><b>图片URL</b></td>

                                </tr>
                                <c:if test="${!empty pagination.list}">
                                    <c:forEach items="${pagination.list}" var="item">
                                        <tr style="background-color:#F9FBFC">
                                            <td>${item.indexItemId}</td>
                                            <td><input type="checkbox" id="id" name="id" value="${item.indexItemId}" /></td>
                                            <td>${item.indexItemName}</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${item.stauts=='1'}">
                                                        生效
                                                    </c:when>
                                                    <c:otherwise>
                                                        无效
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>${item.exectePackge}</td>
                                            <td>${item.execteActivity}</td>
                                            <td>${item.exectePara}</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${empty item.imageUrl}">
                                                        预览
                                                    </c:when>
                                                    <c:otherwise>
                                                        <a href="/AppStoreTV${item.imageUrl}" target="_blank">预览</a>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:if>
                               <tr>
                                    <td colspan="8" align="center" id="dg1_PageBar">
                                        <pg:zPage baseurl="indexItem_list.do?indexItemName=${indexItemName}" pagination="${pagination}"/>
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
        <table width="100%" border="0" align="center" cellpadding="4" cellspacing="4" >

            <tr>
                <td align="right">名称：</td>
                <td align="left">
                    <input type="text" size="30" class="edit" name="indexItemName" />&nbsp;<span style="color: red;" class="edit_tip"></span></td>
            </tr>
            <tr>
                <td align="right">状态(1生效0无效)：</td>
                <td align="left">
                    <input type="text" size="30" class="edit" name="stauts" />&nbsp;<span style="color: red;" class="edit_tip"></span></td>
            </tr>

            <tr>
                <td align="right">启动包名：</td>
                <td align="left">
                    <input type="text" size="30" id="exectePackge" name="exectePackge" />&nbsp;</td>
            </tr>
            <tr>
                <td align="right">启动类名：</td>
                <td align="left">
                    <input type="text" size="30" class="edit" name="execteActivity" />&nbsp;<span style="color: red;" class="edit_tip"></span></td>
            </tr>
            <tr>
                <td align="right">携带数据：</td>
                <td align="left">
                    <input type="text" size="30" id="exectePara"  name="exectePara" />&nbsp;</td>
            </tr>
            <tr>
                <td align="right">图片：</td>
                <td align="left">
                    <input type="file"   name="icon1" accept="image/png" />&nbsp;</td>
            </tr>
        </table>
    </form>
</div>

<!-- 添加信息弹出层 -->
<div id="forAddInfo" style="display: none;">
    <form action="" method="post" id="addForm" name="addForm" enctype="multipart/form-data">
        <table width="100%" border="0" align="center" cellpadding="4" cellspacing="4" >

            <tr>
                <td align="right">名称：</td>
                <td align="left">
                    <input type="text" size="30" class="add" name="indexItemName" />&nbsp;<span style="color: red;" class="add_tip"></span></td>
            </tr>
            <tr>
                <td align="right">状态(1生效0无效)：</td>
                <td align="left">
                    <input type="text" size="30" class="add" name="stauts" />&nbsp;<span style="color: red;" class="add_tip"></span></td>
            </tr>

            <tr>
                <td align="right">启动包名：</td>
                <td align="left">
                    <input type="text" size="30" name="exectePackge" />&nbsp;</td>
            </tr>
            <tr>
                <td align="right">启动类名：</td>
                <td align="left">
                    <input type="text" size="30" class="add" name="execteActivity" />&nbsp;<span style="color: red;" class="add_tip"></span></td>
            </tr>
            <tr>
                <td align="right">携带数据：</td>
                <td align="left">
                    <input type="text" size="30"  name="exectePara" />&nbsp;</td>
            </tr>
            <tr>
                <td align="right">图片：</td>
                <td align="left">
                    <input type="file"  class="add" name="icon1" accept="image/png" />&nbsp;<span style="color: red;" class="add_tip"></span></td>
            </tr>


        </table>
    </form>
</div>

</body>
</html>

