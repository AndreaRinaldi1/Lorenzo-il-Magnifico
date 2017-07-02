package it.polimi.ingsw.GC_28.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketClientHandler implements ClientHandler{
	private Socket socket;
	
	Scanner in;
	PrintWriter out;
	
	public SocketClientHandler(Socket socket){
		this.socket = socket;
		
		try {
			in = new Scanner(this.socket.getInputStream());
			out = new PrintWriter(this.socket.getOutputStream());
		} catch (IOException e) {
			Logger.getAnonymousLogger().log(Level.WARNING,"Cannot instantiate the scanner or the printWriter in the clientHandler" + e);
		}
		
	}

	@Override
	public void send(String message){
		out.println(message);
		out.flush();
	}

	@Override
	public String receive() {
		return in.nextLine();
		
	}

}
