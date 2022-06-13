<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
       <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Task Classification</title>

<link href="mycss.css" rel="stylesheet" type="text/css">

</head>
<body>

	<%@include file="navbar.jsp"%>



	<h3>Task Classification</h3>
	<br></br>

	<div class="container">


		<table style="width: 50%; table-layout: fixed" id="myTable">
			<tbody>

				<tr>
					<th>ID</th>
					<th>Name</th>
				</tr>




				<c:if test="${types != null }">
					<c:forEach items="${types }" var="type">
						<tr>
							<td>${type.id}</td>
							<td><a href="CreateType.jsp?id=${type.id} "
								style="color: blue"> ${type.name} </a></td>



						</tr>

					</c:forEach>
				</c:if>
			</tbody>
		</table>
	</div>
	<br></br>
	<input type="button" value="Add Task Classification"
		class="center-block" onclick="window.location='CreateType.jsp'">
</body>
</html>