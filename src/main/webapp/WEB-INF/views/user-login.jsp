<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>LOGIN USER</title>
<%@ include file="/resources/allimports.jsp"%>
<script>
$(document).ready(function() {
 		$("#loginForm").submit(function(event) {
 			event.preventDefault();
 			uname = document.getElementById('userName').value;
 			upass = document.getElementById('password').value;
 			urole = document.getElementById('role').value;
 		//	alert(username+"-----"+password+" -----" + role);

 			$.post("checkCredentials", {
 				uname : uname,
 				upass : upass,
 				urole : urole
 			}, function(responseJson) {
 				console.log(responseJson.key);
 				// 1 all correct; 2 username wrong; 3 username correct but password is wrong
 				if (responseJson.key == "1") {
 					$("#errorMsg").html("");
 					window.location.href = window.location.href + responseJson.page  ;
 				} else if (responseJson.key == "2") {
 					$("#errorMsg").html("Invalid Combination! Please Check!");
 					event.preventDefault();
 					return false;
 				} else if (responseJson.key == "3") {
 					$("#errorMsg").html("Wrong password");
 					event.preventDefault();
 					return false;
 				}
 			});

 		});
 	});
</script>
</head>

<body>
	<div class="container">
		<div class="alert alert-info" role="alert">
			<h4 class="text-center">
				<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
				Login User
			</h4>
		</div>
		<br /> <br /> <label id="errorMsg" style="color:red;"></label> <br />
		<form:form id="loginForm"
			
			modelAttribute="loginUser" name="loginForm" class="form-horizontal">
			<div class="form-group">
				<select id="role" class="form-control" name="role" required="required">
					<option value="">Select the role</option>
					<option value="Donor">DONOR</option>
					<option value="BloodBank">BLOOD BANK</option>
					<option value="Hospital">HOSPITAL</option>
				</select> <br />
			</div>
			<p style="color:red;">${error }</p>
			<div class="form-group">
				<label class="col-sm-4 control-label">Username:</label>
				<div class="col-sm-8">
					<form:input type="text" path="userName" name="userName"
						class="form-control" required="required" />
					<form:errors path="userName" />
					<br />
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-4 control-label">Password:</label>
				<div class="col-sm-8">
					<form:input type="password" path="password" name="password"
						class="form-control" required="required" />
					<form:errors path="password" />
					<br />
				</div>
			</div>
			<div class="col-sm-offset-4 col-sm-8">
				<input type="submit" value="Login" class="btn btn-primary" /> <br />
				</div>
		</form:form>
	</div>

</body>
</html>