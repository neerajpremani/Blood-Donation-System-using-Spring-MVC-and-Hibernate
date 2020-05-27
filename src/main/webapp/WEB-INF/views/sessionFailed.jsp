<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Exception Page!</title>
</head>
<body>

	<h1>You are not signed in!</h1>
	
	<a href="${pageContext.request.contextPath}/existingUser/main-bloodbank/blooddonateform">DONATE FORM</a><br /><br />
		<a href="${pageContext.request.contextPath}/existingUser/main-bloodbank/donationhistory">BLOOD BANK DONATION HISTORY</a><br /> <br />
        <a href="${pageContext.request.contextPath}/existingUser/main-bloodbank/donordetails">BLOOD BANK DONOR DETAILS</a><br /><br />
		<a href="${pageContext.request.contextPath}/existingUser/main-bloodbank/bbstock">BLOOD BANK STOCK (DONATED)</a><br /><br />
		<a href="${pageContext.request.contextPath}/existingUser/main-bloodbank/bbcurrentstock">BLOOD BANK STOCK (CURRENT)</a><br /><br />
		<a href="${pageContext.request.contextPath}/existingUser/main-bloodbank/bbrequests">BLOOD BANK REQUESTS</a><br /> <br />
	
	
	<c:if
		test="${request.getRequestURI().equals('/bloodbank/existingUser/main-hospital/bloodrequest')}">
Please signed in as Donor <a
			href="http://localhost:8080/bloodbank/existingUser?"> here</a>
	</c:if>

	<c:if
		test="${request.getRequestURI().equals('/bloodbank/existingUser/main-hospital/currentstock')}">
Please signed in as BloodBank <a
			href="http://localhost:8080/bloodbank/existingUser?"> here</a>
	</c:if>
	<c:if
		test="${request.getRequestURI().equals('/bloodbank/existingUser/main-bloodbank/blooddonateform')}">
Please signed in as BloodBank <a
			href="http://localhost:8080/bloodbank/existingUser?"> here</a>
	</c:if>
	<c:if
		test="${request.getRequestURI().equals('/bloodbank/existingUser/main-hospital/bloodhistory')}">
Please signed in as BloodBank <a
			href="http://localhost:8080/bloodbank/existingUser?"> here</a>
	</c:if>
	<c:if
		test="${request.getRequestURI().equals('/bloodbank/existingUser/main-bloodbank/donationhistory')}">
Please signed in as BloodBank <a
			href="http://localhost:8080/bloodbank/existingUser?"> here</a>
	</c:if>

	<c:if
		test="${request.getRequestURI().equals('/bloodbank/existingUser/main-bloodbank/bbstock')}">
Please signed in as Hospital <a
			href="http://localhost:8080/bloodbank/existingUser?"> here</a>
	</c:if>
	<c:if
		test="${request.getRequestURI().equals('/bloodbank/existingUser/main-bloodbank/bbcurrentstock')}">
Please signed in as Hospital <a
			href="http://localhost:8080/bloodbank/existingUser?"> here</a>
	</c:if>
	<c:if
		test="${request.getRequestURI().equals('/bloodbank/existingUser/main-bloodbank/bbrequests')}">
Please signed in as Hospital <a
			href="http://localhost:8080/bloodbank/existingUser?"> here</a>
	</c:if>
	
	<c:if
		test="${request.getRequestURI().equals('/bloodbank/existingUser/main-donor/mydonatehistory')}">
Please signed in as Hospital <a
			href="http://localhost:8080/bloodbank/existingUser?"> here</a>
	</c:if>
	
	<c:if
		test="${request.getRequestURI().equals('/bloodbank/existingUser/main-bloodbank/bbrequests')}">
Please signed in as Hospital <a
			href="http://localhost:8080/bloodbank/existingUser?"> here</a>
	</c:if>
	
	<c:if
		test="${request.getRequestURI().equals('/bloodbank/existingUser/main-bloodbank/bbrequestupdate')}">
Please signed in as Hospital <a
			href="http://localhost:8080/bloodbank/existingUser?"> here</a>
	</c:if>




</body>
</html>