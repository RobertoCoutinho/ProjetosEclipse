package cliente;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import servidor.Mensagem;

public class BancoProxy {
	int contMessageId = 0;
	int requestiId = 0;
	Gson gson = new Gson();
	// O ideal seria solicitar os dados de conexao ao cliente
	// através de um nome de domínio (ex: www.ufc.br)
	UDPClient udpClient = new UDPClient("localhost", 7896);

	public String novaConta(Conta conta) throws IOException {
		// (1) Empacota argumentos de entrada
		String msg = gson.toJson(conta); // gera um json do objeto conta
		byte[] msgEmpac = msg.toString().getBytes("utf-8"); // transforma a string do json em um byteArray
		// (2) Chama doOperation
		byte resposta[] = doOperation("Conta", "novaConta", msgEmpac);
		// (3) Desempacota argumento de resposta (retorno de doOperation)
		String respostaJson = new String(resposta, java.nio.charset.StandardCharsets.UTF_8);
		// (4) Retorna reposta desempacotada
		Object obj = new Object();
		obj = gson.fromJson(respostaJson, obj.getClass());
		return obj.toString();
	}

	public Conta consultarConta(String numConta) throws IOException {
		String msg = gson.toJson(numConta);
		byte[] msgEmpac = msg.toString().getBytes("utf-8");
		byte[] resposta = doOperation("Conta", "consultarConta", msgEmpac);

		String respostaJson = new String(resposta, java.nio.charset.StandardCharsets.UTF_8);
		// (4) Retorna reposta desempacotada
		Object obj = new Object();
		obj = gson.fromJson(respostaJson, obj.getClass());
		if (obj == null) {
			return null;
		}
		Conta conta = gson.fromJson(respostaJson, Conta.class);
		return conta;
	}

	class InfoSaque {
		String numConta, senha;
		float varlorSaque;

		public void setNumConta(String numConta) {
			this.numConta = numConta;
		}

		public void setSenha(String senha) {
			this.senha = senha;
		}

		public void setVarlorSaque(float varlorSaque) {
			this.varlorSaque = varlorSaque;
		}
	}

	public String realizarSaque(String numConta, String senha, float varlorSaque) throws IOException {

		InfoSaque infoSaque = new InfoSaque();
		infoSaque.setNumConta(numConta);
		infoSaque.setSenha(senha);
		infoSaque.setVarlorSaque(varlorSaque);
		String msgJson = gson.toJson(infoSaque);
		byte[] msgEmpac = msgJson.toString().getBytes("utf-8");
		byte[] resposta = doOperation("Conta", "realizarSaque", msgEmpac);
		String respostaJson = new String(resposta, java.nio.charset.StandardCharsets.UTF_8);
		// (4) Retorna reposta desempacotada
		Object obj = new Object();
		obj = gson.fromJson(respostaJson, obj.getClass());
		if (obj == null) {
			return null;
		}
		return obj.toString();
	}

	public String realizarDeposito(String numConta, String senha, float varlorDeposito) throws IOException {

		InfoSaque infoSaque = new InfoSaque();
		infoSaque.setNumConta(numConta);
		infoSaque.setSenha(senha);
		infoSaque.setVarlorSaque(varlorDeposito);
		String msgJson = gson.toJson(infoSaque);
		byte[] msgEmpac = msgJson.toString().getBytes("utf-8");
		byte[] resposta = doOperation("Conta", "realizarDeposito", msgEmpac);
		String respostaJson = new String(resposta, java.nio.charset.StandardCharsets.UTF_8);
		// (4) Retorna reposta desempacotada
		Object obj = new Object();
		obj = gson.fromJson(respostaJson, obj.getClass());
		if (obj == null) {
			return null;
		}
		return obj.toString();
	}

	public byte[] doOperation(String objectRef, String method, byte[] args) throws IOException {

		byte[] data = empacotaMensagem(objectRef, method, args);
		byte[] respostaCompac = null;
		Mensagem resposta = null;
		boolean flagContole = true;
		
		while(flagContole) {
			// envio
			udpClient.sendRequest(data);
			// recebimento
			try {
				respostaCompac = udpClient.getReplay();
				flagContole = false;
				resposta = desempacotaMensagem(respostaCompac);
			} catch (SocketTimeoutException e) {
				System.out.println("tempo de envio expirado");
			}
			
		}
		contMessageId++;
		return resposta.getArgs();
	}

	public void finaliza() {
		udpClient.finaliza();
	}

	private byte[] empacotaMensagem(String objectRef, String method, byte[] args) throws UnsupportedEncodingException {

		// empacota a Mensagem de requisicao
		Mensagem msg = new Mensagem();
		msg.setMessageType(0);
		msg.setRequestId(contMessageId);
		msg.setObjectRef(objectRef);
		msg.setMethod(method);
		msg.setArgs(args);
		String msgJson = gson.toJson(msg);
		byte[] msgEmpac = msgJson.toString().getBytes("utf-8");
		return msgEmpac;

	}

	private Mensagem desempacotaMensagem(byte[] resposta) {

		// desempacota a mensagem de resposta
		String str = new String(resposta, java.nio.charset.StandardCharsets.UTF_8);

		JsonReader reader = new JsonReader(new StringReader(str));
		reader.setLenient(true);
		Mensagem obj = gson.fromJson(reader, Mensagem.class);
		return obj;

	}

}