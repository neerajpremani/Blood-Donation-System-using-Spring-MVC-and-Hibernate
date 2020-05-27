<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="x"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>BB Current Stock</title>
<%@ include file="/resources/allimports.jsp"%>
<style>
a:link {
	text-decoration: underline;
}
</style>
</head>
<body>
	<a style="font-size: 30px; color: red;"
		href="${pageContext.request.contextPath}/existingUser/main-hospital">Back</a>

	<h3>
		<strong>Logged In</strong>: ${sessionScope.userName}
	</h3>

	<div class="container">
		<div class="alert alert-info" role="alert">
			<h2 class="text-center">
				<span class="glyphicon glyphicon-time" aria-hidden="true"></span>
				Blood Stock Hospital
			</h2>
			<br />
		</div>
		<div class="row">
			<form:form
				action="${pageContext.request.contextPath}/existingUser/main-hospital/bbcurrentstock"
				method="get">
				<div class="form-group">
					<label class="col-sm-20 control-label">Select Blood Bank
						Name:</label>
					<div class="col-sm-8">
						<select class="form-control" name="bbName">
							<x:if test="${!requestScope.bbListName.isEmpty()}">
								<x:forEach items="${requestScope.bbListName}" var="name">
									<option value="${name.getName()}" label="${name.getName()}" />
								</x:forEach>
							</x:if>

							<x:if test="${requestScope.bbListName.isEmpty()}">
								<form:option value="noBB" label="BloodBank NOT Found" />
							</x:if>
						</select>
					</div>
				</div>
				<br/>
				<br/>
				<br/>
				<div class="col-sm-offset-4 col-sm-8">
					<input type="submit" value="Send Request" class="btn btn-primary" />
				</div>
				<br />

				<br />

			</form:form>
		</div>
	</div>
</body>
</html>