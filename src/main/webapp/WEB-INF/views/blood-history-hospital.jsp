<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Hospital Request History</title>
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
		href="${pageContext.request.contextPath}/existingUser/main-hospital">Back</a>

	<a style="font-size: 20px; color: red; padding-left: 1000px"
		href="${pageContext.request.contextPath}/homepage">Logout</a>
	<br />

	<h3>
		<strong>Logged In</strong>: ${sessionScope.userName}
	</h3>
	<br />
	<br />
	<h1>Hospital Requests History</h1>
	<br />
	<br />
	<c:set var="index" value="0" scope="page" />
	<div class="container">
		<table class="table">
			<thead>
				<tr>
					<th>Index</th>
					<th>Date Requested</th>
					<th>BloodBank Name</th>
					<th>BloodType</th>
					<th>Amount Requested</th>

					<th>BloodBank Email</th>
					<th>BloodBank Phone</th>
					<th>Status</th>
					<th>Delete If PENDING!</th>
				</tr>

			</thead>
			<c:if test="${!requestScope.hospitalhistory.isEmpty()}">
				<c:forEach items="${requestScope.hospitalhistory}" var="blood">
					<c:set var="index" value="${index+1}" scope="page" />
					<tr class="danger">
						<td><c:out value="${index}" /></td>
						<td><c:out value="${blood.getRequestDate()}" /></td>
						<td><c:out value="${blood.getBloodBank().getName()}" /></td>
						<td><c:out value="${blood.getBloodType()}" /></td>
						<td><c:out value="${blood.getBloodAmount()}" /></td>

						<td><c:out value="${blood.getBloodBank().getEmail()}" /></td>
						<td><c:out value="${blood.getBloodBank().getPhone()}" /></td>

						<td><c:out value="${blood.getConfirmation()}" /></td>
						<td><c:if test="${blood.getConfirmation() eq 'Approved'}">
							Cannot Delete</br>
							</c:if> <c:if test="${blood.getConfirmation() eq 'Rejected'}">
							Cannot Delete</br>
							</c:if> <c:if test="${blood.getConfirmation() eq 'NotApproved'}">
								<form
									action="${pageContext.request.contextPath}/existingUser/main-hospital/bloodhistorydelete"
									method="get">
									<c:set var="reqId" value="${blood.getId()}" />
									<input type="hidden" value="${reqId}" name="reqId" /> <input
										type="hidden" value="delete" name="delete" /> <input
										type="submit" value="Delete Request" />
								</form>
							</c:if></td>
					</tr>
				</c:forEach>

			</c:if>
		</table>
	</div>
	<c:if test="${requestScope.hospitalhistory eq null}">
		<br />
		<p style="font-size: 20px; color: red;">NO HOSPITAL HISTORY FOR
			BLOOD REQUESTS!</p>
	</c:if>
</body>
</html>