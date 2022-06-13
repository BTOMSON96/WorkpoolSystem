<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Open Activities</title>
<link href="mycss.css" rel="stylesheet" type="text/css">

</head>
<body>

<%@include file="navbar.jsp" %>


<h3>ACTIVITIES</h3> <br></br>
<div class="container">
<table  style="width:100%; table-layout:fixed" id="myTable">
<tbody>
<tr>
<th>Ref# </th>
<th>Title </th>
<th>Type </th>
<th>Activity Description </th>
<th>Entry Folder</th>
<th>Task</th>
<th>Date Created</th>
</tr>

  
 <%
//Open activities
 %>
 <c:if test="${ activities != null}">
 <c:forEach items="${activities}" var="activity"> 
 
 <c:if test="${activity.isOpen == true }">
<tr>
<td>${activity.id} </td>
<td><a href="AddActivity.jsp?id=${activity.id}" style="color: blue">${activity.title}</a> </td>
<td>${activity.type_id.name} </td>
<td>${activity.description} </td>
<td>${activity.entry_id.name} </td>
<td>${activity.task_id.id}  </td>
<td>${activity.start.getTime()}</td>
</tr>
</c:if>
 </c:forEach>
 </c:if>

</tbody>
</table>
</div> <br></br>
</body>
</html>