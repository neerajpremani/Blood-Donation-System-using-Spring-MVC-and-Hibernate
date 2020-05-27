<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>BB Donation History</title>
<%@ include file="/resources/allimports.jsp"%>
<style>
table {
	border-collapse: collapse;
	width: 90%;
}

table, th, td {
	border: 1px solid black;
}
a:link {
  text-decoration: underline;
}
</style>
</head>
<body>
	<a style="font-size: 20px; color: red;"
		href="${pageContext.request.contextPath}/existingUser/main-bloodbank">Back</a>

	<a style="font-size: 20px; color: red; padding-left: 1000px"
		href="${pageContext.request.contextPath}/homepage">Logout</a>
	<br />

	<h3>
		<Strong>Logged In</Strong>: ${sessionScope.userName}
	</h3>
	<c:set var="index" value="0" scope="page" />
	<div class="container">
		<div class="alert alert-info" role="alert">
			<h2 class="text-center">
				<span class="glyphicon glyphicon-time" aria-hidden="true"></span>
				BloodBank Donation History
			</h2>
			<br />
		</div>
		<table class="table">
			<thead>
				<tr>
					<th>Index</th>
					<th>Donation Date</th>
					<th>BloodType</th>
					<th>Blood Amount</th>
					<th>BloodBank ID</th>
					<th>Donor ID</th>

				</tr>
			</thead>
			<c:if test="${!requestScope.bloodHistoryList.isEmpty()}">
				<c:forEach items="${requestScope.bloodHistoryList}"
					var="bloodhistory">
					<c:set var="index" value="${index+1}" scope="page" />
					<tr class="info">
						<td><c:out value="${index}" /></td>
						<td><c:out value="${bloodhistory.getDonatedDate()}" /></td>
						<td><c:out value="${bloodhistory.getBloodType()}" /></td>
						<td><c:out value="${bloodhistory.getBloodAmount()}" /></td>
						<td><c:out value="${bloodhistory.getBloodBank().getId()}" /></td>
						<td><c:out value="${bloodhistory.getDonor().getId()}" /></td>

					</tr>
				</c:forEach>
			</c:if>

		</table>
	</div>
</body>
</html>