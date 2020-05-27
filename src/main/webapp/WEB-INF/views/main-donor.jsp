<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Main Donor</title>
<%@ include file="/resources/allimports.jsp"%>
</head>
<body>

	<%
		System.out.println("Donor Main Page");
	%>
	<h3>
		<strong>Logged In</strong>: ${sessionScope.userName}
	</h3>
	<br />
	<br />
	<h1>Donor's Login Home Page</h1>
	<br />
	<br />

	<div>
		
		<a href="${pageContext.request.contextPath}/existingUser/main-donor/mydonatehistory">Donation History</a><br/>
		<a href="${pageContext.request.contextPath}/homepage">Logout</a>
	</div>

</body>
</html>