package fr.esigelec.jee;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ModifyCountry
 */
@WebServlet("/ModifyCountry")
public class ModifyCountry extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private DAO dao = new DAO();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifyCountry() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if ((request.getParameter("name") != "") && (request.getParameter("code") != "") && (request.getParameter("capital") != "")) {
			if(dao.modifyCountry(Integer.parseInt(request.getParameter("id")), request.getParameter("name"), request.getParameter("code"), request.getParameter("capital"))) {
				HttpSession session = request.getSession();
				session.setAttribute("Modified", "true");
				response.setHeader("Location", "CountryCapital.jsp");
				response.setStatus(HttpServletResponse.SC_FOUND); // SC_FOUND = 302							
			}else {
				HttpSession session = request.getSession();
				session.setAttribute("nAdded", "true");
				response.setHeader("Location", "ModifyCountry.jsp");
				response.setStatus(HttpServletResponse.SC_FOUND); // SC_FOUND = 302
			}
		}else {
			HttpSession session = request.getSession();
			session.setAttribute("Confirm", "true");
			response.setHeader("Location", "ModifyCountry.jsp");
			response.setStatus(HttpServletResponse.SC_FOUND); // SC_FOUND = 302
		}
	}

}
