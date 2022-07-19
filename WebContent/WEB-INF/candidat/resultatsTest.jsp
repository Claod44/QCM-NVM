<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@ include file="../header.inc.jspf"%>
<%@ include file="../candidat/header-questionnaire.inc.jspf"%>
<jsp:directive.page import ="fr.eni_ecole.qcm.beans.*, java.util.*" />

<div id="contenu">
<div id="resultats">

<% 
HashMap<String, Double> resultatsSection = (HashMap)request.getAttribute("resultatsSection");
Double resultatGlobal = (Double)request.getAttribute("resultatGlobal");
String mention = (String)request.getAttribute("mention");
Test leTest = (Test)request.getAttribute("leTest");
Double currentSectionResult = 0.0;%>

<h1><%=leTest.getLibelle().toString()+" : " %></h1>

<%
int index = 1;
for(Map.Entry<String, Double> entry : resultatsSection.entrySet()) {
	%>
	<div class="section">
		<div class="nomSection">
			<%= "Section "+index+" : "+entry.getKey() %>
		</div>
		<div class="resultatSection">
			<% 
				currentSectionResult = entry.getValue();
			%>
			<svg id="container_<%=index%>" class="sectionProgressbar"></svg>
	    	<script type="text/javascript">
		      var progress_<%=index%> = $("#container_<%=index%>").Progress({
		    	parentWidth: 583, //peut ne pas être trouvé
		    	childSizePercent: 1.0, //50%
		        height: 20,
				barColor:'#46CFB0',
				backgroundColor: '#C1C1C1',
		        fontSize: 10,
		        increaseSpeed: 6,
		        low: <%=leTest.getSeuilBas()%>,
		  	  	high: <%=leTest.getSeuilHaut()%>
		      });
		      setTimeout(function(){
		        progress_<%=index%>.percent(<%= currentSectionResult %>);
		      }, 800);
		    </script>
		</div>
	</div>
<%index++;}%>
	<div id="resultatGlobal">
	<h2>Résultat global :</h2>
		<div>
		<svg id="seuil_bas">
				<text x="0" y="0"><tspan x="10" y="55">Seuil bas</tspan></text>
				<rect x="40.40" y="57" width="2" height="46" fill="black" ></rect>
		</svg>
		</div>
		<div>
		<svg id="seuil_haut">
				<text x="0" y="0"><tspan x="10" y="55">Seuil haut</tspan></text>
				<rect x="44" y="57" width="2" height="46" fill="black" ></rect>
		</svg>
		</div>
	    <svg id="container_global" class="globalProgressbar"></svg>
		<script type="text/javascript">
		      var progress_global = $("#container_global").Progress({
		    	parentWidth: 583, //peut ne pas être trouvé
			    childSizePercent: 1.0, //100%
		        height: 40,
		        backgroundColor: '#C1C1C1',
		        fontSize: 16,
		        increaseSpeed: 4,
		        toggle: true,
		        low: <%=leTest.getSeuilBas()%>,
		  	  	high: <%=leTest.getSeuilHaut()%>
		      });
		
		      setTimeout(function(){
		        progress_global.percent(<%= resultatGlobal %>);
		      }, 1200);
	    </script>
		<div id="mention">
		<% String classMention; if(mention=="Acquis") classMention = "acquis" ;else if(mention=="En cours d'acquisition") classMention = "enCoursAcquis"; else classMention = "nonAcquis"; %>
			<h2  class="<%= classMention%>"><%=mention%><h2>
			<%
				StringBuffer chaine = new StringBuffer(request.getContextPath()+"/images/mentions/");
				if(mention == "Acquis") {
					chaine.append("Acquis");
				}
				else if(mention == "En cours d'acquisition") {
					chaine.append("En_Cours_d_Acquisition");
				}
				else {
					chaine.append("Non_Acquis");
				}
				chaine.append(".png");
				String mentionImgPath = chaine.toString();
			%>
			<img src="<%=mentionImgPath%>">
			<script type="text/javascript">
			$(document).ready(function() {
				//lancé au chargement
				var progressbarWidth = $("#container_global").css('width');
				progressbarWidth = progressbarWidth.substr(0,3);
				var progressbarPositions = $("#container_global").position();
				var progressbarLeftPos = progressbarPositions.left;
				var leftSeuilHaut =((progressbarWidth*<%=leTest.getSeuilHaut()%>)/100)-44;
				var leftSeuilBas =((progressbarWidth*<%=leTest.getSeuilBas()%>)/100)-40.40;
				$("#seuil_bas").css('left', leftSeuilBas);
				$("#seuil_haut").css('left', leftSeuilHaut);
			    $("#mention").hide();
			    $("#retourAccueil").hide();
			});
			setTimeout(function() {
			    $('#mention').show();
			    $("#retourAccueil").show();
			}, 2800);
			</script>
		</div>
    </div>
</div>
	<form id="retourAccueil" name="retourAccueil" action="<%= request.getContextPath() %>" method="POST">
		<input type="submit" class="bouton" value="Retourner à l'accueil" title="Retourner à l'accueil">
	</form>
</div>
<%@ include file="../footer.inc.jspf"%>