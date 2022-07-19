package fr.eni_ecole.qcm.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import fr.eni_ecole.qcm.beans.QuestionTirage;

public class QuestionTirageDAO {

	public static void update(QuestionTirage questionTirage) throws Exception {
		Connection cnx = null;
		PreparedStatement rqt = null;
		try{
			cnx = GesConnexions.getConnexion();
			rqt = cnx.prepareStatement("update QUESTION_TIRAGE set estMarquee = ?, estRepondue = ?, numordre = ? where idInscription = ? and idQuestion = ?");
			rqt.setBoolean(1, questionTirage.isEstMarquee());
			rqt.setBoolean(2, questionTirage.isEstRepondue());
			rqt.setInt(3, questionTirage.getNumOrdre());
			rqt.setInt(4, questionTirage.getInscription().getIdInscription());
			rqt.setInt(5, questionTirage.getQuestion().getIdQuestion());

			rqt.executeUpdate();
			cnx.commit();
		}finally{
			if (rqt != null) rqt.close();
			if (cnx != null) cnx.close();
		}	
	}

	public static void insert(QuestionTirage questionTirage) throws Exception {
		Connection cnx = null;
		PreparedStatement rqt = null;

		try{
			cnx = GesConnexions.getConnexion();
			rqt=cnx.prepareStatement("insert into QUESTION_TIRAGE(estMarquee, estRepondue, idInscription, idQuestion, numordre) values (?,?,?,?,?)");
			rqt.setBoolean(1, questionTirage.isEstMarquee());
			rqt.setBoolean(2, questionTirage.isEstRepondue());
			rqt.setInt(3, questionTirage.getInscription().getIdInscription());
			rqt.setInt(4, questionTirage.getQuestion().getIdQuestion());
			rqt.setInt(5, questionTirage.getNumOrdre());
			
			rqt.executeUpdate();
			cnx.commit();
		}finally{
			if (rqt != null) rqt.close();
			if (cnx != null) cnx.close();
		}
	}

	public static void deleteAll(int idI) throws Exception {
		Connection cnx = null;
		PreparedStatement rqt = null;
		try{
			cnx = GesConnexions.getConnexion();
			rqt = cnx.prepareStatement("delete from QUESTION_TIRAGE where idInscription = ?");
			rqt.setInt(1, idI);
			rqt.executeUpdate();
			cnx.commit();
		}finally{
			if (rqt != null) rqt.close();
			if (cnx != null) cnx.close();
		}
	}
	//TODO JOINTURE
	public static QuestionTirage selectById(int idI, int idQ) throws Exception {
		Connection cnx = null;
		PreparedStatement rqt = null;
		ResultSet rs = null;
		QuestionTirage questionTirage = null;
		try{
			cnx = GesConnexions.getConnexion();
			rqt = cnx.prepareStatement("select * from QUESTION_TIRAGE where idInscription = ? and idQuestion = ?");
			rqt.setInt(1, idI);
			rqt.setInt(2, idQ);
			rs = rqt.executeQuery();
			while (rs.next()){
				questionTirage = new QuestionTirage(
						rs.getBoolean("estMarquee"), 
						rs.getBoolean("estRepondue"),
						InscriptionDAO.selectById(idI), 
						QuestionDAO.selectById(idQ), 
						rs.getInt("numOrdre"));
			}
		}finally{
			if (rs!=null) rs.close();
			if (rqt!=null) rqt.close();
			if (cnx!=null) cnx.close();
		}
		
		return questionTirage;
	}
	//TODO JOINTURE
	public static ArrayList<QuestionTirage> selectByIdInscription(int idInscription) throws Exception {
		Connection cnx = null;
		PreparedStatement rqt = null;
		ResultSet rs = null;
		QuestionTirage currentQuestionTirage = null;
		ArrayList<QuestionTirage> listeQuestionsTirees = null;
		try{
			cnx = GesConnexions.getConnexion();
			rqt = cnx.prepareStatement("select * from QUESTION_TIRAGE where idInscription = ? order by numOrdre");
			rqt.setInt(1, idInscription);
			rs = rqt.executeQuery();
			while (rs.next()){
				currentQuestionTirage = new QuestionTirage(rs.getBoolean("estMarquee"), 
														   rs.getBoolean("estRepondue"),
														   InscriptionDAO.selectById(idInscription), 
														   QuestionDAO.selectById(rs.getInt("idQuestion")), 
														   rs.getInt("numOrdre"));
				if (listeQuestionsTirees == null) listeQuestionsTirees = new ArrayList<QuestionTirage>();
				listeQuestionsTirees.add(currentQuestionTirage);
			}
			
		}finally{
			if (rs!=null) rs.close();
			if (rqt!=null) rqt.close();
			if (cnx!=null) cnx.close();
		}
		
		return listeQuestionsTirees;
	}
	//TODO JOINTURE
	public static ArrayList<QuestionTirage> selectByOrdre(int idInscription, int numOrdre) throws Exception {
		Connection cnx = null;
		PreparedStatement rqt = null;
		ResultSet rs = null;
		QuestionTirage questionTirage = null;
		ArrayList<QuestionTirage> listeQuestionsTirage = null;
		try{
			cnx = GesConnexions.getConnexion();
			rqt = cnx.prepareStatement("select * from QUESTION_TIRAGE where idInscription = ? and numordre = ?");
			rqt.setInt(1, idInscription);
			rqt.setInt(2, numOrdre);
			rs = rqt.executeQuery();
			while (rs.next()){
				questionTirage = new QuestionTirage(rs.getBoolean("estMarquee"), 
													rs.getBoolean("estRepondue"),
													InscriptionDAO.selectById(idInscription), 
													QuestionDAO.selectById(rs.getInt("idQuestion")), 
													numOrdre);
				if (listeQuestionsTirage == null) listeQuestionsTirage = new ArrayList<QuestionTirage>();
				listeQuestionsTirage.add(questionTirage);
			}
			
		}finally{
			if (rs!=null) rs.close();
			if (rqt!=null) rqt.close();
			if (cnx!=null) cnx.close();
		}
		
		return listeQuestionsTirage;
	}
	
