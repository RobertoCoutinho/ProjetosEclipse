package calculadora;
import java.net.*;
import java.io.*;
public class ServidorCalcTCP {
    public static void main(String args[]) {
        try {
            int serverPort = 7895; // the server port
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
        try {
            clientSocket = aClientSocket;
            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());
            this.start();
        } catch (IOException e) {
            System.out.println("Connection:" + e.getMessage());
        }
    }

    public void run() {
        try { // an echo server
        	//o servidor fica em loop para não ser preciso uma nova conexão a cada calculo.
        	while(true) {
        		String data = in.readUTF(); // read a line of data from the stream
            	//A messagem é separada por partes: operação, primeiro numero e segundo numero.
        		String[] mensagem = data.split(">");
            	int num1 = Integer.parseInt(mensagem[1]);
            	int num2 = Integer.parseInt(mensagem[2]);
            	int res = 0;
            	if(mensagem[0].equals("+")) {
            		res = num1+num2;
            	}
            	else if(mensagem[0].equals("-")) {
            		res = num1-num2;
            	}
            	else if(mensagem[0].equals("*")) {
            		res = num1*num2;
            	}
            	else if(mensagem[0].equals("/")) {
            		res = num1/num2;
            	}
            	//o resultado é enviado para o cliente.
            	out.writeUTF(Integer.toString(res));
        	}
        } catch (EOFException e) {
            System.out.println("EOF:" + e.getMessage());
        } catch (IOException e) {
            System.out.println("readline:" + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                /* close failed */}
        }

    }
}
