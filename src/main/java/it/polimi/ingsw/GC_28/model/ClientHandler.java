package it.polimi.ingsw.GC_28.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler extends Game implements Runnable{

	private Socket socket;
	private ArrayList<Socket> socketList;
	
	BufferedReader in;
	PrintWriter out;
	
	protected ClientHandler(Socket socket, ArrayList<Socket> socketList){
		this.socket = socket;
		this.socketList = socketList;
	}
	@Override
	public void run() {
		try{
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
			
		
		}
		catch(IOException e){
			System.err.println(e.getMessage());
		}
		
	}
	/*
	@Override
	public BufferedReader getIn() {
		return in;
	}
	
	@Override
	public void setIn(BufferedReader in) {
		this.in = in;
	}
	
	@Override
	public PrintWriter getOut() {
		return out;
	}
	
	@Override
	public void setOut(PrintWriter out) {
		this.out = out;
	}*/
	
	
	 

}
