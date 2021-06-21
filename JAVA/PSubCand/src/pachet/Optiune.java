package pachet;

public class Optiune {
	int cod_liceu;
	int cod_specializare;

	public Optiune(int cod, int s) {
		cod_liceu = cod;
		cod_specializare = s;

	}

	public Optiune() {
		// TODO Auto-generated constructor stub
	}
	
	public void setCodLiceu(int cod_liceu) {
		this.cod_liceu = cod_liceu;
	}
	public int getCodLiceu() {
		return this.cod_liceu;
	}
	
}
