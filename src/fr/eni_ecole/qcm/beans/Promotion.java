package fr.eni_ecole.qcm.beans;

public class Promotion {

	private String codePromo;
	private String libelle;
	
	public Promotion() {}
	
	public Promotion(String pCodePromo, String pLibelle) {
		this.setCodePromo(pCodePromo);
		this.setLibelle(pLibelle);
	}

	public String getCodePromo() {
		return codePromo;
	}

	public void setCodePromo(String codePromo) {
		this.codePromo = codePromo;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	
	@Override
	public String toString(){
		return "Promotion - Code Promotion : " + this.getCodePromo() + ", Libelle : " + this.getLibelle();
	}
}