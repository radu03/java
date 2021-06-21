package pachet;


public class Liceu implements Comparable<Liceu> {

	private int codLiceu;
	private String numeLiceu;
	private int nrSpecializari;
	private Specializare[] vectorSpecializari = null;

	public Liceu() {
		this.vectorSpecializari = null;
	}

	public Liceu(int codLiceu, String numeLiceu, int nrSpecializari, Specializare[] vectorSpecializari) {
		this.codLiceu = codLiceu;
		this.numeLiceu = numeLiceu;
		this.nrSpecializari = nrSpecializari;
		this.vectorSpecializari = new Specializare[nrSpecializari];
		this.vectorSpecializari = vectorSpecializari; // shallow copy
	}

	public int getCodLiceu() {
		return codLiceu;
	}

	public void setCodLiceu(int codLiceu) {
		this.codLiceu = codLiceu;
	}

	public String getNumeLiceu() {
		return numeLiceu;
	}

	public void setNumeLiceu(String numeLiceu) {
		this.numeLiceu = numeLiceu;
	}

	public int getNrSpecializari() {
		return nrSpecializari;
	}

	public void setNrSpecializari(int nrSpecializari) {
		this.nrSpecializari = nrSpecializari;
	}

	public Specializare[] getVectorSpecializari() {
		this.vectorSpecializari = new Specializare[vectorSpecializari.length];
		return vectorSpecializari;
	}

	public void setVectorSpecializari(Specializare[] vectorSpecializari) {
		if(vectorSpecializari!= null) {
			this.vectorSpecializari =new Specializare[vectorSpecializari.length];
			for(int i = 0; i< vectorSpecializari.length; i++) {
				this.vectorSpecializari[i] = vectorSpecializari[i];
			}
		}
	}

	@Override
	public String toString() {
		String message = "Liceu: codLiceu=" + codLiceu + ", numeLiceu=" + numeLiceu + ", nrSpecializari="
				+ nrSpecializari + ", vectorSpecializari= ";

		for (int i = 0; i < nrSpecializari; i++) {
			message += "(" + vectorSpecializari[i].getCodSpecializare() + ","
					+ vectorSpecializari[i].getNumarLocuriSpecializare() + ");";
		}

		return message;
	}

	public int nrTotalLocuri() {
		int nrTotal = 0;
		for (int i = 0; i < nrSpecializari; i++) {
			nrTotal += vectorSpecializari[i].getNumarLocuriSpecializare();
		}
		return nrTotal;
	}

	@Override
	public int compareTo(Liceu o) {
		if (this.nrTotalLocuri() > o.nrTotalLocuri()) {
			return 1;
		} else if (this.nrTotalLocuri() < o.nrTotalLocuri()) {
			return -1;
		} else {
			return 0;
		}
	}

}
