package servidor;

import java.util.ArrayList;

public class BancoServente {
	
	// Essa é classe que REALMENTE implementa o serviço.
	ArrayList<Conta> contas = new ArrayList<Conta>();

	public void novaConta(Conta conta) {		
		contas.add(conta);
		System.out.println("conta cadastrada");
	}
}