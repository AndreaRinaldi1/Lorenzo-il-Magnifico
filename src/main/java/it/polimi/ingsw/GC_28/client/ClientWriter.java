package it.polimi.ingsw.GC_28.client;
import java.io.IOException;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientWriter implements Runnable{
	Socket socket;
	PrintWriter socketOut;
	 
	public ClientWriter(Socket socket){
		this.socket = socket;
	}
	
	
	@Override
	public void run(){
		Scanner stdin = null;
		try {
			socketOut = new PrintWriter(socket.getOutputStream());
			stdin = new Scanner(System.in);
			while(true){
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
				if(stdin != null){
					stdin.close();
				}
				if(socketOut != null){
					socketOut.close();
				}
			}catch (IOException e) {
				Logger.getAnonymousLogger().log(Level.SEVERE, "cannot close the socket");
			}
		}
	}
	
}