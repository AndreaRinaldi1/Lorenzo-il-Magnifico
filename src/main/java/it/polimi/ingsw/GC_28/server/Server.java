package it.polimi.ingsw.GC_28.server;

import java.io.IOException;

import java.io.PrintStream;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.GC_28.model.BoardsInitializer;
import it.polimi.ingsw.GC_28.model.Game;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.model.PlayerColor;


public class Server {
	private int port;
	private ServerSocket server;
	Socket sock = new Socket();
	private PrintStream p;
	private Scanner scan;
	
	
	public Server(int p){
		this.port = p;
	}
	
	public static void main(String[] args) {
		Server server = new Server(1337);
		try{
			server.startServer();
		}catch(IOException e){
			Logger.getAnonymousLogger().log(Level.SEVERE,"Cannot start the server" + e);
			
		}
	}
	
	private void startServer() throws IOException{
		server = new ServerSocket(port);
		ExecutorService executor = Executors.newCachedThreadPool();
		p = new PrintStream(sock.getOutputStream());
		scan = new Scanner(sock.getInputStream());
		List<Player> players = new ArrayList<>();
		System.out.println("Server ready");
		while(players.size() < 4){
			Socket socket = server.accept();
			sock = socket;
			p.println("Enter your name:");
			p.flush();
			String name = scan.nextLine();
			p.println("Enter your Color: [red,blue,green,yellow]");
			p.flush();
			String color = scan.nextLine().toUpperCase();
			for(PlayerColor pc : PlayerColor.values()){
				if(pc.name().equals(color)){
					Player player = new Player(name,pc,socket);
					players.add(player);
				}	
			}
		}
		BoardsInitializer bi = new BoardsInitializer();	
		Game game = bi.initializeBoard(players);
		executor.submit(game);
	}
	
}













