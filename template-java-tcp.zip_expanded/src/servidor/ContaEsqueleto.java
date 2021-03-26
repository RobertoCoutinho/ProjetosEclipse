package servidor;

import java.io.StringReader;


import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

public class ContaEsqueleto {

	BancoServente servente;

	public ContaEsqueleto() {
		servente = new BancoServente();
	}

	public void novaConta (byte [] args) {	
		Gson gson = new Gson();
		String str = new String(args, java.nio.charset.StandardCharsets.UTF_8);
		
		JsonReader reader = new JsonReader(new StringReader(str));
		reader.setLenient(true);
		Conta conta = gson.fromJson(reader, Conta.class);
		servente.novaConta(conta);
		
		// (1) Desempacota argumento de entrada
		// (2) chama o metodo do servente
		// (3) empacota resposta do método servente e retorna
	}
}