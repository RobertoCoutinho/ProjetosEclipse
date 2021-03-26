import java.net.*;
import java.util.Scanner;
import java.io.*;
public class ClienteBpTCP {
    public static void main(String[] args) {
        // arguments supply message and hostname
        Socket s = null;
        Scanner ler = new Scanner(System.in);
        String mensagem;
        try {
            int serverPort = 7896;
            s = new Socket("127.0.0.1", serverPort);
            DataInputStream in = new DataInputStream(s.getInputStream());
            DataOutputStream out = new DataOutputStream(s.getOutputStream());
            System.out.println("Você inicia, digite sua mensagem:");
            while(true) {
            	mensagem = ler.nextLine();
            	out.writeUTF(mensagem); // UTF is a string encoding see Sn. 4.4
            	String data = in.readUTF(); // read a line of data from the stream
            	System.out.println(data);
            }
         } catch (UnknownHostException e) {
        		System.out.println("Socket:" + e.getMessage());
        		} catch (EOFException e) {
            	System.out.println("EOF:" + e.getMessage());
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