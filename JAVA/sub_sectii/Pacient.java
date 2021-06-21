package ro.ase.sectii;

public class Pacient {

	private String cnp;
	private String nume;
	private int varsta;
	private int codSectie;

	public Pacient() {
		cnp = "";
		nume = "";
	}

	public Pacient(String cnp, String nume, int varsta, int codSectie) {
		this.cnp = cnp;
		this.nume = nume;
		this.varsta = varsta;
		this.codSectie = codSectie;
	}

	public void setCnp(String c) {
		cnp = c;
	}

	public void setNume(String n) {
		nume = n;
	}

	public void setVarsta(int v) {
		varsta = v;
	}

	public void setCodSectie(int c) {
		codSectie = c;
	}

	public String getCnp() {
		return cnp;
	}

	public String getNume() {
		return nume;
	}

	public int getVarsta() {
		return varsta;
	}

	public int getCodSectie() {
		return codSectie;
	}

	public String toString() {
		return "Pacient cu cnp: " + cnp + ", nume: " + nume + ", varsta: " + varsta + ", cod sectie: " + codSectie;
	}

}
