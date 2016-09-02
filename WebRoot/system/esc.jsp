<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>无标题文档</title>
<%@ include file="/system/head.jsp" %>
<script type="text/javascript">

	$(function(){

		//提示效果二;
		$(".registerform:last").Validform({
			tiptype:2
		});
		
	})

</script> 
</head>
<body> 
 
<form class="registerform" id="regfrom" name="regfrom" action="/Tcms/sfa.do">
    <table width="100%" style="table-layout:fixed;">
        <tr>
            <td class="need" style="width:10px;">*</td>
            <td style="width:70px;">用户名：</td>
            <td style="width:205px;"><input type="text" value="" name="name" class="inputxt" datatype="s6-18" nullmsg="请输入用户名！" errormsg="昵称至少6个字符,最多18个字符！" /></td>
            <td><div class="Validform_checktip">昵称至少6个字符,最多18个字符</div></td>
        </tr>
        <tr>
            <td class="need">*</td>
            <td>密码：</td>
            <td><input type="password" value="" name="userpassword" class="inputxt" datatype="*6-16" nullmsg="请设置密码！" errormsg="密码范围在6~16位之间,不能使用空格！" /></td>
            <td><div class="Validform_checktip">密码范围在6~16位之间,不能使用空格</div></td>
        </tr>
        <tr>
            <td class="need">*</td>
            <td>确认密码：</td>
            <td><input type="password" value="" name="userpassword2" class="inputxt" datatype="*" recheck="userpassword" nullmsg="请再输入一次密码！" errormsg="您两次输入的账号密码不一致！" /></td>
            <td><div class="Validform_checktip">两次输入密码需一致</div></td>
        </tr>
        <tr>
            <td class="need"></td>
            <td>Email：</td>
            <td><input type="text" value="" name="email" class="inputxt" datatype="e" ignore="ignore" nullmsg="请输入您常用的邮箱！" errormsg="请输入您常用的邮箱！" /></td>
            <td><div class="Validform_checktip">请输入您常用的邮箱</div></td>
        </tr>
        <tr>
            <td class="need">*</td>
            <td>性别：</td>
            <td><input type="radio" value="1" name="gender" id="male" class="pr1" datatype="radio" errormsg="请选择性别！" /><label for="male">男</label> <input type="radio" value="2" name="gender" id="female" class="pr1" /><label for="female">女</label></td>
            <td><div class="Validform_checktip"></div></td>
        </tr>
        <tr>
            <td class="need">*</td>
            <td>省份：</td>
            <td><select name="province" id="province" datatype="select" errormsg="请选择省份！" ><option value="">--请选择省份--</option><option value="1">江西省</option></select></td>
            <td><div class="Validform_checktip"></div></td>
        </tr>
        <tr>
            <td class="need">*</td>
            <td>购物网：</td>
            <td>
            	<input name="shoppingsite2" class="rt2" id="shoppingsite21" type="checkbox"  value="1" datatype="checkbox" errormsg="请选择您常去的购物网站！" /><label for="shoppingsite21">淘宝网</label>&nbsp;&nbsp;
				<input name="shoppingsite2" class="rt2" id="shoppingsite22" type="checkbox"  value="2" /><label for="shoppingsite22">当当网</label>
            </td>
            <td><div class="Validform_checktip"></div></td>
        </tr>
        <tr>
            <td class="need"></td>
            <td></td>
            <td colspan="2" style="padding:10px 0 18px 0;">
                <input type="submit" id="cdb" name="cdb" value="提 交" /> <input type="reset" value="重 置" />
            </td>
        </tr>
    </table>
</form>
</body> 
</html>