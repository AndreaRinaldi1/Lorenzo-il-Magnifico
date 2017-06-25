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
	transient Scanner input = new Scanner(System.in);
	private static final long serialVersionUID = 1L;
	ServerInt server;
	protected RMIClient() throws RemoteException {
	}

	@Override
	public void send(String message) {
		if("suspended".equals(message)){
			System.out.println("Type 'reconnect' twice to reconnect");
		}
		else if("close".equals(message)){
			System.out.println("THE END");
			try{
				server.leave(this);
			}
			catch(RemoteException e){
				Logger.getAnonymousLogger().log(Level.FINE, "Error in the game ending closing the RMI client");
			}
		}
		System.out.println(message);
	}

	@Override
	public String receive() {
		return input.nextLine();
	}

	@Override
	public void startClient() throws IOException {
		try {
			Registry reg = LocateRegistry.getRegistry(8080);
            server = (ServerInt) reg.lookup("rmiServer");
			System.out.println("Connection Estabilished!");
			server.join(this);
		} catch (RemoteException e) {
			Logger.getAnonymousLogger().log(Level.SEVERE,"Cannot start RMIClient" + e);
		} catch (NotBoundException e) {
			Logger.getAnonymousLogger().log(Level.SEVERE,"Cannot find the registry name to bind to" + e);
		}	
	}

}
