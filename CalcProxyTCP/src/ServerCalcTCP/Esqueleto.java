package ServerCalcTCP;

public class Esqueleto {
	Calc calc = new Calc();
	
	public String add(String mensagem){
		String res;
		String[] data = mensagem.split(">>");
    	double n1 = Double.parseDouble(data[1]);
    	double n2 = Double.parseDouble(data[2]);
    	res = String.valueOf(calc.add(n1, n2));
    	return res;
	}
	
	public String sub(String mensagem){
		String res;
		String[] data = mensagem.split(">>");
    	double n1 = Double.parseDouble(data[1]);
    	double n2 = Double.parseDouble(data[2]);
    	res = String.valueOf(calc.sub(n1, n2));
    	return res;
	}
	
	public String mult(String mensagem){
		String res;
		String[] data = mensagem.split(">>");
    	double n1 = Double.parseDouble(data[1]);
    	double n2 = Double.parseDouble(data[2]);
    	res = String.valueOf(calc.mult(n1, n2));
    	return res;
	}
	
	public String div(String mensagem){
		String res;
		String[] data = mensagem.split(">>");
    	double n1 = Double.parseDouble(data[1]);
    	double n2 = Double.parseDouble(data[2]);
    	res = String.valueOf(calc.div(n1, n2));
    	return res;
	}
}
