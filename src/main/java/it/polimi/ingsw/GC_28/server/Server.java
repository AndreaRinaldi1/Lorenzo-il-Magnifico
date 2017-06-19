package it.polimi.ingsw.GC_28.server;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.io.PrintStream;
import java.lang.reflect.Type;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.omg.CORBA.TIMEOUT;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import it.polimi.ingsw.GC_28.boards.BonusTile;
import it.polimi.ingsw.GC_28.effects.HarvestEffect;
import it.polimi.ingsw.GC_28.effects.ProductionEffect;
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
	List<BonusTile> bonusList;
	
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
	
	public void startServer()throws IOException{
		Timer timer = new Timer();

		executor = Executors.newCachedThreadPool();
		server = new ServerSocket(port);
		bonusList = initBonusTile(); 
		//server.setReuseAddress(true);
		while(!noStop){
			
			handlers = new HashMap<>();
			started = false;
			
			System.out.println("Server ready");
			while(handlers.size() < 4){
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
							if(!started){
								Map<Player, ClientHandler> handlers2 = new HashMap<>();
								for(Player p : handlers.keySet()){
									handlers2.put(p, handlers.get(p));
								}
								new Thread(){
									public void run(){
										startGame(handlers);
									}
								}.start();
							}
						}
					}, 15000);
				}
			}

			Map<Player, ClientHandler> handlers2 = new HashMap<>();
			for(Player p : handlers.keySet()){
				handlers2.put(p, handlers.get(p));
			}
			/*
			 * QUESTE DUE RIGHE ERANO DA ANDREA
			timer.cancel();
			startGame(handlers);	*/
			timer.cancel();
			new Thread(){
				public void run(){
					startGame(handlers2);
				}
			}.start();
			
			/*game.setHandlers(handlers);
			BoardSetup bs = new BoardSetup(game);
			bs.firstSetUpCards();
			
			
			game.registerObserver(new Controller());
			game.getGameModel().registerObserver(game);
			//game.getGameBoard().display();
			//game.setCurrentPlayer(gameModel.getPlayers().get(0));
			//game.getCurrentPlayer().getBoard().display();
			executor.submit(game);*/
		}
	}
	

	
	public void startGame(Map<Player, ClientHandler> handlers){
		started = true;
		usedColors.clear();
		BoardsInitializer bi = new BoardsInitializer();	
		List<Player> players = new ArrayList<>(handlers.keySet());
		List<BonusTile> tileInstance = new ArrayList<BonusTile>();
		try{
			for(int i = 0; i < bonusList.size(); i++){
				tileInstance.add(bonusList.get(i));
			}
			Game game = bi.initializeBoard(players);
			game.setHandlers(handlers);
			BoardSetup bs = new BoardSetup(game);
			bs.firstSetUpCards();

			List<Player> reversePlayer = new ArrayList<>();
			for(Player p : players){
				reversePlayer.add(p);
			}

			Collections.reverse(reversePlayer);
			for(Player p : reversePlayer){
				enterBonusTile(tileInstance, handlers, p);
			}
			
			
			game.registerObserver(new Controller());
			game.getGameModel().registerObserver(game);
			//game.getGameBoard().display();
			//game.setCurrentPlayer(gameModel.getPlayers().get(0));
			//game.getCurrentPlayer().getBoard().display();
			
			executor.submit(game);
			
		}catch(FileNotFoundException e){
			for(Player p : players){
				handlers.get(p).getOut().println("The server didn't start the game due to an FileNotFoundException,\n"
						+ " we're trying to fix the problem asap. Thanks for your patience!");
				handlers.get(p).getOut().flush();
			}
			Logger.getAnonymousLogger().log(Level.SEVERE,"Cannot start the server" + e);
		}catch(IOException e){
			for(Player p : players){
				handlers.get(p).getOut().println("The server didn't start the game due to an IOException,\n"
						+ " we're trying to fix the problem asap. Thanks for your patience!");
				handlers.get(p).getOut().flush();
			}
			Logger.getAnonymousLogger().log(Level.SEVERE,"Cannot start the server" + e);
		}
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
	
	private void enterBonusTile(List<BonusTile> bonusList,Map<Player,ClientHandler> handlers, Player p){
		boolean found = false;
		do{
			int i = 1;
			for(BonusTile bt : bonusList){
				handlers.get(p).getOut().println("bonusTile n°: "+ i+ "\n");
				handlers.get(p).getOut().println(bt.toString());
				i++;
			}
			handlers.get(p).getOut().println("Choose from above your personal bonusTile: [input number from 1 to " + bonusList.size()+ "]");
			handlers.get(p).getOut().flush();
			Integer bonusTile = handlers.get(p).getIn().nextInt();
			handlers.get(p).getIn().nextLine();
			if(0 < bonusTile && bonusTile <= bonusList.size()){
				BonusTile bt = bonusList.get(bonusTile-1);
				System.out.println(bt.toString());
				p.getBoard().setBonusTile(bt);
				bonusList.remove(bt);
				found = true;
			}else{
				handlers.get(p).getOut().println("Not valid input!");
			}
		}while(!found);
	}
	
	public List<BonusTile> initBonusTile() throws FileNotFoundException{
		Gson gson = new GsonBuilder().create();
		Type bonusTileListType = new TypeToken<ArrayList<BonusTile>>() {}.getType();
		JsonReader reader = new JsonReader(new FileReader("bonusTile2.json"));
		List<BonusTile> bonusList = gson.fromJson(reader, bonusTileListType);
		return bonusList;
	}
	
}



