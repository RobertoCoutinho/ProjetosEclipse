package cliente;

public class Conta {

	protected String numConta;
	protected String nomeCliente;
	protected String cpf;
	protected String tipoConta; // tipo de conta
	protected String senha;
	protected float saldo;

	public Conta() { // construtor
		saldo = 0;
	}

	public void setNumConta(String numConta) {
		this.numConta = numConta;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public void setTipoConta(String tipoConta) {
		this.tipoConta = tipoConta;
	}

	public float getSaldo() {
		return saldo;
	}

	public void setSaldo(float saldo) {
		this.saldo = saldo;
	}

	public String getNumConta() {
		return numConta;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public String getCpf() {
		return cpf;
	}

	public String getTipoConta() {
		return tipoConta;
	}

	public String getSenha() {
		return senha;
	}
	
}
