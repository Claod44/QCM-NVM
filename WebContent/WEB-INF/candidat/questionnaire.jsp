<%@ include file="../header.inc.jspf" %>
<%@ include file="header-questionnaire.inc.jspf" %>
<jsp:directive.page import ="fr.eni_ecole.qcm.beans.*, java.util.*, java.io.PrintWriter" />
<!-- Récupération des variables dans la requete -->
<% 
	QuestionTirage q = (QuestionTirage)request.getAttribute("laQuestion");
	List<Proposition> lesPropositions = (ArrayList<Proposition>)request.getAttribute("lesPropositions");
	List<QuestionTirage> listeQuestionTirage = (ArrayList<QuestionTirage>)request.getAttribute("listeQuestionTirage");
	List<ReponseTirage> listeReponseTirage = (ArrayList<ReponseTirage>)request.getAttribute("listeReponseTirage");
	int dureeTest = (Integer)request.getAttribute("dureeTest");
	int tempsEcoule = (Integer)request.getAttribute("tempsEcoule");
%>

	<!-- Questionnaire -->
	<div id="contenu">
		<!-- Barre de navigation entre questions -->
		<form name="questionnaireForm" id="formulaire_question" action="<%=request.getContextPath()%>/candidat/questionnaire" method="post">
			<div id="conteneur_navigation">
				<div id="navigation" class="navigation_bouton">
				<%
				for(QuestionTirage qt: listeQuestionTirage){
				%>
					<input name="navigation" class="navigation_bouton_defaut <% if(qt.isEstRepondue()){out.print("navigation_bouton_repondue");} if(qt.isEstMarquee()){out.print(" navigation_bouton_marquee");} if(qt.getQuestion().getIdQuestion() == q.getQuestion().getIdQuestion()){out.print(" navigation_bouton_courant");}%>" type="submit" value="<%=qt.getNumOrdre()%>" title="Aller à la question <%=qt.getNumOrdre()%>">
				<%
				}
				%>
				</div>
	            <div id="compteur"></div>
			</div>
			<p><%=q.getNumOrdre()%> | <%=q.getQuestion().getEnonce()%> <% if(q.getQuestion().getType() == "multiple")out.print("(plusieurs choix possibles)");%></p>
		
			<!-- Formulaire avec réponses du candidat -->
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
			<input type="hidden" name="numOrdre" value="<%=q.getNumOrdre()%>">
			<input type="hidden" name="dureeTest" value="<%= dureeTest %>">
			<input type="hidden" name="tempsEcoule" value="" id="tempsEcoule">
			<%
			if((Boolean)request.getAttribute("derniereQuestion")){
			%>
			<input type="hidden" name="derniereQuestion" value="true">
			<%
			}
			%>
			
			<!-- bouton de synthèse -->
			<div id="bouton_synthese">
				<input type="submit" name="synthese" value="Synthèse" title="Accéder à la synthèse du test" class="bouton" id="submit_synthese">
			</div>
			
			<!-- Bouton de marquage -->
			<div id="bouton_marquage">
			<% 
			for(QuestionTirage qt: listeQuestionTirage){
				if(qt.getQuestion().getIdQuestion() == q.getQuestion().getIdQuestion()){
					if(qt.isEstMarquee()){
			%>
			<input type="hidden" name="estMarquee" value="false">
			<input type="submit" name="marquage" value="Démarquer" title="Démarquer la question" class="bouton">
			<%
					}else{
			%>
			<input type="hidden" name="estMarquee" value="true">
			<input type="submit" name="marquage" value="Marquer" title="Marquer la question" class="bouton" id="submit_marquage">
			<%
					}
				}
			}
			%>
			</div>

			<!-- bouton question précédente -->
			<% 
			if(q.getNumOrdre() > 1){
			%>
			<input id="bouton_precedent" type="submit" name="precedent" value="Précédente" title="Revenir à la question précédente" class="bouton">
			<%
			}	
			%>
			<input id="bouton_suivant" type="submit" name="suivant" value="<% if((Boolean)request.getAttribute("derniereQuestion")){out.print("Terminer");}else{out.print("Suivante");}%>" title="<% if((Boolean)request.getAttribute("derniereQuestion")){out.print("Valider la dernière question et aller à la synthèse");}else{out.print("Passer à la question suivante");}%>" class="bouton">
		</form>
	</div>
	<script>
		var dureeTest = <%= dureeTest %>
		var tempsEcoule = <%= tempsEcoule %>
		var tempsRestant = dureeTest - tempsEcoule
		var conteneurCompteur = document.getElementById('compteur');
		var refreshIntervalId;
		
    	compteur();
		function compteur(){
	        var temps = 0;
	        s = tempsRestant;

	        if(s < 0){
	        	conteneurCompteur.innerHTML = "Temps écoulé"
	        	document.getElementById('bouton_suivant').value = "Terminer le test"
	        	$('#bouton_suivant').prop('title', 'Terminer le test');
	        	
	        	$( "#submit_marquage" ).remove();
	            $( "#submit_synthese" ).remove();
	            $( "#navigation" ).remove();
	            $( "#bouton_precedent" ).remove();
	            clearInterval(refreshIntervalId);
	            
	        } else {
	            affichage(s);
	            temps = dureeTest - s;
	            document.getElementById('tempsEcoule').value = temps;
	        }
	        
	        tempsRestant = tempsRestant - 1;
	        window.setTimeout("compteur();",999);
	    }
		
		function affichage(s){
	        m = 0;
	        h = 0;
	        
	        if(s == 10){
	        	conteneurCompteur.className += "finCompteur";
	    	}
	    	if(s > 59){
	        	m = Math.floor(s / 60);
	        	s = s - m * 60
	        }
	        if(m > 59){
	        	h = Math.floor(m / 60);
	            m = m - h * 60
	        }
	        if(s < 10){
	        	s = "0" + s
	        }
	        if(m < 10){
	            m = "0" + m
	        }
	        conteneurCompteur.innerHTML = h + ":" + m + ":" + s
			//conteneurCompteur.innerHTML = s
		}
	    
		// Auto submit
	    window.onload = refreshIntervalId = setInterval(
	   	    function(){submitform();}, 60000);
	   	    function submitform(){document.questionnaireForm.submit();
	   	    }
	    
	</script>
<%@ include file="../footer.inc.jspf" %>