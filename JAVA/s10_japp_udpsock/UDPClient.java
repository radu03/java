package eu.ase.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPClient {

	public static void main(String[] args) {
		DatagramSocket clientSock = null;
		try {
			clientSock = new DatagramSocket();
			byte[] buf = new byte[256];
			buf[0]='H'; buf[1]='e'; buf[2]='y';
			//byte[] buf="Hello!".getBytes();
			InetAddress addr = InetAddress.getByName("127.0.0.1");
			
			DatagramPacket packet = new DatagramPacket(buf, buf.length,addr,7779);
			clientSock.send(packet);
			
			byte[] bufResp = new byte[256];
			DatagramPacket packetFromServ = new DatagramPacket(bufResp,bufResp.length);
			clientSock.receive(packetFromServ);	
			String recvFromServStr = new String(packetFromServ.getData());
			System.out.println("Server replied = "+recvFromServStr);
		}catch (SocketException e) {
			e.printStackTrace();
		}catch (IOException ioe) {
			ioe.printStackTrace();
		}finally {
			if(clientSock != null)
				clientSock.close();
		}
		

	}

}
