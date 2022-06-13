<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>

<style>
.container {
	border-radius: 5px;
	background-color: #f2f2f2;
	padding: 100px;
}

h3 {
	color: white;
	text-align: center;
}

body {
	font-family: verdana;
	font-size: 15px;
	background-color: lightblue;
}

button {
	display: block;
	margin-right: auto;
	margin-left: auto;
	background-color: lightblue;
	color: white;
}

.errorLabel {
	color: red;
	display: block;
	text-align: center;
	font-size: 13px;
}
</style>

</head>
<body>


	<h3>Login</h3>
	<br></br>

	<c:if test="${error != null}">
		<label for="ename" class="errorLabel">${error}</label>
		<br>
	</c:if>
	<div class="container">
		<form action="LoginServlet?action=login" method="post"
			style="margin: auto; width: 500px;">

			<label for="uname"><b>Username</b></label> <input type="text"
				placeholder="Enter Username" name="username" required size="50">
			<br></br> <label for="psw"><b>Password</b></label> <input
				type="password" placeholder="Enter Password" name="password"
				required size="50"><br></br>

			<button type="submit">SIGN IN</button>

		</form>
	</div>


</body>
</html>