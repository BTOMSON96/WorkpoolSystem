<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
            <%@ page import="java.util.*,com.workpool.entity.*, com.workpool.controller.*, com.workpool.servlet.*, com.workpool.util.HibernateUtil, org.hibernate.Session, java.util.Iterator "%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create New Entry Folder</title>

<link href="mycss.css" rel="stylesheet" type="text/css">

</head>
<body> 

<%@include file="navbar.jsp" %>

<h3>DIRECTORY</h3> <br></br>
<div class="container">
   <%
	//load the details from database and set input values
	if(request.getParameter("id") != null){
	Long id = Long.parseLong(request.getParameter("id"));
	Session sess = HibernateUtil.getSessionFactory().openSession();
	EntryFolder entry = (EntryFolder) com.workpool.controller.AbstractController.getObjectById(EntryFolder.class, id , sess);		
	
	%>
	
	
	
	<form action="DirectoryServlet?action=update" method="post">
	
	  <input type="hidden" name="id" id="id" value="<%=id%>">
	  <label>    Entry Name:</label>   <input type="text" name="entry" id="entry"   required size="50" value="<%=entry.getName()%>"/><br></br> 
	  <label >   Date Created:</label>   <input type="text" name="created" id="created"   required size="50" value="<%=entry.getDate_created().getTime() %>"  disabled/><br></br> 
      <label>    Date Modified:</label>   <input type="text" name="modified" id="modified"   required size="50" value="<%=entry.getDate_modified().getTime()%>" disabled/><br></br> 
	
	
	
	   <input type="submit"  value="Update" /> 
	   <span> </span>
	   <input type="submit" value="Remove"  formaction="DirectoryServlet?action=delete"/> 
	   <span> </span>
	   <input type="button" value="Back" onclick="window.location='DirectoryServlet?action=read' " />
	</form>
	<%
	}else{
		
		//Create new entry
	%>

<form action="DirectoryServlet?action=add" method="post">

 <label>    Entry Name:</label>   <input type="text" name="entry" id="entry"   required size="50"/><br></br> 
  <%
  Calendar date_created = Calendar.getInstance();
  Calendar date_modified= Calendar.getInstance();
  %>
  <label >   Date Created:</label>   <input type="text" name="created" id="created"   required size="50" value="<%=BaseServlet.ConvertToDate(date_created) %>"/><br></br> 
  <label>    Date Modified:</label>   <input type="text" name="modified" id="modified"   required size="50" value="<%=BaseServlet.ConvertToDate(date_modified) %>"/><br></br> 


<input type="submit" value="Save"/>
</form>
<%
}
%>
</div>



</body>
</html>