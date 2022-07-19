package fr.eni_ecole.qcm.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import fr.eni_ecole.qcm.beans.Candidat;
import fr.eni_ecole.qcm.beans.Inscription;
import fr.eni_ecole.qcm.dal.InscriptionDAO;


public class AccueilCandidatServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher; 
		Candidat candidat = (Candidat) request.getSession().getAttribute("candidat");
		
		List<Inscription> listeInscriptions = new ArrayList<Inscription>();
		List<Inscription> listeInscriptionsTerminees = new ArrayList<Inscription>();
		
		try {
//			listeInscriptions = InscriptionDAO.selectByCandidat(candidat);
			listeInscriptions = InscriptionDAO.selectInscritByCandidat(candidat);
			listeInscriptionsTerminees = InscriptionDAO.selectTermineByCandidat(candidat);
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("lesInscriptions", listeInscriptions);
		request.setAttribute("lesInscriptionsTerminees", listeInscriptionsTerminees);
		dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/candidat/accueil.jsp"); 
		dispatcher.forward(request, response);
		
	}
}
