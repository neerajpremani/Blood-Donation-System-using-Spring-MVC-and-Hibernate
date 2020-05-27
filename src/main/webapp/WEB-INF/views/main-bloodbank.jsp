<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Main-BloodBank</title>
<%@ include file="/resources/allimports.jsp"%>
</head>
<body>
	
	<h1>
		<strong>Logged In</strong>: ${sessionScope.userName}
	</h1>
	<div class="alert alert-info" role="alert">
		<h2 class="text-center">
			<span class="glyphicon glyphicon-user" aria-hidden="true"></span> <Strong>BloodBank's
				Main Page<Strong>
		</h2>

	</div>
	<br />
	<br />
	<div>
		<a href="${pageContext.request.contextPath}/existingUser/main-bloodbank/blooddonateform">DONATE FORM</a><br /><br />
		<a href="${pageContext.request.contextPath}/existingUser/main-bloodbank/donationhistory">BLOOD BANK DONATION HISTORY</a><br /> <br />
        <a href="${pageContext.request.contextPath}/existingUser/main-bloodbank/donordetails">BLOOD BANK DONOR DETAILS</a><br /><br />
		<a href="${pageContext.request.contextPath}/existingUser/main-bloodbank/bbstock">BLOOD BANK STOCK (DONATED)</a><br /><br />
		<a href="${pageContext.request.contextPath}/existingUser/main-bloodbank/bbcurrentstock">BLOOD BANK STOCK (CURRENT)</a><br /><br />
		<a href="${pageContext.request.contextPath}/existingUser/main-bloodbank/bbrequests">BLOOD BANK REQUESTS</a><br /> <br />
		<a href="${pageContext.request.contextPath}/homepage">LOGOUT</a><br />
	</div>


</body>
</html>