/*class AcceptPlayer implements Runnable{
	private ServerSocket server;
	private Map<Player,ClientHandler> handlers;
	private PrintStream p;
	private Scanner scan;
	//List<PlayerColor> usedColors = new ArrayList<>();
	private Socket s;
	
	public AcceptPlayer(ServerSocket server, Map<Player,ClientHandler> handlers, Socket s){
		this.server = server;
		this.handlers = handlers;
		this.s = s;
	}
	@Override
	public void run(){
		/*while(handlers.size() < 4){
			try{
			Socket socket = server.accept();
			p = new PrintStream(socket.getOutputStream());
			scan = new Scanner(socket.getInputStream());
			p.println("Enter your name:");
			p.flush();
			String name = scan.nextLine();
			PlayerColor color = enterColor();
			Player player = new Player(name, color);
			ClientHandler ch = new ClientHandler(socket);
			handlers.put(player, ch);
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		usedColors.clear();
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("handlers size :"+handlers.size());
		while(handlers.size() < 4){
		Socket socket;
		try{
		if(handlers.size() == 1){
			socket = s;
		}
		else{
			socket = server.accept();
		}
		p = new PrintStream(socket.getOutputStream());
		scan = new Scanner(socket.getInputStream());
		p.println("Enter your name:");
		p.flush();
		String name = scan.nextLine();
		PlayerColor color = enterColor();
		Player player = new Player(name, color);
		ClientHandler ch = new ClientHandler(socket);
		handlers.put(player, ch);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	usedColors.clear();
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
	
}*/

/*while(handlers.size() < 4){
Socket socket = server.accept();
p = new PrintStream(socket.getOutputStream());
scan = new Scanner(socket.getInputStream());
p.println("Enter your name:");
p.flush();
String name = scan.nextLine();
PlayerColor color = enterColor();
Player player = new Player(name, color);
ClientHandler ch = new ClientHandler(socket);
handlers.put(player, ch);
}*/


/*Socket socket = server.accept();
p = new PrintStream(socket.getOutputStream());
scan = new Scanner(socket.getInputStream());
p.println("Enter your name:");
p.flush();
String name = scan.nextLine();
PlayerColor color = enterColor();
Player player = new Player(name, color);
ClientHandler ch = new ClientHandler(socket);
handlers.put(player, ch);
System.out.println("qua");
Socket socket2 = server.accept();
System.out.println(socket);
System.out.println(socket2);
System.out.println("ot");*/











