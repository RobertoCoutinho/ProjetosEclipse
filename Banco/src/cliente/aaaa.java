package cliente;

public class aaaa {

}

//E PESSOA, VAMO EXPLICAR HOJE MAIS UM CÓDIGO 

//CLASSE CONTA 
package agencia2;

	
	public void sacar(float value) {//metodo sacar
		if(this.type.equals("CP")) {//vendo se a cconta é a poupanca
			if((this.saldo-value) > 0) {//se for e o saldo - valor for mair q 0
				this.setSaldo(getSaldo() - value); //entao é feito o saque
			} 
			else {//se nao
				System.out.println("Saldo insuficiante");//nao eh feito o saque porque o saldo é menor q o esperado
			}
		}
		else if(this.type.equals("CC")) {//vendo se a conta eh a corrente
			this.setSaldo(this.getSaldo() - value);//se for, ai eh realiazado o saque
		}
	}
	
	public void depositar(float value) {//metodo depositar
		if(this.type.equals("CP")) {//vendo se a conta eh poupanca
			this.setSaldo(this.getSaldo() + value);//se for ai eh feito o o deposito
		}
		else if(this.type.equals("CC")) { //vendo se a onta eh corrente
			this.setSaldo(this.getSaldo() + value);//se for ai eh feito o deposito
		}
	}
	
	public void transferir(Conta other, float value) { 
		//metodo transferir, passando uma conta e o valor
		other.setSaldo(this.getSaldo() + value); //o valor da outra conta, somando com o valor passado pela primeira conta
	}
	
	public void atualizacaomensal() { //metodo para atualizar a conta {vulgo saldo}
		if(this.type.equals("CC")) { //vendo se a conta é a conrrente
			this.setSaldo(getSaldo() - 20); //se for, é dimunuido 20 reais do saldo atual
		} 
		else if(this.type.equals("CP")){// se a conta for a poupanca
			float novosaldo = (this.saldo * 1)/100;//eh feito a porcentagem pra saber quanto lucrou
			this.setSaldo(this.getSaldo() + novosaldo);//e adicionado ao saldo atual
		}
	}
	
	@Override
	public String toString() {//metodo toString, printar bonito lá
		return "[" + this.getId() + ":" + this.getIdCliente() + ":" + this.getSaldo() + ":" + this.getType() + "]";
	}
	//get e set

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getSaldo() {
		return saldo;
	}

	public void setSaldo(float saldo) {
		this.saldo = saldo;
	}

	public String getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}


//CLASSE CONTACORRENTE

package agencia2;

public class ContaCorrente extends Conta {
	protected float tarifamensal = 20;
	
	public ContaCorrente(int id, String idCliente) {
		super(id, idCliente);
		this.type = "CC";//defininco q a conta eh corrente
	}
	
	@Override
	public void atualizacaomensal() {//metodo para atualizar
		this.setSaldo(this.getSaldo() - tarifamensal);//do saldo atual, eh diminuido -20 {lá da linha 4}
	}
	
	//get e set
	public float getTarifamensal() {
		return tarifamensal;
	}

	public void setTarifamensal(float tarifamensal) {
		this.tarifamensal = tarifamensal;
	}
}


//CLASSE CONTAPOUPANCA
package agencia2;

public class ContaPoupanca extends Conta {
	protected float rendimento;

	public ContaPoupanca(int id, String idCliente) {
		super(id, idCliente);
		this.type = "CP"; //definindo q a conta eh poupanca
	}
	
	@Override
	public void atualizacaomensal() {//metodo de atualizacao mensal
		float novosaldo = (this.saldo * 1)/100;//eh vendo q valor vai da porque estava correndo juros
		this.setSaldo(this.getSaldo() + novosaldo);//e adicionado ao valor atual
	}
	
	//set e get

	public float getRendimento() {
		return rendimento;
	}

	public void setRendimento(float rendimento) {
		this.rendimento = rendimento;
	}
}


//CLASSE AGENCIA

package agencia2;

import java.util.HashMap;
//usando hahsmpa porque arraylist tava era me deixando ariada, no começo dava bom e no fim tava
//dando mais erro que num sei nem oq

public class Agencia {
	//iniciando os hahsmpa
	protected HashMap <String, Cliente> clientes;
	protected HashMap <Integer, Conta> contas;
	protected int nextid; //criando uma variavel para que possa criar as duas contas {poupanca e a concorrente}
	
	public Agencia() {
		//criando so hahsmap
		clientes = new HashMap<String, Cliente>();
		contas = new HashMap<Integer, Conta>();
		this.nextid = 0; //comecando do 0, porque no exercicio começa com 0
	}

	public void adicionarCliente(String id) { //metodo para adicionar cliente
		if(clientes.get(id) == null) { //vendo se indice não existe, caso não exista 
			Cliente cliente = new Cliente(id); //criando uma nova conta
			Conta corrente = new ContaCorrente(nextid++, id); //dessa conta, é criando uma conta corrente
			Conta poupanca = new ContaPoupanca(nextid++, id); //e depois uma conta poupança
			
			clientes.put(id, cliente); //eh adicionado no hashmap
			contas.put(corrente.id, corrente);//sendo assim, eh adicionado um id para cada conta, na corrente
			contas.put(poupanca.id, poupanca); //e na poupança
		}
	}
	
