package fr.eni_ecole.qcm.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import fr.eni_ecole.qcm.beans.Theme;

public class ThemeDAO {

	
	public static void update(Theme theme) throws Exception {
		Connection cnx = null;
		PreparedStatement rqt = null;
		try{
			cnx = GesConnexions.getConnexion();
			rqt = cnx.prepareStatement("update THEME set libelle = ? where idTheme = ?");
			rqt.setString(1, theme.getLibelle());
			rqt.setInt(2, theme.getIdTheme());

			rqt.executeUpdate();
			cnx.commit();
		}finally{
			if (rqt != null) rqt.close();
			if (cnx != null) cnx.close();
		}	
	}

	
	public static void insert(Theme theme) throws Exception {
		Connection cnx = null;
		PreparedStatement rqt = null;

		try{
			cnx = GesConnexions.getConnexion();
			rqt=cnx.prepareStatement("insert into THEME(idTheme, libelle) values (?,?)");
			rqt.setInt(1, theme.getIdTheme());
			rqt.setString(2, theme.getLibelle());
			
			rqt.executeUpdate();
			cnx.commit();
		}finally{
			if (rqt != null) rqt.close();
			if (cnx != null) cnx.close();
		}
	}

	
	public static void delete(Theme theme) throws Exception {
		Connection cnx = null;
		PreparedStatement rqt = null;
		try{
			cnx = GesConnexions.getConnexion();
			rqt = cnx.prepareStatement("delete from THEME where idTheme = ?");
			rqt.setInt(1, theme.getIdTheme());
			rqt.executeUpdate();
			cnx.commit();
		}finally{
			if (rqt != null) rqt.close();
			if (cnx != null) cnx.close();
		}	
	}

	
	public static <Type> Theme selectById(Type id) throws Exception {
		Connection cnx = null;
		PreparedStatement rqt = null;
		ResultSet rs = null;
		Theme theme = new Theme();
		try{
			cnx = GesConnexions.getConnexion();
			rqt = cnx.prepareStatement("select * from THEME where idTheme = ?");
			rqt.setInt(1, (int)id);
			rs = rqt.executeQuery();
			while (rs.next()){
				theme.setIdTheme((int)id);
				theme.setLibelle(rs.getString("libelle"));
			}
		}finally{
			if (rs!=null) rs.close();
			if (rqt!=null) rqt.close();
			if (cnx!=null) cnx.close();
		}
		
		return theme;
	}

	
	public static ArrayList<Theme> selectAll() throws Exception {
		Connection cnx = null;
		Statement rqt = null;
		ResultSet rs = null;
		ArrayList<Theme> listeThemes = new ArrayList<Theme>();
		try{
			cnx = GesConnexions.getConnexion();
			rqt = cnx.createStatement();			
			rs = rqt.executeQuery("select * from THEME");
			Theme theme;
			while (rs.next()){
				theme = new Theme(
									rs.getInt("idTheme"),
									rs.getString("libelle")
						);
				listeThemes.add(theme);				
			}
		}finally{
			if (rs != null) rs.close();
			if (rqt != null) rqt.close();
			if (cnx != null) cnx.close();
		}
		
		return listeThemes;
	}
}