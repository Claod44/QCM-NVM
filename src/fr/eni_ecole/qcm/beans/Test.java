package fr.eni_ecole.qcm.beans;

public class Test {

	private int idTest;
	private String libelle;
	private String description;
	private int duree;
	private int seuilHaut;
	private int seuilBas;
	private Personnel personnel;
	
	public Test() {}
	
	public Test(int pIdTest, String pLibelle, String pDescription, int pDuree, int pSeuilHaut, int pSeuilBas, Personnel pPersonnel) {
		this.setIdTest(pIdTest);
		this.setLibelle(pLibelle);
		this.setDescription(pDescription);
		this.setDuree(pDuree);
		this.setSeuilHaut(pSeuilHaut);
		this.setSeuilBas(pSeuilBas);
		this.setPersonnel(pPersonnel);
	}

	public int getIdTest() {
		return idTest;
	}

	public void setIdTest(int pIdTest) {
		this.idTest = pIdTest;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String pLibelle) {
		this.libelle = pLibelle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String pDescription) {
		this.description = pDescription;
	}

	public int getDuree() {
		return duree;
	}

	public void setDuree(int pDuree) {
		this.duree = pDuree;
	}

	public int getSeuilHaut() {
		return seuilHaut;
	}

	public void setSeuilHaut(int pSeuilHaut) {
		this.seuilHaut = pSeuilHaut;
	}

	public int getSeuilBas() {
		return seuilBas;
	}

	public void setSeuilBas(int pSeuilBas) {
		this.seuilBas = pSeuilBas;
	}

	public Personnel getPersonnel() {
		return personnel;
	}

	public void setPersonnel(Personnel personnel) {
		this.personnel = personnel;
	}
	
}