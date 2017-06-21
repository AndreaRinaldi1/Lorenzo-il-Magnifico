package it.polimi.ingsw.GC_28.client;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public interface Client{
	
	static Scanner input = new Scanner(System.in);


	public static void main(String[] args) {
		try{
			String connection = askConnectionType();
			if(connection.equalsIgnoreCase("RMI")){
				 RMIClient client = new RMIClient();
				 client.startClient();
			}
			else if(connection.equalsIgnoreCase("SOC")){
				int port = 1337;
				SocketClient client = new SocketClient(port);
				client.startClient();
			}
		}
		catch(IOException e){
			Logger.getAnonymousLogger().log(Level.SEVERE,"Cannot start the client" + e);
		}
	}
	
	
	public void startClient() throws IOException;
	

	public static String askConnectionType(){
		String connection;
		do{
			System.out.println("Do you want to play using Socket or RMI? [SOC/RMI]");
			connection = input.nextLine();
			if(connection.equalsIgnoreCase("SOC") || connection.equalsIgnoreCase("RMI")){
				return connection;				
			}
			System.out.println("Not valid input!");
		}while(true);

	}
	

		
}
