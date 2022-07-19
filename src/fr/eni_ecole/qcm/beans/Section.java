package fr.eni_ecole.qcm.beans;

public class Section {

	private int nbQuestionsATirer;
	private Test test;
	private Theme theme;
	
	public Section() {}
	
	public Section(int pNbQuestionsATirer, Test pTest, Theme pTheme) {
		this.setNbQuestionsATirer(pNbQuestionsATirer);
		this.setTest(pTest);
		this.setTheme(pTheme);
	}

	public int getNbQuestionsATirer() {
		return nbQuestionsATirer;
	}

	public void setNbQuestionsATirer(int pNbQuestionsATirer) {
		this.nbQuestionsATirer = pNbQuestionsATirer;
	}

	public Test getTest() {
		return test;
	}

	public void setTest(Test test) {
		this.test = test;
	}

	public Theme getTheme() {
		return theme;
	}

	public void setTheme(Theme theme) {
		this.theme = theme;
	}
	
}