package it.polimi.ingsw.GC_28.model;

import java.io.IOException;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.model.BoardsInitializer;
import it.polimi.ingsw.GC_28.model.Game;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.model.PlayerColor;


public class Server {
	private int port;
	private ServerSocket server;
	private PrintWriter p;
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
		
		List<Player> players = new ArrayList<>();
		System.out.println("Server ready");
		
		while(players.size() < 2){
			Socket socket = server.accept();
			p = new PrintWriter(socket.getOutputStream());
			scan = new Scanner(socket.getInputStream());
			p.println("Enter your name:");
			p.flush();
			String name = scan.nextLine();
			p.println("Enter your Color: [red,blue,green,yellow]");
			p.flush();
			String color = scan.nextLine().toUpperCase();
			for(PlayerColor pc : PlayerColor.values()){
				if(pc.name().equals(color)){
					Player player = new Player(name,pc);
					player.setSocket(socket);
					players.add(player);
				}	
			}
		}
		System.out.println("ciao");
		BoardsInitializer bi = new BoardsInitializer();	
		System.out.println("andrea");
		Game game = bi.initializeBoard(players);
		System.out.println("bell");
		
		BoardSetup bs = new BoardSetup(game);
		bs.firstSetUpCards();
		game.getGameBoard().display();
		game.setCurrentPlayer(game.getPlayers().get(0));
		game.getCurrentPlayer().getBoard().display();
		System.out.println(game.getPlayers().get(0).getName());
		for(FamilyMember fm : game.getCurrentPlayer().getFamilyMembers()){
			System.out.println(fm.toString());
		}
		executor.submit(game);
	}
	
}














