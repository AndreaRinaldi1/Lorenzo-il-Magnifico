package it.polimi.ingsw.GC_28.serverTest;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.client.Client;
import it.polimi.ingsw.GC_28.client.ClientWriter;
import it.polimi.ingsw.GC_28.model.PlayerColor;
import it.polimi.ingsw.GC_28.server.Server;

import org.mockito.Mockito;
import org.mockito.Mockito.*;

public class ServerTest {
	
	private Client client1 = Mockito.mock(Client.class);
	private Server serverMock = Mockito.mock(Server.class);
	private Server server;
	private Socket mockSocket = Mockito.mock(Socket.class);
	private ClientWriter c1 = new ClientWriter(mockSocket);
	@Before
	public void server(){
		ThreadFactory factory = Executors.defaultThreadFactory();
		Runnable r = new Runnable() {
			
			@Override
			public void run() {
				serverMock.main(null);
			}
		};
		Runnable c = new Runnable() {
			
			@Override
			public void run() {
				client1.main(null);
				
			}
		};
		factory.newThread(r).start();
		factory.newThread(c).start();
		factory.newThread(c).start();
		//System.setIn(new ByteArrayInputStream(name.getBytes()));
		//c1.setIn(name);
	}
	
	@Test
	public void testServer(){
		//String name = "\n nick";
		//System.setIn(new ByteArrayInputStream(name.getBytes()));
		while(true){
			
		}
		/*assertEquals("ciao", );
		assertEquals(PlayerColor.BLUE, Mockito.when(server.enterColor()).thenReturn(PlayerColor.RED));
		String name = "nick";
		System.setIn(new ByteArrayInputStream(name.getBytes()));*/
	}
}
