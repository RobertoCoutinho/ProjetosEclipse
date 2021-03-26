package UserCalc;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPClient {
		private Socket s = null;
	public TCPClient(){
		
        try {
            int serverPort = 7895;
            s = new Socket("127.0.0.1", serverPort);
        } catch (IOException e) {
            System.out.println("Socket:" + e.getMessage());
        }
	}
	
	public void sendRequest(String mensagem) {
		try {
			DataOutputStream out = new DataOutputStream(s.getOutputStream());
        	out.writeUTF(mensagem); // UTF is a string encoding see Sn. 4.4
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getResponse() {
        DataInputStream in;
        String mensagem = null;
		try {
			in = new DataInputStream(s.getInputStream());
			mensagem = in.readUTF();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return mensagem;
	}
	
	public void close() {
		try {
            s.close();
        } catch (IOException e) {
            System.out.println("close:" + e.getMessage());
        }
	}
}
