
package it.polimi.ingsw.GC_28.client;

import java.io.*;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.*;

public class Client{
	private String ip;
	private int port;
	
	public Client(String ip, int port){
		//this.ip = ip;
		this.port = port;
	}

	public static void main(String[] args) {
		String ip = " ";
		int port = 1337;
		Client client = new Client(ip , port);
		try{
			client.startClient();
		}
		catch(IOException e){
			System.err.println(e.getMessage());			
		}
	}
	
	
	public void startClient() throws IOException{
		Socket socket = new Socket(getLocalAddress(), port);
		System.out.println("Connection Estabilished!");
		//Scanner socketIn = new Scanner(socket.getInputStream());
		//PrintWriter socketOut = new PrintWriter(socket.getOutputStream());
		//Scanner stdin = new Scanner(System.in);

		new Thread(new ClientListener(socket)).start();
		new Thread(new ClientWriter(socket)).start();
		

	}
	
	
	
	
	 public static InetAddress getLocalAddress(){
		InetAddress addr = null;    
		try
		{
		  addr = InetAddress.getLocalHost();
		  // OK - is this the loopback addr ?
		  if ( ! addr.isLoopbackAddress() )
		  {
		    return addr;
		  }
		  // plan B - enumerate the network interfaces
		  Enumeration ifaces = NetworkInterface.getNetworkInterfaces();
		  while( ifaces.hasMoreElements() )
		  {
		    NetworkInterface netIf = (NetworkInterface)ifaces.nextElement();
		    Enumeration addrs = netIf.getInetAddresses();
		    while( addrs.hasMoreElements() )
		    {
		      addr = (InetAddress)addrs.nextElement();
		      if ( ! addr.isLoopbackAddress() )
		      {
		        return addr;
		      }
		    }
		  }
		  // nothing so far - last resort
		  return null;
		}
		catch ( UnknownHostException uhE ){
			// deal with this
		}
		catch ( SocketException sockE ){
	    // can deal?
		}
	      
	    return null;
	}
		
}
