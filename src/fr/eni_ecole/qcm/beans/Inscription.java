package fr.eni_ecole.qcm.beans;

import java.util.Date;

public class Inscription {

	private int idInscription;
	private Date finValidite;
	private int tempsEcoule;
	private String etat;
	private double resultatObtenu;
	private Test test;
	private Personnel personnel;
	private Candidat candidat;
	
	public Inscription() {}

	public Inscription(int pIdInscription, Date pFinValidite, int pTempsEcoule, String pEtat, double pResultatObtenu, Test pTest, Personnel pPersonnel, Candidat pCandidat) {
		this.setIdInscription(pIdInscription);
		this.setFinValidite(pFinValidite);
		this.setTempsEcoule(pTempsEcoule);
		this.setEtat(pEtat);
		this.setResultatObtenu(pResultatObtenu);
		this.setTest(pTest);
		this.setPersonnel(pPersonnel);
		this.setCandidat(pCandidat);
	}
	
	public int getIdInscription() {
		return idInscription;
	}

	public void setIdInscription(int pIdInscription) {
		this.idInscription = pIdInscription;
	}

	public Date getFinValidite() {
		return finValidite;
	}

	public void setFinValidite(Date pFinValidite) {
		this.finValidite = pFinValidite;
	}

	public int getTempsEcoule() {
		return tempsEcoule;
	}

	public void setTempsEcoule(int pTempsEcoule) {
		this.tempsEcoule = pTempsEcoule;
	}

	public String getEtat() {
		return etat;
	}

	public void setEtat(String pEtat) {
		this.etat = pEtat;
	}

	public double getResultatObtenu() {
		return resultatObtenu;
	}

	public void setResultatObtenu(double pResultatObtenu) {
		this.resultatObtenu = pResultatObtenu;
	}

	public Test getTest() {
		return test;
	}

	public void setTest(Test test) {
		this.test = test;
	}

	public Personnel getPersonnel() {
		return personnel;
	}

	public void setPersonnel(Personnel personnel) {
		this.personnel = personnel;
	}

	public Candidat getCandidat() {
		return candidat;
	}

	public void setCandidat(Candidat candidat) {
		this.candidat = candidat;
	}
}