<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% 
	if(request.getSession().getAttribute("candidat") != null){
		response.sendRedirect(request.getContextPath() + "/candidat/accueil");
	}
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<link media="all" rel="stylesheet" href="<%=request.getContextPath()%>/style/styles.css" type="text/css"/>
		<link rel="icon" href="<%=request.getContextPath()%>/images/favicon.ico" />
		<title>QCM</title>
	</head>
	<body>
		<div id="formulaire_connexion_admin">
			<a href="" title="Connexion Formateur"></a>
		</div>
		<div id="formulaire_connexion">
			<form method="post" action="<%=request.getContextPath()%>/ConnexionCandidatServlet">
				<p><label for="mail">Mail : </label><input type="email" name="mail" placeholder="Ex : bob@eni-ecole.fr" required/></p>
				<p><label for="pwd">Mot de passe : </label><input type="password" name="pwd" required/></p>
				<p id="formulaire_connexion_erreur">
					<%if (request.getAttribute("message") != null)out.println(request.getAttribute("message"));%>
				</p>

				<p><input type="submit" value="Envoyer" class="bouton"/></p>
			</form>
		</div>
	</body>
</html>