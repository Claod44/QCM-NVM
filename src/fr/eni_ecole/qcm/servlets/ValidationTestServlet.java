package fr.eni_ecole.qcm.servlets;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import fr.eni_ecole.qcm.beans.Inscription;
import fr.eni_ecole.qcm.beans.QuestionTirage;
import fr.eni_ecole.qcm.beans.ReponseTirage;
import fr.eni_ecole.qcm.beans.Section;
import fr.eni_ecole.qcm.beans.Test;
import fr.eni_ecole.qcm.dal.InscriptionDAO;
import fr.eni_ecole.qcm.dal.PropositionDAO;
import fr.eni_ecole.qcm.dal.QuestionTirageDAO;
import fr.eni_ecole.qcm.dal.ReponseTirageDAO;
import fr.eni_ecole.qcm.dal.SectionDAO;
/**
 * Servlet implementation class ValidationTestServlet
 */
public class ValidationTestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Inscription inscription = null;
		List<Section> listeSection = null;
		List<QuestionTirage> questionsSection = null;
		List<ReponseTirage> reponsesDonnees = null;
		HashMap<String,Double> resultatsSection = null;
		Test leTest = null;
		String mention = null;
		int idInscription = 0;
		int tempsEcoule;
		double resultatSection = 0;
		double resultatGlobal = 0;
		float totalPoidsSection = 0;
		float totalPoidsCorrectSection = 0;
		float totalPoids = 0;
		float totalPoidsCorrect = 0;
		int nbReponsesAttendues = 0;
		int idQ = 0;
		boolean reponseOk = false;
		
		try {
			// R�cup�ration des param�tres de la requete
			idInscription = Integer.parseInt(request.getParameter("idInscription"));
			tempsEcoule = Integer.parseInt(request.getParameter("tempsEcoule"));
			
			// R�cup�rations des infos n�cessaires au calcul des r�sultats
			inscription = InscriptionDAO.selectById(idInscription);
			listeSection = SectionDAO.selectById(inscription.getTest().getIdTest());
			
			// Calcul du r�sultat par section
			for (Section section : listeSection) {
				questionsSection = QuestionTirageDAO.selectByTheme(section.getTheme().getIdTheme());
				totalPoidsSection = 0;
				totalPoidsCorrectSection = 0;
				resultatSection = 0;
				
				for (QuestionTirage questionTirage : questionsSection) {
					idQ = questionTirage.getQuestion().getIdQuestion();
					totalPoidsSection += questionTirage.getQuestion().getPoids();
					totalPoids += questionTirage.getQuestion().getPoids();
					reponsesDonnees = ReponseTirageDAO.selectById(idQ, idInscription);
					nbReponsesAttendues = PropositionDAO.getNbReponsesCorrectes(idQ);
					
					// Si le candidat a r�pondu � la question
					if(reponsesDonnees != null){
						// Si question simple
						if (nbReponsesAttendues == 1) 
						{
							// Si la r�ponse donn�e est la bonne
							if (reponsesDonnees.get(0).getProposition().isEstBonne()){
								totalPoidsCorrectSection += questionTirage.getQuestion().getPoids();
								totalPoidsCorrect += questionTirage.getQuestion().getPoids();
							}
						} 
						else // Question � choix multiple
						{ 
							reponseOk = true;
							
							// Si le candidat a donn� le bon nombre de r�ponses
							if (ReponseTirageDAO.getNbReponses(idQ, idInscription) == nbReponsesAttendues){
								for (ReponseTirage reponseTirage : reponsesDonnees) {
									if (reponseTirage.getProposition().isEstBonne() == false) reponseOk = false;
								}
								
								// Si toutes les r�ponses sont correctes
								if (reponseOk){
									totalPoidsCorrectSection += questionTirage.getQuestion().getPoids();
									totalPoidsCorrect += questionTirage.getQuestion().getPoids();
								}
							}
						}
					}
				}
				resultatSection = (totalPoidsCorrectSection/totalPoidsSection)*100;
				
				if (resultatsSection == null) resultatsSection = new HashMap<String,Double>();
				resultatsSection.put(section.getTheme().getLibelle(), resultatSection);
			}
			
			resultatGlobal = (totalPoidsCorrect/totalPoids)*100;
			
			// D�terminer la mention obtenue
			leTest = inscription.getTest();
			
			if (resultatGlobal < leTest.getSeuilBas()){
				mention = "Non acquis";
			} else if (resultatGlobal >= leTest.getSeuilBas() && resultatGlobal < leTest.getSeuilHaut()){
				mention = "En cours d'acquisition";
			} else {
				mention = "Acquis";
			}
			
			// Mise � jour de l'inscription
			inscription.setEtat("TE");
			inscription.setResultatObtenu(resultatGlobal);
			inscription.setTempsEcoule(tempsEcoule);
			InscriptionDAO.update(inscription);
			
			// Vidage des tables temporaires
//			ReponseTirageDAO.deleteAllInscription(idInscription);
//			QuestionTirageDAO.deleteAll(idInscription);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		// Ajout des attributs � envoyer au JSP
		request.setAttribute("resultatsSection", resultatsSection);
		request.setAttribute("resultatGlobal", resultatGlobal);
		request.setAttribute("mention", mention);
		request.setAttribute("leTest", leTest);
		
		// affichage de l'�cran des r�sultats
		RequestDispatcher dispatcher;
		dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/candidat/resultatsTest.jsp"); 
		dispatcher.forward(request, response);
	}
}
