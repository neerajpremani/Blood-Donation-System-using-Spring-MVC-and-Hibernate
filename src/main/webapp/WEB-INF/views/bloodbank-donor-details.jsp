<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>BB Donor Details</title>
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
		<strong>Logged In</strong>: ${sessionScope.userName}
	</h3>
	<br />
	<c:set var="index" value="0" scope="page" />
	<div class="container">
		<div class="alert alert-info" role="alert">
			<h2 class="text-center">
				<span class="glyphicon glyphicon-time" aria-hidden="true"></span>
				BloodBank Donor Details
			</h2>
			<br />
		</div>
		<table class="table" border="1" cellspacing="5" cellpadding="5"
			align="center">
			<tr>
				<th>Index</th>
				<th>Donation Date</th>
				<th>Blood Amount Donated</th>
				<th>FirstName</th>
				<th>LastName</th>
				<th>Gender</th>
				<th>BloodType</th>
				<th>Date of Birth</th>
				<th>Email Address</th>
				<th>Phone Number</th>

			</tr>
			<c:if test="${!requestScope.bloodHistoryList.isEmpty()}">
				<c:forEach items="${requestScope.bloodHistoryList}" var="dd">
					<c:set var="index" value="${index+1}" scope="page" />
					<tr class="info">
						<td><c:out value="${index}" /></td>
						<td><c:out value="${dd.getDonatedDate()}" /></td>
						<td><c:out value="${dd.getBloodAmount()}" /></td>
						<td><c:out value="${dd.getDonor().getFirstName()}" /></td>
						<td><c:out value="${dd.getDonor().getLastName()}" /></td>
						<td><c:out value="${dd.getDonor().getGender()}" /></td>
						<td><c:out value="${dd.getDonor().getBloodType()}" /></td>
						<td><c:out value="${dd.getDonor().getDateOfBirth()}" /></td>
						<td><c:out value="${dd.getDonor().getEmail()}" /></td>
						<td><c:out value="${dd.getDonor().getPhone()}" /></td>

					</tr>
				</c:forEach>
			</c:if>

		</table>
	</div>
</body>
</html>