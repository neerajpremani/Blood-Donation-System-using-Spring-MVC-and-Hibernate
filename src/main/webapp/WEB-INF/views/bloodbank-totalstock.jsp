<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Blood Donation History Stock</title>
<%@ include file="/resources/allimports.jsp"%>
<style>
table {
	border-collapse: collapse;
	width: 90%;
}

table, th, td {
	text-align: center;
	border: 1px solid black;
}

a:link {
	text-decoration: underline;
}
</style>
</head>
<body >
	<a style="font-size: 20px; color: red;"
		href="${pageContext.request.contextPath}/existingUser/main-bloodbank">Back</a>

	<a style="font-size: 20px; color: red; padding-left:950px"
		href="${pageContext.request.contextPath}/homepage">Logout</a>

	<h3>
		<Strong>Logged In</Strong>: ${sessionScope.userName}
	</h3>
	<br />
	<br />
	<div class="container">
		<div class="alert alert-info" role="alert">
			<h2 class="text-center">
				<span class="glyphicon glyphicon-time" aria-hidden="true"></span>
				BloodBank Donation Stock by BloodType
			</h2>
			<br />
		</div>
	<a style="font-size: 20px; color: blue; padding-left:880px"
		href="${pageContext.request.contextPath}/existingUser/main-bloodbank/downloadPDF">Download
		PDF</a>
	<table class="table">
		<tr>
			<th>Blood Type</th>
			<th>Blood Amount in ML</th>
		</tr>
		<c:if test="${!requestScope.bbstocks.isEmpty()}">
			<c:forEach items="${requestScope.bbstocks}" var="bb">
				<tr>
					<td><c:out value="${bb[0]}" /></td>
					<td><c:out value="${bb[1]}" /></td>
				</tr>
			</c:forEach>
		</c:if>

	</table>
	</div>



</body>
</html>