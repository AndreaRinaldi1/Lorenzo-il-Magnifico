package it.polimi.ingsw.GC_28.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

import it.polimi.ingsw.GC_28.client.RMIClientInt;

public interface ServerInt extends Remote{
	public void join(RMIClientInt cli) throws RemoteException;
	public void leave(RMIClientInt cli) throws RemoteException;
}
