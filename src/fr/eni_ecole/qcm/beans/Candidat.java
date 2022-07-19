package fr.eni_ecole.qcm.beans;

public class Candidat extends Personne{

	private int idCandidat;
	private Promotion promotion;

	public Candidat(){}
	
	public Candidat(int pIdCandidat, String pNom, String pPrenom, String pMail, String pPassword, Promotion pPromotion){
		this.setIdCandidat(pIdCandidat);
		this.setNom(pNom);
		this.setPrenom(pPrenom);
		this.setMail(pMail);
		this.setPassword(pPassword);
		this.setPromotion(pPromotion);
	}

	public int getIdCandidat() {
		return idCandidat;
	}

	public void setIdCandidat(int idCandidat) {
		this.idCandidat = idCandidat;
	}

	public Promotion getPromotion() {
		return promotion;
	}

	public void setPromotion(Promotion promotion) {
		this.promotion = promotion;
	}
		
}