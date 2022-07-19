<%@ include file="../header.inc.jspf" %>
<%@ include file="header-questionnaire.inc.jspf" %>
<jsp:directive.page import ="fr.eni_ecole.qcm.beans.*, java.util.*, java.io.PrintWriter" />
<!-- Récupération des variables dans la requete -->
<% 
	QuestionTirage q = (QuestionTirage)request.getAttribute("laQuestion");
	List<Proposition> lesPropositions = (ArrayList<Proposition>)request.getAttribute("lesPropositions");
	List<QuestionTirage> listeQuestionTirage = (ArrayList<QuestionTirage>)request.getAttribute("listeQuestionTirage");
	List<ReponseTirage> listeReponseTirage = (ArrayList<ReponseTirage>)request.getAttribute("listeReponseTirage");
%>

	<!-- Questionnaire -->
	<div id="contenu">
		<!-- Barre de navigation entre questions -->
		<div id="navigation">
		<%
		for(QuestionTirage qt: listeQuestionTirage){
		%>
			<form class="navigation_bouton" action="<%=request.getContextPath()%>/candidat/questionnaire" method="post">
				<input type="hidden" name="idInscription" value="<%=qt.getInscription().getIdInscription()%>">
				<input type="hidden" name="numOrdre" value="<%=qt.getNumOrdre()%>">
				<input class="navigation_bouton_defaut <% if(qt.isEstRepondue()){out.print("navigation_bouton_repondue");} if(qt.isEstMarquee()){out.print(" navigation_bouton_marquee");} if(qt.getQuestion().getIdQuestion() == q.getQuestion().getIdQuestion()){out.print(" navigation_bouton_courant");}%>" type="submit" value="<%=qt.getNumOrdre()%>" title="Aller à la question <%=qt.getNumOrdre()%>">
			</form>
		<%
		}
		%>
		</div>
		<p><%=q.getNumOrdre()%> | <%=q.getQuestion().getEnonce()%> <% if(q.getQuestion().getType() == "multiple")out.print("(plusieurs choix possibles)");%></p>
		
		<!-- Formulaire avec réponses du candidat -->
		<form id="formulaire_question" action="<%=request.getContextPath()%>/candidat/questionnaire" method="post">
		<% 
		if (q.getQuestion().getType().equals("simple")){
			for(Proposition p : lesPropositions) { 
		%>
				<div class="question">
					<div>
						<input id="radio_<%=p.getIdProposition()%>" type="radio" name="reponse" value="<%=p.getIdProposition()%>" <% if(listeReponseTirage != null){for(ReponseTirage r:listeReponseTirage){if(p.getIdProposition() == r.getProposition().getIdProposition())out.print("checked");}} %>><label for="radio_<%=p.getIdProposition()%>"><span><span></span></span><%=p.getEnonce()%></label>
					</div>
				</div>
		<% 
			} 
		} else {
			for(Proposition p : lesPropositions) { 
		%>
			
				<div class="question">
					<div>
 						<input id="checkbox_<%=p.getIdProposition()%>" type="checkbox" name="reponse" value="<%=p.getIdProposition()%>" <% if(listeReponseTirage != null){for(ReponseTirage r:listeReponseTirage){if(p.getIdProposition() == r.getProposition().getIdProposition())out.print("checked");}} %>><label for="checkbox_<%=p.getIdProposition()%>"><span></span><%=p.getEnonce()%></label>
					</div>
				</div>
		<%
			}
		}
		%>
			<input type="hidden" name="idInscription" value="<%=q.getInscription().getIdInscription()%>">
			<input type="hidden" name="idQuestionRepondue" value="<%=q.getQuestion().getIdQuestion()%>">
			<input type="hidden" name="numOrdre" value="<%=q.getNumOrdre() + 1 %>">
			<%
			if((Boolean)request.getAttribute("derniereQuestion")){
			%>
			<input type="hidden" name="derniereQuestion" value="true">
			<%
			}
			%>
			<input type="submit" value="Suivante" title="Passer à la question suivante" class="bouton">
		</form>
		
		<!-- bouton question précédente -->
		<% 
		if(q.getNumOrdre() > 1){
		%>
		<form id="bouton_precedent" action="<%=request.getContextPath()%>/candidat/questionnaire" method="post">
			<input type="hidden" name="idInscription" value="<%=q.getInscription().getIdInscription()%>">
			<input type="hidden" name="idQuestionRepondue" value="<%=q.getQuestion().getIdQuestion()%>">
			<input type="hidden" name="numOrdre" value="<%=q.getNumOrdre() - 1 %>">
			<input type="submit" value="Précédente" title="Revenir à la question précédente" class="bouton">
		</form>
		<%
		}
		
		%>

		<!-- bouton de synthèse -->
		<form id="bouton_synthese" action="<%=request.getContextPath()%>/candidat/synthese" method="post">
			<input type="hidden" name="idInscription" value="<%=q.getInscription().getIdInscription()%>">
			<input type="submit" value="Synthèse" title="Accéder à la synthèse du test" class="bouton">
		</form>
		
		<!-- Bouton de marquage -->
		<form id="bouton_marquage" action="<%=request.getContextPath()%>/candidat/questionnaire" method="post">
			<input type="hidden" name="idInscription" value="<%=q.getInscription().getIdInscription()%>">
			<input type="hidden" name="idQuestionRepondue" value="<%=q.getQuestion().getIdQuestion()%>">
			<input type="hidden" name="numOrdre" value="<%=q.getNumOrdre()%>">
			<% 
			for(QuestionTirage qt: listeQuestionTirage){
				if(qt.getQuestion().getIdQuestion() == q.getQuestion().getIdQuestion()){
					if(qt.isEstMarquee()){
			%>
			<input type="hidden" name="estMarquee" value="false">
			<input type="submit" value="Démarquer" title="Démarquer la question" class="bouton">
			<%
					}else{
			%>
			<input type="hidden" name="estMarquee" value="true">
			<input type="submit" value="Marquer" title="Marquer la question" class="bouton">
			<%
					}
				}
			}
			%>
		</form>
	</div>
<%@ include file="../footer.inc.jspf" %>