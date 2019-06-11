<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
		<title>Country Capital</title>
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous" />
		<title>Login Form</title>
	</head>
	<body class="container">
		<div class="card">
		  <div class="card-header bg-primary text-white"><h3>Country Capitals - Add User</h3></div>
		  <div class="card-body">
			<form method="post" action="AddUser">
			  <div class="form-group">
			    <label for="name">Your name</label>
			    <input type="text" class="form-control" name="name" id="name" placeholder="Enter your name">
			  </div>
			  <div class="form-group">
			    <label for="email">Your email address</label>
			    <input type="email" class="form-control" name="email" id="email" placeholder="Enter your email address">
			  </div>
			  <div class="form-group">
			    <label for="password">Password</label>
			    <input type="password" class="form-control" name="password" id="password" placeholder="Enter your password">
			  </div>
			  <div class="form-group">
			    <label for="cpassword">Confirm Password</label>
			    <input type="password" class="form-control" name="cpassword" id="cpassword" placeholder="Confirm your password">
			  </div>
			  <button type="submit" class="btn btn-primary" id="submit" value="submit">Submit</button>
			</form>
		  </div>
		</div>
		<% if(request.getSession().getAttribute("Confirm") != null){ %>
			<div class="alert alert-warning alert-danger fade show" role="alert">
			   Please control your entries
			  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
			    <span aria-hidden="true">&times;</span>
			  </button>
			</div>
		<% 
				request.getSession().removeAttribute("Confirm");
			} 
		%>
		<% if(request.getSession().getAttribute("nAdded") != null){ %>
			<div class="alert alert-warning alert-danger fade show" role="alert">
			   The user could not be added
			  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
			    <span aria-hidden="true">&times;</span>
			  </button>
			</div>
		<% 
				request.getSession().removeAttribute("nAdded");
			} 
		%>
		<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
		<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
	</body>
</html>