package fr.esigelec.jee;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class AddUser
 */
@WebServlet("/AddUser")
public class AddUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private DAO dao = new DAO();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddUser() {
        super();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if ((request.getParameter("name") != "") && (request.getParameter("password") != "") && (request.getParameter("email") != "") && (request.getParameter("password").equals(request.getParameter("cpassword")))) {
			if(dao.createNewUser(request.getParameter("email"), request.getParameter("name"), request.getParameter("password"))) {
				HttpSession session = request.getSession();
				session.setAttribute("Added", "true");
				response.setHeader("Location", "LoginForm.jsp");
				response.setStatus(HttpServletResponse.SC_FOUND); // SC_FOUND = 302							
			}else {
				HttpSession session = request.getSession();
				session.setAttribute("nAdded", "true");
				response.setHeader("Location", "NewUser.jsp");
				response.setStatus(HttpServletResponse.SC_FOUND); // SC_FOUND = 302
			}
		}else {
			HttpSession session = request.getSession();
			session.setAttribute("Confirm", "true");
			response.setHeader("Location", "NewUser.jsp");
			response.setStatus(HttpServletResponse.SC_FOUND); // SC_FOUND = 302
		}
	}

}
