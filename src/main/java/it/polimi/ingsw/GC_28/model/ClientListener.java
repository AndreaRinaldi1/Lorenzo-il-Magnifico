package it.polimi.ingsw.GC_28.model;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.Future;

public class ClientListener implements Runnable{
	Socket socket;
	
	public ClientListener(Socket socket){
		this.socket = socket;
	}
	
	@Override
	public void run(){
		BufferedReader socketIn = null;
		try{
			socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while(true){
				String socketLine = socketIn.readLine();
				System.out.println(socketLine);
			}
		}
		catch(IOException e){
			System.out.println(e.getMessage());
		}
		catch(NoSuchElementException e){
			System.out.println("Client chiuso -listener-");
		}finally{
			try {
				socketIn.close();
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		}
	}

}
