package fr.eni_ecole.qcm.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import fr.eni_ecole.qcm.beans.Candidat;
import fr.eni_ecole.qcm.beans.Inscription;

public class InscriptionDAO {

	
	public static void update(Inscription inscription) throws Exception {
		Connection cnx = null;
		PreparedStatement rqt = null;
		try{
			cnx = GesConnexions.getConnexion();
			rqt = cnx.prepareStatement("update INSCRIPTION set finValidite = ?, tempsEcoule = ?, etat = ?, resultat_obtenu = ?, idTest = ?, idEni = ?, idCandidat = ? where idInscription = ?");
			rqt.setDate(1, new java.sql.Date(inscription.getFinValidite().getTime()));
			rqt.setInt(2, inscription.getTempsEcoule());
			rqt.setString(3, inscription.getEtat());
			rqt.setDouble(4, inscription.getResultatObtenu());
			rqt.setInt(5, inscription.getTest().getIdTest());
			rqt.setInt(6, inscription.getPersonnel().getIdEni());
			rqt.setInt(7, inscription.getCandidat().getIdCandidat());
			rqt.setInt(8, inscription.getIdInscription());
			
			rqt.executeUpdate();
			cnx.commit();
		}finally{
			if (rqt != null) rqt.close();
			if (cnx != null) cnx.close();
		}	
	}

	
	public static void insert(Inscription inscription) throws Exception {
		Connection cnx = null;
		PreparedStatement rqt = null;

		try{
			cnx = GesConnexions.getConnexion();
			rqt=cnx.prepareStatement("insert into INSCRIPTION (idInscription, finValidite, tempsEcoule, etat, resultat_obtenu, idTest, idEni, idCandidat) values (?,?,?,?,?,?,?,?)");
			rqt.setInt(1, inscription.getIdInscription());
			rqt.setDate(2, new java.sql.Date(inscription.getFinValidite().getTime()));
			rqt.setInt(3, inscription.getTempsEcoule());
			rqt.setString(4, inscription.getEtat());
			rqt.setDouble(5, inscription.getResultatObtenu());
			rqt.setInt(6, inscription.getTest().getIdTest());
			rqt.setInt(7, inscription.getPersonnel().getIdEni());
			rqt.setInt(8, inscription.getCandidat().getIdCandidat());
			
			rqt.executeUpdate();
			cnx.commit();
		}finally{
			if (rqt != null) rqt.close();
			if (cnx != null) cnx.close();
		}	
	}

	
	public static void delete(Inscription inscription) throws Exception {
		Connection cnx = null;
		PreparedStatement rqt = null;
		try{
			cnx = GesConnexions.getConnexion();
			rqt = cnx.prepareStatement("delete from INSCRIPTION where idInscription = ?");
			rqt.setInt(1, inscription.getIdInscription());
			rqt.executeUpdate();
			cnx.commit();
		}finally{
			if (rqt != null) rqt.close();
			if (cnx != null) cnx.close();
		}
	}

	//TODO JOINTURE
	public static Inscription selectById(int id) throws Exception {
		Connection cnx = null;
		PreparedStatement rqt = null;
		ResultSet rs = null;
		Inscription inscription = null;
		try{
			cnx = GesConnexions.getConnexion();
			rqt = cnx.prepareStatement("select * from INSCRIPTION where idInscription = ?");
			rqt.setInt(1, id);
			rs = rqt.executeQuery();
			while (rs.next()){
				inscription = new Inscription(
						id,
						rs.getDate("finValidite"),
						rs.getInt("tempsEcoule"),
						rs.getString("etat"),
						rs.getDouble("resultat_obtenu"),
						TestDAO.selectById(rs.getInt("idTest")),
						PersonnelDAO.selectById(rs.getInt("idEni")),
						CandidatDAO.selectById(rs.getInt("idCandidat"))
						);
			}
		}catch(Exception e){
			System.out.println("Ca pète dans le try catch de selectById");
		}finally{
			if (rs!=null) rs.close();
			if (rqt!=null) rqt.close();
			if (cnx!=null) cnx.close();
		}
		
		return inscription;
	}

	//TODO JOINTURE
	public static ArrayList<Inscription> selectByCandidat(Candidat candidat) throws Exception {
		Connection cnx = null;
		PreparedStatement rqt = null;
		ResultSet rs = null;
		ArrayList<Inscription> listeInscriptions = null;
		try{
			cnx = GesConnexions.getConnexion();
			rqt = cnx.prepareStatement("SELECT * FROM INSCRIPTION WHERE idCandidat = ?");			
			rqt.setInt(1, candidat.getIdCandidat());
			rs = rqt.executeQuery();
			Inscription inscription;
			int idInscription = 0;
			while (rs.next()){
				idInscription = rs.getInt("idInscription");
				inscription = new Inscription(
									idInscription,
									rs.getDate("finValidite"),
									rs.getInt("tempsEcoule"),
									rs.getString("etat"),
									rs.getDouble("resultat_obtenu"),
									TestDAO.selectById(rs.getInt("idTest")),
									PersonnelDAO.selectById(rs.getInt("idEni")),
									CandidatDAO.selectById(candidat.getIdCandidat())
						);
				if (listeInscriptions == null) listeInscriptions = new ArrayList<Inscription>();
				listeInscriptions.add(inscription);				
			}
		}finally{
			if (rs != null) rs.close();
			if (rqt != null) rqt.close();
			if (cnx != null) cnx.close();
		}
		
		return listeInscriptions;
	}
	
