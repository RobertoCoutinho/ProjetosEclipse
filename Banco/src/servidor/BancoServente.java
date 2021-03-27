package servidor;

import java.util.ArrayList;
import java.util.Iterator;


public class BancoServente {

	// Essa é classe que REALMENTE implementa o serviço.
	static ArrayList<Conta> contas = new ArrayList<Conta>();

	public String novaConta(Conta conta) {
		contas.add(conta);
		return "cadastrado com sucesso";
	}

	public Conta consultarConta(String numConta) {

		for (Conta conta : contas) {

			if (conta.getNumConta().equals(numConta)) {
				// System.out.println("aqui");
				return conta;
			}
		}
		return null;
	}

	public String realizarSaque(InfoSaque infoSaque) {

		for (Conta conta : contas) {
			if (conta.getNumConta().equals(infoSaque.getNumConta()) && conta.getSenha().equals(infoSaque.getSenha())) {
				conta.setSaldo(conta.getSaldo() - infoSaque.getVarlorSaque());
				return conta.getSaldo() + "";
			}
			// System.out.println("aqui");
		}
		return null;
	}
	
	public String realizarDeposito(InfoSaque infoSaque) {
		for (Conta conta : contas) {
			if (conta.getNumConta().equals(infoSaque.getNumConta()) && conta.getSenha().equals(infoSaque.getSenha())) {
				conta.setSaldo(conta.getSaldo() + infoSaque.getVarlorSaque());
				return conta.getSaldo() + "";
			}
			// System.out.println("aqui");
		}
		return null;
	}
}