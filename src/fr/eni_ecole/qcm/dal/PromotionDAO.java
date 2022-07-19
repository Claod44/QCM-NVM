package fr.eni_ecole.qcm.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import fr.eni_ecole.qcm.beans.Promotion;

public class PromotionDAO {

	
	public static void update(Promotion promotion) throws Exception {
		Connection cnx = null;
		PreparedStatement rqt = null;
		try{
			cnx = GesConnexions.getConnexion();
			rqt = cnx.prepareStatement("update PROMOTION set libelle = ? where codePromo = ?");
			rqt.setString(1, promotion.getLibelle());
			rqt.setString(2, promotion.getCodePromo());

			rqt.executeUpdate();
			cnx.commit();
		}finally{
			if (rqt != null) rqt.close();
			if (cnx != null) cnx.close();
		}
	}

	
	public static void insert(Promotion promotion) throws Exception {
		Connection cnx = null;
		PreparedStatement rqt = null;
		
		try{
			cnx = GesConnexions.getConnexion();
			rqt=cnx.prepareStatement("insert into PROMOTION(codePromo, libelle) values (?,?)");
			rqt.setString(1, promotion.getLibelle());
			rqt.setString(2, promotion.getCodePromo());
			
			rqt.executeUpdate();
			cnx.commit();
		}finally{
			if (rqt != null) rqt.close();
			if (cnx != null) cnx.close();
		}
	}

	
	public static void delete(Promotion promotion) throws Exception {
		Connection cnx = null;
		PreparedStatement rqt = null;
		try{
			cnx = GesConnexions.getConnexion();
			rqt = cnx.prepareStatement("delete from PROMOTION where codePromo = ?");
			rqt.setString(1, promotion.getCodePromo());
			
			rqt.executeUpdate();
			cnx.commit();
		}finally{
			if (rqt != null) rqt.close();
			if (cnx != null) cnx.close();
		}	
	}

	
	public static Promotion selectById(String id) throws Exception {
		Connection cnx = null;
		PreparedStatement rqt = null;
		ResultSet rs = null;
		Promotion promotion = new Promotion();
		try{
			cnx = GesConnexions.getConnexion();
			rqt = cnx.prepareStatement("select * from PROMOTION where codePromo = ?");
			rqt.setString(1, id);
			rs = rqt.executeQuery();
			while (rs.next()){
				promotion = new Promotion(rs.getString("codePromo"), 
						            	  rs.getString("libelle"));
			}
		}finally{
			if (rs!=null) rs.close();
			if (rqt!=null) rqt.close();
			if (cnx!=null) cnx.close();
		}
		
		return promotion;
	}

	
	public static ArrayList<Promotion> selectAll() throws Exception {
		Connection cnx = null;
		Statement rqt = null;
		ResultSet rs = null;
		ArrayList<Promotion> listePromotions = new ArrayList<Promotion>();
		try{
			cnx = GesConnexions.getConnexion();
			rqt = cnx.createStatement();			
			rs = rqt.executeQuery("select * from PROMOTION");
			Promotion promotion;
			while (rs.next()){
				promotion = new Promotion(
									rs.getString("codePromo"),
									rs.getString("libelle")
						);
				listePromotions.add(promotion);				
			}
		}finally{
			if (rs != null) rs.close();
			if (rqt != null) rqt.close();
			if (cnx != null) cnx.close();
		}
		
		return listePromotions;
	}
	
	
	
}
