<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page session="false"%>
<!DOCTYPE html>
<html>
<head>
<head>
<title>Home</title>

<%@ include file="/resources/allimports.jsp"%>
</head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

<style>
a:link, a:visited {
	background-color: white;
	color: black;
	border: 2px solid red;
	padding: 10px 20px;
	text-align: center;
	text-decoration: none;
	display: inline-block;
}

a:hover, a:active {
	background-color: red;
	color: white;
}
</style>


</head>

<body>

	<div class="container">
		<div class="alert alert-info" role="alert">
			<h2 class="text-center">
				<span class="glyphicon glyphicon-time" aria-hidden="true"></span>
				Blood Bank Donation System
			</h2>
			<br />
		</div>
		<br />
	</div>

	<p>
		<strong>Register User</strong>
	</P>
	<ul>
		<li><a href="${pageContext.request.contextPath}/register-donor">REGISTER
				AS DONOR</a></li>
	</br>
		<li><a href="${pageContext.request.contextPath}/register-bb">REGISTER
				AS BLOOD BANK</a></li>
		</br>
		<li><a
			href="${pageContext.request.contextPath}/register-hospital">REGISTER
				AS HOSPITAL</a></li>
		</br>
	</ul>

	<p>
		<strong>EXISTING USER?</strong>
	</p>

	<a class="logclass"
		href="${pageContext.request.contextPath}/existingUser">LOGIN</a>


</body>
</html>
