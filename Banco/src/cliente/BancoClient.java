package cliente;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class BancoClient {

	BancoProxy proxy;

	public BancoClient() {
		proxy = new BancoProxy();
	}

	public int selecionaOperacao() throws IOException {

		Scanner ler = new Scanner(System.in);
		int operacao;
		operacao = ler.nextInt();
		switch (operacao) {
		case 1:
			Conta conta = new Conta();
			System.out.println("Numero da conta:");
			conta.setNumConta(ler.next());
			System.out.println("Nome do cliente:");
			conta.setNomeCliente(ler.next());
			System.out.println("CPF do ciente:");
			conta.setCpf(ler.next());
			System.out.println("Tipo da conta");
			conta.setTipoConta(ler.next());
			System.out.println("Senha da conta");
			conta.setSenha(ler.next());
			String resultado = proxy.novaConta(conta);
			System.out.println(resultado);
			break;
		case 2:
			System.out.println("Numero de conta");
			proxy.consultarConta(ler.next());
			break;

		case 3:
			System.out.println("Numero da conta");
			String numConta = ler.next();
			System.out.println("Senha");
			String senha = ler.next();
			System.out.println("Valor do saque");
			float valorSaque = ler.nextFloat();
			proxy.realizarSaque(numConta, senha, valorSaque);
			break;

		case 0:
			proxy.finaliza();
			break;

		default:
			System.out.println("Operação invalida, tente outra.");
			break;
		}
		return operacao;
	}

	public void printMenu() {
		System.out.println("\nDigite o n# da operação que deseja executar: ");
		System.out.println("1 - Abrir nova conta");
		System.out.println("2 - Consultar conta");
		System.out.println("3 - Realizar saque");
		System.out.println("0 - Sair\n");
	}

	public static void main(String[] args) {
		BancoClient bookClient = new BancoClient();
		int operacao = -1;
		do {
			bookClient.printMenu();
			try {
				operacao = bookClient.selecionaOperacao();
			} catch (IOException ex) {
				System.out.println("Escolha uma das operações pelo número");
			}
		} while (operacao != 0);
	}
}
