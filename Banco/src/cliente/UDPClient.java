package cliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class UDPClient {

	DatagramSocket aSocket = null;
	InetAddress aHost;
	int serverPort;

	public UDPClient(String serverIP, int port) {
		try {
			aSocket = new DatagramSocket();
			aHost = InetAddress.getByName(serverIP);
			serverPort = port;
		} catch (SocketException | UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void sendRequest(byte[] requisicao) {
		
		byte[] m = requisicao;
		DatagramPacket request = new DatagramPacket(m, m.length, aHost, serverPort);
		try {
			aSocket.send(request);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public byte[] getReplay() throws SocketTimeoutException {
		byte[] buffer = new byte[1000];
		DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
		try {
			aSocket.setSoTimeout(1000);
			aSocket.receive(reply);
		} catch (SocketTimeoutException e1) {
			// TODO Auto-generated catch block
			throw new SocketTimeoutException();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return buffer;
	}

	public void finaliza() {
		aSocket.close();
	}

}