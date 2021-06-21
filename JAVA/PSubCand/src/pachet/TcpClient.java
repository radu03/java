package pachet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class TcpClient {

	public static void main(String[] args) {
		//pt citirea de la consola
		Scanner sc = new Scanner(System.in);
		
		try(Socket socket = new Socket("localhost", 7777)) {
			//trimitere cod candidat catre server
			int codCandidat = sc.nextInt(); //daca nu avem citirea de la consola dam un cod (ex 1)
			OutputStream outputStream = socket.getOutputStream();
			DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
			dataOutputStream.writeInt(codCandidat);
			
			//primire raspuns cu nr optiuni
			InputStream inputStream = socket.getInputStream();
			DataInputStream dataInputStream = new DataInputStream(inputStream);
			int messageNrOptiuni = dataInputStream.readInt();
			System.out.println(messageNrOptiuni);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		sc.close(); //inchidem scanner ul 
	}

}
