<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Donor Form</title>
<%@ include file="/resources/allimports.jsp"%>
<style>
a:link {
  text-decoration: underline;
}
</style>
<script type="text/javascript">
	var flag = true;
	var flag1=false;
	function keypressFunc(val) {

		if (val.match(/^\d+$/)) {
			flag = true;
			$("#numbersOnly").html("");
			//this.value = this.value.replace(/[^0-9\.]/g, '');
		} else {
			flag = false;
			$("#numbersOnly").html("Please add numbers");
			event.preventDefault();
			return false;
		}

	}

	$(document).ready(function() {
 		$("#donateForm").submit(function(event) {
 			console.log(flag,flag1);
			duser = document.getElementById('donorusername').value;
 			demail = document.getElementById('donoremail').value;
 			
 		//	alert(username+"-----"+password+" -----" + role);

 			$.ajax({
				url : "checkDonor",
				type: 'POST',
				data : {duser: duser, demail : demail},
				async: false,
				success: function(responseJson){
				//	alert(responseJson.key);
					//console.log(data);
					if (responseJson.key == "1") {
						
	 					flag1=true;
	 					console.log(flag,flag1);
	 					//alert(flag+"  "+ flag1);
	 					$("#errorMsg").html("");
	 					if(flag && flag1){
	 						return true;
	 					}
	 					else{
	 						event.preventDefault();
	 						return false;
	 					}	
	 				}else  {
	 					flag1=false;
	 					console.log(flag,flag1);
	 					$("#errorMsg").html("Donor Username OR Email Doesnt exist!");
	 					event.preventDefault();
	 					return false;
	 				}
				}
 			}); 
		});
	}); 

/*	$(document).ready(function() {
 		$("#donateFormbtn").click(function(event) {
 			
 			duser = document.getElementById('donorusername').value;
 			demail = document.getElementById('donoremail').value;
 			
 		//	alert(username+"-----"+password+" -----" + role);

 			$.post("checkDonor", {
 				duser : duser,
 				demail : demail
 				
 			}, function(responseJson) {
 				console.log(responseJson.key);
 				// 1 all correct; 2 username wrong; 3 username correct but password is wrong
 				if (responseJson.key == "1") {
 					flag1=true;
 					$("#errorMsg").html("");
 					
 				} else if (responseJson.key == "2") {
 					flag1=false;
 					$("#errorMsg").html("Donor Username OR Email Doesnt exist!");
 					event.preventDefault();
 					return false;
 				} 
 			});

 		});
 	});
 	*/
</script>
</head>
<body>

	<a style="font-size:20px; color:red;"
		href="${pageContext.request.contextPath}/existingUser/main-bloodbank">Back</a>
	
	<a style="font-size:20px; color:red; padding-left:1000px;" href="${pageContext.request.contextPath}/homepage">Logout</a>
	
	
	<h3>
		<strong>Logged In</strong>: ${sessionScope.userName}
	</h3>
	<br />
	<div class="container">
		<div class="alert alert-info" role="alert">
			<h4 class="text-center">
				<span class="glyphicon glyphicon-time" aria-hidden="true"></span>
				 Blood Donate Form!
			</h4>
		</div>
		<br />
		<div class="row">
			<form:form id="donateForm"
				action="${pageContext.request.contextPath}/existingUser/main-bloodbank/blooddonateform"
				method="POST" modelAttribute="donationObj" >
					<label id="errorMsg" style="color: red;"></label> <br />
				<div class="form-group">
					<label class="col-sm-4 control-label">Name: </label>
					<div class="col-sm-8">
						<input type="text" name="donorname" class="form-control"
							placeholder="Donor Name" required="required" /> <br />
					</div>
				</div>
				
			 
				<div class="form-group">
					<label class="col-sm-4 control-label">Username: </label>
					<div class="col-sm-8">
						<input type="text" id="donorusername" name="donorusername" class="form-control"
							placeholder="Donor username" required="required" minlength="4"
							maxlength="16" /> <br />
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-sm-4 control-label">Email: </label>
					<div class="col-sm-8">
						<input type="text" class="form-control" id="donoremail" required="required" name="donoremail"
							placeholder="Donor Email"></input> <br />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-4 control-label">Blood Amount: </label>
					<div class="col-sm-8">
						<form:input type="number" min="1" required="required" path="bloodAmount"
							class="form-control" onkeyup="keypressFunc(this.value)"
							placeholder="Blooad Amount" />
						<form:errors path="bloodAmount" />
						<label id="numbersOnly" style="color: red;"></label> <br /> <br />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-4 control-label">Today's Date: </label>
					<div class="col-sm-8">
						<form:input type="text" class="form-control" path="donatedDate"
							readonly="true" />
						<form:errors path="donatedDate" />
						<br />
					</div>
				</div>
				<div class="col-sm-offset-4 col-sm-8">
					<input id="donateFormbtn" type="submit" value="DONATE BLOOD" class="btn btn-primary" />
				</div>
			</form:form>
		</div>
	</div>
</body>
</html>