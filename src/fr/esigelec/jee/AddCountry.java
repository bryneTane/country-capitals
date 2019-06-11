package fr.esigelec.jee;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class AddCountry
 */
@WebServlet("/AddCountry")
public class AddCountry extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private DAO dao = new DAO();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddCountry() {
        super();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if ((request.getParameter("name") != "") && (request.getParameter("code") != "") && (request.getParameter("capital") != "")) {
			if(dao.createNewCountry(request.getParameter("name"), request.getParameter("code"), request.getParameter("capital"))) {
				HttpSession session = request.getSession();
				session.setAttribute("Added", "true");
				response.setHeader("Location", "CountryCapital.jsp");
				response.setStatus(HttpServletResponse.SC_FOUND); // SC_FOUND = 302							
			}else {
				HttpSession session = request.getSession();
				session.setAttribute("nAdded", "true");
				response.setHeader("Location", "NewCountry.jsp");
				response.setStatus(HttpServletResponse.SC_FOUND); // SC_FOUND = 302
			}
		}else {
			HttpSession session = request.getSession();
			session.setAttribute("Confirm", "true");
			response.setHeader("Location", "NewCountry.jsp");
			response.setStatus(HttpServletResponse.SC_FOUND); // SC_FOUND = 302
		}
	}

}
