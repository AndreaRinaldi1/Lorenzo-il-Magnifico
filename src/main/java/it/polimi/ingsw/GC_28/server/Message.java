package it.polimi.ingsw.GC_28.server;

public class Message {
	
	private String message;
	private boolean result;
	
	public Message(String s, boolean state){
		this.message = s;
		this.result = state;
	}

	public String getMessage() {
		return message;
	}

	public boolean isResult() {
		return result;
	}
	
}
