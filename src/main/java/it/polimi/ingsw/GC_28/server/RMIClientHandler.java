package it.polimi.ingsw.GC_28.server;

import java.rmi.RemoteException;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.GC_28.client.RMIClientInt;

/**
 * This class represents the RMI implementation of the client handler (so, server-side)
 * @author andreaRinaldi
 * @version 1.0, 07/04/2017
 */
public class RMIClientHandler implements ClientHandler {
	RMIClientInt user;
	/**
	 * This contructor initializes the user (i.e. the client stub)
	 * @param user the client interface visible by the server
	 */
	public RMIClientHandler(RMIClientInt user) {
		this.user = user;
	}
	
	/**
	 * @param message the message the server wants to send to the client
	 */
	@Override
	public void send(String message) {
		try{
			user.send(message);
		}
		catch(RemoteException e){
			Logger.getAnonymousLogger().log(Level.FINE,"Cannot send a message" + e);
		}
	}
	
	/**
	 * @return the answer given by the client
	 */
	@Override
	public String receive() {
		String answer = null;
		try{
			answer = user.receive();
		}
		catch(NoSuchElementException | IllegalStateException | RemoteException e){
			Logger.getAnonymousLogger().log(Level.FINE,"Cannot receive a message" + e);
		}
		return answer;
	}

}
