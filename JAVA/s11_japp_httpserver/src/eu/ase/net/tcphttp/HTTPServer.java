package eu.ase.net.tcphttp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HTTPServer {

	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		
		int port = Integer.parseInt(args[0]);
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("Java Web Server listens in port: "+ port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		while(true) {
			try {
				Socket clientBrowserAtServer = serverSocket.accept();
				
				HTTPProcThread objClient = new HTTPProcThread(clientBrowserAtServer);
				objClient.start();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			//if (serverSocket != null) {
			//	clientBrowserAtServer.close();
		//}
			//}
			
		}
		
		

	}

}
