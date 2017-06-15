package it.polimi.ingsw.GC_28.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
	
	ObjectInputStream in;
	ObjectOutputStream out;
	
	public ClientHandler(ObjectOutputStream out, ObjectInputStream in){
		this.in = in;
		this.out = out;
		
		/*try {
			in = new ObjectInputStream(socket.getInputStream());//new Scanner(socket.getInputStream());
			out = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			Logger.getAnonymousLogger().log(Level.WARNING,"Cannot instantiate the scanner or the printWriter in the clientHandler" + e);
		}*/
		
	}

	public ObjectInputStream getIn() {
		return in;
	}

	public ObjectOutputStream getOut() {
		return out;
	}
	
	
	
}