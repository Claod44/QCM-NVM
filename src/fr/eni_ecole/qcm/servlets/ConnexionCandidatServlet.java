package fr.eni_ecole.qcm.servlets;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import fr.eni_ecole.qcm.beans.Candidat;
import fr.eni_ecole.qcm.dal.CandidatDAO;

/**
 * Servlet implementation class ConnexionCandidatsServlet
 */
public class ConnexionCandidatServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//System.out.println(request.getAttribute("mail"));
		//System.out.println(request.getParameter("pwd"));
		// TODO Auto-generated method stub
		//v�rifie que le mail et le mot de passe n'est pas nul, et si ils ne sont pas compos�s que d'espaces
		if((request.getParameter("mail")!=null)&&(request.getParameter("pwd")!=null)) {
			//regexp de validation email ?
			Candidat candidat = null;
			try {
				candidat = CandidatDAO.authentifier(request.getParameter("mail").toString(), request.getParameter("pwd").toString());
				if (candidat!=null)
				{
					HttpSession session = request.getSession(false);
					if(session!=null) {
						//verifier
						session.setAttribute("candidat", candidat);
						response.sendRedirect(request.getContextPath() + "/candidat/accueil");
					}
				}
				else {
					//todo
				String lemessage = "Identifiants incorrects.";
				request.setAttribute("message", lemessage);
				this.getServletContext().getRequestDispatcher("/").forward(request, response);
				}
			}
			catch (SQLException ex) {
				String lemessage = "Probl�me de base";
				request.setAttribute("message", lemessage);
				this.getServletContext().getRequestDispatcher("/").forward(request, response);
				
			}
			catch (Exception ex) {
				String lemessage = "Ton server xml bordel !!!";
				HttpSession session = request.getSession(false);
				session.setAttribute("message", lemessage);
				response.sendRedirect(request.getContextPath());
			}
		}
		else {
		String lemessage = "Un de vos champs est incorrect.";
		request.setAttribute("message", lemessage);
		this.getServletContext().getRequestDispatcher("/").forward(request, response);
		}
	}
}
