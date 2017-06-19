package it.polimi.ingsw.GC_28.client;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

import it.polimi.ingsw.GC_28.server.ServerInt;

public class RMIClient extends UnicastRemoteObject implements Client, RMIClientInt, Serializable{

	private static final long serialVersionUID = 1L;
	protected RMIClient() throws RemoteException {
	}

	Scanner input = new Scanner(System.in);
	@Override
	public void send(String message) {
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
            ServerInt server = (ServerInt) reg.lookup("rmiServer");
			server.join(this);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

}
