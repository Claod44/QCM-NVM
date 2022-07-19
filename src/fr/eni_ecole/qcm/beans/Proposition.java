package fr.eni_ecole.qcm.beans;

public class Proposition {

	private int idProposition;
	private String enonce;
	private boolean estBonne;
	private Question question;
	
	public Proposition() {}
	
	public Proposition(int pIdProposition, String pEnonce, boolean pEstBonne, Question pQuestion) {
		this.setIdProposition(pIdProposition);
		this.setEnonce(pEnonce);
		this.setEstBonne(pEstBonne);
		this.setQuestion(pQuestion);
	}

	public int getIdProposition() {
		return idProposition;
	}

	public void setIdProposition(int pIdProposition) {
		this.idProposition = pIdProposition;
	}

	public String getEnonce() {
		return enonce;
	}

	public void setEnonce(String pEnonce) {
		this.enonce = pEnonce;
	}

	public boolean isEstBonne() {
		return estBonne;
	}

	public void setEstBonne(boolean pEstBonne) {
		this.estBonne = pEstBonne;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}
	
}