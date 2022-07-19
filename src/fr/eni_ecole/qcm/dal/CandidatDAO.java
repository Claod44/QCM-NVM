package fr.eni_ecole.qcm.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import fr.eni_ecole.qcm.beans.Candidat;

public class CandidatDAO {

	
	public static void update(Candidat candidat) throws Exception {
		Connection cnx = null;
		PreparedStatement rqt = null;
		try{
			cnx = GesConnexions.getConnexion();
			rqt = cnx.prepareStatement("update CANDIDAT set nom = ?, prenom = ?, email = ?, password = ?, codepromo = ? where idCandidat = ?");
			rqt.setString(1, candidat.getNom());
			rqt.setString(2, candidat.getPrenom());
			rqt.setString(3, candidat.getMail());
			rqt.setString(4, candidat.getPassword());
			rqt.setString(5, candidat.getPromotion().getCodePromo());
			rqt.setInt(6, candidat.getIdCandidat());

			rqt.executeUpdate();
			cnx.commit();
		}finally{
			if (rqt != null) rqt.close();
			if (cnx != null) cnx.close();
		}		
	}

	
	public static void insert(Candidat candidat) throws Exception {
		Connection cnx = null;
		PreparedStatement rqt = null;

		try{
			cnx = GesConnexions.getConnexion();
			rqt=cnx.prepareStatement("insert into CANDIDAT(idCandidat, nom, prenom, email, password, codePromo) values (?,?,?,?,?,?)");
			rqt.setInt(1, candidat.getIdCandidat());
			rqt.setString(2, candidat.getNom());
			rqt.setString(3, candidat.getPrenom());
			rqt.setString(4, candidat.getMail());
			rqt.setString(5, candidat.getPassword());
			rqt.setString(6, candidat.getPromotion().getCodePromo());
			
			rqt.executeUpdate();
			cnx.commit();
		}finally{
			if (rqt != null) rqt.close();
			if (cnx != null) cnx.close();
		}		
	}

	
	public static void delete(Candidat candidat) throws Exception {
		Connection cnx = null;
		PreparedStatement rqt = null;
		try{
			cnx = GesConnexions.getConnexion();
			rqt = cnx.prepareStatement("delete from CANDIDAT where idCandidat = ?");
			rqt.setInt(1, candidat.getIdCandidat());
			rqt.executeUpdate();
			cnx.commit();
		}finally{
			if (rqt != null) rqt.close();
			if (cnx != null) cnx.close();
		}		
	}

	//TODO JOINTURE
	public static Candidat selectById(int id) throws Exception {
		Connection cnx = null;
		PreparedStatement rqt = null;
		ResultSet rs = null;
		Candidat candidat = null;
		try{
			cnx = GesConnexions.getConnexion();
			rqt = cnx.prepareStatement("select * from CANDIDAT where idCandidat = ?");
			rqt.setInt(1, id);
			rs = rqt.executeQuery();
			while (rs.next()){
				candidat = new Candidat(
									rs.getInt("idCandidat"),
									rs.getString("nom"),
									rs.getString("prenom"),
									rs.getString("email"),
									rs.getString("password"),
									PromotionDAO.selectById(rs.getString("codePromo"))
						);
			}
		}finally{
			if (rs!=null) rs.close();
			if (rqt!=null) rqt.close();
			if (cnx!=null) cnx.close();
		}
		
		return candidat;
	}

	//TODO JOINTURE
	public static ArrayList<Candidat> selectAll() throws Exception {
		Connection cnx = null;
		Statement rqt = null;
		ResultSet rs = null;
		ArrayList<Candidat> listeCandidats = new ArrayList<Candidat>();
		try{
			cnx = GesConnexions.getConnexion();
			rqt = cnx.createStatement();			
			rs = rqt.executeQuery("select * from CANDIDAT");
			Candidat candidat;
			while (rs.next()){
				candidat = new Candidat(
									rs.getInt("idCandidat"),
									rs.getString("nom"),
									rs.getString("prenom"),
									rs.getString("email"),
									rs.getString("password"),
									PromotionDAO.selectById(rs.getString("codePromo"))
						);
				listeCandidats.add(candidat);				
			}
		}finally{
			if (rs != null) rs.close();
			if (rqt != null) rqt.close();
			if (cnx != null) cnx.close();
		}
		
		return listeCandidats;
	}
	//TODO JOINTURE
	/**
	 * 
	 * @param username
	 * @param password
	 * @return null ! OR Candidat
	 * @throws Exception
	 */
	public static Candidat authentifier(String username, String password) throws Exception {
		Connection cnx = null;
		PreparedStatement rqt = null;
		ResultSet rs = null;
		Candidat candidat = null;
		try{
			cnx = GesConnexions.getConnexion();
			rqt = cnx.prepareStatement("select * from CANDIDAT where email = ? AND password = ?");
			rqt.setString(1, username);
			rqt.setString(2, password);
			rs = rqt.executeQuery();
			while (rs.next()){
				candidat = new Candidat(
						rs.getInt("idCandidat"),
						rs.getString("nom"),
						rs.getString("prenom"),
						rs.getString("email"),
						rs.getString("password"),
						PromotionDAO.selectById(rs.getString("codePromo"))
				);
			}
		}finally{
			if (rs != null) rs.close();
			if (rqt != null) rqt.close();
			if (cnx != null) cnx.close();
		}
		return candidat;
	}
}