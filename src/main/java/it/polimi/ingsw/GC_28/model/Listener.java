package it.polimi.ingsw.GC_28.model;

import java.util.List;

import it.polimi.ingsw.GC_28.server.ClientHandler;

public class Listener implements Runnable{
	List<Player> suspended;
	ClientHandler handler;
	Player player;
	
	public Listener(List<Player> suspended, Player player, ClientHandler handler){
		this.suspended = suspended;
		this.player = player;
		this.handler = handler;
	}
	
	@Override
	public void run(){
		boolean reconnect = false;
		while(true){
			try{
				reconnect = handler.receive().equalsIgnoreCase("reconnect");
				if(reconnect){
					handler.send("connected");
					suspended.remove(player);
					break;
				}
			}
			catch(IndexOutOfBoundsException e){
				continue;
			}
			
		}
	}

}
