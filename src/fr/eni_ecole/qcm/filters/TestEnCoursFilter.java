package fr.eni_ecole.qcm.filters;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import fr.eni_ecole.qcm.beans.Candidat;
import fr.eni_ecole.qcm.beans.Inscription;
import fr.eni_ecole.qcm.dal.InscriptionDAO;

/**
 * Servlet Filter implementation class TestEnCoursFilter
 */
public class TestEnCoursFilter implements Filter {

    /**
     * Default constructor. 
     */
    public TestEnCoursFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		Inscription inscription = null;
		
		HttpSession session = ((HttpServletRequest)request).getSession(false);
		Candidat candidat = (Candidat)session.getAttribute("candidat");
		
		try {
			// Si le candidat a un test en cours
			if (InscriptionDAO.selectEnCoursByCandidat(candidat) != null){
				inscription = InscriptionDAO.selectEnCoursByCandidat(candidat).get(0);
				
				// Ajout des attributs � envoyer au JSP
				request.setAttribute("idInscription", inscription.getIdInscription());
				request.setAttribute("numOrdre", 1);
				
				// affichage du questionnaire
				RequestDispatcher dispatcher;
				dispatcher = request.getServletContext().getRequestDispatcher("/candidat/questionnaire"); 
				dispatcher.forward(request, response);
				
			} else {
				chain.doFilter(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
