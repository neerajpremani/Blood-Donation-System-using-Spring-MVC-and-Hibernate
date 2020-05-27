<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Main Hospital</title>
<%@ include file="/resources/allimports.jsp"%>
</head>
<body>

	<h3>
		<strong>Logged In as user</strong>: ${sessionScope.userName}
	</h3>
	<div class="alert alert-info" role="alert">
		<h2 class="text-center">
			<span class="glyphicon glyphicon-user" aria-hidden="true"></span> <Strong>Hospital's
				Main Page<Strong>
		</h2>

	</div>
	<br />
	<div>
		<a
			href="${pageContext.request.contextPath}/existingUser/main-hospital/bloodrequest">Send
			Blood Request to Hospital</a><br /> <br />
		<a
			href="${pageContext.request.contextPath}/existingUser/main-hospital/currentstock">Blood
			Availability in BloodBanks</a><br />
		<br /> <a
			href="${pageContext.request.contextPath}/existingUser/main-hospital/bloodhistory">See
			Your Request History</a><br />
		<br /> <a href="${pageContext.request.contextPath}/homepage">Logout</a><br />
	</div>

</body>
</html>