package fr.esigelec.jee;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private DAO dao = new DAO();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		if( request.getParameter("login") != null &&  request.getParameter("password") != null ) {
//			if(request.getParameter("login").equals("user") && request.getParameter("password").equals("2019")) {
//				response.getWriter().append("Welcome");
//			}else {
//				response.sendError(403);
//			}
//		}else {
//			response.sendError(403);
//		}
		
		String name = dao.validateUserCredentials(request.getParameter("email"), request.getParameter("password"));
		if(name != null) {
			response.getWriter().append("<h1>Welcome</h1>");
//			response.sendRedirect("CountryCapital");
			HttpSession session = request.getSession();
			session.setAttribute("UserInformation", name);
			response.setHeader("Location", "CountryCapital.jsp");
			response.setStatus(HttpServletResponse.SC_FOUND); // SC_FOUND = 302
		}else /*if (dao.validateUserCredentials(request.getParameter("login"), request.getParameter("password")) == 2)*/ {
//			response.getWriter().append("FORBIDDEN");
//			response.sendError(403);
			HttpSession session = request.getSession();
			session.setAttribute("Invalid", "true");
			response.setHeader("Location", "LoginForm.jsp");
			response.setStatus(HttpServletResponse.SC_FOUND); // SC_FOUND = 302
//		}else {
//			response.getWriter().append("BAD REQUEST");
//			response.sendError(400);	
		}
	}

}
