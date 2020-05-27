<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="x"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Request Blood Form</title>
<%@ include file="/resources/allimports.jsp"%>
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
	integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
	crossorigin="anonymous"></script>
<script>
	/*$('.numbersOnly').keyup(function () {
	 alert(this.value);
	 if (this.value != this.value.replace(/[^0-9\.]/g, '')) {
	 this.value = this.value.replace(/[^0-9\.]/g, '');
	 }else{
	 $("#numbersOnly").html("Please add numbers");
	 }
	 });*/

	var flag = true;

	function keypressFunc(val) {
		if (val.match(/^\d+$/)) {
			flag = true;
			$("#numbersOnly").html("");
			//this.value = this.value.replace(/[^0-9\.]/g, '');
		} else {
			flag = false;
			$("#numbersOnly").html("Please add numbers");
			event.preventDefault();
		}
	}

	function checkForm() {
		return flag;
	}
</script>

<style>
a:link {
	text-decoration: underline;
}
</style>
</head>
<body>
	<a style="font-size: 30px; color: red;"
		href="${pageContext.request.contextPath}/existingUser/main-hospital">Back</a>

	<br />
	<h3>
		<strong>Logged In</strong>: ${sessionScope.userName}
	</h3>

	<div class="container">
		<div class="alert alert-info" role="alert">
			<h2 class="text-center">
				<span class="glyphicon glyphicon-time" aria-hidden="true"></span>
				Blood Request Form
			</h2>
			<br />
		</div>
		<div class="row">
			<form:form
				action="${pageContext.request.contextPath}/existingUser/main-hospital/bloodrequest"
				modelAttribute="requestBloodForm" class="form-horizontal"
				onSubmit="return checkForm()">
				<div class="form-group">
					<label class="col-sm-4 control-label">Request Blood Amount:</label>
					<div class="col-sm-8">
						<form:input class="form-control"
							onkeyup="keypressFunc(this.value)" type="number" min="1"
							required="required" path="bloodAmount" />
						<form:errors path="bloodAmount" />
						<label id="numbersOnly" style="color: red;"></label> <br />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-4 control-label">Date: </label>
					<div class="col-sm-8">
						<form:input type="text" path="requestDate" readonly="true"
							class="form-control" />
						<form:errors path="requestDate" />
						<br />
					</div>
				</div>
				<br />
				<div class="form-group">
					<label class="col-sm-4 control-label">Blood Type:</label>
					<div class="col-sm-8">
						<form:select class="form-control" path="bloodType"
							name="bloodType">
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
					
						<label class="col-sm-4 control-label">BloodBank:</label>
						<div class="col-sm-8"> <select
							class="form-control" name="bbName">

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
				<br />
				<br />
				<br />
			


				<div class="col-sm-offset-4 col-sm-8">
					<input type="submit" value="Send Request" class="btn btn-primary" />
				</div>
				<br />
				<br />

				<div class="col-sm-offset-4 col-sm-8">
					<input type="reset" value="Clear Form" class="btn btn-primary" />
					<br />
				</div>
			</form:form>
		</div>
	</div>

</body>
</html>