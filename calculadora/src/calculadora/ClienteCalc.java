package calculadora;
import java.net.*;
import java.util.Scanner;
import java.io.*;
public class ClienteCalc {
    public static void main(String[] args) {
        // arguments supply message and hostname
        Socket s = null;
        Scanner ler = new Scanner(System.in);
        String mensagem;
        try {
            int serverPort = 7895;
            s = new Socket("127.0.0.1", serverPort);
            DataInputStream in = new DataInputStream(s.getInputStream());
            DataOutputStream out = new DataOutputStream(s.getOutputStream());
            while(true){
            	//A mensagem mandada para o servidor é no formato operação>numero>numero
            	System.out.println("Escolha a operação:");
            	System.out.println("+ para soma");
            	System.out.println("- para subtração");
            	System.out.println("* para multiplicação");
            	System.out.println("/ para divisão");
            	System.out.println(". para sair");
            	mensagem = ler.next();
            	//quando o usuario digita um "." o sistema sai do loop.
            	if(mensagem.equals(".")) {
            		break;
            	}
            	System.out.println("Digite o primeiro valor:");
            	mensagem += ">".concat(ler.next());
            	System.out.println("Digite o segundo valor");
            	mensagem += ">".concat(ler.next());
            	out.writeUTF(mensagem); // UTF is a string encoding see Sn. 4.4
            	String data = in.readUTF(); // read a line of data from the stream
            	System.out.println("Resultado: " + data);
            }
        } catch (UnknownHostException e) {
            System.out.println("Socket:" + e.getMessage());
        } catch (EOFException e) {
            System.out.println("EOF:" + e.getMessage());
        } catch (IOException e) {
            System.out.println("readline:" + e.getMessage());
        } finally {
            if (s != null)
                try {
                    s.close();
                } catch (IOException e) {
                    System.out.println("close:" + e.getMessage());
                }
        }
    }
}