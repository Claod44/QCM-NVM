package fr.eni_ecole.qcm.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import fr.eni_ecole.qcm.beans.*;
import fr.eni_ecole.qcm.dal.*;

public class QuestionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
		//On initialise nos variables
		Inscription inscription = null;
		QuestionTirage laQuestion = null;
		List<Section> listeSection = null;
		List<Proposition> listePropositions = null;
		List<QuestionTirage> listeQuestionTirage = null;
		List<ReponseTirage> listeReponseTirage = null;
		boolean derniereQuestion = false;
		int idInscription = 0;
		int numOrdre = 0;
		int nbQuestions = 0;
		int idQuestionRepondue = 0;
		int dureeTest = 0;
		int tempsEcoule = 0;
		
		try {					
			// R�cup�ration des param�tres de la requete
			if(request.getParameterMap().containsKey("idInscription")){
				idInscription = Integer.parseInt(request.getParameter("idInscription"));
			} else {
				idInscription = (Integer)request.getAttribute("idInscription");
			}
			
			// On r�cup�re notre Inscription depuis la requete
			inscription = InscriptionDAO.selectById(idInscription);
			
			// On r�cup�re la dur�e du test
			dureeTest = inscription.getTest().getDuree();
			
			// On r�cup�re le temps �coul�
			if(request.getParameter("tempsEcoule") != null){
				tempsEcoule = Integer.parseInt(request.getParameter("tempsEcoule"));
			}else{
				tempsEcoule = inscription.getTempsEcoule();
			}
			
			// Enregistrement du temps �coul� en BDD
			if(tempsEcoule > 0){
				inscription = InscriptionDAO.selectById(idInscription);
				inscription.setTempsEcoule(tempsEcoule);
				InscriptionDAO.update(inscription);
			}
			
			// Test a supprimer
			if(tempsEcoule < 0){
				System.out.println("tempsEcoule : " + tempsEcoule);
			}

			// V�rification du timer. Si temps �coul� = dur�e du test, on route vers la page de r�sultats
			if(tempsEcoule == dureeTest){
				RequestDispatcher dispatcher;
				dispatcher = request.getServletContext().getRequestDispatcher("/candidat/validation"); 
				dispatcher.forward(request, response);
				return;
			}
						
			// D�termination de la valeur du numordre en fonction du contexte de la requete ( pr�c�dent, suivant ou acc�s depuis le bouton de lancement du test )
			if(request.getParameter("precedent") != null){
				numOrdre = Integer.parseInt(request.getParameter("numOrdre")) - 1;
			}else if(request.getParameter("suivant") != null){
				numOrdre = Integer.parseInt(request.getParameter("numOrdre")) + 1;
			}else{
				if(request.getParameterMap().containsKey("numOrdre")){
					numOrdre = Integer.parseInt(request.getParameter("numOrdre"));
				} else {
					numOrdre = (Integer)request.getAttribute("numOrdre");
				}
			}
			
			if(request.getParameter("navigation") != null){
				numOrdre = Integer.parseInt(request.getParameter("navigation"));
			}
			
			// Gestion du marquage
			if(request.getParameter("marquage") != null){
				idQuestionRepondue = Integer.parseInt(request.getParameter("idQuestionRepondue"));
				QuestionTirage questionRepondue = QuestionTirageDAO.selectById(idInscription, idQuestionRepondue);
				
				if(Boolean.parseBoolean(request.getParameter("estMarquee")) == true){
					questionRepondue.setEstMarquee(true);
					QuestionTirageDAO.update(questionRepondue);
				}else{
					questionRepondue.setEstMarquee(false);
					QuestionTirageDAO.update(questionRepondue);
				}
			}
			
			if(request.getParameterMap().containsKey("reponse")){
							
				idQuestionRepondue = Integer.parseInt(request.getParameter("idQuestionRepondue"));
				
				if(ReponseTirageDAO.selectById(idQuestionRepondue, idInscription) != null){
					ReponseTirageDAO.deleteAllQuestion(idInscription, idQuestionRepondue);
				}
				
				for (String i : request.getParameterValues("reponse")) {
					ReponseTirageDAO.insert(
							new ReponseTirage(
									PropositionDAO.selectById(Integer.parseInt(i)),
									QuestionDAO.selectById(idQuestionRepondue),
									InscriptionDAO.selectById(idInscription)
									)
							);
					QuestionTirage questionRepondue = QuestionTirageDAO.selectById(idInscription, idQuestionRepondue);
					questionRepondue.setEstRepondue(true);
					QuestionTirageDAO.update(questionRepondue);
					
				}
			}
			
			// V�rification d'existance de l'attribut synthese, reroutage vers la synthese le cas �chant
			if(request.getParameter("synthese") != null){
				RequestDispatcher dispatcher;
				dispatcher = request.getServletContext().getRequestDispatcher("/candidat/synthese"); 
				dispatcher.forward(request, response);
				return;
			}

			// On v�rifie si des questions ont d�j� �t� selectionn�es
			nbQuestions = QuestionTirageDAO.selectNbQuestions(idInscription);

			// Si le tirage des questions n'a pas encore �t� effectu�
			if (nbQuestions == 0){
				//On va chercher les sections correspondantes en BDD
				listeSection = SectionDAO.selectById(inscription.getTest().getIdTest());
				
				//On ajoute dans la liste toutes les questions de chaque th�me en fonction du nombre
				int nbQuestionsATirer = 0;
				List<Question> listeQuestions = new ArrayList<Question>();
				QuestionTirage questionTirage = null;
				int ordreQuestion = 1;
				
				for (Section s : listeSection) {
					nbQuestionsATirer = s.getNbQuestionsATirer();

					//On g�n�re une liste de questions a partir du th�me et du nombre de questions
					listeQuestions = QuestionDAO.tirageQuestions(nbQuestionsATirer, s.getTheme().getIdTheme());
					
					for (Question q : listeQuestions) {
						questionTirage = new QuestionTirage(false, false, inscription, q, ordreQuestion);
						QuestionTirageDAO.insert(questionTirage);
						ordreQuestion++;
					}
				}
				
				// Mise � jour de l'etat � "en cours"
				inscription.setEtat("EC");
				InscriptionDAO.update(inscription);
			}
						
			// On r�cup�re la Question depuis la BDD
			laQuestion = QuestionTirageDAO.selectByOrdre(idInscription, numOrdre).get(0);
			
			// On r�cup�re les propositions pour cette question
			listePropositions = PropositionDAO.selectByQuestion(laQuestion.getQuestion().getIdQuestion());
			
			// On r�cup�re la liste des questions tirage
			listeQuestionTirage = QuestionTirageDAO.selectByIdInscription(inscription.getIdInscription());
			
			// On r�cup�re la liste des r�ponses d�j� apport�es � la question � afficher
			listeReponseTirage = ReponseTirageDAO.selectById(laQuestion.getQuestion().getIdQuestion(), inscription.getIdInscription());
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		// On v�rifie si la r�ponse re�ue est la derni�re, si oui on route vers la page de synth�se
		if(Boolean.parseBoolean(request.getParameter("derniereQuestion")) & (request.getParameter("suivant") != null)){
			RequestDispatcher dispatcher;
			dispatcher = request.getServletContext().getRequestDispatcher("/candidat/synthese"); 
			dispatcher.forward(request, response);
			return;
		}
		
		// Test si derni�re question
		if (numOrdre == nbQuestions) {
			derniereQuestion = true;
		}
		
		// Ajout des attributs � envoyer au JSP
		request.setAttribute("laQuestion", laQuestion);
		request.setAttribute("dureeTest", dureeTest);
		request.setAttribute("tempsEcoule", tempsEcoule);
		request.setAttribute("lesPropositions", listePropositions);
		request.setAttribute("derniereQuestion", derniereQuestion);
		request.setAttribute("listeQuestionTirage", listeQuestionTirage);
		request.setAttribute("listeReponseTirage", listeReponseTirage);
		
		// (R�)affichage du questionnaire
		RequestDispatcher dispatcher;
		dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/candidat/questionnaire.jsp"); 
		dispatcher.forward(request, response);
	}
}