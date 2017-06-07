package it.polimi.ingsw.GC_28.client;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ClientWriter implements Runnable{
	Socket socket;
	
	public ClientWriter(Socket socket){
		this.socket = socket;
	}
	
	@Override
	public void run(){
		Scanner stdin = null;
		try {
			PrintWriter socketOut = new PrintWriter(socket.getOutputStream());
			stdin = new Scanner(System.in);
			while(true){
				String inputLine = stdin.nextLine();
				socketOut.println(inputLine);
				socketOut.flush();
				if(inputLine.equals("end")){
					socket.close();
					break;
				}
			} 
		} 
		catch (IOException e) {
			System.out.println(e.getMessage());
		}	
		finally{
			try{
				socket.close();
			}catch (IOException e) {
				System.out.println(e.getMessage());			
			}
			if(stdin != null){
				stdin.close();
			}

		}
	}
}