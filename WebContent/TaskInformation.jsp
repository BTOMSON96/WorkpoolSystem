<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
     <%@ page import="java.util.*,com.workpool.entity.*, com.workpool.controller.*, com.workpool.util.HibernateUtil, org.hibernate.Session, java.util.Iterator "%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Task Information</title>

<link href="mycss.css" rel="stylesheet" type="text/css">
</head>
<body>

	<%@include file="navbar.jsp"%>



	<%--  <%
	//load the details from database and set input values
	if(request.getParameter("id") != null){
	Long id = Long.parseLong(request.getParameter("id"));
	Session sess = HibernateUtil.getSessionFactory().openSession();
	Task task = (Task) com.workpool.controller.AbstractController.getObjectById(Task.class, id , sess);		
	
	%> --%>

	<div class="botomnav">

		<button type="button" title="Edit task"
			onclick="window.location='AddTask.jsp?id=${param.id}'">Edit
			Task</button>
		<label class="lblbold">Task No : ${param.id}</label>

	</div>
	<br></br>
	<br></br>



	<c:if test="${param.id != null}">
		<label style="font-weight: bold">Task Title:</label> ${taskDetails.title }  <br>
		<br>
		<label style="font-weight: bold">Task Details:</label>${taskDetails.description} <br>
		<br>
		<label style="font-weight: bold">Classification:</label> ${taskDetails.task_type_id.name } <br>
		<br>
	</c:if>

	<form action="TaskServlet?action=details" method="post">
		<input type="hidden" name="id" id="id" value="${param.id}"> <input
			type="submit" value="Show Activities">
	</form>
	<br>
	<br>


	<c:if test="${activityUnderTask != null}">
		<c:forEach items="${activityUnderTask}" var="act">
			<div class="container">
				<label> ${act.title}</label> <label
					style="display: block; text-align: right">
					${act.author.name } ${act.author.lastname } On
					${act.start.getTime()}</label>
			</div>
			<br></br>
		</c:forEach>
	</c:if>


</body>
</html>