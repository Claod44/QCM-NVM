package fr.eni_ecole.qcm.dal;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import fr.eni_ecole.qcm.beans.Personnel;
import fr.eni_ecole.qcm.dal.GesConnexions;

public class PersonnelDAO {

	
	public static void update(Personnel personnel) throws Exception {
		Connection cnx = null;
		PreparedStatement rqt = null;
		try{
			cnx=GesConnexions.getConnexion();
			rqt=cnx.prepareStatement("update PERSONNEL set nom = ?, prenom = ?, mail = ?, password = ? where idEni = ?");
			rqt.setString(1, personnel.getNom());
			rqt.setString(2, personnel.getPrenom());
			rqt.setString(3, personnel.getMail());
			rqt.setString(4, personnel.getPassword());
			rqt.setInt(5, personnel.getIdEni());

			rqt.executeUpdate();
			cnx.commit();
		}finally{
			if (rqt != null) rqt.close();
			if (cnx != null) cnx.close();
		}
	}

	
	public static void insert(Personnel personnel) throws Exception {
		Connection cnx = null;
		PreparedStatement rqt = null;

		try{
			cnx = GesConnexions.getConnexion();
			rqt=cnx.prepareStatement("insert into PERSONNEL(idEni, nom, prenom, mail, password) values (?,?,?,?,?)");
			rqt.setInt(1, personnel.getIdEni());
			rqt.setString(2, personnel.getNom());
			rqt.setString(3, personnel.getPrenom());
			rqt.setString(4, personnel.getMail());
			rqt.setString(5, personnel.getPassword());
			
			rqt.executeUpdate();
			cnx.commit();
		}finally{
			if (rqt != null) rqt.close();
			if (cnx != null) cnx.close();
		}		
		
	}

	
	public static void delete(Personnel personnel) throws Exception {
		Connection cnx = null;
		PreparedStatement rqt = null;
		try{
			cnx = GesConnexions.getConnexion();
			rqt = cnx.prepareStatement("delete from PERSONNEL where idEni = ?");
			rqt.setInt(1, personnel.getIdEni());
			rqt.executeUpdate();
			cnx.commit();
		}finally{
			if (rqt != null) rqt.close();
			if (cnx != null) cnx.close();
		}	
		
	}

	
	public static Personnel selectById(int id) throws Exception {
		Connection cnx = null;
		PreparedStatement rqt = null;
		ResultSet rs = null;
		Personnel personnel = null;
		try{
			cnx = GesConnexions.getConnexion();
			rqt = cnx.prepareStatement("select * from PERSONNEL where idEni = ?");
			rqt.setInt(1, id);
			rs = rqt.executeQuery();
			while (rs.next()){
				personnel = new Personnel(
						rs.getInt("idEni"),
						rs.getString("nom"),
						rs.getString("prenom"),
						rs.getString("mail"),
						rs.getString("password")
						);
			}
		}finally{
			if (rs!=null) rs.close();
			if (rqt!=null) rqt.close();
			if (cnx!=null) cnx.close();
		}
		
		return personnel;
	}

	
	public static ArrayList<Personnel> selectAll() throws Exception {
		Connection cnx = null;
		Statement rqt = null;
		ResultSet rs = null;
		ArrayList<Personnel> listePersonnels = null;
		try{
			cnx = GesConnexions.getConnexion();
			rqt = cnx.createStatement();			
			rs = rqt.executeQuery("select * from PERSONNEL");
			Personnel personnel;
			while (rs.next()){
				personnel = new Personnel(
									rs.getInt("idEni"),
									rs.getString("nom"),
									rs.getString("prenom"),
									rs.getString("mail"),
									rs.getString("password")
						);
				if(listePersonnels == null) listePersonnels = new ArrayList<Personnel>();
				listePersonnels.add(personnel);				
			}
		}finally{
			if (rs != null) rs.close();
			if (rqt != null) rqt.close();
			if (cnx != null) cnx.close();
		}
		
		return listePersonnels;
	}


	
	
}
