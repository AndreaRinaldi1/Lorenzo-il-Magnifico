package it.polimi.ingsw.GC_28.server;

import java.rmi.RemoteException;
import java.rmi.server.RemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.GC_28.client.RMIClientInt;

public class RMIClientHandler implements ClientHandler {
	RMIClientInt user;
	
	public RMIClientHandler(RMIClientInt user) {
		this.user = user;
	}
	@Override
	public void send(String message) {
		try{
			user.send(message);
		}
		catch(RemoteException e){
			Logger.getAnonymousLogger().log(Level.SEVERE,"Cannot send a message" + e);

		}
	}
	
	@Override
	public String receive() {
		String answer = null;
		try{
			answer = user.receive();
		}
		catch(RemoteException e){
			Logger.getAnonymousLogger().log(Level.SEVERE,"Cannot receive a message" + e);
		}
		return answer;
	}

}
