<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
      <%@ page import="java.util.List,com.workpool.entity.*, com.workpool.controller.ResourceController, com.workpool.util.HibernateUtil, org.hibernate.Session" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Resource</title>
 <link href="mycss.css" rel="stylesheet" type="text/css">
</head>
<body>

<%@include file="navbar.jsp" %>

     
     
     
<h3>Resource Management</h3> <br></br>
<%
    if(request.getAttribute("errorList") != null){
    List<String> errorList = (List<String>)request.getAttribute("errorList");
 %>

 <%
 
for(int i=0;i<errorList.size();i++)    
{ 
  
 %>
  <label for="ename" class="errorLabel"><%=errorList.get(i) %></label><br>
 
 <%
 }
 }
 %>
 
 
 
 
 <%
 //when removing e resource who is a owner of a task
    if(request.getAttribute("FKConstraintError") != null){
    String error = request.getAttribute("FKConstraintError").toString();
 %>
  <label for="ename" class="errorLabel"><%=error %></label> <br></br>
 
 <%
 }
 %>
 
 
 
 
 

<div class="container">

<form action="ResourceServlet?action=update" method="post" id="edit_form"  ><br></br>
  
 				
	<%
	//load the details from database and set input values
	if(request.getParameter("id") != null){
	Long id = Long.parseLong(request.getParameter("id"));
	Session sess = HibernateUtil.getSessionFactory().openSession();
	Resource resource = (Resource) com.workpool.controller.AbstractController.getObjectById(Resource.class, id , sess);		
	
	
	%>				

<input type="hidden" name="id" id="id" value="<%=id%>">
<label for="ename">    Name:</label> <input type="text" name="name" id="name"   value="<%=resource.getFirstname()%>" size="50" /><br></br>
<label for="esurname"> Surname:</label><input type="text" name="lastname" id="lastname" value="<%=resource.getLastname()%>"  size="50"/><br></br>
<label for="edob">     Date of Birth:</label><input type="text" name="dob" id="dob"  value="<%=resource.getDob().getTime()%>" size="50" disabled/><br></br>
<label for="eaddress"> Address:</label> <input type="text" name="address" id="address" value="<%=resource.getAddress()%>" size="50"/><br></br>
<label for="eemail">   Email:</label> <input type="text" name="email" id="email" value="<%=resource.getEmail()%>" size="50"><br></br>
<label for="eusername">Username:</label> <input type="text" name="username" id="username" value="<%=resource.getUsername()%>" size="50"/><br></br>
<label for="epassowrd">Password:</label><input type="password" name="password" id="password" value="<%=resource.getPassword()%>" size="50"/><br></br>
<label for="etype">    Type:</label><select  name="type" id="type" value="<%=resource.getType()%>">
                 <option value="Internal">Internal</option>
                 <option value="External">External</option>
                 </select><br></br>
<label for="emanager">Manager:</label>
<%if(resource.getManager() == null){ %>
<input type="text" name="manager" id="manager"  size="50" disabled/><br></br>
<%}else{ %>
<input type="text" name="manager" id="manager" value="<%=resource.getManager().getFirstname()%> <%=resource.getManager().getLastname()%>" size="50" disabled/><br></br>
<%} %>
  <input type="submit" value="Update" />
  <span id="cls_f">  </span>
  <% if(resource.getisActive() == true){ %>
 <input type="submit" value="Deactivate" formaction="ResourceServlet?action=deactivate"/><br></br>
<%}else{ %>
<input type="submit" value="Activate" formaction="ResourceServlet?action=activate"/><br></br>
<%} %>
</form>







 <%
 }  if(request.getAttribute("errorList") == null && request.getParameter("id") == null){
 %>
 
 
 <form action="ResourceServlet?add=add" method="post"   ><br></br>
  
      <label for="fname">    First Name:</label>   <input type="text" name="name" id="name"   required size="50" /><br></br> 
      <label for="esurname"> Surname:</label>      <input type="text" name="lastname" id="lastname" required size="50" /><br></br>
      <label for="edob">     Date of Birth:</label><input type="text" name="dob" id="dob" placeholder="YYYY-MM-DD" required size="50"/> <label for="edob">YYYY-MM-DD</label><br></br>
      <label for="eaddress"> Address:</label>      <input type="text" name="address" id="address" required size="50"/><br></br>
      <label for="eemail">   Email:</label>        <input type="text" name="email" id="email" required size="50"/><br></br>
      <label for="eusername">Username:</label>     <input type="text" name="username" id="username" required size="50" /><br></br>
      <label for="epassowrd">Password:</label>     <input type="password" name="password" id="password" required size="50" /><br></br>
      <label for="etype">    Type:</label>         <select  name="type" id="type">
                                                     <option value="Internal">Internal</option>
                                                     <option value="External">External</option>
                                                  </select><br></br>
   
                                                    
     <label for="emanager">Manager:</label>
       <select   name="manager" id="manager">
     
      <%
      //get all resources (Managers)
        ResourceController controller = new ResourceController();
        List<Resource> resourceList = (List<Resource>)controller.getAllResource();
      
      for(int i=0;i<resourceList.size();i++)    
       { 
      %>
      
     <option value="<%=resourceList.get(i).getId()%>"><%= resourceList.get(i).getFirstname() %> <%= resourceList.get(i).getLastname()%> </option>
                                              
       <%
       }
      %>         
  </select> <br></br>
  
   <label for="etype">    System Admin:</label>  <input type="checkbox" name="isAdmin" id="isAdmin" style="width: 15px; height: 15px"> <br></br>
                                        
    <input type="submit" value="Save" formaction="ResourceServlet?action=add" />       
</form>

<%
 }

