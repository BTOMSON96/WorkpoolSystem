<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
      <%@ page import="java.util.Calendar, java.util.List, java.util.List, com.workpool.entity.*, com.workpool.controller.TaskTypeController" %>
        <%@ page import="java.util.*,com.workpool.entity.*, com.workpool.controller.*, com.workpool.util.HibernateUtil, org.hibernate.Session, java.util.Iterator,com.workpool.servlet.* "%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Activity</title>

<link href="mycss.css" rel="stylesheet" type="text/css">

</head>
<body>
<%@include file="navbar.jsp" %>




<h3>ADD ACTIVITY</h3> <br></br>
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
 <div>

<a href="ActivityServlet?action=read" style="float: right; color: blue;">OPEN ACTIVITIES</a>  <br></br>

</div>
<div class="container">

<%
if(request.getParameter("id") != null){
	Long id = Long.parseLong(request.getParameter("id"));
	Session sess = HibernateUtil.getSessionFactory().openSession();
	Activity activity = (Activity) com.workpool.controller.AbstractController.getObjectById(Activity.class, id , sess);	
	
%>	
	<form action="ActivityServlet?action=update" method="post">
	  <input type="hidden" name="id" id="id" value="<%=id%>">
	<label for="title">    Title:</label>   <input type="text" name="title" id="title" value="<%=activity.getTitle() %>"  required size="50"/><br></br> 
	<label for="description"> Description:</label><br> <textarea id="description" name="description"    rows="4" cols="50"><%=activity.getDescription() %> </textarea><br></br>
	<label for="entry">    EntryFolder:</label> <input type="text" name="entry" id="entry" placeholder="<%=activity.getEntry_id().getName() %>"  size="50" disabled/><br></br>
    <label for="TaskNo">    Task No:</label> <input type="text" name="taskno" id="taskno"  placeholder="#<%=activity.getTask_id().getId() %>"   size="50" disabled/><br></br>
	
	<label for="type">   Type:</label>
 
  <select  name="type" id="type">
  
       <option value="<%=activity.getType_id().getId() %>"><%=activity.getType_id().getName() %> </option>
             
        <%
          //get types
        TaskTypeController controller = new TaskTypeController();
        List<TaskType> typeList = (List<TaskType>)controller.getAllTaskType();
             
       for(int i=0;i<typeList.size();i++)    
          { 
        %>
               <option value="<%=typeList.get(i).getId()%>"><%=typeList.get(i).getName() %></option>
                      
        <%
         }
       %>                               
    </select><br></br>
    <label for="started">   Started On:</label> <input type="text" name="started" id="started" disabled  size="50" value="<%=activity.getStart().getTime() %>"/><br></br>                                     
    <label for="taken">     Time Taken:</label> <input type="number" name="taken" id="taken"  disabled value="<%=activity.getEnd().get(Calendar.MINUTE) %>" size="50" /> <select  name="time" id="time"><option value=" ">Minutes</option> </select>  <br></br><br></br>
    <input type="submit"  value="Update" /> 
    <span></span>
    <input type="submit" value="Close Activity" formaction="ActivityServlet?action=close">
     <span></span>
     <input type="button" value="Back" onclick="window.location='ActivityServlet?action=read'">
	</form>


<% 
}else{
%>

<form action="ActivityServlet?action=create" method="post">
<label for="title">    Title:</label>   <input type="text" name="title" id="title"   required size="50"/><br></br> 
<label for="description"> Description:</label><br> <textarea id="description" name="description" rows="4" cols="50"> </textarea><br></br>
<label for="entry">    EntryFolder:</label> <input type="number" name="entry" id="entry"   required size="50"/><br></br>
<label for="TaskNo">    Task No:</label> <input type="number" name="taskno" id="taskno"    size="50"/><br></br>

 <label for="type">   Type:</label>
 
  <select  name="type" id="type">
             
        <%
          //get types
        TaskTypeController controller = new TaskTypeController();
        List<TaskType> typeList = (List<TaskType>)controller.getAllTaskType();
             
       for(int i=0;i<typeList.size();i++)    
          { 
       %>
               <option value="<%=typeList.get(i).getId()%>"><%=typeList.get(i).getName() %></option>
                      
      <%
       }
      %>                               
    </select><br></br>
                                     
   <%
  Calendar started = Calendar.getInstance();
  %>                                   
<label for="started">   Started On:</label> <input type="text" name="started" id="started"   required size="25" value="<%=BaseServlet.ConvertToDate(started)%>"/> <input type="text"  size="1" value="<%=started.get(started.HOUR_OF_DAY) %>:<%=started.get(started.MINUTE) %>"/><br></br>                                     
<label for="taken">     Time Taken:</label> <input type="number" name="taken" id="taken"   required size="50" /> <select  name="time" id="time"><option value=" ">Minutes</option> </select> <br></br>  
<label for="close">     Close Activity:</label> <input type="checkbox" name="close" id="close" />  <br></br><br></br>
<input type="submit"  value="Save" /> 
</form>
<%
}
%>
</div> <br></br>


</body>
</html>