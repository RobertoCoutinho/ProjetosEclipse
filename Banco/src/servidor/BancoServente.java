package servidor;

import java.util.ArrayList;

public class BancoServente {
	
	// Essa é classe que REALMENTE implementa o serviço.
	ArrayList<Conta> contas = new ArrayList<Conta>();

	public String novaConta(Conta conta) {		
		contas.add(conta);
		return "cadastrado com sucesso";
	}
}