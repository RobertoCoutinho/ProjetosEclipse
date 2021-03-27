package servidor;

public class InfoSaque {
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

		public String getNumConta() {
			return numConta;
		}

		public String getSenha() {
			return senha;
		}

		public float getVarlorSaque() {
			return varlorSaque;
		}
	
}
