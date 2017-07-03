package it.polimi.ingsw.GC_28.client;
import java.io.IOException;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class allows the socket client to be always able to responds to the server interrogations
 * @author andreaRinaldi
 * @version 1.0, 07/03/2017
 */
public class ClientWriter implements Runnable{
	Socket socket;
	PrintWriter socketOut;
	
	/**
	 * This constructor builds the object out of the socket with which it's connected to the server
	 * @param socket the socket with which it's connected to the server
	 */
	public ClientWriter(Socket socket){
		this.socket = socket;
	}
	
	/**
	 * this method sends to the server the player inputs from the keyboard.
	 * Finally it closes the connection.
	 */
	@Override
	public void run(){
		Scanner stdin = null;
		try {
			socketOut = new PrintWriter(socket.getOutputStream());
			stdin = new Scanner(System.in);
			while(stdin.hasNextLine()){
				String inputLine = stdin.nextLine();
				
				socketOut.println(inputLine);
				socketOut.flush();
			} 
		} 
		catch (IOException e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, "cannot start the printWriter");
		}
		finally{
			try{
				socket.close();
				if(socketOut != null){
					socketOut.close();
				}
			}catch (IOException e) {
				Logger.getAnonymousLogger().log(Level.SEVERE, "cannot close the socket");
			}
		}
	}
	
}