<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.*,com.workpool.entity.*, com.workpool.controller.ResourceController, com.workpool.util.HibernateUtil, org.hibernate.Session, java.util.Iterator "%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Resource Group</title>
<link href="mycss.css" rel="stylesheet" type="text/css">
</head>
<body>
<%@include file="navbar.jsp" %>

<h3>Resource Group</h3> <br></br>
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
	Group group = (Group) com.workpool.controller.AbstractController.getObjectById(Group.class, id , sess);		
	
	
	%>	

<form action="GroupsServlet?action=update" method="post" id="edit_form"  ><br></br>
  
 			
<input type="hidden" name="id" id="id" value="<%=id%>">
<label>Name : </label> <input type="text" name="name" id="name" value="<%=group.getName() %>" required size="50"/>  <br></br>

<label>Manager : </label>
 <select name="manager" id="manager" >
                          
 <option value="<%= group.getManager().getId()%>"><%=group.getManager().getFirstname() %> <%=group.getManager().getLastname() %> (Current Manager) </option>
                              
   <%
   //get all resources (Members)
     ResourceController members = new ResourceController();
     List<Resource> membersList = (List<Resource>)members.getAllResource();
                           
  for(int i=0;i<membersList.size();i++){ 
                        	
  %>
                           
      <option value="<%=membersList.get(i).getId()%>"><%= membersList.get(i).getFirstname() %> <%= membersList.get(i).getLastname()%> </option>
                                                                   
 <%
  }
 %>
                              
                              
</select>     <br></br>

<label>Quality Assurer: </label>
 <select name="qa" id="qa">
<option value="<%=group.getQualityAssurer().getId()%>"><%=group.getQualityAssurer().getFirstname() %> <%=group.getQualityAssurer().getLastname() %> (Current QA)</option>
                            
   <%
   for(int i=0;i<membersList.size();i++){ 
    %>                    
      <option value="<%=membersList.get(i).getId()%>"><%= membersList.get(i).getFirstname() %> <%= membersList.get(i).getLastname()%> </option> 
                                                                   
    <%
     }
    %>
                            
                            
</select>    <br></br>

<label>Internal Members: </label> <br> 
<select multiple name="members" id="members">
<%
if (group.getResources().size() != 0){

	 // Converting HashSet to ArrayList
    List<Resource> resources = new ArrayList<Resource>(group.getResources());
	   
	for(int i = 0; i < resources.size(); i ++ ){
%>
    <option value="<%=resources.get(i).getId()%>"> <%=resources.get(i).getFirstname()%> <%=resources.get(i).getLastname() %></option> 
<%
}
	%>
	<input type="button" onclick="removeMember()" value="remove selected member"/>
	<%
}else{
		 
    %>     
    <option value="" >No Members </option  >                  
   
   <%
}
   %>                         
</select> <br></br> 


<label>Internal Resources : </label> <br> 
<select multiple name="members" id="members">

  <%

                           
  for(int i=0;i<membersList.size();i++){ 
                           
  %>
                           
         <option value="<%=membersList.get(i).getId()%>"><%= membersList.get(i).getFirstname() %> <%= membersList.get(i).getLastname()%> </option>
                                                                   
  <%
  }
 %>
                                               
</select> <br></br> <br></br> 



<input type="submit" value="Update" />
<span id="cls_f">  </span>
<input type="submit" value="Remove" formaction="GroupsServlet?action=delete"/>
<span id="cls_f">  </span>
<input type="button" value="Back" onclick="window.location='GroupsServlet?action=read'"/> <br></br> 

</form>

 <%
 }else{
	 
	 //CREATE GROUP
 %>
 

<form action="GroupsServlet?action=create" method="post">

<label>Name : </label> <input type="text" name="name" id="name"   required size="50"/>  <br></br>
<label>Manager : </label>
 <select name="managerId" id="managerId" >
                          
   <%
  //get all resources (Members)
      ResourceController controller = new ResourceController();
      List<Resource> resourceList = (List<Resource>)controller.getAllResource();
                           
        for(int i=0;i < resourceList.size(); i++){ 
  %>                
         <option value="<%=resourceList.get(i).getId()%>"> <%= resourceList.get(i).getFirstname() %> <%= resourceList.get(i).getLastname()%>  </option>
                                                                   
 <%
  }
 %>
                            
</select>    <br></br>

<label>Quality Assurer: </label>
 <select name="qaId" id="qaId">
                           
  <%
                           
       for(int i=0; i <resourceList.size(); i++){ 
    	   
  %>
                           
        <option value="<%=resourceList.get(i).getId()%>"><%= resourceList.get(i).getFirstname() %> <%= resourceList.get(i).getLastname()%> </option>
                                                                   
  <%
   }
  %>
                            
</select>    <br></br>
                            
                            
<label>Members : </label> <br> 
<select multiple name="membersId" id="membersId">

  <%

                           
     for(int i=0;i<resourceList.size(); i++){ 
                           
  %>
                           
         <option value="<%=resourceList.get(i).getId()%>"><%= resourceList.get(i).getFirstname() %> <%= resourceList.get(i).getLastname()%> </option>
                                                                   
  <%
  }
 %>
                                               
</select> <br></br>

<input type="submit"  value="Save" />
<span id="cls_f">  </span>
<input type="button" value="Back" onclick="window.location='GroupsServlet?action=read'"/> <br></br> 

</form>

<%
 }	
%>




<script>
function removeMember() {
  var x = document.getElementById("members");
  x.remove(x.selectedIndex);
}
</script>

</div>
</body>
</html>