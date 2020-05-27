<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Donor History Details</title>
<%@ include file="/resources/allimports.jsp"%>
<style>
table {
	border-collapse: collapse;
	width: 90%;
}

table, th, td {
	border: 1px solid black;
}
</style>
</head>
<body>
	<a href="${pageContext.request.contextPath}/homepage">Logout</a>
	<br />
	<a href="${pageContext.request.contextPath}/existingUser/main-donor">Back</a>
	<br />
	<h3>
		<Strong>Logged In</Strong>: ${sessionScope.userName}
	</h3>
	<br />
	<br />
	<h1>
		<Strong>Donor History Details</Strong>
	</h1>
	<br />
	<br />
	<table>
		<tr>
			<th>Donation ID</th>
			<th>Donor Name</th>
			<th>Donated Date</th>
			<th>Blood Type</th>
			<th>Blood Amount</th>
			<th>Blood Bank Id</th>

			<th>Blood Bank Name</th>
		</tr>
		<c:if test="${!requestScope.bbDonorHistory.isEmpty()}">
			<c:forEach items="${requestScope.bbDonorHistory}" var="donor">
				<tr>
					<td><c:out value="${donor.getId()}" /></td>
					<td><c:out value="${donor.getDonor().getFirstName()}" /> <c:out
							value="${donor.getDonor().getLastName()}" /></td>
					<td><c:out value="${donor.getDonatedDate()}" /></td>
					<td><c:out value="${donor.getBloodType()}" /></td>
					<td><c:out value="${donor.getBloodAmount()}" /></td>
					<td><c:out value="${donor.getBloodBank().getId()}" /></td>
					<td><c:out value="${donor.getBloodBank().getName()}" /></td>
				</tr>
			</c:forEach>
		</c:if>

	</table>


</body>
</html>