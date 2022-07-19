package fr.eni_ecole.qcm.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import fr.eni_ecole.qcm.beans.Test;

public class TestDAO {

	
	public static void update(Test test) throws Exception {
		Connection cnx = null;
		PreparedStatement rqt = null;
		try{
			cnx = GesConnexions.getConnexion();
			rqt = cnx.prepareStatement("update TEST set libelle = ?, description = ?, duree = ?, seuil_haut = ?, seuil_bas = ?, idEni = ? where idTest = ?");
			rqt.setString(1, test.getLibelle());
			rqt.setString(2, test.getDescription());
			rqt.setInt(3, test.getDuree());
			rqt.setInt(4, test.getSeuilHaut());
			rqt.setInt(5, test.getSeuilBas());
			rqt.setInt(6, test.getPersonnel().getIdEni());
			rqt.setInt(7, test.getIdTest());
			
			rqt.executeUpdate();
			cnx.commit();
		}finally{
			if (rqt != null) rqt.close();
			if (cnx != null) cnx.close();
		}
	}

	
	public static void insert(Test test) throws Exception {
		Connection cnx = null;
		PreparedStatement rqt = null;

		try{
			cnx = GesConnexions.getConnexion();
			rqt=cnx.prepareStatement("insert into TEST(idTest, libelle, description, duree, seuil_haut, seuil_bas, idEni) values (?,?,?,?,?,?,?)");
			rqt.setInt(1, test.getIdTest());
			rqt.setString(2, test.getLibelle());
			rqt.setString(3, test.getDescription());
			rqt.setInt(4, test.getDuree());
			rqt.setInt(5, test.getSeuilHaut());
			rqt.setInt(6, test.getSeuilBas());
			rqt.setInt(7, test.getPersonnel().getIdEni());
			
			rqt.executeUpdate();
			cnx.commit();
		}finally{
			if (rqt != null) rqt.close();
			if (cnx != null) cnx.close();
		}	
	}

	
	public static void delete(Test test) throws Exception {
		Connection cnx = null;
		PreparedStatement rqt = null;
		try{
			cnx = GesConnexions.getConnexion();
			rqt = cnx.prepareStatement("delete from TEST where idTest = ?");
			rqt.setInt(1, test.getIdTest());
			rqt.executeUpdate();
			cnx.commit();
		}finally{
			if (rqt != null) rqt.close();
			if (cnx != null) cnx.close();
		}
	}

	//TODO JOINTURE
	public static Test selectById(int id) throws Exception {
		Connection cnx = null;
		PreparedStatement rqt = null;
		ResultSet rs = null;
		Test test = new Test();
		try{
			cnx = GesConnexions.getConnexion();
			rqt = cnx.prepareStatement("select * from TEST where idTest = ?");
			rqt.setInt(1, id);
			rs = rqt.executeQuery();
			while (rs.next()){
				test.setIdTest(id);
				test.setLibelle(rs.getString("libelle"));
				test.setDescription(rs.getString("description"));
				test.setDuree(rs.getInt("duree"));
				test.setSeuilHaut(rs.getInt("seuil_haut"));
				test.setSeuilBas(rs.getInt("seuil_bas"));
				test.setPersonnel(PersonnelDAO.selectById(rs.getInt("idEni")));
			}
		}finally{
			if (rs!=null) rs.close();
			if (rqt!=null) rqt.close();
			if (cnx!=null) cnx.close();
		}
		
		return test;
	}

	//TODO JOINTURE
	public static ArrayList<Test> selectAll() throws Exception {
		Connection cnx = null;
		Statement rqt = null;
		ResultSet rs = null;
		ArrayList<Test> listeTests = new ArrayList<Test>();
		try{
			cnx = GesConnexions.getConnexion();
			rqt = cnx.createStatement();			
			rs = rqt.executeQuery("select * from TEST");
			Test test;
			while (rs.next()){
				test = new Test(
									rs.getInt("idTest"),
									rs.getString("libelle"),
									rs.getString("description"),
									rs.getInt("duree"),
									rs.getInt("seuil_haut"),
									rs.getInt("seuil_bas"),
									PersonnelDAO.selectById(rs.getInt("idEni"))
						);
				listeTests.add(test);				
			}
		}finally{
			if (rs != null) rs.close();
			if (rqt != null) rqt.close();
			if (cnx != null) cnx.close();
		}
		
		return listeTests;
	}
}