package fr.eni_ecole.qcm.beans;

public class Personnel extends Personne{

	private int idEni;
	
	public Personnel() {}
	
	public Personnel(int pIdEni, String pNom, String pPrenom, String pMail, String pPassword) {
		this.setIdEni(pIdEni);
		this.setNom(pNom);
		this.setPrenom(pPrenom);
		this.setMail(pMail);
		this.setPassword(pPassword);
	}

	public int getIdEni() {
		return idEni;
	}

	public void setIdEni(int pIdEni) {
		this.idEni = pIdEni;
	}
	
	@Override
	public String toString(){
		return "Id ENI : " + this.getIdEni() 
				+ ", " + super.toString();
	}
}