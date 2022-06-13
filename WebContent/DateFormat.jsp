<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
      <%@ page import="java.util.Calendar" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 <%@ taglib prefix = "w" uri = "WEB-INF/customtags.tld"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Custom Date Format</title>

<link href="mycss.css" rel="stylesheet" type="text/css"> 
<script src="jquery-3.6.0.min.js"></script>
</head>
<body>
	<%@include file="navbar.jsp"%>
	<br>
	<br>
	<h3>Date format settings</h3>
	<br></br>
	<%
		Calendar cal = Calendar.getInstance();
	%>


		<div class="container">

			<table style="width: 100%; table-layout: fixed" id="myTable">
				<tbody>
					<tr>
						<th>Field Name</th>
						<th>Choose Date Format</th>
						<th>Results</th>
					</tr>

					<tr>
						<td>Date Format</td>
						<td><select id="dateFormat" name="dateFormat">
								<option value="DD-MM-YYYY">DD-MM-YYYY</option>
								<option value="YYYY-MM-DD">YYYY-MM-DD</option>
								<option value="MM-DD-YYYY">MM-DD-YYYY</option>
						</select></td>
						<td><w:formatCalendar calendar="<%=cal%>"
								format="${dateFormat}" /></td>
					</tr>

				</tbody>
			</table>

		</div>
		<br> <input type="button" value="Save" class="center-block" id="save">

	<script>
$("document").ready(function(){
	//var fmt = $("#dateFormat").val();
	

	$("#save").on("click", function(){
		var fmt = $("#dateFormat").find(":selected").text();
	    $.ajax({

	           url: 'ResourceServlet?action=changeFormat',
		       type: 'get',
		       cache: 'false',
		       data: {dateFormat: fmt},
		       success : function(data) {

		    	 $('#myTable td:last').html(data);
                alert('Format changed to : ' +data);


		       },
		       error : function() {
			       $("#res")
					alert('Something went wrong');
				}

		    
		    });

		
		});
		


	
});



	</script>
</body>
</html>