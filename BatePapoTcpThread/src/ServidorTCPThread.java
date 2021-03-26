import java.net.*;
import java.util.Scanner;
import java.io.*;

public class ServidorTCPThread {
	public static void main(String args[]) {
		try {
			int serverPort = 7896; // the server port
			ServerSocket listenSocket = new ServerSocket(serverPort);
			while (true) {
				Socket clientSocket = listenSocket.accept();
				Connection c = new Connection(clientSocket);
			}
		} catch (IOException e) {
			System.out.println("Listen socket:" + e.getMessage());
		}
	}
}

class Connection extends Thread {
	DataInputStream in;
	DataOutputStream out;
	Socket clientSocket;

	public Connection(Socket aClientSocket) {

		clientSocket = aClientSocket;
		this.start();

	}

	public void run() {
		Scanner ler = new Scanner(System.in);
		String mensagem;
		try { // an echo server
			EnviMsg envia = new EnviMsg();
			RecebMsg recebe = new RecebMsg();
			envia.setS(clientSocket);
			recebe.setS(clientSocket);
			envia.start();
			recebe.start();
		}finally {
			try {
				clientSocket.close();
			} catch (IOException e) {
				/* close failed */}
		}

	}
}

class EnviMsg extends Thread{
	Socket s = null;
	Scanner ler = new Scanner(System.in);
	String mensagem;

	void setS(Socket s) {
		this.s = s;
	}

	public void run() {
		System.out.println("entrou envia");
		try {
			DataOutputStream out = new DataOutputStream(s.getOutputStream());
			System.out.println("Você inicia, digite sua mensagem:");
			while (true) {
				mensagem = ler.nextLine();
				out.writeUTF(mensagem); // UTF is a string encoding see Sn. 4.4
			}
		} catch (IOException e) {
			System.out.println("readline:" + e.getMessage());
		}

		finally {
			if (s != null)
				try {
					s.close();
				} catch (IOException e) {
					System.out.println("close:" + e.getMessage());
				}
		}
	}
}

class RecebMsg extends Thread{
	
	String mensagem;
	Socket s;

	void setS(Socket s) {
		this.s = s;
	}

	public void run() {
		System.out.println("entrou recebe");
		try {
			DataInputStream in = new DataInputStream(s.getInputStream());
			while (true) {
				String data = in.readUTF(); // read a line of data from the stream
				System.out.println(data);
			}
		}catch (IOException e) {
			System.out.println("readline:" + e.getMessage());
		}

		finally {
			if (s != null)
				try {
					s.close();
				} catch (IOException e) {
					System.out.println("close:" + e.getMessage());
				}
		}
	}
}