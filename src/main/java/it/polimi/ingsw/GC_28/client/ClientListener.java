package it.polimi.ingsw.GC_28.client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.Future;
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
				if(socketLine.equals("sospeso")){
					System.out.println("entro");
					socketOut.println("disconnect");
					socketOut.flush();
				}
			}while(!socketLine.equals("close"));
		}
		catch(IOException | IllegalStateException | NoSuchElementException e){
			Logger.getAnonymousLogger().log(Level.SEVERE,"Cannot receive a message" + e);
		}
		finally{
			try {
				if(socketIn != null){
					socketIn.close();
				}
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		}
	}

}
