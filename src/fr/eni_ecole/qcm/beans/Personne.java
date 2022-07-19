package fr.eni_ecole.qcm.beans;

public abstract class Personne {

	private String nom;
	private String prenom;
	private String mail;
	private String password;
	
	public String getNom() {
		return nom;
	}

	public void setNom(String pNom) {
		this.nom = pNom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String pPrenom) {
		this.prenom = pPrenom;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String pMail) {
		this.mail = pMail;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String pPassword) {
		this.password = pPassword;
	}
	
	@Override
	public String toString(){
		return "Nom : " + this.getNom() 
				+ ", Prénom : " + this.getPrenom() 
				+ ", Mail : " + this.getMail()
				+ ", Password : " + this.getPassword();
	}
}