<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

    
     <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Resource Groups</title>

 <link href="mycss.css" rel="stylesheet" type="text/css">
</head>
<body>

<%@include file="navbar.jsp" %>



<h3>Resource Groups</h3> <br></br>



<div class="container">


<table style="width:100%; table-layout:fixed" id="myTable">
<tbody >

<tr>
<th>ID</th>
<th>Group Name</th>
<th>Manager</th>
<th>Quality Assurer</th>
<th>No of Members</th>

</tr>


<c:if test="${groups != null}">
<c:forEach items="${groups}" var="group">
<tr>
<td>${group.id} </td>
<td><a href="CreateGroup.jsp?id=${group.id} " style="color: blue" > ${group.name}  </a> </td>
<td> ${group.manager.name}  ${group.manager.lastname} </td>
<td>${group.qualityAssurer.name}  ${group.qualityAssurer.lastname}  </td>
<td>${group.resources.size()} </td>
</tr>
</c:forEach>
</c:if>


</tbody>
</table>
</div><br/><br/>

<input type="button" value="Add Group" class="center-block" onclick="window.location='CreateGroup.jsp'" > 

</body>
</html>