package it.polimi.ingsw.GC_28.server;

public interface ClientHandler{
	public abstract void send(String message) ;
	public abstract String receive() ;
	
}
