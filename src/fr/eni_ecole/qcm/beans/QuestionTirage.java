package fr.eni_ecole.qcm.beans;

public class QuestionTirage {

	private boolean estMarquee;
	private boolean estRepondue;
	private Inscription inscription;
	private Question question;
	
	private int numOrdre;
	
	public QuestionTirage() {}

	public QuestionTirage(boolean pEstMarquee, boolean pEstRepondue, Inscription pInscription, Question pQuestion, int pNumOrdre) {
		this.setEstMarquee(pEstMarquee);
		this.setEstRepondue(pEstRepondue);
		this.setInscription(pInscription);
		this.setQuestion(pQuestion);
		this.setNumOrdre(pNumOrdre);
	}
	
	public boolean isEstMarquee() {
		return estMarquee;
	}

	public void setEstMarquee(boolean pEstMarquee) {
		this.estMarquee = pEstMarquee;
	}
	
	public boolean isEstRepondue() {
		return estRepondue;
	}

	public void setEstRepondue(boolean estRepondue) {
		this.estRepondue = estRepondue;
	}

	public Inscription getInscription() {
		return inscription;
	}

	public void setInscription(Inscription pInscription) {
		this.inscription = pInscription;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question pQuestion) {
		this.question = pQuestion;
	}

	public int getNumOrdre() {
		return numOrdre;
	}

	public void setNumOrdre(int pNumOrdre) {
		this.numOrdre = pNumOrdre;
	}
	
}