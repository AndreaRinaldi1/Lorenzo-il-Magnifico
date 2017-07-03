package it.polimi.ingsw.GC_28.client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class allows the socket client to be always listening to the input coming from the server
 * @author andreaRinaldi
 * @version 1.0, 07/03/2017
 */
public class ClientListener implements Runnable{
	Socket socket;
	
	/**
	 * This constructor builds the object out of the socket with which it's connected to the server
	 * @param socket the socket with which it's connected to the server
	 */
	public ClientListener(Socket socket){
		this.socket = socket;
	}
	
	/**
	 * this method is always listening and showing on display what the server writes.
	 * Finally it closes the connection and the bufferedReader.
	 */
	@Override
	public void run(){
		BufferedReader socketIn = null;
		String socketLine;
		try{
			socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			do{
				socketLine = socketIn.readLine();
				if("suspended".equals(socketLine)){
					System.out.println("Type 'reconnect' to play again");
				}
				else if("close".equals(socketLine)){
					System.out.println("THE END");
				}
				else{
					System.out.println(socketLine);
				}
			}while(!"close".equals(socketLine));
		}
		catch(IOException | IllegalStateException | NoSuchElementException e){
			Logger.getAnonymousLogger().log(Level.SEVERE,"Cannot receive a message" + e);
		}
		finally{
			try {
				if(socketIn != null){
					socket.close();
					socketIn.close();
				}
			} catch (IOException e) {
				Logger.getAnonymousLogger().log(Level.SEVERE, "cannot close the socket");
			}
		}
	}

}
