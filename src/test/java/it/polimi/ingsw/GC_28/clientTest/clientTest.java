package it.polimi.ingsw.GC_28.clientTest;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import it.polimi.ingsw.GC_28.client.Client;
import it.polimi.ingsw.GC_28.server.Server;

public class clientTest {
	
	private Client client = Mockito.mock(Client.class);
	private Server server = Mockito.mock(Server.class);
	
	@Before
	public void client(){
		/*ThreadFactory tf = Executors.defaultThreadFactory();
		Runnable a = new Runnable() {
			public void run() {
				client.main(null);
			}
		};
		Runnable s = new Runnable() {
			
			@Override
			public void run() {
				server.main(null);
			}
		};
		Thread t = tf.newThread(a);
		Thread serverThread = tf.newThread(s);
		try {
			serverThread.start();
			t.join(1000);
			serverThread.interrupt();
		} catch (InterruptedException e) {
			Logger.getAnonymousLogger().log(Level.INFO,"stopped clientthread");
		}*/
		
	}
	
	@Test
	public void testClient(){
		//server.main(null);
		Client.main(null);	
		//client.startClient();
	}
	
	@Test
	public void testClient2(){
		ThreadFactory tf = Executors.defaultThreadFactory();
		Runnable a = new Runnable() {
			public void run() {
				Client.main(null);
			}
		};
		Runnable s = new Runnable() {
			
			@Override
			public void run() {
				try {
					Server.main(null);
				} catch (RemoteException | AlreadyBoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		Thread t = tf.newThread(a);
		Thread serverThread = tf.newThread(s);
		try {
			serverThread.start();
			t.join(2000);
			serverThread.interrupt();
		} catch (InterruptedException e) {
			Logger.getAnonymousLogger().log(Level.INFO,"stopped clientthread");
		}
	}
	
}
