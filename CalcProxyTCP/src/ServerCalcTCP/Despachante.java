package ServerCalcTCP;

public class Despachante {
	Esqueleto esq = new Esqueleto();
	public String invoke(String mensagem) {
		String res = null;
		String[] data = mensagem.split(">>");
    	String op = data[0];
    	if(op.equals("+")) {
    		res = esq.add(mensagem);
    	}
    	else if(op.equals("-")) {
    		res = esq.sub(mensagem);
    	}
    	else if(op.equals("*")) {
    		res = esq.mult(mensagem);
    	}
    	else if(op.equals("/")) {
    		res = esq.div(mensagem);
    	}
    	return res;
	}
}
