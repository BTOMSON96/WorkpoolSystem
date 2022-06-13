<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="com.workpool.servlet.*" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib prefix = "w" uri = "WEB-INF/customtags.tld"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Workpool Home</title>


<link href="mycss.css" rel="stylesheet" type="text/css">
<script src="jquery-3.6.0.min.js"></script>

</head>
<body>



	<%@include file="navbar.jsp"%>


	<h3>Resource Management</h3>
	<br></br>

	<c:if test="${errors != null}">
		<label for="ename" class="errorLabel">${errors}</label>
		<br>
	</c:if>



	<form action="ResourceServlet?action=byname" method="post">
		<input type="text" id="myInput" name="myInput"
			placeholder="Search by name.." title="Type in a name" required>
		<input type="submit" value="Search"> <a
			href="GroupsServlet?action=read" style="float: right; color: blue;">Resource
			Groups</a> <br></br>
			
			<a href="DateFormat.jsp" style="float: right; color: blue;">Date Format
			</a>
	</form>

	 <input type="checkbox" name="actie" id="actie"
				style="width: 15px; height: 15px">
			<label for="active">View Active Resources AJAX:</label>
			<br>

	<c:choose>

		<c:when test="${ActiveChecked  != null}">
			<input type="checkbox" name="active" id="active" checked="checked"
				onclick="getActive()" style="width: 15px; height: 15px">
			<label for="active">View Active Resources:</label>
			<br>
		</c:when>
		<c:otherwise>
			<input type="checkbox" name="active" id="active"
				onclick="getActive()" style="width: 15px; height: 15px">
			<label for="active">View Active Resources:</label>
			<br>
		</c:otherwise>
	</c:choose>

	<c:choose>
		<c:when test="${InActiveChecked != null}">
			<input type="checkbox" name="inactive" id="inactive"
				checked="checked" onclick="getInactive()"
				style="width: 15px; height: 15px">
			<label for="inactive">View Inactive Resources:</label>
			<br></br>
		</c:when>

		<c:otherwise>
			<input type="checkbox" name="inactive" id="inactive"
				onclick="getInactive()" style="width: 15px; height: 15px">
			<label for="inactive">View Inactive Resources:</label>
			<br></br>
		</c:otherwise>
	</c:choose>
<table style="width: 20%; display:none; table-layout: fixed" id="Table">
<tbody>
<tr>
<th>ID </th>
<th>NAME </th>
</tr>


</tbody>

