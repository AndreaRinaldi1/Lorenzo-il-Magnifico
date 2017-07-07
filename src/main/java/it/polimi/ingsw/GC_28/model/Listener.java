package it.polimi.ingsw.GC_28.model;

import java.util.List;

import it.polimi.ingsw.GC_28.server.ClientHandler;
/**
 * This class, that is a runnable, is invoked when a player does not take an action in the given time and so the timer expires.
 * @author andreaRinaldi
 * @version 1.0, 07/07/2017
 */
public class Listener implements Runnable{
	List<Player> suspended;
	ClientHandler handler;
	Player player;
	
	/**
	 * @param suspended the list of the currently suspended players
	 * @param player the player that has to be suspended
	 * @param handler the client handler relative to the new suspended player
	 */
	public Listener(List<Player> suspended, Player player, ClientHandler handler){
		this.suspended = suspended;
		this.player = player;
		this.handler = handler;
	}
	
	/**
	 * This method is always receiving the player input and looks if his intention is to reconnect and join the game again.
	 */
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
