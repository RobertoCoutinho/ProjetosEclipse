package servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Stack;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import servidor.Mensagem;

public class UDPServer {

	static DatagramSocket aSocket = null;
	static Gson gson = new Gson();

	public static void main(String args[]) throws InterruptedException {
		BancoDespachante despachante = new BancoDespachante();
		Stack<Mensagem> historicoMsg = new Stack<Mensagem>();
		try {
			aSocket = new DatagramSocket(7896);
			// create socket at agreed port
			byte[] buffer = new byte[1000];
			while (true) {
				DatagramPacket request = new DatagramPacket(buffer, buffer.length);
				aSocket.receive(request);
				Mensagem requisicao = desempacotaRequisicao(request.getData());
				
				Thread.sleep(1500);
				
				if (historicoMsg.empty()) {
					historicoMsg.push(requisicao);
					byte[] resultado = despachante.selecionaEqueleto(requisicao);

					byte[] resultadoEmpac = empacotaResposta(resultado, requisicao.getRequestId());
					DatagramPacket reply = new DatagramPacket(resultadoEmpac, resultadoEmpac.length,
							request.getAddress(), request.getPort());
					aSocket.send(reply);
					
				} else if (historicoMsg.peek().getRequestId() != requisicao.getRequestId()) {
					historicoMsg.pop();
					byte[] resultado = despachante.selecionaEqueleto(requisicao);

					byte[] resultadoEmpac = empacotaResposta(resultado, requisicao.getRequestId());
					DatagramPacket reply = new DatagramPacket(resultadoEmpac, resultadoEmpac.length,
							request.getAddress(), request.getPort());
					aSocket.send(reply);
				}
				else if(historicoMsg.peek().getRequestId() == requisicao.getRequestId()) {
					System.out.println("mensagem duplicada");
				}
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

		String str = new String(array, java.nio.charset.StandardCharsets.UTF_8);

		JsonReader reader = new JsonReader(new StringReader(str));
		reader.setLenient(true);
		Mensagem obj = gson.fromJson(reader, Mensagem.class);
		return obj;
	}

	public static byte[] empacotaResposta(byte[] resultado, int requestId) throws UnsupportedEncodingException {
		// Empacota mensagem de resposta
		Mensagem msg = new Mensagem();
		msg.setMessageType(1);
		msg.setRequestId(requestId);
		msg.setArgs(resultado);
		String msgJson = gson.toJson(msg);
		byte[] msgEmpac = msgJson.toString().getBytes("utf-8");
		return msgEmpac;
	}

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