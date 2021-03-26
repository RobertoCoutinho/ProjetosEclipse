package servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import servidor.Mensagem;

public class UDPServer {

	static DatagramSocket aSocket = null;

	public static void main(String args[]) {
		BancoDespachante despachante = new BancoDespachante();

		try {
			aSocket = new DatagramSocket(7896);
			// create socket at agreed port
			byte[] buffer = new byte[1000];
			while (true) {
				DatagramPacket request = new DatagramPacket(buffer, buffer.length);
				aSocket.receive(request);
				Mensagem requisicao = desempacotaRequisicao(request.getData());
				despachante.selecionaEqueleto(requisicao);
				// DatagramPacket reply = new DatagramPacket(request.getData(),
				// request.getLength(), request.getAddress(),
				// request.getPort());
				// aSocket.send(reply);
			}
		} catch (SocketException e) {
			System.out.println("Socket: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO: " + e.getMessage());
		} finally {
			if (aSocket != null)
				aSocket.close();
		}

	}

	// public byte[] getRequest() {
	// enviado via moodle

	// }

	public static Mensagem desempacotaRequisicao(byte[] array) {
		// Desempacota mensagem de requisicao
		Gson gson = new Gson();
		String str = new String(array, java.nio.charset.StandardCharsets.UTF_8);
		
		JsonReader reader = new JsonReader(new StringReader(str));
		reader.setLenient(true);
		Mensagem obj = gson.fromJson(reader, Mensagem.class);
		return obj;
	}

	// public byte[] empacotaResposta(byte[] resultado, int requestId) {
	// Empacota mensagem de resposta
	// }

	// public void sendReply(byte[] resposta) {
	// enviado via moodle
	// }

	// public void run() {
	// Loop para intergair com socket client - TCP Permanente
	// while (clientSocket.isConnected()) {

	// byte[] resultado = despachante.selecionaEqueleto(requisicao);
	// sendReply(empacotaResposta(resultado, requisicao.getRequestId()));
	// }
	// try {
	// clientSocket.close();
	// } catch (IOException e) {
	// TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
}