	public void depositar(int indice, float value) { //metodo depositar o dinheiro
		if(contas.containsKey(indice)) { //procurando a chave {vulgo indice}
			Conta conta = contas.get(indice); //se a conta for achada,
			conta.depositar(value);//eh adicionado no valor da vonta
		}
		else {//se nao
			System.out.println("A conta nao foi encontrada/naoexiste");//a conta nao existe
		}
	}
	
	public void sacar(int indice, float value) {//metodo sacar
		if(contas.containsKey(indice)) {//procurando o indice
			Conta conta = contas.get(indice);//se a conta for achada
			conta.sacar(value);//eh sacado da conta
		}
		else {//se nao
			System.out.println("a conta nao foi encontrada/nao existe");//a conta nao existe
		}
	}
	
	public void transferir(int indice1, int indice2, float value) { //meotodo trasnferir
		if(contas.containsKey(indice1) && contas.containsKey(indice2)) {//procurando o indice
			Conta c1 = contas.get(indice1); //se a conta existe
			Conta c2 = contas.get(indice2);//se a conta existe
			c1.sacar(value);//eh tirardo o valor da conta1
			c1.transferir(c2, value);// e adicionado o valor na conta 2
		}
		else {//senao
			System.out.println("A conta nao existe");//a conta nao existe
		}
	}
	
	public void atualizar() {//metodo atualizar
		for(Conta c : contas.values()) { //para cada Conta c ira peccorer o valor
			if(c.equals("CC")) {//se a conta for corrente
				ContaCorrente corrente = (ContaCorrente) contas.get(c.id);//a conta eh achada {buscada pelo indice}
				corrente.atualizacaomensal();//e eh atualizado
			} 
			else if(c.equals("CP")) {// se a conta for poupanca
				ContaPoupanca poupanca = (ContaPoupanca) contas.get(c.id);// a conta eh achada {buscando pelo indice
				poupanca.atualizacaomensal();//e eh atualizada lá
			}
		}
	}
	
	@Override
	public String toString() {//metodo para ficar bonito no console 
		String resul = "";
		for(Conta c : contas.values()) {
			resul += c + "\n";
		}
		return resul;
	}
	//get e se
	public HashMap<String, Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(HashMap<String, Cliente> clientes) {
		this.clientes = clientes;
	}

	public HashMap<Integer, Conta> getContas() {
		return contas;
	}

	public void setContas(HashMap<Integer, Conta> contas) {
		this.contas = contas;
	}

	public int getNextid() {
		return nextid;
	}

	public void setNextid(int nextid) {
		this.nextid = nextid;
	}
	//getConta, depois de ajeitar a cambiarra, deu certo
	public Conta getConta(int id) {
		return contas.get(id);
		/*ps: eu tiha feito isso mas o compilador fez o favor de da erro ai acabou que apaguei
		 * ai depois de tentar entender a iscandilice q eu fiz, resolvi tentar de novo e acabou q deu certo
		 * {ps2: ódio mortal, dor de cabeça a toa
		 */
	}
}

//CLASSE CLIENTE

package agencia2;

import java.util.HashMap; //fazendo com hashmap pq >>> que arraylist

public class Cliente {
	
	protected String id;
	protected HashMap <Integer, Conta> contas;
	
	public Cliente(String id) { //construtor
		this.id = id;
		contas = new HashMap<Integer, Conta>();
		//e por hoje é só pessoal
	}
}

//CLASSE MAIN


package agencia2;

public class Main {

	public static void main(String[] args) {
		Agencia agencia = new Agencia();
		agencia.adicionarCliente("Almir");
		agencia.adicionarCliente("Julia");
		agencia.adicionarCliente("Maria");
		System.out.println(agencia);

		agencia.getConta(0).depositar(100);
		agencia.getConta(1).depositar(200);
		agencia.getConta(2).depositar(50);
		agencia.getConta(3).depositar(300);

		agencia.getConta(3).sacar(50);
		agencia.getConta(0).sacar(70);
		agencia.getConta(1).sacar(300);
		// fail: saldo insuficiente

		System.out.println(agencia);

		Conta conta1 = agencia.getConta(3);
		Conta conta2 = agencia.getConta(5);
		if(conta1 != null && conta2 != null) {
		    conta1.transferir(conta2, 200);
		}

		conta1 = agencia.getConta(0);
		conta2 = agencia.getConta(4);
		if(conta1 != null && conta2 != null) {
		    conta1.transferir(conta2, 25);
		}

		conta1 = agencia.getConta(9);
		// fail: conta nao encontrada
		conta2 = agencia.getConta(1);
		if(conta1 != null && conta2 != null) {
		    conta1.transferir(conta2, 30);
		}

		conta1 = agencia.getConta(2);
		conta2 = agencia.getConta(8);
		// fail: conta nao encontrada
		if(conta1 != null && conta2 != null) {
		    conta1.transferir(conta2, 10);
		}

		System.out.println(agencia);

		/*for(Conta conta: agencia.getContas()) {
		    conta.atualizacaomensal();
		}*/
		System.out.println(agencia);
	}

}