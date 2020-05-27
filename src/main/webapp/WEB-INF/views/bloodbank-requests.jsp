<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>BB Requests</title>
<%@ include file="/resources/allimports.jsp"%>
<style>
table {
	border-collapse: collapse;
	width: 98%;
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
<body>

	<a style="font-size: 20px; color: red;"
		href="${pageContext.request.contextPath}/existingUser/main-bloodbank">Back</a>
	<a style="font-size: 20px; color: red; padding-left: 1000px"
		href="${pageContext.request.contextPath}/homepage">Logout</a>
	<h3>
		<Strong>Logged In</Strong>: ${sessionScope.userName}
	</h3>
	<div class="container">
		<div class="alert alert-info" role="alert">
			<h2 class="text-center">
				<span class="glyphicon glyphicon-time" aria-hidden="true"></span>
				BloodBank Current Stock by BloodType
			</h2>
			<br />
		</div>

		<a style="font-size: 20px; color: red;"
			href="${pageContext.request.contextPath}/existingUser/main-bloodbank/downloadExcel">Download
			Excel</a>
		<c:set var="index" value="0" scope="page" />
		<table class="table">
			<thead>
				<tr>
					<th>Index</th>
					<th>Date Requested</th>
					<th>Blood-Type</th>
					<th>Amount Requested</th>
					<th>Hospital Name</th>
					<th>Hospital Email</th>
					<th>Status</th>
					<th>Approve/Reject</th>
				</tr>
			</thead>
			<c:if test="${!requestScope.bbRequestList.isEmpty()}">
				<c:forEach items="${requestScope.bbRequestList}"
					var="bbReq">
					<c:set var="index" value="${index+1}" scope="page" />
					<tr class="info">
						<td><c:out value="${index}" /></td>
						<td><c:out value="${bbReq.getRequestDate()}" /></td>
						<td><c:out value="${bbReq.getBloodType()}" /></td>
						<td><c:out value="${bbReq.getBloodAmount()}" /></td>
						<td><c:out value="${bbReq.getHospital().getName()}" /></td>
						<td><c:out value="${bbReq.getHospital().getEmail()}" /></td>


						<td><c:out value="${bbReq.getConfirmation()}" /></td>
						<td><c:if
								test="${bbReq.getConfirmation() eq 'Approved'}">
							Already Approved</br>
							</c:if> <c:if test="${bbReq.getConfirmation() eq 'Rejected'}">
							Already Rejected!</br>
							</c:if> <c:if test="${bbReq.getConfirmation() eq 'NotApproved'}">

								<form
									action="${pageContext.request.contextPath}/existingUser/main-bloodbank/bbrequestupdate"
									method="get">
									<c:set var="requestId" value="${bbReq.getId()}" />
									<input type="hidden" value="${requestId}" name="requestId" />
									<input type="hidden" value="update" name="update" /> <input
										type="submit" value="Update Request!" />
								</form>
							</c:if></td>
					</tr>
				</c:forEach>

			</c:if>
		</table>

	</div>

</body>
</html>