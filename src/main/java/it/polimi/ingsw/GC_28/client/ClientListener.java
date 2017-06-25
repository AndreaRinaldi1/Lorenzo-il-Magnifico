package it.polimi.ingsw.GC_28.client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientListener implements Runnable{
	Socket socket;
	
	public ClientListener(Socket socket){
		this.socket = socket;
	}
	
	@Override
	public void run(){
		BufferedReader socketIn = null;
		String socketLine;
		try{
			PrintWriter socketOut = new PrintWriter(socket.getOutputStream());
			socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			do{
				socketLine = socketIn.readLine();
				System.out.println(socketLine);
				if("suspended".equals(socketLine)){
					socketOut.println("disconnect");
					socketOut.flush();
				}
			}while(!"close".equals(socketLine));
		}
		catch(IOException | IllegalStateException | NoSuchElementException e){
			Logger.getAnonymousLogger().log(Level.SEVERE,"Cannot receive a message" + e);
		}
		finally{
			try {
				if(socketIn != null){
					socket.close();
					socketIn.close();
				}
			} catch (IOException e) {
				Logger.getAnonymousLogger().log(Level.SEVERE, "cannot close the socket");
			}
		}
	}

}
