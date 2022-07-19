package fr.eni_ecole.qcm.beans;

public class Question {

	private int idQuestion;
	private String enonce;
	private String media;
	private float poids;
	private Theme theme;
	private String type;
	
	public Question() {}
	
	public Question(int pIdQuestion, String pEnonce, String pMedia, float pPoids, Theme pTheme, String pType) {
		this.setIdQuestion(pIdQuestion);
		this.setEnonce(pEnonce);
		this.setMedia(pMedia);
		this.setPoids(pPoids);
		this.setTheme(pTheme);
		this.setType(pType);
	}

	public int getIdQuestion() {
		return idQuestion;
	}

	public void setIdQuestion(int pIdQuestion) {
		this.idQuestion = pIdQuestion;
	}

	public String getEnonce() {
		return enonce;
	}

	public void setEnonce(String pEnonce) {
		this.enonce = pEnonce;
	}

	public String getMedia() {
		return media;
	}

	public void setMedia(String pMedia) {
		this.media = pMedia;
	}

	public float getPoids() {
		return poids;
	}

	public void setPoids(float pPoids) {
		this.poids = pPoids;
	}

	public Theme getTheme() {
		return theme;
	}

	public void setTheme(Theme theme) {
		this.theme = theme;
	}

	public String getType() {
		return type;
	}

	public void setType(String pType) {
		this.type = pType;
	}
}