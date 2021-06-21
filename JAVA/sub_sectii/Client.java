package ro.ase.sectii;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

	public static void main(String[] args) {

		try (Socket socket = new Socket("localhost", 7777)) {

			int cod = 1;
			
			//trimitere cod catre server
			OutputStream outStream = socket.getOutputStream();
			DataOutputStream dataOutStream = new DataOutputStream(outStream);
			dataOutStream.writeInt(cod);
			
			//primire nr locuri libere
			InputStream inpStr = socket.getInputStream();
			DataInputStream dataImpStr = new DataInputStream(inpStr);
			int nrLocuriLibere = dataImpStr.readInt();

			System.out.println("nr locuri libere: "+ nrLocuriLibere);
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
