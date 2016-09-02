<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/5/25
  Time: 17:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>hello word</title>


</head>
<body>

<center>
    <div>
        <form method="get" action="${pageContext.request.contextPath}/service/queryOrderInfo.do">
            <label>spId:</label> <input type="text" id="spId" name="spId" value="99999999"><br>
            <label>userId:</label> <input type="text" id="userId" name="userId" value="ztezte"><br>
            <label>userIdType:</label> <input type="text" id="userIdType" name="userIdType" value="0"><br>
            <label>userToken:</label> <input type="text" id="userToken" name="userToken"><br>
            <input type="submit" value="查询"> &nbsp;&nbsp;<input type="reset" value="重置">
        </form>

    </div>
</center>
</body>
</html>
