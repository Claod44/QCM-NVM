package fr.eni_ecole.qcm.servlets;

import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import fr.eni_ecole.qcm.beans.Inscription;
import fr.eni_ecole.qcm.beans.QuestionTirage;
import fr.eni_ecole.qcm.dal.InscriptionDAO;
import fr.eni_ecole.qcm.dal.QuestionTirageDAO;

public class SyntheseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public SyntheseServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//ent�te avec timer, aide, deconnexion, nom utilisateur
		//liste de questions, id des questions, �tat des questions
		//r�ponse tirage
		//message
		
		// D�claration des variables
		RequestDispatcher dispatcher; 
		ArrayList<QuestionTirage> listeQuestionsTirees = new ArrayList<QuestionTirage>();
		String message = "Bienvenue sur l'�cran de synthese";
		int dureeTest = 0;
		int tempsEcoule = 0;
		int idInscription = 0;
		
		try {
			// On r�cup�re la dur�e du test
			
			idInscription = Integer.parseInt(request.getParameter("idInscription"));
			Inscription inscription = InscriptionDAO.selectById(idInscription);
			dureeTest = inscription.getTest().getDuree();
			
			// On r�cup�re le temps �coul�
			tempsEcoule = Integer.parseInt(request.getParameter("tempsEcoule"));
			//prendre l'inscription dans la session
			//utiliser l'inscription pour obtenir la liste des questions tir�es.
			listeQuestionsTirees = QuestionTirageDAO.selectByIdInscription(idInscription);
		}
		catch (Exception e){
			System.out.println("Golden shower dans l'oeil : SyntheseServlet catched");
		}
		finally {
			request.setAttribute("dureeTest", dureeTest);
			request.setAttribute("tempsEcoule", tempsEcoule);
			request.setAttribute("message", message);
			request.setAttribute("listeQuestionsTirees", listeQuestionsTirees);

			dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/candidat/synthese.jsp"); 
			dispatcher.forward(request, response);
		}
	}
}