package it.polimi.ingsw.GC_28.serverTest;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockingDetails;
import org.mockito.Mockito;

import it.polimi.ingsw.GC_28.server.Server;

public class ServerTest {
	
	private Server server; 
	
	public void initServer(){
		try {
			server = new Server();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		  
		}
		catch ( UnknownHostException uhE ){
			// deal with this
		}
		
	    return null;
	}
	
	@Before
	public void prova(){
		
	}
	
	@Test
	public void test(){
		ScheduledThreadPoolExecutor s = new ScheduledThreadPoolExecutor(3);
		Runnable st = new Runnable() {
			
			@Override
			public void run() {
				try {
					initServer();
					server.startServer();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				};
			}
		};
		Runnable a = new Runnable() {
			private PrintStream p;
			private BufferedReader b;
			private Socket sock;
			@Override
			public void run() {
				System.out.println("prova");
				try {
					sock = new Socket(getLocalAddress(), 1337);
					System.out.println("connected");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("connected");
				//String prova = "prova";
				//System.setIn(new ByteArrayInputStream(prova.getBytes()));
				try{
					b = new BufferedReader(new InputStreamReader(sock.getInputStream()));
					String socketLine = b.readLine();
					System.out.println(socketLine);
					p = new PrintStream(sock.getOutputStream());
					p.println("Peter");
					p.flush();
					String fawd = b.readLine();
					System.out.println(fawd);
					p.println("red");
					p.flush();
					String vef = b.readLine();
					System.out.println(vef);
					p.println(1);
					p.flush();
				}
				catch(IOException e){
					System.out.println(e.getMessage());
				}
				
			}
		};
		Runnable b = new Runnable() {
			private PrintStream p;
			private BufferedReader b;
			private Socket sock;
			@Override
			public void run() {
				System.out.println("prova");
				try {
					sock = new Socket(getLocalAddress(), 1337);
					System.out.println("connected");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("connected");
				//String prova = "prova";
				//System.setIn(new ByteArrayInputStream(prova.getBytes()));
				try{
					b = new BufferedReader(new InputStreamReader(sock.getInputStream()));
					String socketLine = b.readLine();
					System.out.println(socketLine);
					p = new PrintStream(sock.getOutputStream());
					p.println("peter");
					p.flush();
					String fawd = b.readLine();
					System.out.println(fawd);
					p.println("red");
					p.flush();
					String te = b.readLine();
					p.println("blue");
					p.flush();
					System.out.println(te);
					String vef = b.readLine();
					System.out.println(vef);
					p.println(1);
					p.flush();
				}
				catch(IOException e){
					System.out.println(e.getMessage());
				}
				
			}
		};
		s.execute(st);
		s.schedule(a, 2, TimeUnit.SECONDS);
		System.out.println("partito");
		s.schedule(b, 4, TimeUnit.SECONDS);
		try {
			s.awaitTermination(16, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}








