package it.polimi.ingsw.GC_28.model;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class Client{
	private String ip;
	private int port;
	
	public Client(String ip, int port){
		this.ip = ip;
		this.port = port;
	}

	public static void main(String[] args) {
		
		Client client = new Client("127.0.0.1", 1337);
		try{
			client.startClient();
		}
		catch(IOException e){
			System.err.println(e.getMessage());			
		}
	}
	
	
	public void startClient() throws IOException{
		Socket socket = new Socket(ip, port);
		System.out.println("Connection Estabilished!");
		//Scanner socketIn = new Scanner(socket.getInputStream());
		//PrintWriter socketOut = new PrintWriter(socket.getOutputStream());
		//Scanner stdin = new Scanner(System.in);

		new Thread(new ClientListener(socket)).start();;
		new Thread(new ClientWriter(socket)).start();
		

	}
}
