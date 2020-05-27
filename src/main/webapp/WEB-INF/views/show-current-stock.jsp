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
<body>
	<a style="font-size: 20px; color: red;"
		href="${pageContext.request.contextPath}/existingUser/main-hospital/currentstock">Back</a>
	
	<a style="font-size: 20px; color: red; padding-left:950px"
		href="${pageContext.request.contextPath}/homepage">Logout</a>
	<br />

	<h3>
		<Strong>Logged In</Strong>: ${sessionScope.userName}
	</h3>
	<br />

	<h3
		style="letter-spacing: 4px; border: 3px; border-style: solid; border-color: #FF0000;">
		<Strong>Blood Bank Stock for </Strong>: ${requestScope.mybb}
	</h3>
	<br />

<a style="font-size: 20px; color: blue; padding-left: 850px"
		href="${pageContext.request.contextPath}/existingUser/main-hospital/downloadPDF/${requestScope.mybb}">Download
		PDF</a>
	<div class="container">
		<table class="table">
			<thead>
				<tr>
					<th>Blood Type</th>
					<th>Blood Amount in ML</th>
				</tr>
			</thead>
			<c:if test="${!requestScope.bbsingle.isEmpty()}">
				<c:forEach items="${requestScope.bbsingle}" var="bb">
					<tr class="danger">
						<td><c:out value="${bb[0]}" /></td>
						<td><c:out value="${bb[1]}" /></td>
					</tr>
				</c:forEach>
			</c:if>

		</table>
	</div>
</body>
</html>