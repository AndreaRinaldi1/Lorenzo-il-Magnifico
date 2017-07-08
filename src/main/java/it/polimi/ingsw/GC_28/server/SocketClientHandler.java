package it.polimi.ingsw.GC_28.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class represents the socket implementation of the client handler (so, server-side)
 * @author andreaRinaldi
 * @version 1.0, 07/04/2017
 */
public class SocketClientHandler implements ClientHandler{
	private Socket socket;
	
	Scanner in;
	PrintWriter out;
	
	/**
	 * This constructor builds the SocketClientHandler initializing a scanner and a printwriter
	 * using the input and output streams of the given socket
	 * @param socket the socket to communicate with the client
	 */
	public SocketClientHandler(Socket socket){
		this.socket = socket;
		
		try {
			in = new Scanner(this.socket.getInputStream());
			out = new PrintWriter(this.socket.getOutputStream());
		} catch (IOException e) {
			Logger.getAnonymousLogger().log(Level.WARNING,"Cannot instantiate the scanner or the printWriter in the clientHandler" + e);
		}
		
	}

	/**
	 * @param message the message the server wants to send to the client
	 */
	@Override
	public void send(String message){
		out.println(message);
		out.flush();
	}

	/**
	 * @return the answer given by the client
	 */
	@Override
	public String receive() throws NoSuchElementException{
		return in.nextLine();
		
	}

}
