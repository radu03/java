package pachet;

public class Candidat implements Comparable<Candidat> {
	public int cod;
	public String nume;
	public float medie;
	public Optiune[] optiuni;

	public Candidat() {

	}

	public Candidat(int cod, String nume, float medie, Optiune[] optiuni) {
		this.cod = cod;
		this.medie = cod;
		this.medie = medie;
		this.optiuni = optiuni;
	}

	public int getCod() {
		return cod;
	}

	public void setCod(int cod) {
		this.cod = cod;
	}

	public String getNume() {
		return nume;
	}

	public void setNume(String nume) {
		this.nume = nume;
	}

	public float getMedie() {
		return medie;
	}

	public void setMedie(float medie) {
		this.medie = medie;
	}

	public Optiune[] getOptiuni() {
		this.optiuni = new Optiune[optiuni.length];
		return this.optiuni;
	}

	public void setOptiuni(Optiune[] optiuni) {
		if (optiuni != null) {
			this.optiuni = new Optiune[optiuni.length];
			for (int i = 0; i < optiuni.length; i++) {
				this.optiuni[i] = optiuni[i];
			}
		}
	}

	@Override
	public String toString() {
		String message = "Candidat [cod=" + cod + ", nume=" + nume + ", medie=" + medie + ", optiuni= ";

		for (int i = 0; i < optiuni.length; i++) {
			message += "(" + optiuni[i].cod_liceu + "," + optiuni[i].cod_specializare + ");";
		}

		return message;
	}

	@Override
	public int compareTo(Candidat o) {
		if (this.optiuni.length > o.optiuni.length) {
			return 1;
		} else if (this.optiuni.length < o.optiuni.length) {
			return -1;
		} else {
			if (this.medie > o.medie) {
				return 1;
			} else if (this.medie < o.medie) {
				return -1;
			} else {
				return 0;
			}
		}
	}
}
