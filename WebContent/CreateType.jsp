<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   <%@ page import="java.util.*,com.workpool.entity.*, com.workpool.controller.*, com.workpool.util.HibernateUtil, org.hibernate.Session, java.util.Iterator "%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Task Classification</title>

<link href="mycss.css" rel="stylesheet" type="text/css">
</head>
<body>

<%@include file="navbar.jsp" %>

<h3>Add Classification</h3> <br></br>
<%
    if(request.getAttribute("errorList") != null){
    List<String> errorList = (List<String>)request.getAttribute("errorList");
 %>

 <%
 
for(int i=0;i<errorList.size();i++)    
{ 
  
 %>
  <label for="ename" class="errorLabel"><%=errorList.get(i) %></label> <br></br>
 
 <%
 }
 }
 %>
 
 
<div class="container">


   <%
	//load the details from database and set input values
	if(request.getParameter("id") != null){
	Long id = Long.parseLong(request.getParameter("id"));
	Session sess = HibernateUtil.getSessionFactory().openSession();
	TaskType type = (TaskType) com.workpool.controller.AbstractController.getObjectById(TaskType.class, id , sess);		
	
	
	%>	

<form action="TypeServlet?action=update" method="post" id="edit_form"  ><br></br>

<input type="hidden" name="id" id="id" value="<%=id%>">
<label>Name : </label> <input type="text" name="name" id="name" value="<%=type.getName() %>" required size="50"/>  <br></br> <br></br>

<input type="submit" value="Update" />
<span id="cls_f">  </span>
<input type="submit" value="Remove" formaction="TypeServlet?action=delete"/>
<span id="cls_f">  </span>
<input type="button" value="Back" onclick="window.location='TypeServlet?action=read'"/> <br></br> 
</form>
<%
}else{
%>


<form action="TypeServlet?action=create" method="post">

<label>Name : </label> <input type="text" name="name" id="name"   required size="50"/>  <br></br> <br></br>

<input type="submit"  value="Save" />
<span id="cls_f">  </span>
<input type="button" value="Back" onclick="window.location='TypeServlet?action=read'"/> <br></br> 

</form>

<%
}
%>

</div>

</body>
</html>