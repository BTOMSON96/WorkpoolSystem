<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
       <%@ page import="java.util.List,com.workpool.entity.*, com.workpool.servlet.*, com.workpool.controller.TaskController" %>
         <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <%@ taglib prefix = "w" uri = "WEB-INF/customtags.tld"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Workpool</title>

<link href="mycss.css" rel="stylesheet" type="text/css">
<script src="jquery-3.6.0.min.js"></script>

</head>
<body>
<%@include file="navbar.jsp" %>
<h3>TASK PAGE</h3>

<br></br> <br></br>
<div id="More-Options" style="display: none;">
<a href="TaskServlet?action=owner" style="float: left; color: blue;">Tasks Managed By Me </a> <a href="TypeServlet?action=read" style="float: right; color: blue;">Task Classification</a>  <br></br>
<a href="TaskServlet?action=all" style="float: left; color: blue;">All Tasks </a>
</div> <br></br>

 <c:if test="${error != null}">
  <label for="ename" class="errorLabel">${error}</label> <br></br>
 </c:if>


<div class="container">
<table style="width:100%; table-layout:fixed" id="myTable">
<tbody >

<tr>
<th>ID</th>
<th>Title</th>
<th>Entry Folder</th>
<th>Task Type</th>
<th>Assigned To</th>
<th>Owner</th>
<th>Due On</th>
<th>Next Action</th>
<th>Status</th>
<th>Priority</th>

</tr>
 
 <c:if test="${tasks != null }">
 <c:forEach items="${tasks}" var="task">
<tr>
<td><c:out value="${task.id}"/></td>
<td> <a href="TaskInformation.jsp?id=${task.id}" style="color: blue"><c:out value="${task.title} "/></a> </td>
<td><c:out value="${task.entry_id.name}"/></td>
<td><c:out value="${task.task_type_id.name}"/></td>
<td><c:out value="${BaseServlet.resourceOrGroupObject(task.assigned_to_id.id)} "/></td>
<td><c:out value="${task.owner_id.name } ${task.owner_id.lastname }"/></td>
<td> <w:formatCalendar calendar="${task.date_due}" format="${dateFormat}" /></td>
<td><w:formatCalendar calendar="${task.date_next}" format="${dateFormat}" /></td>
<td><c:out value="${task.status}"/></td>
<td><c:out value="${task.priority }"/></td>
</tr>
</c:forEach>
 </c:if>


 

  <c:if test="${taskById != null }">
 <tr>
<td><c:out value="${taskById.id}"/></td>
<td> <a href="TaskInformation.jsp?id=${taskById.id}" style="color: blue"><c:out value="${taskById.title} "/></a> </td>
<td><c:out value="${taskById.entry_id.name}"/></td>
<td><c:out value="${taskById.task_type_id.name}"/></td>
<td><c:out value="${BaseServlet.resourceOrGroupObject(taskById.assigned_to_id.id)} "/></td>
<td><c:out value="${taskById.owner_id.name } ${taskById.owner_id.lastname }"/></td>
<td> <w:formatCalendar calendar="${taskById.date_due}" format="${dateFormat}" /></td>
<td><w:formatCalendar calendar="${taskById.date_next}" format="${dateFormat}" /></td>
<td><c:out value="${taskById.status}"/></td>
<td><c:out value="${taskById.priority}"/></td>
</tr>
 </c:if>

</tbody>
</table><br/><br/>
</div>

<a href="#" id="fade-in" style="float: left; color: blue;">More Options</a><br/><br/>
<a href="#" id="fade-out" style="float: left; color: blue;">Hide Options</a>
	<script>
		$("document").ready(function() {
			$("#myTable").hover(function() {

				$(this).css("border", "2px solid blue");
			});
			
			$("#myTable").on("mouseleave", function() {
				
				$(this).css("border", "2px solid black");
			});

			//fade in options on task
			$("#fade-in").on("click", function(){
                   $("#More-Options").fadeIn(3000, function(){ 
                       alert("Done");
                       });
				});

			//fade out
			$("#fade-out").on("click", function(){
                $("#More-Options").fadeOut("fast", function(){ 
                    //alert("Done");
                    });
				});
		});
	</script>
</body>
</html>