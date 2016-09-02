<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
	<title>管理中心left</title>
	<meta http-equiv=Content-Type content="text/html; charset=utf-8" />
	<link type="text/css" rel="stylesheet" href="<c:url value='/css/style.css'/>" />
	<link type="text/css" rel="stylesheet" href="<c:url value='/css/jquery.treeTable.css'/>" />
	<script type="text/javascript" src="<c:url value='/js/jquery.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/jquery.treeTable.js'/>"></script>
	<script type="text/javascript">
	  $(document).ready(function() {

	     $("#example").treeTable({
	        expandable: false
	     });

	  });
    </script>
  </head>
  <body>
 	  <table id="example" >
 	  	  <c:if test="${!empty mlist}">
 	   	    <c:forEach items="${mlist}" var="menu">
 	   	    
	 	   	  <c:if test="${menu.parentId==0}">
	 	   	    <tr id="ex2-node-${menu.menuId}">
			      <td><a href="<c:url value='${menu.menuUrl}'/>">${menu.menuName}</a></td>
			    </tr>
	 	   	  </c:if>
	 	   	  
	 	   	  <c:forEach items="${mlist}" var="menu1">
	 	   	  	<c:if test="${menu.menuId==menu1.parentId}">
	 	   	      <tr id="ex2-node-${menu1.menuId}" class="child-of-ex2-node-${menu1.parentId}">
			        <td><a href="<c:url value='${menu1.menuUrl}'/>">${menu1.menuName}1</a></td>
			      </tr>
	 	   	  	</c:if>
	 	   	  </c:forEach>
	 	   	  
		    </c:forEach>
 	      </c:if>
 	  </table>
  </body>
</html>

