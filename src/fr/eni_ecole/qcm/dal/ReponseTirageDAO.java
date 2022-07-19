package fr.eni_ecole.qcm.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import fr.eni_ecole.qcm.beans.ReponseTirage;

public class ReponseTirageDAO {

	public static void insert(ReponseTirage reponseTirage) throws Exception {
		Connection cnx = null;
		PreparedStatement rqt = null;

		try{
			cnx = GesConnexions.getConnexion();
			rqt=cnx.prepareStatement("insert into REPONSE_TIRAGE(idProposition, idQuestion, idInscription) values (?,?,?)");
			rqt.setInt(1, reponseTirage.getProposition().getIdProposition());
			rqt.setInt(2, reponseTirage.getQuestion().getIdQuestion());
			rqt.setInt(3, reponseTirage.getInscription().getIdInscription());
			
			rqt.executeUpdate();
			cnx.commit();
		}finally{
			if (rqt != null) rqt.close();
			if (cnx != null) cnx.close();
		}
	}
	
	public static void deleteAllQuestion(int idI, int idQ) throws Exception {
		Connection cnx = null;
		PreparedStatement rqt = null;
		try{
			cnx = GesConnexions.getConnexion();
			rqt = cnx.prepareStatement("delete from REPONSE_TIRAGE where idInscription = ? and idQuestion = ?");
			rqt.setInt(1, idI);
			rqt.setInt(2, idQ);
			rqt.executeUpdate();
			cnx.commit();
		}finally{
			if (rqt != null) rqt.close();
			if (cnx != null) cnx.close();
		}
	}
	
	public static void deleteAllInscription(int idI) throws Exception {
		Connection cnx = null;
		PreparedStatement rqt = null;
		try{
			cnx = GesConnexions.getConnexion();
			rqt = cnx.prepareStatement("delete from REPONSE_TIRAGE where idInscription = ?");
			rqt.setInt(1, idI);
			rqt.executeUpdate();
			cnx.commit();
		}finally{
			if (rqt != null) rqt.close();
			if (cnx != null) cnx.close();
		}
	}
	
	public static void deleteOne(ReponseTirage reponseTirage) throws Exception {
		Connection cnx = null;
		PreparedStatement rqt = null;
		try{
			cnx = GesConnexions.getConnexion();
			rqt = cnx.prepareStatement("delete from REPONSE_TIRAGE where idQuestion = ? and idInscription = ? and idProposition = ?");
			rqt.setInt(1, reponseTirage.getQuestion().getIdQuestion());
			rqt.setInt(2, reponseTirage.getInscription().getIdInscription());
			rqt.setInt(3, reponseTirage.getProposition().getIdProposition());
			rqt.executeUpdate();
			cnx.commit();
		}finally{
			if (rqt != null) rqt.close();
			if (cnx != null) cnx.close();
		}
	}
	
	public static List<ReponseTirage> selectById(int idQuestion, int idInscription) throws Exception {
		Connection cnx = null;
		PreparedStatement rqt = null;
		ResultSet rs = null;
		List<ReponseTirage> listeReponsesTirage = null;
		try{
			cnx = GesConnexions.getConnexion();
			rqt = cnx.prepareStatement("select * from REPONSE_TIRAGE where idQuestion = ? and idInscription = ?");
			rqt.setInt(1, idQuestion);
			rqt.setInt(2, idInscription);
			rs = rqt.executeQuery();
			
			ReponseTirage reponseTirage;
			
			while (rs.next()){
				reponseTirage = new ReponseTirage(
									PropositionDAO.selectById(rs.getInt("idProposition")),
									QuestionDAO.selectById(rs.getInt("idQuestion")),
									InscriptionDAO.selectById(rs.getInt("idInscription"))
						);
				if(listeReponsesTirage == null)listeReponsesTirage = new ArrayList<ReponseTirage>();
				listeReponsesTirage.add(reponseTirage);				
			}
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}finally{
			if (rs != null) rs.close();
			if (rqt != null) rqt.close();
			if (cnx != null) cnx.close();
		}

		return listeReponsesTirage;
	}
	
	public static int getNbReponses(int idQuestion, int idInscription) throws Exception {
		Connection cnx = null;
		PreparedStatement rqt = null;
		ResultSet rs = null;
		int nbQuestions = 0;
		
		try{
			cnx = GesConnexions.getConnexion();
			rqt = cnx.prepareStatement("select count(*) from REPONSE_TIRAGE where idQuestion = ? and idInscription = ?");
			rqt.setInt(1, idQuestion);
			rqt.setInt(2, idInscription);
			rs = rqt.executeQuery();
			
			while (rs.next()){
				nbQuestions = rs.getInt(1);	
			}
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}finally{
			if (rs != null) rs.close();
			if (rqt != null) rqt.close();
			if (cnx != null) cnx.close();
		}

		return nbQuestions;
	}
	
		
}