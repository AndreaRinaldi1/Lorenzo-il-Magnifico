package it.polimi.ingsw.GC_28.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.GC_28.model.Game;

public class ClientHandler{

	private Socket socket;
	private ArrayList<Socket> socketList;
	
	Scanner in;
	PrintWriter out;
	
	public ClientHandler(Socket socket){
		this.socket = socket;
		
		try {
			in = new Scanner(socket.getInputStream());
			out = new PrintWriter(socket.getOutputStream());
		} catch (IOException e) {
			Logger.getAnonymousLogger().log(Level.WARNING,"Cannot instantiate the scanner or the printWriter in the clientHandler" + e);
		}
		
	}

	public Scanner getIn() {
		return in;
	}

	public PrintWriter getOut() {
		return out;
	}
	
	
	
}
