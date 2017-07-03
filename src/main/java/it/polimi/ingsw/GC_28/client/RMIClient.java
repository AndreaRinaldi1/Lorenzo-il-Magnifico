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

/**
 * This class represent the Client when the selected connection is RMI and it's the class the server is interacting with during the game
 * by calling its methods 
 * @author andreaRinaldi
 * @version 1.0 07/03/2017
 */
public class RMIClient extends UnicastRemoteObject implements Client, RMIClientInt, Serializable{
	transient Scanner input = new Scanner(System.in);
	private static final long serialVersionUID = 1L;
	transient ServerInt server;
	protected RMIClient() throws RemoteException {
	}

	/**
	 * This method overrides the method of RMIClientInt and it's the method that the server
	 * calls every time it needs to communicate with the client by sending messages
	 * @param message the body of the communication message the server sends to the client
	 */
	@Override
	public void send(String message) {
		if("suspended".equals(message)){
			System.out.println("Type 'reconnect' twice to reconnect");
		}
		else if("close".equals(message)){
			System.out.println("THE END");
		}
		else{
			System.out.println(message);
		}
	}

	/**
	 * This method overrides the method of RMIClientInt and it's the method that the server
	 * calls every time it needs to communicate with the client by receiving / expecting messages
	 * @return the answer the client typed on the keyboard
	 */
	@Override
	public String receive() {
		return input.nextLine();
	}

	/**
	 * This method overrides the method of Client and it's where the client connects to the server skeleton
	 */
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
