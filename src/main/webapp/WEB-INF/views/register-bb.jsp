<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>BB REGISTER</title>

<%@ include file="/resources/allimports.jsp"%>
<style>
a:link {
  text-decoration: underline;
}
.errors{
color:red;}
</style>
</head>
<body>
	
<a style="font-size:30px; color:red;" href="${pageContext.request.contextPath}/">Back</a>
	<br />
	<div class="container">
		<div class="alert alert-info" role="alert">
			<h2 class="text-center">
				<span class="glyphicon glyphicon-time" aria-hidden="true"></span>
				Welcome to Blood Bank Registration
			</h2>
		</div>
		<div class="row">
			<form:form action="register-bb" modelAttribute="bb"
				>
				<div class="form-group">
					<label class="col-sm-4 control-label">Username</label>
					<div class="col-sm-12">
						<form:input type="text" path="userName" class="form-control"
							placeholder="BloodBank Username" />
						<form:errors class="errors" path="userName" />
						<br />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-4 control-label">Password</label>
					<div class="col-sm-12">
						<form:input type="password" path="password" class="form-control" />
						<form:errors class="errors" path="password" />
						<br />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-4 control-label">Name</label>
					<div class="col-sm-12">
						<form:input type="text" path="name" class="form-control"
							placeholder="BloodBank Name" />
						<form:errors class="errors" path="name" />
						<br />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-4 control-label">Email</label>
					<div class="col-sm-12">
						<form:input type="text" path="email" class="form-control"
							placeholder="BloodBank Email" />
						<form:errors  class="errors" path="email" />
						<br />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-4 control-label">Phone</label>
					<div class="col-sm-12">
						<form:input type="text" path="phone" class="form-control"
							placeholder="BloodBank Phone" />
						<form:errors  class="errors" path="phone" />
						<br />
					</div>
				</div>
				<div class="col-sm-offset-4 col-sm-8">
					<input type="submit" value="Register Blood Bank"
						class="btn btn-primary" />
				</div>
			</form:form>
		</div>
	</div>
</body>
</html>