package servidor;

public class Mensagem {
	protected int messageType;
	protected int requestId;
	protected String objectRef;
	protected String method;
	protected byte [] args;
	
	public int getMessageType() {
		return messageType;
	}
	public void setMessageType(int messageType) {
		this.messageType = messageType;
	}
	public int getRequestId() {
		return requestId;
	}
	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}
	public String getObjectRef() {
		return objectRef;
	}
	public void setObjectRef(String objectRef) {
		this.objectRef = objectRef;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public byte[] getArgs() {
		return args;
	}
	public void setArgs(byte[] args) {
		this.args = args;
	}
	
}
