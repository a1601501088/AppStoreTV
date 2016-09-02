<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<%@ taglib uri="/WEB-INF/tag/pages.tld" prefix="pg"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
	<title>用户列表</title>
	<meta http-equiv=Content-Type content="text/html; charset=utf-8" />
	<%@ include file="/system/head.jsp" %>
	<style type="text/css">.redFont{color: red;}</style>
	<script type="text/javascript">
	  //添加用户信息
  	  function showUserInfoAdd(){
  	  
  	  $.dialog({
  			id: "forAddUserInfo",
  			title: "添加用户",
  			content: document.getElementById("forAddUserInfo"),
  			ok: function(){
  				var userName = replaces(document.getElementById("userName").value);
			    if(userName == null || userName == ""){
			    	$.dialog.alert("请输入用户名！",function(){
			    		document.getElementById("userName").focus();
			    	});
			    	return false;	    		 
			    }else{
			    	if(userName.length < 3 || userName.lenght > 16){
			    		$.dialog.alert("用户名不能小于3个字符大于16个字符！",function(){
			    			document.getElementById("userName").focus();
			    		});
			    		return false;
			    	}
			    }
			    
			    var password = replaces(document.getElementById("password").value);
			    if(password == null || password == ""){
			    	$.dialog.alert("请输入密码！",function(){
			    		document.getElementById("password").focus();
			    	});
			    	return false;	    		 
			    }else{
			    	if(password.length < 6 || password.lenght > 32){
			    		$.dialog.alert("密码不能小于6个字符大于32个字符！",function(){
			    			document.getElementById("password").focus();
			    		});
			    		return false;
			    	}
			    }
			    
			    var subPassword =replaces(document.getElementById("subPassword").value);
			    if(subPassword == null || subPassword == ""){
			    	$.dialog.alert("请确定密码！",function(){
			    		document.getElementById("subPassword").focus();
			    	});
			    	return false;	    		 
			    }else{
			    	if(subPassword != password){
			    		$.dialog.alert("两次输入的密码不一致！",function(){
			    			document.getElementById("subPassword").focus();
			    		});
			    		return false;
			    	}
			    }
			    
			    var realName =replaces(document.getElementById("realName").value);
			    if(realName == null || realName == ""){
			    	$.dialog.alert("请输入姓名！",function(){
			    		document.getElementById("realName").focus();
			    	});
			    	return false;	    		 
			    }else{
			    	if(realName.length < 3 || realName.lenght > 32){
			    		$.dialog.alert("姓名不能小于3个字符大于32个字符！",function(){
			    			document.getElementById("realName").focus();
			    		});
			    		return false;
			    	}
			    }
			    
			    var sex =replaces(document.getElementById("sex").value);
			    if(sex == null || sex == ""){
			    	$.dialog.alert("请选择用户姓别！",function(){
			    		document.getElementById("sex").focus();
			    	});
			    	return false;	    		 
			    }
			    $.dialog.list["forAddUserInfo"].close();
	        	document.addUser.action="user_add.do"
	        	document.addUser.submit();
  			},
    		cancelVal: '&nbsp;关闭&nbsp;',
    		cancel: true 
  		});
	  }
	  
	  
	  //修改用户信息
  	  function showUserInfoEdit(id){
  	  	var id = getCheckedValue(form);
  		if(isNotNull(id)){
  			
  			$.dialog({
	  			id: "forEditUserInfo",
	  			title: "修改用户信息",
	  			content: document.getElementById("forEditUserInfo"),
	  			ok: function(){
	  				var password =replaces(document.getElementById("e_password").value);
				    if(password.length > 0){
				    	if(password.length < 6 || password.lenght > 32){
				    		$.dialog.alert("密码不能小于6个字符大于32个字符！",function(){
				    			document.getElementById("e_password").focus();
				    		});
				    		return false;
				    	}
				    	var subPassword =replaces(document.getElementById("e_subPassword").value);
					    if(subPassword == null || subPassword == ""){
					    	$.dialog.alert("请确定密码！",function(){
					    		document.getElementById("e_subPassword").focus();
					    	});
					    	return false;	    		 
					    }else{
					    	if(subPassword != password){
					    		$.dialog.alert("两次输入的密码不一致！",function(){
					    			document.getElementById("e_subPassword").focus();
					    		});
					    		return false;
					    	}
					    }
				    }
				    
				    
				    var realName =replaces(document.getElementById("e_realName").value);
				    if(realName == null || realName == ""){
				    	$.dialog.alert("请输入姓名！",function(){
				    		document.getElementById("e_realName").focus();
				    	});
				    	return false;	    		 
				    }else{
				    	if(realName.length < 3 || realName.lenght > 32){
				    		$.dialog.alert("姓名不能小于3个字符大于32个字符！",function(){
				    			document.getElementById("e_realName").focus();
				    		});
				    		return false;
				    	}
				    }
				    
				    var sex =replaces(document.getElementById("e_sex").value);
				    if(sex == null || sex == ""){
				    	$.dialog.alert("请选择用户姓别！",function(){
				    		document.getElementById("e_sex").focus();
				    	});
				    	return false;	    		 
				    }
		        	$.dialog.list["forEditUserInfo"].close();
		        	document.editUser.action="<c:url value='/system/user/user_edit.do?userId=' />"+ id;
		        	document.editUser.submit();
	  			},
	  			init: function () {
			         $.post("<c:url value='/system/user/user_view.do' />",{id : id},function(data){
						if(data!=null){
							$("#e_userName").html(data.userName),
							$("#e_realName").val(data.realName),
							$("#e_sex").val(data.sex),
							$("#e_birthday").val(data.birthday),
							$("#e_email").val(data.email),
							$("#e_departments").val(data.departments),
							$("#e_duties").val(data.duties),
							$("#e_officePhone").val(data.officePhone),
							$("#e_homePhone").val(data.homePhone),
							$("#e_mobile").val(data.mobilePhone),
							$("#e_role").html(data.roleIdList)
						}
					},"json");
			    },
	    		cancelVal: '&nbsp;关闭&nbsp;',
	    		cancel: true 
	  		});
		}
	  }
	  
	  
	  //用户信息详情
  	  function showUserInfo(id){
  	  	var id = getCheckedValue(form);
  		if(isNotNull(id)){
  		
  			$.dialog({
	  			id: "forUserInfo",
	  			title: "用户信息详情",
	  			content: document.getElementById("forUserInfo"),
	  			init: function () {
			        $.post("<c:url value='/system/user/user_view.do' />",{id : id},function(data){
			        	var sex = data.sex == 0?"女":"男";
			        	var status = data.status == 0?"禁用":"正常";
						if(data!=null){
							$("#a_userName").html(data.userName),
							$("#a_realName").html(data.realName),
							$("#a_sex").html(sex),
							$("#a_birthday").html(data.birthday),
							$("#a_email").html(data.email),
							$("#a_officePhone").html(data.officePhone),
							$("#a_homePhone").html(data.homePhone),
							$("#a_mobile").html(data.mobilePhone),
							$("#a_departments").html(data.departments),
							$("#a_duties").html(data.duties),
							$("#a_status").html(status),
							$("#a_createTime").html((data.createTime).substring(0,19)),
							$("#a_lastLoginTime").html((data.lastLoginTime).substring(0,19)),
							$("#a_lastLoginIP").html(data.lastLoginIP),
							$("#a_loginTimes").html(data.loginTimes),
							$("#a_role").html(data.roleNameList)
						}
					},"json");
			    },
	    		cancelVal: '&nbsp;关闭&nbsp;',
	    		cancel: true 
	  		});
		}
	  }
	  
	  //修改状态
	  function editUserStatus(id,status){
	  	if(status == 0){
	  		$.dialog.confirm('警告：你确定要把状态改成“正常”吗？',function(){
				window.location.href="<c:url value='/system/user/user_status_edit.do?id="+ id +"&status=1'/>";
			});
	  	}
	  	if(status == 1){
	  		$.dialog.confirm('警告：你确定要把状态改成“禁用”吗？',function(){
				window.location.href="<c:url value='/system/user/user_status_edit.do?id="+ id +"&status=0'/>";
			});
	  	}
	  }
	  
	//去左右空格
	function replaces(str){
	    str=str.replace(/\s+/g, "");
	    return str;
	}
       
	</script>
  </head>
  <body>
    
  <form action="<c:url value='/system/user/user_list.do'/>" name="form" id="form" method="post">
  <table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
	<tr valign="top">
		<td>
		<table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
			<tr>
				<td height="30" valign="middle" class="blockTd"><img src="<c:url value='/images/system/role.gif'/>" /> 用户列表</td>
			</tr>
			<tr>
				<td style="padding:0 8px 4px;">
				<div style="float: left; white-space: nowrap;">
					<pg:button type="button" action="user_add" onclick="showUserInfoAdd();" value="新 增" />
					<pg:button type="button" action="user_edit" onclick="showUserInfoEdit(this.form);" value="修 改" />
					<pg:button type="button" action="user_view" onclick="showUserInfo(this.form);" value="详 情" />
				</div>
				<div style="float: right; white-space: nowrap;">
				
			  登录名：<input name="userName" type="text" value="${userName}" />
           	  <input type="button" class="inputButton" name="Submitbutton" id="Submitbutton" value="查询" onclick="form.submit();" />
           	</div>
			</td>
		</tr>
		<tr>
			<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
			<table width="100%" cellpadding="2" cellspacing="0" class="dataTable" id="dg1">
			<tr class="dataTableHead">
			  <td width="5%">序号</td>
			  <td width="5%"><input type="checkbox" id="chkAll" name="chkAll" onclick="checkall(this.form);"/></td>
			  <td width="12%"><b>登录名</b></td>
			  <td width="12%"><b>姓名</b></td>
			  <td width="6%"><b>性别</b></td>
		      <td width="12%"><b>生日</b></td>
		      <td width="8%"><b>状态</b></td>
		      <td width="40%"><b>所属角色</b></td>
			</tr>
			<c:if test="${!empty pagination.list}">
  			<c:forEach items="${pagination.list}" var="user">
			<tr style="background-color:#F9FBFC">
			  <td>${user.userId}</td>
			  <td><input type="checkbox" id="id" name="id" value="${user.userId}" /></td>
			  <td>${user.userName}</td>
			  <td>${user.realName}</td>
		      <td>${user.sex==0?"女":"男"}</td>
		      <td>${user.birthday}</td>
		      <td><a href="#" title="点击更新状态" onclick="editUserStatus(${user.userId},${user.status});">${user.status==0?"禁用":"正常"}<a/></td>
		      <td>
		      	<c:forEach items="${urList}" var="ur">
		      	<c:if test="${ur.userId==user.userId}">
		      		<c:forEach items="${rList}" var="r" varStatus="status">
		      			<c:if test="${r.roleId==ur.roleId}">
		      				${r.roleName}<c:if test="${!status.last}">,</c:if>
		      			</c:if>
		      		</c:forEach>
		      	</c:if>
		      	</c:forEach>
		      </td>
			</tr>
			</c:forEach>
  		  	</c:if>
			<tr>
			<td colspan="8" align="center" id="dg1_PageBar">
			<pg:zPage baseurl="user_list.do?userName=${userName}" pagination="${pagination}" />
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
    
    <!-- 增加用户 -->
    <div id="forAddUserInfo" style="display: none;">
    <form action="" name="addUser" id="addUser" method="post">
	<table width="400" border="0" align="center" cellpadding="0" cellspacing="0" >
	  <tr>
	    <td height="10" align="right"></td>
	    <td height="10" align="left"></td>
      </tr>
	  <tr>
	    <td width="103" height="28" align="right">用户名：</td>
	    <td width="247" height="28" align="left">
	    <input name="userName" type="text" id="userName" value="" maxlength="16"/>
	    <span class="redFont">*</span>
	    </td>
      </tr>
	  <tr>
	    <td width="103" height="28" align="right">密码：</td>
	    <td height="28" align="left">
	    <input name="password" type="password" id="password" value="" maxlength="16"/>
	    <span class="redFont"> *</span></td>
      </tr>
      <tr>
	    <td width="103" height="28" align="right">确认密码：</td>
	    <td height="28" align="left">
	    <input name="subPassword" type="password" id="subPassword" value="" maxlength="16"/>
        <span class="redFont"> *</span></td>
      </tr>
	  <tr>
	    <td width="103" height="28" align="right">姓名：</td>
	    <td height="28" align="left">
	    <input name="realName" type="text" id="realName" value="" maxlength="32"/>
	    <span class="redFont"> *</span>
	    </td>
      </tr>
	  <tr>
	    <td width="103" height="28" align="right">性别：</td>
	    <td height="28" align="left"><select id="sex" name="sex" style="width: 128px;">
	        <option value="" >请选择</option>
	        <option value="0">男</option>
	        <option value="1">女</option>
          </select><span class="redFont">*</span></td>
      </tr>
	  <tr>
	    <td width="103" height="28" align="right">生日： </td>
	    <td height="28" align="left"><input name="birthday" type="text" id="birthday" maxlength="32"/></td>
      </tr>
	  <tr>
	    <td height="28" align="right">部门：</td>
	    <td height="28" align="left"><input name="departments" type="text" id="departments" maxlength="32"/></td>
      </tr>
	  <tr>
	    <td height="28" align="right">职位：</td>
	    <td height="28" align="left"><input name="duties" type="text" id="duties" maxlength="32"/></td>
      </tr>
	  <tr>
	    <td width="103" height="28" align="right">邮箱： </td>
	    <td height="28" align="left"><input name="email" type="text" id="email" size="30" maxlength="64"/></td>
      </tr>
	  <tr>
	    <td width="103" height="28" align="right">办公室电话： </td>
       <td height="28" align="left"><input name="officePhone" type="text" id="officePhone" value="" size="40" maxlength="128"/></td>
      </tr>
	  <tr>
	    <td width="103" height="28" align="right">家庭电话： </td>
	    <td height="28" align="left"><input name="homePhone" type="text" id="homePhone" value="" size="40" maxlength="128"/></td>
      </tr>
	  <tr>
	    <td width="103" height="28" align="right">手机号码： </td>
	    <td height="28" align="left"><input name="mobilePhone" type="text" id="mobilePhone" value="" size="40" maxlength="128"/></td>
      </tr>
	</table>
	</form>
    </div>
    
    <!-- 修改用户 -->
    <div id="forEditUserInfo" style="display: none;">
    <span class="redFont">注：不修改密码请留空！</span>
    <form action="" name="editUser" id="editUser" method="post">
      <table width="470">
	  <tr>
	    <td width="302">
        <fieldset style="height: 360px;">
        <legend>用户信息</legend>
        <table width="300" border="0" align="center" cellpadding="0" cellspacing="0" >
          <tr>
            <td width="91" height="28" align="right">用户名：</td>
            <td width="209" height="28" align="left"><span id="e_userName"></span></td>
          </tr>
          <tr>
            <td width="91" height="28" align="right">密码：</td>
            <td height="28" align="left"><input type="password" id="e_password" name="e_password" value="" maxlength="16"/>
              </td>
          </tr>
          <tr>
            <td width="91" height="28" align="right">确认密码：</td>
            <td height="28" align="left"><input name="e_subPassword" type="password" id="e_subPassword" maxlength="16"/>
              </td>
          </tr>
          <tr>
            <td width="91" height="28" align="right">姓名：</td>
            <td height="28" align="left"><input name="e_realName" type="text" id="e_realName" maxlength="32"/>
              <span class="redFont"> *</span></td>
          </tr>
          <tr>
            <td width="91" height="28" align="right">性别：</td>
            <td height="28" align="left"><select id="e_sex" name="e_sex" style="width: 128px;">
              <option value="" >请选择</option>
              <option value="0">男</option>
              <option value="1">女</option>
            </select>
              <span class="redFont">*</span></td>
          </tr>
          <tr>
            <td width="91" height="28" align="right">生日： </td>
            <td height="28" align="left"><input name="e_birthday" type="text" id="e_birthday" maxlength="32"/></td>
          </tr>
          <tr>
            <td height="28" align="right">部门：</td>
            <td height="28" align="left"><input name="e_departments" type="text" id="e_departments" maxlength="32"/></td>
          </tr>
          <tr>
            <td height="28" align="right">职位：</td>
            <td height="28" align="left"><input name="e_duties" type="text" id="e_duties" maxlength="32"/></td>
          </tr>
          <tr>
            <td width="91" height="28" align="right">邮箱： </td>
            <td height="28" align="left"><input name="e_email" type="text" id="e_email" size="30" maxlength="128"/></td>
          </tr>
          <tr>
            <td width="91" height="28" align="right">办公室电话： </td>
            <td height="28" align="left"><input name="e_officePhone" type="text" id="e_officePhone" size="30" maxlength="128"/></td>
          </tr>
          <tr>
            <td width="91" height="28" align="right">家庭电话： </td>
            <td height="28" align="left"><input name="e_homePhone" type="text" id="e_homePhone" size="30" maxlength="128"/></td>
          </tr>
          <tr>
            <td width="91" height="28" align="right">手机号码： </td>
            <td height="28" align="left"><input name="e_mobilePhone" type="text" id="e_mobilePhone" size="30" maxlength="128"/></td>
          </tr>
        </table>
        </fieldset>
        </td>
	    <td width="160" valign="top">
        	<fieldset style="height:360px; overflow:auto;">
        	<legend>角色</legend>
        	<span id="e_role"></span>
            </fieldset>
        </td>
      </tr>
	  </table>
    </form>
    </div>
    
    <!-- 用户详情 -->
    <div id="forUserInfo" style="display: none;">
    <div></div>
	<table width="568" border="1" cellpadding="3" cellspacing="0" bordercolor="#eeeeee" >
	  <tr>
	    <td width="111" height="28" align="right">用户名：</td>
	    <td width="154" align="left"><span id="a_userName"></span></td>
	    <td width="88" align="right">姓名：</td>
	    <td width="181" height="28" align="left">
	    <span id="a_realName"></span>
	    </td>
      </tr>
	  <tr>
	    <td width="111" height="28" align="right">性别：</td>
	    <td align="left"><span id="a_sex"></span></td>
	    <td align="right">生日： </td>
	    <td height="28" align="left"><span id="a_birthday"></span></td>
      </tr>
      <tr>
	    <td width="111" height="28" align="right">邮箱：</td>
	    <td align="left"><span id="a_email"></span></td>
	    <td align="right">部门：</td>
	    <td height="28" align="left"><span id="a_departments"></span></td>
      </tr>
	  <tr>
	    <td width="111" height="28" align="right">职位：</td>
	    <td align="left"><span id="a_duties"></span></td>
	    <td align="right">状态：</td>
	    <td height="28" align="left"><span id="a_status"></span></td>
      </tr>
	  <tr>
	    <td height="28" align="right">办公室电话：</td>
	    <td height="28" colspan="3" align="left"><span id="a_officePhone"></span></td>
      </tr>
	  <tr>
	    <td width="111" height="28" align="right">家庭电话： </td>
	    <td align="left"><span id="a_homePhone"></span></td>
	    <td align="right">手机号码： </td>
	    <td height="28" align="left"><span id="a_mobilePhone"></span></td>
      </tr>
	  <tr>
	    <td width="111" height="28" align="right">最后登录时间：</td>
	    <td align="left"><span id="a_lastLoginTime"></span></td>
	    <td align="right">最后登录IP：</td>
	    <td height="28" align="left"><span id="a_lastLoginIP"></span></td>
      </tr>
	  <tr>
	    <td height="28" align="right">登录次数：</td>
	    <td align="left"><span id="a_loginTimes"></span></td>
	    <td align="right">创建时间：</td>
	    <td height="28" align="left"><span id="a_createTime"></span></td>
      </tr>
	  <tr>
	    <td width="111" height="28" align="right">所属角色：</td>
	    <td height="28" colspan="3" align="left"><span id="a_role"></span></td>
      </tr>
	</table>
    </div>
    
  </body>
</html>

