package it.polimi.ingsw.GC_28.server;

/**
 * This class represent the message that is exchanged between view, controller and model
 * containing a body and the result of the action
 * @author andreaRinaldi
 * @version 1.0, 07/04/2017
 */
public class Message {
	
	private String message;
	private boolean result;
	
	public Message(String s, boolean state){
		this.message = s;
		this.result = state;
	}

	/**
	 * @return the body of the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @return the result of the action
	 */
	public boolean isResult() {
		return result;
	}
	
}
