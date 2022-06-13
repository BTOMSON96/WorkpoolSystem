<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Workpool Home</title>

<link href="mycss.css" rel="stylesheet" type="text/css">
</head>
<body>


	<%@include file="navbar.jsp"%>
	<h3>Resource Rensponsibilities Confirmation</h3>
	<br></br>
	
	
	
<div class="container" > 

	<c:if test="${tasksOwned != null }">

	
			<form action="TaskServlet?action=updateTaskOwner" method="post">

				<label style="font-weight: bold;">WORKFLOW FOR:
					${tasksOwned.iterator().next().getOwner_id().name }
					${tasksOwned.iterator().next().getOwner_id().lastname } </label> <br></br> <input
					type="hidden" name="tasksOwner" id="tasksOwner"
					value="${tasksOwned.iterator().next().getOwner_id().id}"> <label>Tasks
					owned by this resource : ${tasksOwned.size()}</label> <br></br> <label>Reassign
					Ownership of these tasks before deactivating</label> <select name="owner"
					id="owner">


					<c:forEach items="${resources}" var="rs">
						<option value="${rs.id}">${rs.name} ${rs.lastname}</option>
					</c:forEach>

				</select> <span> </span><input type="submit" value="Submit" /> <br></br>

			</form>
	</c:if>


	<c:if test="${resourcesManaged != null}">
		<form action="ResourceServlet?action=updateManager" method="post">
			<input type="hidden" name="manager" id="manager"
				value="${resourcesManaged.iterator().next().getManager().id }">
			<label>Resources managed by this resource :
				${resourcesManaged.size()}</label><br></br> <label>Change manager
				for these resources</label> <select name="newManager" id="newManager">

				<c:forEach items="${resources}" var="rs">
					<option value="${rs.id}">${rs.name} ${rs.lastname}</option>
				</c:forEach>


			</select> <span> </span><input type="submit" value="Submit" /> <br></br>
		</form>
	</c:if>

	<input type="button" value="Back" onclick="history.back()" />
	
</div>

</body>
</html>