package it.polimi.ingsw.GC_28.client;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.GC_28.server.ServerInt;

public class RMIClient extends UnicastRemoteObject implements Client, RMIClientInt, Serializable{

	private static final long serialVersionUID = 1L;
	protected RMIClient() throws RemoteException {
	}

	transient Scanner input = new Scanner(System.in);
	@Override
	public void send(String message) {
		System.out.println(message);
		if(message.equals("sospeso")){
			System.out.println("rmi");
		}
		
	}

	@Override
	public String receive() {
		return input.nextLine();
	}

	@Override
	public void startClient() throws IOException {
		try {
			Registry reg = LocateRegistry.getRegistry(8080);
            ServerInt server = (ServerInt) reg.lookup("rmiServer");
			server.join(this);
		} catch (RemoteException e) {
			Logger.getAnonymousLogger().log(Level.SEVERE,"Cannor start RMIClient" + e);
		} catch (NotBoundException e) {
			Logger.getAnonymousLogger().log(Level.SEVERE,"Cannot find the registry name to bind to" + e);

		}	
	}

}
