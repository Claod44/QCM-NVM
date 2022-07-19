package fr.eni_ecole.qcm.beans;

public class ReponseTirage {

	private Proposition proposition;
	private Question question;
	private Inscription inscription;
	
	public ReponseTirage() {}

	public ReponseTirage(Proposition pProposition, Question pQuestion, Inscription pInscription) {
		this.setProposition(pProposition);
		this.setQuestion(pQuestion);
		this.setInscription(pInscription);
	}

	public Proposition getProposition() {
		return proposition;
	}

	public void setProposition(Proposition proposition) {
		this.proposition = proposition;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public Inscription getInscription() {
		return inscription;
	}

	public void setInscription(Inscription inscription) {
		this.inscription = inscription;
	}
}