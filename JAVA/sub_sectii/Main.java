package ro.ase.sectii;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Main {

	public static void main(String[] args) throws IOException, JSONException {

		BufferedReader reader = new BufferedReader(new FileReader("date/sectii.json"));
		String line = null;
		StringBuilder strBuilder = new StringBuilder();
		String ls = System.getProperty("line.separator");

		while ((line = reader.readLine()) != null) {
			strBuilder.append(line);
			strBuilder.append(ls);
		}
		reader.close();

		String myNodeJson = strBuilder.toString();
		JSONArray sectii = new JSONArray(myNodeJson);

		List<Sectie> listaSectii = new ArrayList<Sectie>();

		for (int i = 0; i < sectii.length(); i++) {
			JSONObject s = sectii.getJSONObject(i);
			Sectie sectie = new Sectie();
			sectie.setCodSectie(s.getInt("cod_sectie"));
			sectie.setDenumire(s.getString("denumire"));
			sectie.setNumarLocuri(s.getInt("numar_locuri"));
			listaSectii.add(sectie);
		}

		// CERINTA 1
		System.out.println("SECTII CU NR LOCURI MAI MARE DE 10: ");
		for (Sectie s : listaSectii) {
			if (s.getNumarLocuri() > 10) {
				System.out.println(s);
			}
		}

		////////
		// citire pacienti
		List<Pacient> listaPacienti = new ArrayList<Pacient>();

		FileInputStream file = new FileInputStream("date/pacienti.txt");
		InputStreamReader inputStr = new InputStreamReader(file);
		BufferedReader buffReader = new BufferedReader(inputStr);

		String linie = null;
		String delim = ",";
		while ((linie = buffReader.readLine()) != null) {
			String[] str = linie.split(delim);
			String cnp = str[0];
			String nume = str[1];
			int varsta = Integer.parseInt(str[2]);
			int codS = Integer.parseInt(str[3]);

			Pacient p = new Pacient(cnp, nume, varsta, codS);

			listaPacienti.add(p);
		}

		for (Sectie s : listaSectii) {
			//System.out.println("------------");
			//System.out.println(s);
			int varstaTotala = 0;
			int nrP = 0;
			for (Pacient p : listaPacienti) {
				if (s.getCodSectie() == p.getCodSectie()) {
					//System.out.println(p);
					varstaTotala += p.getVarsta();
					nrP++;
				}
			}
			s.nrPacienti = nrP;
			if (nrP > 0) {
				s.setVarstaM(varstaTotala / nrP);
			} else {
				s.setVarstaM(0);
			}
		}

		//CERINTA 2
		listaSectii = listaSectii.stream().sorted(Comparator.comparing(Sectie::getVarstaM).reversed())
				.collect(Collectors.toList());

		System.out.println("\nLista sectii ordonata descr dupa varsta medie pacienti:");
		for (Sectie s : listaSectii) {
			System.out.println(s.toString() + ", varsta medie:" + s.getVarstaM());
		}
		
		//CERINTA 3
		FileOutputStream fileOut = new FileOutputStream("date/jurnal.txt");
		OutputStreamWriter outStr = new OutputStreamWriter(fileOut);
		BufferedWriter writer = new BufferedWriter(outStr);
		
		for(Sectie s : listaSectii) {
			Integer cod = s.getCodSectie();
			writer.write(cod.toString()+",");
			writer.write(s.getDenumire().toString()+",");
			Integer nr = s.nrPacienti;
			writer.write(nr.toString()+",");
		
			writer.write(System.lineSeparator());
		}
		
		writer.close();
		
		//CERINTA 4
		//server
		try(ServerSocket server = new ServerSocket(7777)){
			System.out.println("Serverul a pornit");
			
			Socket socket = server.accept();
			
			//primire cod sectie de la client
			InputStream inpStr = socket.getInputStream();
			DataInputStream dataImpStr = new DataInputStream(inpStr);
			int codPrimit = dataImpStr.readInt();
			System.out.println("cod sectie primit: "+ codPrimit);
			
			//calcul nr libere
			int locuriLibere = 0;
			for(Sectie s : listaSectii) {
				if(codPrimit == s.getCodSectie()) {
					locuriLibere = s.getNumarLocuri() - s.nrPacienti;
				}
			}
			
			//trimitere raspuns cu nr locuri libere catre client
			OutputStream outStream = socket.getOutputStream();
			DataOutputStream dataOutStream = new DataOutputStream(outStream);
			dataOutStream.writeInt(locuriLibere);
		}
	}

}
