import java.net.*;
import java.util.Scanner;
import java.io.*;

public class ClienteTCPThread {
	public static void main(String[] args) {
		// arguments supply message and hostname
		Socket s = null;
		try {
			int serverPort = 7896;
			s = new Socket("127.0.0.1", serverPort);
				EnviaMsg envia = new EnviaMsg();
				RecebeMsg recebe = new RecebeMsg();
				envia.setS(s);
				recebe.setS(s);
				envia.start();
				recebe.start();
		} catch (UnknownHostException e) {
			System.out.println("Socket:" + e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		//finally {
			//if (s != null)
				//try {
					//s.close();
				//} catch (IOException e) {
				//	System.out.println("close:" + e.getMessage());
				//}
		//}
	}
}

class EnviaMsg extends Thread {
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

class RecebeMsg extends Thread {
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