</table> <br> <br>

	 
	<table style="width: 100%; table-layout: fixed" id="myTable">
		<tbody>

			<tr>
				<th>ID</th>
				<th>Name</th>
				<th>Surname</th>
				<th>Date of Birth</th>
				<th>address</th>
				<th>Email</th>
				<th>Username</th>
				<th>Password</th>
				<th>Type</th>
				<th>Manager</th>
				<th>Active</th>
				<th>Sys Admin</th>


			</tr>



			<c:if test="${resources != null }">
				<c:forEach items="${resources}" var="rs">


					<tr>
						<td class="edit_row"> <w:workpoolLink url="Create.jsp?" link_name="${rs.id}">
								<w:param id="${rs.id}" />
							</w:workpoolLink></td>
						<td class="row_name"><c:out value="${rs.name}" /></td>
						<td class="row_surname"><c:out value="${rs.lastname}" /></td>
						<td class="row_dob"><w:formatCalendar calendar="${rs.dob}"
								format="${dateFormat}" /></td>
						<td class="row_address"><c:out value="${rs.address}" /></td>
						<td class="row_email"><c:out value="${rs.email}" /></td>
						<td class="row_username"><c:out value="${rs.username}" /></td>
						<td class="row_password"><c:out value="${rs.password}" /></td>
						<td class="row_type"><c:out value="${rs.type}" /></td>




						<c:choose>
							<c:when test="${rs.manager == null }">
								<td class="row_manager"></td>
							</c:when>

							<c:otherwise>

								<td class="row_manager"><c:out
										value="${rs.manager.name} ${rs.manager.lastname}"></c:out></td>

							</c:otherwise>

						</c:choose>


						<c:choose>
							<c:when test="${rs.isActive == true }">
								<td class="row_active"><input type="checkbox"
									name="isActive" checked="checked" disabled></td>
							</c:when>

							<c:otherwise>
								<td class="row_active"><input type="checkbox"
									name="isActive" disabled></td>
							</c:otherwise>

						</c:choose>

						<c:choose>
							<c:when test="${rs.isAdmin == true }">
								<td class="row_admin"><input type="checkbox" name="isAdmin"
									checked="checked" disabled></td>
							</c:when>

							<c:otherwise>
								<td class="row_admin"><input type="checkbox" name="isAdmin"
									disabled></td>
							</c:otherwise>
						</c:choose>

					</tr>



				</c:forEach>
			</c:if>




			<c:if test="${activeResources != null }">
				<c:forEach items="${activeResources}" var="rs">

					<tr>
						<td class="edit_row"><a href="Create.jsp?id=${rs.id}"
							style="color: blue"> <c:out value="${rs.id}"/>  </a></td>
						<td class="row_name"><c:out value="${rs.name}"/></td>
						<td class="row_surname"><c:out value="${rs.lastname}"/></td>
						<td class="row_dob"><w:formatCalendar calendar="${rs.dob}"
								format="${dateFormat}" /></td>
						<td class="row_address"><c:out value="${rs.address}"/></td>
						<td class="row_email"><c:out value="${rs.email}"/></td>
						<td class="row_username"><c:out value="${rs.username}"/></td>
						<td class="row_password"><c:out value="${rs.password}"/></td>
						<td class="row_type"><c:out value="${rs.type}"/></td>



						<c:choose>
							<c:when test="${rs.manager == null }">
								<td class="row_manager"></td>
							</c:when>

							<c:otherwise>

								<td class="row_manager"> <c:out value="${rs.manager.name} ${rs.manager.lastname}"></c:out></td>

							</c:otherwise>

						</c:choose>



						<td class="row_active"><input type="checkbox" name="isActive"
							checked="checked" disabled></td>

                        <c:choose>
							<c:when test="${rs.isAdmin == true }">
								<td class="row_admin"><input type="checkbox" name="isAdmin"
									checked="checked" disabled></td>
							</c:when>

							<c:otherwise>
								<td class="row_admin"><input type="checkbox" name="isAdmin"
									disabled></td>
							</c:otherwise>
						</c:choose>
						
					</tr>
				</c:forEach>
			</c:if>



			<c:if test="${inactiveResources != null }">
				<c:forEach items="${inactiveResources}" var="rs">

					<tr>
						<td class="edit_row"><a href="Create.jsp?id=${rs.id}"
							style="color: blue"> <c:out value="${rs.id}"/> </a></td>
						<td class="row_name"><c:out value="${rs.name}"/></td>
						<td class="row_surname"><c:out value="${rs.lastname}"/></td>
						<td class="row_dob"><w:formatCalendar calendar="${rs.dob}"
								format="${dateFormat}" /></td>
						<td class="row_address"><c:out value="${rs.address}"/></td>
						<td class="row_email"><c:out value="${rs.email}"/></td>
						<td class="row_username"><c:out value="${rs.username}"/></td>
						<td class="row_password"><c:out value="${rs.password}"/></td>
						<td class="row_type"><c:out value="${rs.type}"/></td>



						<c:choose>
							<c:when test="${rs.manager == null }">
								<td class="row_manager"></td>
							</c:when>

							<c:otherwise>

								<td class="row_manager">
									 <c:out value="${rs.manager.name} ${rs.manager.lastname}"></c:out></td>

							</c:otherwise>

						</c:choose>


						<td class="row_active"><input type="checkbox" name="isActive"
							disabled></td>

	                    <c:choose>
							<c:when test="${rs.isAdmin == true }">
								<td class="row_admin"><input type="checkbox" name="isAdmin"
									checked="checked" disabled></td>
							</c:when>

							<c:otherwise>
								<td class="row_admin"><input type="checkbox" name="isAdmin"
									disabled></td>
							</c:otherwise>
						</c:choose>


					</tr>

				</c:forEach>
			</c:if>

		</tbody>
	</table>
	
	<br />
	<br />


	<input type="button" value="Add Resource" class="center-block"
		onclick="window.location='Create.jsp'">







	<script>
		function getActive() {
			var checkBoxActive = document.getElementById("active");

			if (checkBoxActive.checked == true) {
				window.location = 'ResourceServlet?action=active';

			} else {
				window.location = 'ResourceServlet?action=read';
			}
		}

		function getInactive() {
			var checkBoxInactive = document.getElementById("inactive");
			if (checkBoxInactive.checked == true) {
				window.location = 'ResourceServlet?action=inactive';
			} else {
				window.location = 'ResourceServlet?action=read';
			}
		}

		
		$("document").ready(function() {
			//$("#Table").hide("fast");
			$("h3").css("border", "3px solid black");
			
			$("#actie").change(function() {
				if(this.checked) {
					$.ajax({
						url : 'ResourceServlet?action=activeAjax',
						type : 'get',
						cache : false,
						success : function(data) {
							
							//alert(data);
							$("#myTable").css("display", "none");
							$("#Table").show(4000);
							 var arr = $.parseJSON(data); //convert to javascript array
							 
							    $.each(arr, function(id, name){
							    	$("#Table tbody").append("<tr><td>"+id+"</td><td>"+name+"</td> </tr>" );
							    });

						},//end success
						error : function() {
							alert('Something went wrong');
						}

					});//end ajax call

				}//end if statement
				else{
					$("#Table").hide("fast");
					$("#myTable").show(4000);
			
					}
			});//end onchange event

		});
	</script>



</body>
</html>