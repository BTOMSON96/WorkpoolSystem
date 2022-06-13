<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   <%@ page import="java.util.Calendar, java.util.List, java.util.List, com.workpool.entity.*, com.workpool.controller.*" %>
       <%@ page import="java.util.*,com.workpool.entity.*, com.workpool.servlet.*, com.workpool.controller.*, com.workpool.util.HibernateUtil, org.hibernate.Session, java.util.Iterator "%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Task</title>
<link href="mycss.css" rel="stylesheet" type="text/css">
</head>
<body>

<%@include file="navbar.jsp" %>



<h3>START TASK</h3> <br></br>
<%! @SuppressWarnings("unchecked") %>
<%
    if(request.getAttribute("errorList") != null){
    List<String> errorList = (List<String>)request.getAttribute("errorList");
 %>

 <%
 
for(int i=0;i<errorList.size();i++)    
{ 
  
 %>
  <label for="ename" class="errorLabel"><%=errorList.get(i) %></label> <br>
 
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
	Task task = (Task) com.workpool.controller.AbstractController.getObjectById(Task.class, id , sess);		
	
	%>
	
	<form action="TaskServlet?action=update" method="post">
	
	  <input type="hidden" name="id" id="id" value="<%=id%>">
	  <label for="title">    Title:</label>   <input type="text" name="title" id="title"  value="<%=task.getTitle() %>" required size="50"/><br></br> 
	  <label for="description"> Description:</label><br> <textarea id="description" name="description" rows="4" cols="50" value=><%=task.getDescription() %> </textarea><br></br>
      <label for="entry">    EntryFolder:</label> <select   name="entry" id="entry"  disabled> <option ><%=task.getEntry_id().getName() %></option>  </select><br></br>
      <label for="assign">   Assign To:</label> <select   name="entry" id="entry" disabled > <option ><%=task.getAssigned_to_id().getName()%></option>  </select><br></br>
      <label for="owner">  Owner:</label>  <select   name="entry" id="entry"  disabled> <option ><%=task.getOwner_id().getFirstname()%> <%=task.getOwner_id().getLastname()%></option>  </select><br></br>
	  <label for="type">   Type:</label> 
	  <select  name="type" id="type">
             <option value="<%=task.getTask_type_id().getId() %>"><%=task.getTask_type_id().getName() %> </option>
      <%
         //get types
        TaskTypeController controller = new TaskTypeController();
       List<TaskType> typeList = (List<TaskType>)controller.getAllTaskType();
             
      for(int i=0;i<typeList.size();i++){ 
       %>
               <option value="<%=typeList.get(i).getId()%>"><%=typeList.get(i).getName() %></option>
                      
      <%
       }
      %>                               
                                     
     </select><br></br>
     
       <label for="priority">   Priority:</label>  <select  name="priority" id="priority">
                                                     <option value="<%=task.getPriority().name() %>"><%=task.getPriority().name() %> </option>
                                                     <option value="Critical">Critical</option>
                                                     <option value="Important">Important</option>
                                                     <option value="Normal">Normal</option>
                                                     <option value="LowImportance">Low Importance</option>
                                                    
                                                 </select><br></br>                                    

      <label for="status">   Status:</label>  <select  name="status" id="status">
                                              <option value="<%=task.getStatus().name()%>"><%=task.getStatus().name() %> </option>
                                               <option value="Received">Received</option>
                                               <option value="Completed">Completed</option>
                                               <option value="Closed">Closed</option>
                                                    
                                              </select><br></br>

     <label for="due">  Due Date:</label><input type="text" name="due" id="due" value="<%=BaseServlet.ConvertToDate(task.getDate_due())%>" size="50"/> <label> YYYY-MM-DD</label>  <br></br>
   
     <label for="next">  Next Action Date:</label> <input type="text" name="next" id="next" value="<%=BaseServlet.ConvertToDate(task.getDate_next()) %>" size="50"/> <label> YYYY-MM-DD</label> <br></br>
                           
	  <input type="submit"  value="Save" /> 
	  <span> </span>
	  <input type="submit" value="Mark As Complete"  formaction="TaskServlet?action=complete"/> 
	   <span> </span>
	   <input type="button" value="Back" onclick="window.location='TaskServlet?action=read' " />
	     
	 </form>

	
	<%
	}else{
		
		//Create new task
	%>


<form action="TaskServlet?action=create" method="post">

 <label for="title">    Title:</label>   <input type="text" name="title" id="title"   required size="50"/><br></br> 
 <label for="description"> Description:</label><br> <textarea id="description" name="description" rows="4" cols="50"> </textarea><br></br>
  <label for="entry">    EntryFolder:</label>
   <select   name="entry" id="entry" >
   <%
   EntryFolderController entries = new EntryFolderController();
   List<EntryFolder> entryList = (List<EntryFolder>)entries.getAllEntryFolders();
   
   for(int i = 0; i < entryList.size(); i++ ){
   %>
   <option value="<%= entryList.get(i).getId()%>"> <%=entryList.get(i).getName() %></option>
   
   <%
     }
    %> 
   </select><br></br>
   
   
   <label for="assign">   Assign To:</label>
   
   
   <%
   //select for resources
   ResourceController resourceAssign = new ResourceController();
   List<Resource> assignList = (List<Resource>) resourceAssign.getAllResource();
   if(request.getAttribute("viewGroups") == null){
    %>
   <select name="assign" id="assign" > 
   <%
   for(int i = 0; i < assignList.size(); i++ ){
   %>
   
   <option value="<%= assignList.get(i).getId()%>"> <%=assignList.get(i).getFirstname() %> <%=assignList.get(i).getLastname() %> </option>
  
   <%
   }
   }
   %>
   </select>
    <%
  //select for groups
   if(request.getAttribute("viewGroups") != null){
	  %>
   <select name="assignToGroup" id="assignToGroup" > 
      <%
    	 List<Group> assignToGroup = (List<Group>)request.getAttribute("viewGroups");
    	 for(int i = 0 ; i <assignToGroup.size(); i++ ){
      %>
   <option value="<%=assignToGroup.get(i).getId()%>"><%=assignToGroup.get(i).getName() %> </option>
   
   <%
   }
   }%>
   </select> <span></span> <input type="button" value="Resource Group" title="Assign task to resource groups" onclick="window.location='GroupsServlet?action=viewGroups' "/> <br></br>
    
    
    
   <label for="owner">Owner:</label> 
    
   <select name="owner" id="owner" >
   <%
   for(int i = 0; i < assignList.size(); i++ ){
   %>
   
   <option value="<%= assignList.get(i).getId()%>"> <%=assignList.get(i).getFirstname() %> <%=assignList.get(i).getLastname() %> </option>
   
   <%
     }
    %>
   </select><br></br>
    
    
   <label for="type">Type:</label> 
  
   <select  name="type" id="type">    
      <%
     //get types
    TaskTypeController controller = new TaskTypeController();
    List<TaskType> typeList = (List<TaskType>)controller.getAllTaskType();
             
      for(int i=0;i<typeList.size();i++){ 
       %>
    <option value="<%=typeList.get(i).getId()%>"><%=typeList.get(i).getName() %></option>
                      
      <%
       }
      %>                               
                                     
     </select><br></br>
                                        
                                        
   <label for="priority">   Priority:</label>  <select  name="priority" id="priority">
                                                  <option value="Critical">Critical</option>
                                                   <option value="Important">Important</option>
                                                    <option value="Normal">Normal</option>
                                                    <option value="LowImportance">Low Importance</option>
                                             </select><br></br>                                    

 <label for="status">   Status:</label>  <select  name="status" id="status">
                                                  <option value="Received">Received</option>
                                                   <option value="Completed">Completed</option>
                                                    <option value="Closed">Closed</option>
                                                    
                                             </select><br></br>

  <label for="due">  Due Date:</label><input type="text" name="due" id="due"  placeholder="YYYY-MM-DD" required size="50"/> <label> YYYY-MM-DD</label>  <br></br>
   
  <label for="next">  Next Action Date:</label> <input type="text" name="next" id="next" placeholder="YYYY-MM-DD"  required size="50"/> <label> YYYY-MM-DD</label> <br></br> 
    
 <input type="submit"  value="Save" /> 
     
                                                                                
</form>
<%
}
%>
</div>
</body>
</html>