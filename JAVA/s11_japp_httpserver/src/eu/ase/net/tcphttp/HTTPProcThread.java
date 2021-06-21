package eu.ase.net.tcphttp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class HTTPProcThread extends Thread {
	private static final String OutputStream = null;
	private Socket sockClient;

	public HTTPProcThread(Socket clientBrowserAtServer) {
		this.sockClient = clientBrowserAtServer;
	}

	@Override
	public void run() {
		InputStream is = null;
		java.io.OutputStream os = null;
		BufferedReader in = null;
		PrintWriter out = null;

		try {
			is = this.sockClient.getInputStream();
			in = new BufferedReader(new InputStreamReader(is));
			os = this.sockClient.getOutputStream();
			out = new PrintWriter(os, true);

			StringBuffer processLine = new StringBuffer();
			String inputLine = "";
			String outputLine = "";

			while (((inputLine = in.readLine()) != null) && (inputLine.length() > 1)) {
				processLine.append(inputLine);
			}

			System.out.println("JVM SERVER THREAD =" + this.getName() + ", processing client sock =" + this.sockClient
					+ ", having the content =" + processLine.toString());

			HTTPProto objProtocol = new HTTPProto();
			outputLine = objProtocol.processInput(processLine.toString());

			out.println(outputLine);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (out != null)
				out.close();
		}

	}

}
