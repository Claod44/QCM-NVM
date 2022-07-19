package fr.eni_ecole.qcm.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import fr.eni_ecole.qcm.beans.Section;

public class SectionDAO {

	public static void update(Section section) throws Exception {
		Connection cnx = null;
		PreparedStatement rqt = null;
		try{
			cnx = GesConnexions.getConnexion();
			rqt = cnx.prepareStatement("update SECTION_TEST set nbQuestionsATirer = ? where idTest = ? and idTheme = ?");
			rqt.setInt(1, section.getNbQuestionsATirer());
			rqt.setInt(2, section.getTest().getIdTest());
			rqt.setInt(3, section.getTheme().getIdTheme());

			rqt.executeUpdate();
			cnx.commit();
		}finally{
			if (rqt != null) rqt.close();
			if (cnx != null) cnx.close();
		}	
	}

	public static void insert(Section section) throws Exception {
		Connection cnx = null;
		PreparedStatement rqt = null;

		try{
			cnx = GesConnexions.getConnexion();
			rqt=cnx.prepareStatement("insert into SECTION_TEST(nbQuestionsATirer, idTest, idTheme) values (?,?,?)");
			rqt.setInt(1, section.getNbQuestionsATirer());
			rqt.setInt(2, section.getTest().getIdTest());
			rqt.setInt(3, section.getTheme().getIdTheme());
			
			rqt.executeUpdate();
			cnx.commit();
		}finally{
			if (rqt != null) rqt.close();
			if (cnx != null) cnx.close();
		}	
	}

	public static void delete(Section section) throws Exception {
		Connection cnx = null;
		PreparedStatement rqt = null;
		try{
			cnx = GesConnexions.getConnexion();
			rqt = cnx.prepareStatement("delete from SECTION_TEST where idTest = ? and idTheme = ?");
			rqt.setInt(1, section.getTest().getIdTest());
			rqt.setInt(2, section.getTheme().getIdTheme());
			rqt.executeUpdate();
			cnx.commit();
		}finally{
			if (rqt != null) rqt.close();
			if (cnx != null) cnx.close();
		}
	}
	//TODO JOINTURE
	public static ArrayList<Section> selectById(int idTest) throws Exception {
		Connection cnx = null;
		PreparedStatement rqt = null;
		ResultSet rs = null;
		ArrayList<Section> listeSections = new ArrayList<Section>();
		
		try{
			cnx = GesConnexions.getConnexion();
			rqt = cnx.prepareStatement("select * from SECTION_TEST where idTest = ?");
			rqt.setInt(1, (int)idTest);
			rs = rqt.executeQuery();
			Section section;
			while (rs.next()){
				section = new Section(
						rs.getInt("nbQuestionsATirer"),
						TestDAO.selectById(rs.getInt("idTest")),
						ThemeDAO.selectById(rs.getInt("idTheme"))
						);
				listeSections.add(section);
			}
		}finally{
			if (rs!=null) rs.close();
			if (rqt!=null) rqt.close();
			if (cnx!=null) cnx.close();
		}
		
		return listeSections;
	}
	//TODO JOINTURE
	public static ArrayList<Section> selectAll() throws Exception {
		Connection cnx = null;
		Statement rqt = null;
		ResultSet rs = null;
		ArrayList<Section> listeSections = new ArrayList<Section>();
		try{
			cnx = GesConnexions.getConnexion();
			rqt = cnx.createStatement();			
			rs = rqt.executeQuery("select * from SECTION_TEST");
			Section section;
			while (rs.next()){
				section = new Section(
									rs.getInt("nbQuestionsATirer"),
									TestDAO.selectById(rs.getInt("idTest")),
									ThemeDAO.selectById(rs.getInt("idTheme"))
						);
				listeSections.add(section);				
			}
		}finally{
			if (rs != null) rs.close();
			if (rqt != null) rqt.close();
			if (cnx != null) cnx.close();
		}
		
		return listeSections;
	}
}