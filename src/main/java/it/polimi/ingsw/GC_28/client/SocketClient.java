package it.polimi.ingsw.GC_28.client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketClient implements Client{
	private int port;
	
	public SocketClient(int port) {
		this.port = port;
	}

	@Override
	public void startClient() throws IOException{
		Socket socket = new Socket(getLocalAddress(), port);
		System.out.println("Connection Estabilished!");
		new Thread(new ClientListener(socket)).start();
		new Thread(new ClientWriter(socket)).start();
			
	}
	
	
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
