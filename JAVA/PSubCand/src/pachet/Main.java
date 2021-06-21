package pachet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
//import java.io.FileInputStream;
import java.io.FileOutputStream;
//import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
//import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
//import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Main {

	public static void main(String[] args) throws IOException, JSONException {

		BufferedReader reader = new BufferedReader(new FileReader("fisier.json"));

		String line = null;
		StringBuilder stringBuilder = new StringBuilder();
		String ls = System.getProperty("line.separator");

		while ((line = reader.readLine()) != null) {
			stringBuilder.append(line);
			stringBuilder.append(ls);
		}

		reader.close();

		String myNodeJson = stringBuilder.toString();
		JSONArray candidati = new JSONArray(myNodeJson);

		List<Candidat> listMain = new ArrayList<>();

		// punem intr o lista toti candidatii
		for (int i = 0; i < candidati.length(); i++) {

			JSONObject c = candidati.getJSONObject(i);

			Candidat c_for = new Candidat();
			;

			c_for.setCod(c.getInt("cod_candidat"));
			c_for.setNume(c.getString("nume_candidat"));
			c_for.setMedie((float) c.getDouble("media"));

			// vector optiuni
			JSONArray optiuni = c.getJSONArray("optiuni");
			Optiune[] vectorOptiuni = new Optiune[optiuni.length()];
			for (int j = 0; j < optiuni.length(); j++) {

				JSONObject o = optiuni.getJSONObject(j);

				Optiune o_for = new Optiune();

				o_for.cod_liceu = o.getInt("cod_liceu");
				o_for.cod_specializare = o.getInt("cod_specializare");

				vectorOptiuni[j] = o_for;
			}

			c_for.setOptiuni(vectorOptiuni);
			listMain.add(c_for);
		}

		// cerinta 1
		int nr = 0;
		for (Candidat c : listMain) {
			if (c.medie >= 9) {
				nr++;
			}
		}
		System.out.println("CERINTA 1 - nr candidati cu medie mai mare de 9 este: " + nr);

		// cerinta 2
		// citire din fisier
		Scanner input = new Scanner(new File("fisierText.txt"));
		input.useDelimiter(",|\r\n");
		List<Liceu> listaLicee = new ArrayList<>();

		while (input.hasNext()) {

			String cod = input.next();
			int codLiceu = Integer.parseInt(cod);

			String numeLiceu = input.next();

			String nrS = input.next();
			int nrSpecializari = Integer.parseInt(nrS);

			Specializare[] vectS = new Specializare[nrSpecializari];
			for (int i = 0; i < vectS.length; i++) {
				String codS = input.next();
				int codSpecializare = Integer.parseInt(codS);

				String nrL = input.next();
				int nrLocuri = Integer.parseInt(nrL);

				Specializare s = new Specializare();
				s.setCodSpecializare(codSpecializare);
				s.setNumarLocuriSpecializare(nrLocuri);

				vectS[i] = s;
			}

			Liceu l = new Liceu(codLiceu, numeLiceu, nrSpecializari, vectS);

			listaLicee.add(l);
		}

		input.close();

//		for (Liceu l : listaLicee) {
//			System.out.println(l);
//		}

		// sortare lista
		Collections.sort(listaLicee, Collections.reverseOrder());
		System.out.println("\nCERINTA 2: LISTA LICEE SORTATA DESCRESCATOR DUPA NR TOTAL DE LOCURI");
		for (Liceu l : listaLicee) {
			System.out.println(l);
		}

		// CERINTA 3

		Collections.sort(listMain, Collections.reverseOrder());

		// scriere in fisier
		FileOutputStream fileOutputStream = new FileOutputStream("candidati.txt");

		OutputStreamWriter streamWriter = new OutputStreamWriter(fileOutputStream);

		// arunca exceptie
		BufferedWriter writer = new BufferedWriter(streamWriter);
		System.out.println("\nCERINTA 3: SALVAREA IN TXT SI AFISAREA IN CONSOLA");
		for (Candidat c : listMain) {
			System.out.println(c);
			writer.write(c.toString());
			writer.write("\r\n");
		}

		writer.close();

		// alt tip de scriere in fisier
//		FileWriter fw = new FileWriter("tabel.txt");
//		for(Factura f : ListFacturi)
//	    {
//	    	fw.write(f.getDenumire()+", ");
//	    	fw.write(f.getRepartizare()+", ");
//	    	fw.write(f.getValoare()+", ");
//	    	fw.write("\n");
//	    	
//	    }
//		
//		fw.close()

		// 4. scriere candidati in bd

		Connection connection = null;
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:database.db");

			connection.setAutoCommit(false);

			// creare tabela
			createTable(connection);

			// inserare candidati
			insertValues(connection, listMain);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		
		Runnable rFirstLetter= () -> {
			
			String s = new String(returnfirstLetter(listMain));
			System.out.println(s);
			
		};
		
		Thread thread = new Thread(rFirstLetter);
		thread.start();
		
		

		// TCP SERVER
		try (ServerSocket server = new ServerSocket(7777)) {
			System.out.println("Server started! ");

			Socket socket = server.accept();

			// primirea mesajului de la client (codul unui candidat)
			InputStream inputStream = socket.getInputStream();
			DataInputStream dataInputStream = new DataInputStream(inputStream);
			int messageCod = dataInputStream.readInt();

			// cautare candidat
			int nrOptiuni = 0;
			for (Candidat c : listMain) {
				if (c.cod == messageCod) {
					nrOptiuni = c.optiuni.length;
				}
			}

			// trimitere mesaj catre client (numarul de optiuni candidat)
			OutputStream outputStream = socket.getOutputStream();
			DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
			dataOutputStream.writeInt(nrOptiuni);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void createTable(Connection connection) {
		String sqlCreate = "create table IF NOT EXISTS CANDIDATI (cod_candidat integer,nume_candidat text,medie double,numar_optiuni integer)";
		Statement statement;
		try {
			statement = connection.createStatement();
			statement.executeUpdate(sqlCreate);
			statement.close();
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void insertValues(Connection connection, List<Candidat> listaCandidati) {
		String sqlInsertParam = "INSERT INTO candidati VALUES (?,?,?,?)";

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sqlInsertParam);
			for (Candidat c : listaCandidati) {
				preparedStatement.setInt(1, c.cod);
				preparedStatement.setString(2, c.nume);
				preparedStatement.setDouble(3, c.medie);
				preparedStatement.setInt(4, c.optiuni.length);

				preparedStatement.executeUpdate();
			}
			preparedStatement.close();
			connection.commit();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	
	public static String returnfirstLetter(List<Candidat> l)
	{
		String concatenare= new String();
		for( Candidat c : l)
		{
			concatenare+=c.nume.charAt(0);
			
		}
		
		
		
		return concatenare;
	}
	

}
