package servidor;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

public class ContaEsqueleto {

	BancoServente servente;

	public ContaEsqueleto() {
		servente = new BancoServente();
	}

	public byte[] novaConta (byte [] args) throws UnsupportedEncodingException {	
		Gson gson = new Gson();
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
}
