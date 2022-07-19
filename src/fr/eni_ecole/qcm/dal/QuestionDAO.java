package fr.eni_ecole.qcm.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.eni_ecole.qcm.beans.Question;

public class QuestionDAO {

	
	public static void update(Question question) throws Exception {
		Connection cnx = null;
		PreparedStatement rqt = null;
		try{
			cnx = GesConnexions.getConnexion();
			rqt = cnx.prepareStatement("update QUESTION set enonce = ?, media = ?, poids = ?, idTheme = ?, type = ? where idQuestion = ?");
			rqt.setString(1, question.getEnonce());
			rqt.setString(2, question.getMedia());
			rqt.setFloat(3, question.getPoids());
			rqt.setInt(4, question.getTheme().getIdTheme());
			rqt.setString(5, question.getType());
			rqt.setInt(6, question.getIdQuestion());

			rqt.executeUpdate();
			cnx.commit();
		}finally{
			if (rqt != null) rqt.close();
			if (cnx != null) cnx.close();
		}
	}

	
	public static void insert(Question question) throws Exception {
		Connection cnx = null;
		PreparedStatement rqt = null;

		try{
			cnx = GesConnexions.getConnexion();
			rqt=cnx.prepareStatement("insert into QUESTION(idQuestion, enonce, media, poids, idTheme, type) values (?,?,?,?,?,?)");
			rqt.setInt(1, question.getIdQuestion());
			rqt.setString(2, question.getEnonce());
			rqt.setString(3, question.getMedia());
			rqt.setFloat(4, question.getPoids());
			rqt.setInt(5, question.getTheme().getIdTheme());
			rqt.setString(6, question.getType());
			
			rqt.executeUpdate();
			cnx.commit();
		}finally{
			if (rqt != null) rqt.close();
			if (cnx != null) cnx.close();
		}
	}

	
	public static void delete(Question question) throws Exception {
		Connection cnx = null;
		PreparedStatement rqt = null;
		try{
			cnx = GesConnexions.getConnexion();
			rqt = cnx.prepareStatement("delete from QUESTION where idQuestion = ?");
			rqt.setInt(1, question.getIdQuestion());
			rqt.executeUpdate();
			cnx.commit();
		}finally{
			if (rqt != null) rqt.close();
			if (cnx != null) cnx.close();
		}
	}

	//TODO JOINTURE
	public static Question selectById(int id) throws Exception {
		Connection cnx = null;
		PreparedStatement rqt = null;
		ResultSet rs = null;
		Question question = null;
		try{
			cnx = GesConnexions.getConnexion();
			rqt = cnx.prepareStatement("select * from QUESTION where idQuestion = ?");
			rqt.setInt(1, id);
			rs = rqt.executeQuery();
			while (rs.next()){
				question = new Question(
						id,
						rs.getString("enonce"),
						rs.getString("media"),
						rs.getFloat("poids"),
						ThemeDAO.selectById(rs.getInt("idTheme")),
						rs.getString("type")
						);
			}
		}finally{
			if (rs!=null) rs.close();
			if (rqt!=null) rqt.close();
			if (cnx!=null) cnx.close();
		}
		
		return question;
	}

	//TODO JOINTURE
	public static ArrayList<Question> selectAll() throws Exception {
		Connection cnx = null;
		Statement rqt = null;
		ResultSet rs = null;
		ArrayList<Question> listeQuestions = null;
		try{
			cnx = GesConnexions.getConnexion();
			rqt = cnx.createStatement();			
			rs = rqt.executeQuery("select * from QUESTION");
			Question question;
			while (rs.next()){
				question = new Question(
									rs.getInt("idQuestion"),
									rs.getString("enonce"),
									rs.getString("media"),
									rs.getFloat("poids"),
									ThemeDAO.selectById(rs.getInt("idTheme")),
									rs.getString("type")
						);
				if(listeQuestions == null)listeQuestions = new ArrayList<Question>();
				listeQuestions.add(question);				
			}
		}finally{
			if (rs != null) rs.close();
			if (rqt != null) rqt.close();
			if (cnx != null) cnx.close();
		}
		
		return listeQuestions;
	}
	//TODO JOINTURE
	public static List<Question> tirageQuestions(int nbQuestionsATirer, int idTheme) throws Exception{
		Connection cnx = null;
		PreparedStatement rqt = null;
		ResultSet rs = null;
		List<Question> listeQuestions = null;

		try{
			cnx = GesConnexions.getConnexion();
			rqt = cnx.prepareStatement("select top " + nbQuestionsATirer + " * from QUESTION where idTheme = ? order by newid()");
			rqt.setInt(1, idTheme);
			rs = rqt.executeQuery();
			
			while (rs.next()){
				Question question = new Question(
									rs.getInt("idQuestion"),
									rs.getString("enonce"),
									rs.getString("media"),
									rs.getFloat("poids"),
									ThemeDAO.selectById(rs.getInt("idTheme")),
									rs.getString("type"));
				if(listeQuestions == null)listeQuestions = new ArrayList<Question>();
				listeQuestions.add(question);
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}finally{
			if (rs != null) rs.close();
			if (rqt != null) rqt.close();
			if (cnx != null) cnx.close();
		}
		
		return listeQuestions;
	}
}