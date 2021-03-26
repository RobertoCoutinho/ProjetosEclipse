package UserCalc;

public class Proxy {
	TCPClient tcpClient = new TCPClient();
	
	public double add(double n1, double n2) {
		//formato da mensagem op>>n1>>n2, exemplo: +>>5>>3
		String mensagem;
		double res;
		mensagem = "+>>";
		mensagem += String.valueOf(n1);
		mensagem += ">>";
		mensagem += String.valueOf(n2);
		tcpClient.sendRequest(mensagem);
		res = Double.parseDouble(tcpClient.getResponse());
		return res;
	}
	public double sub(double n1, double n2) {
		//formato da mensagem op>>n1>>n2, exemplo: +>>5>>3
		String mensagem;
		double res;
		mensagem = "->>";
		mensagem += String.valueOf(n1);
		mensagem += ">>";
		mensagem += String.valueOf(n2);
		tcpClient.sendRequest(mensagem);
		res = Double.parseDouble(tcpClient.getResponse());
		return res;
	}
	public double mult(double n1, double n2) {
		//formato da mensagem op>>n1>>n2, exemplo: +>>5>>3
		String mensagem;
		double res;
		mensagem = "*>>";
		mensagem += String.valueOf(n1);
		mensagem += ">>";
		mensagem += String.valueOf(n2);
		tcpClient.sendRequest(mensagem);
		res = Double.parseDouble(tcpClient.getResponse());
		return res;
	}
	public double div(double n1, double n2) {
		//formato da mensagem op>>n1>>n2, exemplo: +>>5>>3
		String mensagem;
		double res;
		mensagem = "/>>";
		mensagem += String.valueOf(n1);
		mensagem += ">>";
		mensagem += String.valueOf(n2);
		tcpClient.sendRequest(mensagem);
		res = Double.parseDouble(tcpClient.getResponse());
		return res;
	}
	public void close() {
		tcpClient.close();
	}
}
