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
	// contador pra gerar erro a cada 3 execuções
	static int contFalha = 1;
	static DatagramSocket aSocket = null;
	static Gson gson = new Gson();

	public static void main(String args[]) throws InterruptedException {
		BancoDespachante despachante = new BancoDespachante();

		// o tratamento de mensagens duplicadas é feito armazenando as requisições
		// na pilha historiMsgRecebidas e as respostas na pilha historico Respostas
		// caso seja identificado que a requisição atual é igual a que esta na pilha
		// é mandado a resposta que esta na pilha de respostas, não tendo assim
		// que fazer todo o processamanto.
		Stack<Mensagem> historicoMsgRecebidas = new Stack<Mensagem>();
		Stack<DatagramPacket> historicoRespostas = new Stack<DatagramPacket>();
		try {
			aSocket = new DatagramSocket(7896);
			// create socket at agreed port
			byte[] buffer = new byte[1000];
			while (true) {
				DatagramPacket request = new DatagramPacket(buffer, buffer.length);
				aSocket.receive(request);
				Mensagem requisicao = desempacotaRequisicao(request.getData());

				

				if (historicoMsgRecebidas.empty()) {
					historicoMsgRecebidas.push(requisicao);
					byte[] resultado = despachante.selecionaEqueleto(requisicao);

					byte[] resultadoEmpac = empacotaResposta(resultado, requisicao.getRequestId());
					DatagramPacket reply = new DatagramPacket(resultadoEmpac, resultadoEmpac.length,
							request.getAddress(), request.getPort());
					historicoRespostas.push(reply);
					if (contFalha % 3 != 0) {
						aSocket.send(reply);
					}

				} else if (historicoMsgRecebidas.peek().getRequestId() != requisicao.getRequestId()) {
					historicoMsgRecebidas.pop();
					historicoMsgRecebidas.push(requisicao);
					byte[] resultado = despachante.selecionaEqueleto(requisicao);

					byte[] resultadoEmpac = empacotaResposta(resultado, requisicao.getRequestId());
					DatagramPacket reply = new DatagramPacket(resultadoEmpac, resultadoEmpac.length,
							request.getAddress(), request.getPort());
					historicoRespostas.pop();
					historicoRespostas.push(reply);
						if (contFalha % 3 != 0) {
							aSocket.send(reply);
						}
				} else if (historicoMsgRecebidas.peek().getRequestId() == requisicao.getRequestId()) {
					System.out.println("mensagem duplicada");
					
					// correção de erro notado durante apresentação
					if (contFalha % 3 != 0) {
						aSocket.send(historicoRespostas.peek());
					}
					contFalha--;
				}
				System.out.println(contFalha);
				contFalha++;
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

}