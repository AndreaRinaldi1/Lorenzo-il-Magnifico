package it.polimi.ingsw.GC_28.server;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientProva {
	private int port;
	private String s;
	
	public ClientProva(int port){
		//this.s = str;
		this.port = port;
	}
	
	public static void main(String[] args) {
		ClientProva clP  = new ClientProva(1337);
		try{
			clP.start();
		}catch(IOException e ){
			Logger.getAnonymousLogger().log(Level.SEVERE, "cannot start client" + e);
		}
	}
	
	private void start() throws IOException{
		Socket client = new Socket(s,port);
		Scanner in = new Scanner(client.getInputStream());
		PrintStream out = new PrintStream(client.getOutputStream());
		Scanner str = new Scanner(System.in);
		while(true){
			String txt = in.nextLine();
			System.out.println(txt);
			txt = str.nextLine();
			out.println(txt);
			out.flush();
		}
	}
}
