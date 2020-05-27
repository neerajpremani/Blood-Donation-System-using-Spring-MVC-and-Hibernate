<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>BB Request Update</title>
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
</style>
</head>
<body>
	<a style="font-size: 20px; color: red;"
		href="${pageContext.request.contextPath}/existingUser/main-bloodbank/bbrequests">Back</a>

	<a style="font-size: 20px; color: red; padding-left: 1000px"
		href="${pageContext.request.contextPath}/homepage">Logout</a>

	<h3>
		<Strong>Logged In</Strong>: ${sessionScope.userName}
	</h3>
	<div class="container">
		<div class="alert alert-info" role="alert">
			<h2 class="text-center">
				<span class="glyphicon glyphicon-time" aria-hidden="true"></span>
				BloodBank Request Update
			</h2>
			<br />
		</div>
		<c:set var="index" value="0" scope="page" />
		<table class="table">
			<thead>
				<tr>
					<th>Sr No.</th>
					<th>Date Requested</th>
					<th>Blood-Type</th>
					<th>Amount Requested</th>
					<th>Hospital Name</th>
					<th>Hospital Email</th>
					<th>Status</th>
					<th>Approve/Reject Action</th>
				</tr>
			</thead>
			<c:set var="singleRequest" value="${requestScope.singleBloodRequest}" />
			<c:set var="index" value="${index+1}" scope="page" />
			<tr class="info">
				<td><c:out value="${index}" /></td>
				<td><c:out value="${singleRequest.getRequestDate()}" /></td>
				<td><c:out value="${singleRequest.getBloodType()}" /></td>
				<td><c:out value="${singleRequest.getBloodAmount()}" /></td>
				<td><c:out value="${singleRequest.getHospital().getName()}" /></td>
				<td><c:out value="${singleRequest.getHospital().getEmail()}" /></td>
				<td><c:out value="${singleRequest.getConfirmation()}" /></td>
				<td><c:if test="${not empty yesstock}">
						<form
							action="${pageContext.request.contextPath}/existingUser/main-bloodbank/bbrequestupdate"
							method="get">
							<c:set var="requestId" value="${singleRequest.getId()}" />
							<input type="hidden" value="${requestId}" name="requestId" /> <input
								type="hidden" value="Approved" name="update" /> <input
								type="submit" value="Approve Request!" />
						</form>
						<form
							action="${pageContext.request.contextPath}/existingUser/main-bloodbank/bbrequestupdate"
							method="get">
							<c:set var="requestId" value="${singleRequest.getId()}" />
							<input type="hidden" value="${requestId}" name="requestId" /> <input
								type="hidden" value="Rejected" name="update" /> <input
								type="submit" value="Reject Request!" />
						</form>
					</c:if> <c:if test="${not empty nostock}">
		Cannot Update
		</c:if></td>
			</tr>

		</table>

		<h4 style="color: red;">
			BloodBank Stock for BloodType
			${requestScope.singleBloodRequest.getBloodType()} is <Strong>${requestScope.bloodQuantity}</Strong>
		</h4>

		<c:if test="${requestScope.nostock.length()>0}">

			<h4 style="color: red;">${requestScope.nostock}</h3>
		</c:if>
		<c:if test="${requestScope.yesstock.length()>0}">

			<h4 style="color: red;">${requestScope.yesstock}</h3>
		</c:if>
		<br /> <br />
	</div>
</body>
</html>