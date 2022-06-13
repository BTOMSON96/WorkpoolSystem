<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   <%@ page import="java.util.List,com.workpool.entity.*, com.workpool.controller.ResourceController, com.workpool.util.HibernateUtil, org.hibernate.Session" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title> </title>
<style>
.topnav {
	overflow: hidden;
	background-color: #333;
}

.topnav a {
	float: left;
	color: #f2f2f2;
	text-align: center;
	padding: 14px 16px;
	text-decoration: none;
	font-size: 17px;
}

.topnav a:hover {
	background-color: #ddd;
	color: black;
}

</style>
</head>
<body>



<div class="topnav">
   
  <a href="home.jsp" >Home</a>
   <a href="TaskServlet?action=read" >Tasks</a>
  <a href="Directory.jsp" >Directory</a>
  <a href="#">  <form action="TaskServlet?action=getbyid" method="post"> <input type="number" name="searchTask"  placeholder="Search Task#" title="Task"  required> </form> </a>
  <a href="AddTask.jsp" title="Add Task">Add Task</a>
  <a href="AddActivity.jsp" title="Add Activity">Add Activity</a>
  <a href="ResourceServlet?action=read" title="Admin">Resource Management</a>
  <a href="LoginServlet?action=signout" >Sign out</a>
</div>

</body>
</html>