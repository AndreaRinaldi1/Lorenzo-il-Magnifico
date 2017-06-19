package it.polimi.ingsw.GC_28.server;

import java.rmi.RemoteException;
import java.rmi.server.RemoteObject;

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
			System.out.println("remote exc");
		}
	}
	
	@Override
	public String receive() {
		String answer = null;
		try{
			answer = user.receive();
		}
		catch(RemoteException e){
			System.out.println("remote exc");
		}
		return answer;
	}

}
