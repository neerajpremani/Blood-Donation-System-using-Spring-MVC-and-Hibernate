<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>BB HOSPITAL</title>
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

	</br>
	<div class="container">
		<div class="alert alert-info" role="alert">
			<h2 class="text-center">
				<span class="glyphicon glyphicon-time" aria-hidden="true"></span> <Strong>Welcome
					to Hospital Registration<Strong>
			</h2>
			<br />
		</div>
		<div class="row">

			<form:form action="register-hospital"  modelAttribute="hospital">
				<div class="form-group">
					<label class="col-sm-4 control-label">Username</label>
					<div class="col-sm-8">
						<form:errors path="userName" class="errors"/>
						<form:input type="text" path="userName" class="form-control"  
							placeholder="Hospital Username" />
						<br />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-4 control-label">Password</label>
					<div class="col-sm-8">
						<form:errors path="password" class="errors"/>
						<form:input type="password" path="password" class="form-control"   />
						<br />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-4 control-label">Name</label>
					<div class="col-sm-8">
						<form:errors path="name" class="errors"/>
						<form:input type="text" path="name" placeholder="Hospital Name"  
							class="form-control" />
						<br />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-4 control-label">Email</label>
					<div class="col-sm-8">
						<form:errors path="email" class="errors"/>
						<form:input type="text" path="email" placeholder="Hospital Email"  
							class="form-control" />
						<br />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-4 control-label">Phone</label>
					<div class="col-sm-8">
						<form:errors path="phone" class="errors"/>
						<form:input type="text" path="phone" placeholder="Hospital Phone"  
							class="form-control" />
						<br />
					</div>
				</div>
				<div class="col-sm-offset-4 col-sm-8">
					<input type="submit" value="Register Hospital"
						class="btn btn-primary" />
				</div>
			</form:form>
		</div>
	</div>

</body>
</html>