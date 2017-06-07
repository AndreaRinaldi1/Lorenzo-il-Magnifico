package it.polimi.ingsw.GC_28.server;

import java.io.IOException;

import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.GC_28.model.BoardSetup;
import it.polimi.ingsw.GC_28.model.BoardsInitializer;
import it.polimi.ingsw.GC_28.model.Game;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.model.PlayerColor;


public class Server {
	private int port;
	private ServerSocket server;
	private PrintStream p;
	private Scanner scan;
	List<PlayerColor> usedColors = new ArrayList<>();
	boolean noStop = false;
	ExecutorService executor;
	boolean started;
	Map<Player, ClientHandler> handlers;
	
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
		Timer timer = new Timer();
		executor = Executors.newCachedThreadPool();
		server = new ServerSocket(port);
		//server.setReuseAddress(true);
		while(!noStop){
			handlers = new HashMap<>();
			started = false;
			System.out.println("Server ready");
			while(handlers.size() < 1){
				Socket socket = server.accept();
				p = new PrintStream(socket.getOutputStream());
				scan = new Scanner(socket.getInputStream());
				p.println("Enter your name:");
				p.flush();
				String name = scan.nextLine();
				PlayerColor color = enterColor();
				Player player = new Player(name, color);
				ClientHandler ch = new ClientHandler(socket);
				if(started){
					started = false;
					handlers = new HashMap<>();
				}
				handlers.put(player, ch);
				if(handlers.size() == 2){
					timer = new Timer();
					timer.schedule(new TimerTask(){
						@Override
						public void run() {
							System.out.println("passati 15 sec");
							startGame(handlers);
						}
					}, 15000);
				}
			}
			timer.cancel();
			startGame(handlers);	
		}
	}

	
	public void startGame(Map<Player, ClientHandler> handlers){
		usedColors.clear();
		BoardsInitializer bi = new BoardsInitializer();	
		List<Player> players = new ArrayList<>(handlers.keySet());
		Game game = bi.initializeBoard(players);

		game.setHandlers(handlers);
		BoardSetup bs = new BoardSetup(game);
		bs.firstSetUpCards();
		
		
		game.registerObserver(new Controller());
		game.getGameModel().registerObserver(game);
		//game.getGameBoard().display();
		//game.setCurrentPlayer(gameModel.getPlayers().get(0));
		//game.getCurrentPlayer().getBoard().display();
		started = true;
		executor.submit(game);
	}
	
	public void noStop(){
		noStop = true;
	}
	
	private PlayerColor enterColor(){
		boolean found = false;
		do{
			p.println("Enter the color you prefer: [red / blue / green / yellow] ");
			p.flush();
			String playerColor = scan.nextLine().toUpperCase();
			for(PlayerColor color : PlayerColor.values()){
				if(playerColor.equals(color.name())){
					if(!usedColors.contains(color)){
						usedColors.add(color);
						return color;
					}
					else{
						p.println("This color has already been choosed");
						found = true;
						break;
					}
				}
			}
			if(!found){
				p.println("Not valid input!");
			}
		}while(true);
	}
	
}














