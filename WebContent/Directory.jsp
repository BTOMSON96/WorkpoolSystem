<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.List,com.workpool.entity.*, com.workpool.controller.EntryFolderController" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Directory</title>

<link href="mycss.css" rel="stylesheet" type="text/css">

</head>
<body>

<%@include file="navbar.jsp" %>


<div class="botomnav">
   
  <button type="button" title="Add New Entry"  onclick="window.location='AddDirectory.jsp'" >ADD</button> <label class="lblbold">DIRECTORY</label>
  
</div> <br></br> <br></br> <br></br>
<%
    if(request.getAttribute("errors") != null){
   
 %>
  <label for="ename" class="errorLabel"><%=request.getAttribute("errors").toString()%></label> <br>
 
 <%
 }
 %>

<div class="container">

<form action="DirectoryServlet?action=directorybyname" method="post">
<input type="text"name="searchDirectory"  placeholder="Search Entry#" title="Directory" required> <input type="submit" value="Search">   
<a href="DirectoryServlet?action=read" style="float: right; color: blue;">VIEW ALL</a>  <br></br
</form>

</div>

      

<table style="width:100%; table-layout:fixed" id="myTable">
<tbody >
<tr>
<th>ID</th>
<th>Entry name</th>
<th>Date Created</th>
<th>Date Modified</th>
</tr>
<% 
      if(request.getAttribute("entries") != null){
         List<EntryFolder> entryList = (List<EntryFolder>)request.getAttribute("entries");
      
         for(int i=0;i< entryList .size();i++)    
         { 
           EntryFolder entry =  entryList.get(i);
       %>


<tr>
<td><%=entry.getId() %> </td>
<td><a href="AddDirectory.jsp?id=<%=entry.getId() %> " style="color: blue" > <%=entry.getName() %> </a> </td>
<td><%=entry.getDate_created().getTime() %> </td>
<td><%=entry.getDate_modified().getTime() %> </td>
</tr>

<% 
  }
  }if(request.getAttribute("entryList") != null){
	  List<EntryFolder> entriesList = (List<EntryFolder>)request.getAttribute("entryList");
	  
	  for(int i=0;i< entriesList .size();i++)    
      { 
        EntryFolder entrries =  entriesList.get(i);
  
 %>
 
 <tr>
<td><%=entrries.getId() %> </td>
<td> <a href="AddDirectory.jsp?id=<%=entrries.getId() %> " style="color: blue" > <%=entrries.getName() %> </a> </td>
<td><%=entrries.getDate_created().getTime() %> </td>
<td><%=entrries.getDate_modified().getTime() %> </td>
</tr>
<% 
  }
  }
  %>
</tbody>
</table><br/><br/>
</body>
</html>