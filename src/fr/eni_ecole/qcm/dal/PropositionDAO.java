package fr.eni_ecole.qcm.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import fr.eni_ecole.qcm.beans.Proposition;

public class PropositionDAO {

	public static void update(Proposition proposition) throws Exception {
		Connection cnx = null;
		PreparedStatement rqt = null;
		try{
			cnx = GesConnexions.getConnexion();
			rqt = cnx.prepareStatement("update PROPOSITION set enonce = ?, estBonne = ? where idProposition = ? and idQuestion = ?");
			rqt.setString(1, proposition.getEnonce());
			rqt.setBoolean(2, proposition.isEstBonne());
			rqt.setInt(3, proposition.getIdProposition());
			rqt.setInt(4, proposition.getQuestion().getIdQuestion());

			rqt.executeUpdate();
			cnx.commit();
		}finally{
			if (rqt != null) rqt.close();
			if (cnx != null) cnx.close();
		}
	}

	public static void insert(Proposition proposition) throws Exception {
		Connection cnx = null;
		PreparedStatement rqt = null;

		try{
			cnx = GesConnexions.getConnexion();
			rqt=cnx.prepareStatement("insert into PROPOSITION(idProposition, enonce, estBonne, idQuestion) values (?,?,?,?)");
			rqt.setInt(1, proposition.getIdProposition());
			rqt.setString(2, proposition.getEnonce());
			rqt.setBoolean(3, proposition.isEstBonne());
			rqt.setInt(4, proposition.getQuestion().getIdQuestion());
			
			rqt.executeUpdate();
			cnx.commit();
		}finally{
			if (rqt != null) rqt.close();
			if (cnx != null) cnx.close();
		}
	}

	public static void delete(Proposition proposition) throws Exception {
		Connection cnx = null;
		PreparedStatement rqt = null;
		try{
			cnx = GesConnexions.getConnexion();
			rqt = cnx.prepareStatement("delete from PROPOSITION where idProposition = ? and idQuestion = ?");
			rqt.setInt(1, proposition.getIdProposition());
			rqt.setInt(2, proposition.getQuestion().getIdQuestion());
			rqt.executeUpdate();
			cnx.commit();
		}finally{
			if (rqt != null) rqt.close();
			if (cnx != null) cnx.close();
		}
	}

	public static Proposition selectById(int idP) throws Exception {
		Connection cnx = null;
		PreparedStatement rqt = null;
		ResultSet rs = null;
		Proposition proposition = null;
		try{
			cnx = GesConnexions.getConnexion();
			rqt = cnx.prepareStatement("select * from PROPOSITION where idProposition = ?");
			rqt.setInt(1, idP);
			rs = rqt.executeQuery();
			while (rs.next()){
				proposition = new Proposition(
						rs.getInt("idProposition"),
						rs.getString("enonce"),
						rs.getBoolean("estBonne"),
						QuestionDAO.selectById(rs.getInt("idQuestion"))
						);
			}
		}finally{
			if (rs!=null) rs.close();
			if (rqt!=null) rqt.close();
			if (cnx!=null) cnx.close();
		}
		
		return proposition;
	}
	//TODO JOINTURE
	public static ArrayList<Proposition> selectByQuestion(int idQ) throws Exception {
		Connection cnx = null;
		PreparedStatement rqt = null;
		ResultSet rs = null;
		Proposition proposition = null;
		ArrayList<Proposition> listePropositions = null;
		
		try{
			cnx = GesConnexions.getConnexion();
			rqt = cnx.prepareStatement("select * from PROPOSITION where idQuestion = ?");
			rqt.setInt(1, idQ);
			rs = rqt.executeQuery();
			while (rs.next()){
				proposition = new Proposition(
						rs.getInt("idProposition"),
						rs.getString("enonce"),
						rs.getBoolean("estBonne"),
						QuestionDAO.selectById(idQ)
						);
				
				if(listePropositions == null) listePropositions = new ArrayList<Proposition>();
				listePropositions.add(proposition);
			}
		}finally{
			if (rs!=null) rs.close();
			if (rqt!=null) rqt.close();
			if (cnx!=null) cnx.close();
		}
		
		return listePropositions;
	}
	//TODO JOINTURE
	public static ArrayList<Proposition> selectAll() throws Exception {
		Connection cnx = null;
		Statement rqt = null;
		ResultSet rs = null;
		ArrayList<Proposition> listePropositions = null;
		try{
			cnx = GesConnexions.getConnexion();
			rqt = cnx.createStatement();			
			rs = rqt.executeQuery("select * from PROPOSITION");
			Proposition proposition;
			while (rs.next()){
				proposition = new Proposition(
									rs.getInt("idProposition"),
									rs.getString("enonce"),
									rs.getBoolean("estBonne"),
									QuestionDAO.selectById(rs.getInt("idQuestion"))
									);
				if(listePropositions == null) listePropositions = new ArrayList<Proposition>();
				listePropositions.add(proposition);				
			}
		}finally{
			if (rs != null) rs.close();
			if (rqt != null) rqt.close();
			if (cnx != null) cnx.close();
		}
		
		return listePropositions;
	}
	
	public static int getNbReponsesCorrectes(int idQ) throws Exception {
		Connection cnx = null;
		PreparedStatement rqt = null;
		ResultSet rs = null;
		int nbQuestion = 0;
		
		try{
			cnx = GesConnexions.getConnexion();
			rqt = cnx.prepareStatement("select count(*) from PROPOSITION where idQuestion = ? and estBonne = 1");
			rqt.setInt(1, idQ);
			rs = rqt.executeQuery();
			
			while (rs.next()){
				nbQuestion = rs.getInt(1);
			}
		}finally{
			if (rs!=null) rs.close();
			if (rqt!=null) rqt.close();
			if (cnx!=null) cnx.close();
		}
		
		return nbQuestion;
	}
	
}