//if there's an error send back the values so that user cannot retype
if(request.getAttribute("errorList") != null && request.getParameter("id") == null){
 %>
 
 <form action="ResourceServlet?add=add" method="post"   ><br></br>
  
      <label for="fname">    First Name:</label>   <input type="text" name="name" id="name"   required size="50"  value="<%=request.getParameter("name")%>"/><br></br> 
      <label for="esurname"> Surname:</label>      <input type="text" name="lastname" id="lastname" required size="50" value="<%=request.getParameter("lastname")%>"/><br></br>
      <label for="edob">     Date of Birth:</label><input type="text" name="dob" id="dob" placeholder="YYYY-MM-DD" required size="50" value="<%=request.getParameter("dob")%>"/> <label for="edob">YYYY-MM-DD</label><br></br>
      <label for="eaddress"> Address:</label>      <input type="text" name="address" id="address" required size="50" value="<%=request.getParameter("address")%>"/><br></br>
      <label for="eemail">   Email:</label>        <input type="text" name="email" id="email" required size="50" value="<%=request.getParameter("email")%>"/><br></br>
      <label for="eusername">Username:</label>     <input type="text" name="username" id="username" required size="50" value="<%=request.getParameter("username")%>"/><br></br>
      <label for="epassowrd">Password:</label>     <input type="password" name="password" id="password" required size="50" value="<%=request.getParameter("password")%>"/><br></br>
      <label for="etype">    Type:</label>         <select  name="type" id="type">
                                                     <option value="Internal">Internal</option>
                                                     <option value="External">External</option>
                                                  </select><br></br>
   
                                                    
     <label for="emanager">Manager:</label>
       <select   name="manager" id="manager">
     
      <%
      //get all resources (Managers)
        ResourceController controller = new ResourceController();
        List<Resource> resourceList = (List<Resource>)controller.getAllResource();
      
      for(int i=0;i<resourceList.size();i++)    
       { 
      %>
      
     <option value="<%=resourceList.get(i).getId()%>"><%= resourceList.get(i).getFirstname() %> <%= resourceList.get(i).getLastname()%> </option>
                                              
       <%
       }
      %>         
  </select> <br></br>                                              
    <input type="submit" value="Save" formaction="ResourceServlet?action=add" />       
</form>
 
 
 
 
 <%
 }
 %>
 
 
</div>
</body>
</html>