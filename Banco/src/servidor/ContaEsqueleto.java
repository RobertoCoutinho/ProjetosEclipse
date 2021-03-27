package servidor;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

public class ContaEsqueleto {

	BancoServente servente;
	Gson gson = new Gson();

	public ContaEsqueleto() {
		servente = new BancoServente();
	}

	public byte[] novaConta(byte[] args) throws UnsupportedEncodingException {

		String str = new String(args, java.nio.charset.StandardCharsets.UTF_8);

		JsonReader reader = new JsonReader(new StringReader(str));
		reader.setLenient(true);
		Conta conta = gson.fromJson(reader, Conta.class);
		String result = servente.novaConta(conta);

		// (1) Desempacota argumento de entrada
		// (2) chama o metodo do servente
		String resulJson = gson.toJson(result);
		byte[] resultEmpac = resulJson.toString().getBytes("utf-8");
		// (3) empacota resposta do método servente e retorna
		return resultEmpac;
	}

	public byte[] consultarConta(byte[] args) throws UnsupportedEncodingException {

		String str = new String(args, java.nio.charset.StandardCharsets.UTF_8);
		JsonReader reader = new JsonReader(new StringReader(str));
		reader.setLenient(true);
		Object numeroConta = gson.fromJson(reader, Object.class);

		Conta result = servente.consultarConta(numeroConta.toString());

		// (1) Desempacota argumento de entrada
		// (2) chama o metodo do servente
		// (3) empacota resposta do método servente e retorna
		if (result != null) {
			String resulJson = gson.toJson(result);
			byte[] resultEmpac = resulJson.toString().getBytes("utf-8");
			return resultEmpac;
		} else {
			String resulJson = gson.toJson(null);
			byte[] resultEmpac = resulJson.toString().getBytes("utf-8");
			return resultEmpac;
		}
	}

	public byte[] realizarSaque(byte[] args) throws Exception {
		String str = new String(args, java.nio.charset.StandardCharsets.UTF_8);
		JsonReader reader = new JsonReader(new StringReader(str));
		reader.setLenient(true);
		InfoSaque infoSaque = gson.fromJson(reader, InfoSaque.class);
		String result = servente.realizarSaque(infoSaque);

		// (1) Desempacota argumento de entrada
		// (2) chama o metodo do servente
		String resulJson = gson.toJson(result);
		byte[] resultEmpac = resulJson.toString().getBytes("utf-8");
		// (3) empacota resposta do método servente e retorna
		return resultEmpac;
	}
	
	public byte[] realizarDeposito(byte[] args) throws Exception {
		String str = new String(args, java.nio.charset.StandardCharsets.UTF_8);
		JsonReader reader = new JsonReader(new StringReader(str));
		reader.setLenient(true);
		InfoSaque infoSaque = gson.fromJson(reader, InfoSaque.class);
		String result = servente.realizarDeposito(infoSaque);

		// (1) Desempacota argumento de entrada
		// (2) chama o metodo do servente
		String resulJson = gson.toJson(result);
		byte[] resultEmpac = resulJson.toString().getBytes("utf-8");
		// (3) empacota resposta do método servente e retorna
		return resultEmpac;
	}
}