	public static ArrayList<Inscription> selectInscritByCandidat(Candidat candidat) throws Exception {
		Connection cnx = null;
		PreparedStatement rqt = null;
		ResultSet rs = null;
		ArrayList<Inscription> listeInscriptions = null;
		try{
			cnx = GesConnexions.getConnexion();
			rqt = cnx.prepareStatement("SELECT * FROM INSCRIPTION "
									 + "WHERE idCandidat = ? AND etat = 'NC'");			
			rqt.setInt(1, candidat.getIdCandidat());
			rs = rqt.executeQuery();
			Inscription inscription;
			int idInscription = 0;
			while (rs.next()){
				idInscription = rs.getInt("idInscription");
				inscription = new Inscription(
									idInscription,
									rs.getDate("finValidite"),
									rs.getInt("tempsEcoule"),
									rs.getString("etat"),
									rs.getDouble("resultat_obtenu"),
									TestDAO.selectById(rs.getInt("idTest")),
									PersonnelDAO.selectById(rs.getInt("idEni")),
									CandidatDAO.selectById(candidat.getIdCandidat())
						);
				if (listeInscriptions == null) listeInscriptions = new ArrayList<Inscription>();
				listeInscriptions.add(inscription);				
			}
		}finally{
			if (rs != null) rs.close();
			if (rqt != null) rqt.close();
			if (cnx != null) cnx.close();
		}
		
		return listeInscriptions;
	}
	
	public static ArrayList<Inscription> selectEnCoursByCandidat(Candidat candidat) throws Exception {
		Connection cnx = null;
		PreparedStatement rqt = null;
		ResultSet rs = null;
		ArrayList<Inscription> listeInscriptions = null;
		try{
			cnx = GesConnexions.getConnexion();
			rqt = cnx.prepareStatement("SELECT * FROM INSCRIPTION "
									 + "WHERE idCandidat = ? AND etat = 'EC'");			
			rqt.setInt(1, candidat.getIdCandidat());
			rs = rqt.executeQuery();
			Inscription inscription;
			int idInscription = 0;
			while (rs.next()){
				idInscription = rs.getInt("idInscription");
				inscription = new Inscription(
									idInscription,
									rs.getDate("finValidite"),
									rs.getInt("tempsEcoule"),
									rs.getString("etat"),
									rs.getDouble("resultat_obtenu"),
									TestDAO.selectById(rs.getInt("idTest")),
									PersonnelDAO.selectById(rs.getInt("idEni")),
									CandidatDAO.selectById(candidat.getIdCandidat())
						);
				if (listeInscriptions == null) listeInscriptions = new ArrayList<Inscription>();
				listeInscriptions.add(inscription);				
			}
		}finally{
			if (rs != null) rs.close();
			if (rqt != null) rqt.close();
			if (cnx != null) cnx.close();
		}
		
		return listeInscriptions;
	}
	
	public static ArrayList<Inscription> selectTermineByCandidat(Candidat candidat) throws Exception {
		Connection cnx = null;
		PreparedStatement rqt = null;
		ResultSet rs = null;
		ArrayList<Inscription> listeInscriptions = null;
		try{
			cnx = GesConnexions.getConnexion();
			rqt = cnx.prepareStatement("SELECT * FROM INSCRIPTION "
									 + "WHERE idCandidat = ? AND etat = 'TE'");			
			rqt.setInt(1, candidat.getIdCandidat());
			rs = rqt.executeQuery();
			Inscription inscription;
			int idInscription = 0;
			while (rs.next()){
				idInscription = rs.getInt("idInscription");
				inscription = new Inscription(
									idInscription,
									rs.getDate("finValidite"),
									rs.getInt("tempsEcoule"),
									rs.getString("etat"),
									rs.getDouble("resultat_obtenu"),
									TestDAO.selectById(rs.getInt("idTest")),
									PersonnelDAO.selectById(rs.getInt("idEni")),
									CandidatDAO.selectById(candidat.getIdCandidat())
						);
				if (listeInscriptions == null) listeInscriptions = new ArrayList<Inscription>();
				listeInscriptions.add(inscription);				
			}
		}finally{
			if (rs != null) rs.close();
			if (rqt != null) rqt.close();
			if (cnx != null) cnx.close();
		}
		
		return listeInscriptions;
	}
	
}