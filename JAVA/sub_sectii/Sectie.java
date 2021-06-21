package ro.ase.sectii;

public class Sectie {

	private int codSectie;
	private String denumire;
	private int numarLocuri;

	private int varstaMediePacienti;
	public int nrPacienti;

	public Sectie() {
		denumire = "";
	}

	public Sectie(int codSectie, String denumire, int numarLocuri) {
		this.codSectie = codSectie;
		this.denumire = denumire;
		this.numarLocuri = numarLocuri;
	}

	public int getCodSectie() {
		return codSectie;
	}

	public void setCodSectie(int c) {
		codSectie = c;
	}

	public String getDenumire() {
		return denumire;
	}

	public void setDenumire(String d) {
		denumire = d;
	}

	public int getNumarLocuri() {
		return numarLocuri;
	}

	public void setNumarLocuri(int n) {
		numarLocuri = n;
	}

	public int getVarstaM() {
		return varstaMediePacienti;
	}

	public void setVarstaM(int n) {
		varstaMediePacienti = n;
	}
	
	public String toString() {
		return "Sectie cu cod: " + codSectie + ", denumire: " + denumire + ", nr locuri: " + numarLocuri;
	}
}
