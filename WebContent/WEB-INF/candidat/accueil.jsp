<%@page import="fr.eni_ecole.qcm.dal.SectionDAO"%>
<%@ include file="../header.inc.jspf" %>
<%@ include file="header-questionnaire.inc.jspf" %>
<jsp:directive.page import ="java.text.SimpleDateFormat, java.text.DecimalFormat" />
	<div class="listing_tests">
		
		<%
			ArrayList<Inscription> lesInscriptions = (ArrayList<Inscription>) request.getAttribute("lesInscriptions");
			int heures = 0;
			int minutes = 0;
			String resultat = "";
			
			if(lesInscriptions != null){
		%>
		<div class="listing_tests_titre">Tests à effectuer :</div>
		<%
				
				
				int index = 0;
			 	for(Inscription i : lesInscriptions) {
			 		
			 		List<Section> listeSections = SectionDAO.selectById(i.getTest().getIdTest());
			 		int nbQuestions = 0;
			 		
					for (int j = 0; j < listeSections.size(); j++) {
						nbQuestions += listeSections.get(j).getNbQuestionsATirer();
					}
					
					Date sqlDate = i.getFinValidite();
					SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

					String date = formatter.format(sqlDate);
					
					heures = i.getTest().getDuree() / 3600;
					minutes = (i.getTest().getDuree() % 3600) / 60;		 		
		%>
		<div class=listing_tests_test>
			<form method="post" action="<%=request.getContextPath()%>/candidat/questionnaire">
				<input type="hidden" name="idInscription" value="<%=i.getIdInscription()%>">
				<input type="hidden" name="numOrdre" value="1">
				<input type="submit" value="Lancer le test" title="Accéder au test <%=i.getTest().getLibelle()%>">
			</form>
			<div class="icone-test"></div>
			<h2><%=i.getTest().getLibelle()%> : </h2>
			<h4><%=i.getTest().getDescription()%></h4>
			<div class="listing_tests_test_nbquestions">Nombre de questions : <%= nbQuestions %></div>
			<div class="listing_tests_test_duree">Durée du test : 
			<% 
			
			if(heures > 0){
				out.print(heures);
				if(heures > 1){
					out.print(" heures ");
				}else{
					out.print(" heure ");
				} 
			}
			if(minutes > 0)
			{
				if(minutes < 10){
					out.print("0");
				}
				out.print(minutes + " min");
			}
			
			
			%>
			</div>
			<div class="listing_tests_test_validite">Inscription possible jusqu'au : <%=date%></div>
			<div class="separator"></div>
		</div>

		<%
					index++;
			 	}
			}else{
		%>
		<div>Vous n'êtes inscrit à aucun test</div>
		<%
			}
		%>
	</div>
	<%
		ArrayList<Inscription> lesInscriptionsTerminees = (ArrayList<Inscription>) request.getAttribute("lesInscriptionsTerminees");
		
		if(lesInscriptionsTerminees != null && !lesInscriptionsTerminees.isEmpty()){
	%>
	<div class="listing_tests">
		<div class="listing_tests_titre">Tests terminés :</div>
		<%
			int index = 0;
			
		 	for(Inscription i : lesInscriptionsTerminees) {		
				heures = i.getTempsEcoule() / 3600;
				minutes = (i.getTempsEcoule() % 3600) / 60;
				DecimalFormat df = new DecimalFormat("0.00");
				resultat = df.format(i.getResultatObtenu());
		%>
		<div class="listing_tests_test">
			<div class="icone-test-termine"></div>
			<h2><%=i.getTest().getLibelle()%> : </h2>
			<h4><%=i.getTest().getDescription()%></h4>
			<div class="listing_tests_test_resultat">Résultat obtenu : <%=resultat%>%</div>
			<div class="listing_tests_test_temps_ecoule">Temps passé : 
			<% 
				if(heures > 0){
					out.print(heures);
					if(heures > 1){
						out.print(" heures ");
					}else{
						out.print(" heure ");
					} 
				}
				if(minutes > 0)
				{
					if(minutes < 10){
						out.print("0");
					}
					out.print(minutes + " min");
				}
				if (heures == 0 && minutes == 0)
				{
					out.print("01 min");
				}
			%>
			</div>
			<div class="separator"></div>
		</div>
		<%
					index++;
			 	}
		%>
	</div>
	<%
		}
	%>
<%@ include file="../footer.inc.jspf" %>