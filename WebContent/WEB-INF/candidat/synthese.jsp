<%@ include file="../header.inc.jspf"%>
<%@ include file="../candidat/header-questionnaire.inc.jspf"%>


<% 
ArrayList<QuestionTirage> listeQuestionsTirees = (ArrayList<QuestionTirage>) request.getAttribute("listeQuestionsTirees");
int dureeTest = (Integer)request.getAttribute("dureeTest");
int tempsEcoule = (Integer)request.getAttribute("tempsEcoule");

%>
<div id="contenu">
	<div id="conteneur_navigation">
		<div id="synthese_titre">Synthèse : </div>
    	<div id="compteur"></div>
	</div>
	<div id="synthese">
	<%
		for(QuestionTirage currentQT : listeQuestionsTirees)
		{
		%>
		<form class="synthese_question" action="<%=request.getContextPath()%>/candidat/questionnaire" method="post">
			<input type="hidden" name="idInscription" value="<%=currentQT.getInscription().getIdInscription()%>">
			<input type="hidden" name="idQuestionRepondue" value="<%=currentQT.getQuestion().getIdQuestion()%>">
			<input type="hidden" name="numOrdre" value="<%=currentQT.getNumOrdre() %>">
			<input type="hidden" name="tempsEcoule" value="" class="tempsEcoule">
			<input <% String currentClass = "nonRepondue"; if(currentQT.isEstRepondue() == true) currentClass = "repondue"; if(currentQT.isEstMarquee() == true) currentClass += " marquee"; out.write(" class=" + "\"" + currentClass+"\""); %> type="submit" value="Question <%= currentQT.getNumOrdre() %> : <%= currentQT.getQuestion().getEnonce() %>" title="Question <%= currentQT.getNumOrdre() %> : <%= currentQT.getQuestion().getEnonce() %>">
		</form>
	<%	}%>
		<form class="synthese_fin_test" action="<%=request.getContextPath()%>/candidat/validation" method="post">
			<input type="hidden" name="idInscription" value="<%=listeQuestionsTirees.get(0).getInscription().getIdInscription()%>">
			<input type="hidden" name="tempsEcoule" value="" id="tempsEcouleFinTest">
			<input type="submit" value="Mettre fin au test" class="bouton" title="Mettre fin au test">
		</form>
	</div>
</div>
	<script>
		function compteur()
	          {
	        var compteur = document.getElementById('compteur');
	        s = duree;
	        m = 0;
	        h = 0;
	        if(s < 0){
	        	compteur.innerHTML = "terminé<br />"
	        } else {
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
	            compteur.innerHTML = h + ":" + m + ":" + s
	            var temps = <%= dureeTest %> - ((h * 60 * 60) + (m * 60) + s)
	            
	            var listeInputs = document.getElementsByClassName('tempsEcoule')

	            for (var int = 0; int < listeInputs.length; int++) {
	            	listeInputs[int].value = temps
				}
	            
	            document.getElementById('tempsEcouleFinTest').value = temps
	        }
	        
	        duree = duree - 1;
	        window.setTimeout("compteur();",999);
	    }
	
	    duree = <%= (dureeTest - tempsEcoule) %>;
	    compteur();
	</script>
<%@ include file="../footer.inc.jspf"%>