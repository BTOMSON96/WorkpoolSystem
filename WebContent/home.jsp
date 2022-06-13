<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
         <%@ page import="com.workpool.entity.*, com.workpool.controller.ResourceController, com.workpool.util.HibernateUtil, org.hibernate.Session" %>
   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Workpool</title>

<link href="mycss.css" rel="stylesheet" type="text/css">

</head>
<body>

<%@include file="navbar.jsp" %>

<%
Long id = Long.parseLong(session.getAttribute("LoggedUser").toString());
Session sess = HibernateUtil.getSessionFactory().openSession();
Resource resource = (Resource) com.workpool.controller.AbstractController.getObjectById(Resource.class, id , sess);	
%>

<div class="botomnav">
<label class="lblbold">DASHBOARD FOR <%=resource.getFirstname().toUpperCase() %> <%=resource.getLastname().toUpperCase()%>  </label>
</div>


</body>
</html>