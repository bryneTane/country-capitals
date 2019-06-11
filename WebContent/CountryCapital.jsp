<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="fr.esigelec.jee.DAO" %>

<%
		String content = "";
		DAO dao = new DAO();
		String country = null;
	if(request.getSession().getAttribute("UserInformation") != null){		
		Cookie[] cookies = request.getCookies();
		if(cookies != null) {
			for (Cookie c : cookies){
				if (c.getName().equals("COUNTRY")){
					country = c.getValue();
				}
			}			
		}
							
		if (country == null) {	
			String buttons = "";
			for(int i=1; i<dao.getCountryCode().size(); i++) {
				buttons += "<a href='CountryCapital.jsp?country="+dao.getCountryCode().get(i)+"' class='btn btn-primary'>"+dao.getCountryName(dao.getCountryCode().get(i))+"</a>";
			}
			content = "	<h1>World Country Capital Web Application</h1>"
						+ "	<h3>The capital of "+dao.getCountryName(dao.getCountryCode().get(0))+" is "+dao.getCapital(dao.getCountryCode().get(0))+"</h3>"
						+ "<br>"
						+ buttons;
			Cookie cookie = new Cookie("COUNTRY", dao.getCountryCode().get(0));
			cookie.setMaxAge(-1); //this is a session cookie
			response.addCookie(cookie);
		}else {
			if (request.getParameter("country") != null) country = request.getParameter("country");
			String buttons = "";
			for(String code : dao.getCountryCode()) {
				if(!code.equals(country)) buttons += "<a href='CountryCapital.jsp?country="+code+"' class='btn btn-primary'>"+dao.getCountryName(code)+"</a>";
			}
			
			content = "	<h1>World Country Capital Web Application</h1>"
					+ "	<h3>The Capital of "+dao.getCountryName(country)+" is "+dao.getCapital(country)+".</h3>"
					+ "<br>"
					+ buttons;
			for (Cookie c : cookies){
				if (c.getName().equals("COUNTRY")){
					c.setValue(country);
					response.addCookie(c);
				}
			}
			
		}
	}else{
		response.setHeader("Location", "LoginForm.jsp");
		response.setStatus(HttpServletResponse.SC_FOUND); // SC_FOUND = 302
	}
%>
<!DOCTYPE html>
<html>
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
		<title>Country Capital</title>
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous" /> 
		<style>
			body{
				text-align: center;
			}
			.btn{
				margin: 30px;
				width: 250px;
			}
		</style>
	</head>
	<body>
		<nav class="navbar navbar-light bg-light">
		  <h1 class="navbar-brand"><%= request.getSession().getAttribute("UserInformation") %></h1>
		  <form action="Logout" method="post">
			  <button class="btn btn-outline-success my-2 my-sm-0" style="width: 100px" type="submit">Logout</button>
		  </form>
		</nav>
		<% if(request.getSession().getAttribute("Added") != null){ %>
			<div class="alert alert-success alert-success fade show" role="alert">
			   The country was successfully added
			  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
			    <span aria-hidden="true">&times;</span>
			  </button>
			</div>
		<% 
				request.getSession().removeAttribute("Added");
			} 
		%>
		<% if(request.getSession().getAttribute("Modified") != null){ %>
			<div class="alert alert-success alert-success fade show" role="alert">
			   The country was successfully modified
			  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
			    <span aria-hidden="true">&times;</span>
			  </button>
			</div>
		<% 
				request.getSession().removeAttribute("Modified");
			} 
		%>
		<div class="text-center">
			<%= content %>	
			<br/>	
			<a href="NewCountry.jsp" class="btn btn-primary">Add new country</a>
			<a href="ModifyCountry.jsp?name=<%= dao.getCountryName(country) %>&code=<%= country %>&capital=<%= dao.getCapital(country) %>" class="btn btn-primary">Modify <%= dao.getCountryName(country) %></a>
		</div>
		<footer><%@ include file="footer.jsp" %></footer>
		<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
		<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
	</body>
</html>