package cliente;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.google.gson.Gson;

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
		doOperation("Conta", "novaConta", msgEmpac);
		// (3) Desempacota argumento de resposta (retorno de doOperation)
		// (4) Retorna reposta desempacotada
		return "aaa";
	}

	public String consultarConta(String numConta) throws IOException {
		String msg = gson.toJson(numConta);
		byte[] msgEmpac = msg.toString().getBytes("utf-8");
		doOperation("conta", "consultarConta", msgEmpac);
		return "aaa";
	}
	
	class InfoSaque{
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
		doOperation("conta", "realizarSaque", msgEmpac);
		return "aaa";
	}

	public byte[] doOperation(String objectRef, String method, byte[] args) throws IOException {

		byte[] data = empacotaMensagem(objectRef, method, args);

		// envio
		udpClient.sendRequest(data);

		// recebimento
		// Message resposta = desempacotaMensagem(tcpClient.getReplay());

		// return resposta.getArguments().toByteArray();
		return args;
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
		contMessageId++;
		return msgEmpac;

	}

}