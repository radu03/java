package eu.ase.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPServer {

	public static void main(String[] args)  {
		byte[] bufferRcv = null;
		byte[] bufferResp = null;
	
		try (DatagramSocket socket = new DatagramSocket(7779)){
			
			System.out.println("Server UDP is bound on 7779 port!");
			while(true) {
				bufferRcv = new byte[256];
				DatagramPacket packet = new DatagramPacket(bufferRcv, bufferRcv.length);
				try {
					socket.receive(packet);
					System.out.println("Client says: "+new String(packet.getData()));
					String respStr=new String("ACK_OK!");
					bufferResp = respStr.getBytes();
					InetAddress addReply = packet.getAddress();
					int portreply = packet.getPort(); 
					DatagramPacket packetResp = new DatagramPacket(bufferResp, bufferResp.length,addReply,portreply);
					socket.send(packetResp);
				} catch (IOException e) {
		
					e.printStackTrace();
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
}

		
		