	public static int selectNbQuestions(int idI) throws Exception {
		Connection cnx = null;
		PreparedStatement rqt = null;
		ResultSet rs = null;
		int nbQuestions = 0;
		try{
			cnx = GesConnexions.getConnexion();
			rqt = cnx.prepareStatement("select count(*) from QUESTION_TIRAGE where idInscription = ?");
			rqt.setInt(1, idI);
			rs = rqt.executeQuery();
			
			while (rs.next()){
				nbQuestions = rs.getInt(1);
			}
			
		}finally{
			if (rs!=null) rs.close();
			if (rqt!=null) rqt.close();
			if (cnx!=null) cnx.close();
		}
		
		return nbQuestions;
	}
	//TODO JOINTURE
	public static ArrayList<QuestionTirage> selectAll() throws Exception {
		Connection cnx = null;
		Statement rqt = null;
		ResultSet rs = null;
		ArrayList<QuestionTirage> listeQuestionsTirage = null;
		try{
			cnx = GesConnexions.getConnexion();
			rqt = cnx.createStatement();			
			rs = rqt.executeQuery("select * from QUESTION_TIRAGE");
			QuestionTirage questionTirage;
			while (rs.next()){
				questionTirage = new QuestionTirage(
									rs.getBoolean("estMarquee"),
									rs.getBoolean("estRepondue"),
									InscriptionDAO.selectById(rs.getInt("idInscription")),
									QuestionDAO.selectById(rs.getInt("idQuestion")),
									rs.getInt("numOrdre")
						);
				if(listeQuestionsTirage == null)listeQuestionsTirage = new ArrayList<QuestionTirage>();
				listeQuestionsTirage.add(questionTirage);				
			}
		}finally{
			if (rs != null) rs.close();
			if (rqt != null) rqt.close();
			if (cnx != null) cnx.close();
		}
		
		return listeQuestionsTirage;
	}
	
	public static ArrayList<QuestionTirage> selectByTheme(int idTheme) throws Exception {
		Connection cnx = null;
		PreparedStatement rqt = null;
		ResultSet rs = null;
		ArrayList<QuestionTirage> listeQuestionsTirage = null;
		try{
			cnx = GesConnexions.getConnexion();
			rqt = cnx.prepareStatement("SELECT estMarquee, estRepondue, idInscription, QT.idQuestion, numOrdre "
									 + "FROM QUESTION_TIRAGE QT "
									 + "INNER JOIN QUESTION Q ON QT.idQuestion = Q.idQuestion "
									 + "INNER JOIN THEME T ON Q.idTheme = T.idTheme "
									 + "WHERE T.idTheme = ?");		
			rqt.setInt(1, idTheme);
			rs = rqt.executeQuery();
			
			QuestionTirage questionTirage;
			
			while (rs.next()){
				questionTirage = new QuestionTirage(
									rs.getBoolean("estMarquee"),
									rs.getBoolean("estRepondue"),
									InscriptionDAO.selectById(rs.getInt("idInscription")),
									QuestionDAO.selectById(rs.getInt("idQuestion")),
									rs.getInt("numOrdre")
						);
				if(listeQuestionsTirage == null) listeQuestionsTirage = new ArrayList<QuestionTirage>();
				listeQuestionsTirage.add(questionTirage);				
			}
		}finally{
			if (rs != null) rs.close();
			if (rqt != null) rqt.close();
			if (cnx != null) cnx.close();
		}
		
		return listeQuestionsTirage;
	}
	
}