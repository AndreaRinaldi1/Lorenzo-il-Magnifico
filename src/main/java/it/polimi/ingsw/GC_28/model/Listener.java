package it.polimi.ingsw.GC_28.model;

import java.io.BufferedReader;
import java.util.List;
import java.util.Map;

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
		System.out.println("dentro thread listener per player: "  + player.getName());
		boolean reconnect = false;
		while(true){
			try{
				reconnect = handler.receive().equalsIgnoreCase("reconnect");
				if(reconnect){
					handler.send("connected");
					suspended.remove(player);
					System.out.println("dentro thread listener, i player in suspended dopo reconnect di: " + player.getName());
					for(Player p : suspended){
						System.out.println(p.getName());
					}
					break;
				}
			}
			catch(IndexOutOfBoundsException e){
				continue;
			}
			
		}
	}

}
