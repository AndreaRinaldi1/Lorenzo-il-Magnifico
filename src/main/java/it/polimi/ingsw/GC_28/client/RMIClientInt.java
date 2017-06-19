package it.polimi.ingsw.GC_28.client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIClientInt extends Remote{
	public void send(String message) throws RemoteException;
	public String receive()  throws RemoteException;
}
