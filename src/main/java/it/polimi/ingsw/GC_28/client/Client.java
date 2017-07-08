
package it.polimi.ingsw.GC_28.client;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This interface is the starting point for a player to enter the game. It allows to select the type of connection and 
 * therefore it launches the specific kind of client.
 * @author andreaRinaldi
 * @version 1.0 07/03/2017
 */
public interface Client{
	Scanner input = new Scanner(System.in);

	public static void main(String[] args) {
		try{
			String connection = askConnectionType();
			if("RMI".equalsIgnoreCase(connection)){
				 RMIClient client = new RMIClient();
				 client.startClient();
			}
			else if("SOC".equalsIgnoreCase(connection)){
				int port = 1337;
				SocketClient client = new SocketClient(port);
				client.startClient();
			}
		}
		catch(IOException e){
			Logger.getAnonymousLogger().log(Level.SEVERE,"Cannot start the client" + e);
		}
	}
	
	/**
	 * this method needs to be overwritten by the subclass;
	 * @throws IOException due to the scanner interaction 
	 */
	public void startClient() throws IOException;
	
	/**
	 * This method asks the client for the preferred connection (RMI or Socket)
	 * @return the player's choice
	 */
	public static String askConnectionType(){
		String connection;
		do{
			System.out.println("Do you want to play using Socket or RMI? [SOC/RMI]");
			connection = input.nextLine();
			if("SOC".equalsIgnoreCase(connection) || "RMI".equalsIgnoreCase(connection)){
				return connection;				
			}
			System.out.println("Not valid input!");
		}while(true);
	}
	

		
}
