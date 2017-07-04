package it.polimi.ingsw.GC_28.client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class represent the Client when the selected connection is Socket and it's the class the server is interacting with during the game
 * by sending messages through the socket connection
 * @author andreaRinaldi
 * @version 1.0 07/03/2017
 */
public class SocketClient implements Client{
	private int port;
	
	/**
	 * This constructor builds a Client based on socket given the port of the connection
	 * @param port the port for the connection to the server
	 */
	public SocketClient(int port) {
		this.port = port;
	}

	/**
	 * This method overrides the method of Client and it's where the client connects to the server 
	 * and where the two threads (one for always listening and one for writing) are launched
	 */
	@Override
	public void startClient() throws IOException{
		Socket socket = new Socket(getLocalAddress(), port);
		System.out.println("Connection Estabilished!");
		new Thread(new ClientListener(socket)).start();
		new Thread(new ClientWriter(socket)).start();
			
	}
	
	/**
	 * @return the loopback address
	 */
	 public static InetAddress getLocalAddress(){
		InetAddress addr = null;    
		try
		{
			addr = InetAddress.getLocalHost();
			if ( ! addr.isLoopbackAddress() )
			{
				return addr;
			}
		}
		catch ( UnknownHostException uhE ){
			Logger.getAnonymousLogger().log(Level.SEVERE, "Cannot find the loopback address");
		}	      
	    return null;
	}
}
