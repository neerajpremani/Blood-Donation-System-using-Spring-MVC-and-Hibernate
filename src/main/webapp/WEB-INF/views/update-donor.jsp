<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>DONOR UPDATE</title>
<%@ include file="/resources/allimports.jsp"%>
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
	integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
	crossorigin="anonymous"></script>
<script>
	$(document).ready(function() {
		$("#submit").submit(function(event) {
			var dateV = $("#dateOfBirth").val();
			var regEx = /([12]\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\d|3[01]))/;
			if (!dateV.match(regEx)) {
				$("#invaliddate").html("Please use yyyy-mm-dd format");
				event.preventDefault();
				return false; // Invalid format
			} else {
				$("#invaliddate").html("");
			}

		});
	})
</script>
<style>
a:link {
	text-decoration: underline;
}
</style>
</head>

<body>
	<a style="font-size: 30px; color: red;"
		href="${pageContext.request.contextPath}/">Back</a>
	<br />
	<div class="container">
		<div class="alert alert-info" role="alert">
			<h2 class="text-center">
				<span class="glyphicon glyphicon-time" aria-hidden="true"></span>
				Donor Update Page!
			</h2>
			<br />
		</div>
		<div class="row">
			<form:form id="submit"
				action="${pageContext.request.contextPath}/existingUser/main-donor/updatedonor"
				enctype="multipart/form-data" modelAttribute="updateDonor">
				<div class="form-group">
					<label class="col-sm-4 control-label">Username</label>
					<div class="col-sm-8">
						<input type="text" name="userName" value="${requestScope.donorUser}" class="form-control"
							placeholder="Donor Username" readonly="true"/>
						
						<br />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-4 control-label">Password</label>
					<div class="col-sm-8">
						<form:input type="password" path="password" class="form-control" />
						<form:errors path="password" />
						<br />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-4 control-label">First Name</label>
					<div class="col-sm-8">
						<form:input type="text" path="firstName" class="form-control"
							placeholder="Donor First Name" />
						<form:errors path="firstName" />
						<br />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-4 control-label">Last Name</label>
					<div class="col-sm-8">
						<form:input type="text" path="lastName" class="form-control"
							placeholder="Donor Last Name" />
						<form:errors path="lastName" />
						<br />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-4 control-label">Date of Birth</label>
					<div class="col-sm-8">
						<form:input id="dateOfBirth" type="date" path="dateOfBirth"
							class="form-control" data-provide="datepicker"
							placeholder="YYYY-MM-DD" />
						<form:errors path="lastName" />
						<label id="invaliddate" style="color: red;"></label> <br />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-4 control-label">Blood Type</label>
					<div class="col-sm-8">
						<form:select path="bloodType" class="form-control">
							<form:option value="A+" label="A+" />
							<form:option value="A-" label="A-" />
							<form:option value="AB+" label="AB+" />
							<form:option value="AB-" label="AB-" />
							<form:option value="B+" label="B+" />
							<form:option value="B-" label="B-" />
							<form:option value="O+" label="O+" />
							<form:option value="O-" label="O-" />
						</form:select>
						<form:errors path="bloodType" />
						<br />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-4 control-label">Gender</label>
					<div class="col-sm-8">
						<form:select path="gender" class="form-control">
							<form:option value="Male" label="Male" />
							<form:option value="Female" label="Female" />
							<form:option value="Other" label="Other" />
						</form:select>
						<form:errors path="gender" />
						<br />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-4 control-label">Email</label>
					<div class="col-sm-8">
						<input type="text" name="email" value="${requestScope.donorEmail}" class="form-control"
							placeholder="Donor Email" readonly="true"/>
						
						<br />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-4 control-label">Phone</label>
					<div class="col-sm-8">
						<form:input type="text" path="phone" class="form-control"
							placeholder="Donor Phone" />
						<form:errors path="phone" />
						<br />
					</div>
				</div>

				
				<div class="col-sm-offset-4 col-sm-8">
					<input type="submit" value="Register Donor" class="btn btn-primary" />
				</div>
			</form:form>
		</div>
	</div>
</body>
</html>