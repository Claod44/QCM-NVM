package fr.eni_ecole.qcm.beans;

public class Theme {

	private int idTheme;
	private String libelle;
	
	public Theme() {}
	
	public Theme(int pIdTheme, String pLibelle) {}

	public int getIdTheme() {
		return idTheme;
	}

	public void setIdTheme(int pIdTheme) {
		this.idTheme = pIdTheme;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String pLibelle) {
		this.libelle = pLibelle;
	}

	@Override
	public String toString() {
		return "Theme [idTheme : " + idTheme 
				+ ", libelle : " + libelle + "]";
	}	
}