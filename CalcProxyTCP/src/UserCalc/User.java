package UserCalc;

import java.util.Scanner;

public class User {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Proxy proxy = new Proxy();
		double n1 = 0,n2 = 0, res = 0;		
		String op;
		Scanner ler = new Scanner(System.in);
		System.out.println("Digite o primeiro numero");
		n1 = ler.nextDouble();
		System.out.println("Escolha a operação:");
    	System.out.println("+ para soma");
    	System.out.println("- para subtração");
    	System.out.println("* para multiplicação");
    	System.out.println("/ para divisão");
    	op = ler.next();
    	System.out.println("Digite o segundo numero");
    	n2 = ler.nextDouble();
    	

    	if(op.equals("+")) {
    		res = proxy.add(n1, n2);
    	}
    	else if(op.equals("-")) {
    		res = proxy.sub(n1, n2);
    	}
    	else if(op.equals("*")) {
    		res = proxy.mult(n1, n2);
    	}
    	else if(op.equals("/")) {
    		res = proxy.div(n1, n2);
    	}
    	
    	System.out.println("Resultado = "+res);
    	proxy.close();
	}

}
