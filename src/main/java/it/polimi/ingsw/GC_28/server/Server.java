package it.polimi.ingsw.GC_28.server;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.lang.reflect.Type;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.ArrayList;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import it.polimi.ingsw.GC_28.boards.BonusTile;
import it.polimi.ingsw.GC_28.client.RMIClientInt;
import it.polimi.ingsw.GC_28.model.BoardSetup;
import it.polimi.ingsw.GC_28.model.BoardsInitializer;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.model.PlayerColor;
import it.polimi.ingsw.GC_28.view.GameManager;
import it.polimi.ingsw.GC_28.view.GameView;


public class Server extends UnicastRemoteObject implements ServerInt{

	private static final long serialVersionUID = 1L;
	private final int MIN_SIZE = 2;
	private final int MAX_SIZE = 2;
	private final int PORT = 1337;
	transient private ServerSocket serverSocket;
	transient List<PlayerColor> usedColors = new ArrayList<>();
	transient ExecutorService executor;
	transient Map<Player, ClientHandler> handlers = new HashMap<>();
	transient List<BonusTile> bonusList;
	transient Timer timer = new Timer();
	
	boolean noStop = false;
	boolean started = false;
	
	public Server() throws RemoteException {
		super();
	}
	
	public static void main(String[] args) throws RemoteException, AlreadyBoundException {
		
		LocateRegistry.createRegistry(8080);
        Registry reg = LocateRegistry.getRegistry(8080);
        Server server = new Server();
        reg.bind("rmiServer", server);
        System.out.println("ChatServer RMI up and running...");
            
		try{
			server.startServer();
		}catch(IOException e){
			Logger.getAnonymousLogger().log(Level.SEVERE,"Cannot start the server" + e); 
			
		}
	}

	public void startServer() throws IOException{

		executor = Executors.newCachedThreadPool();
		serverSocket = new ServerSocket(PORT);
		bonusList = initBonusTile(); 
		//server.setReuseAddress(true);
		while(!noStop){
			started = false;
			
			System.out.println("Server ready");

			while(handlers.size() < MAX_SIZE){
				Socket socket = serverSocket.accept();
				ClientHandler ch = new SocketClientHandler(socket);
				String name = enterName(ch);
				PlayerColor color = enterColor(ch);
				Player player = new Player(name, color);
				if(started){
					started = false;
				}
				handlers.put(player, ch);
				checkNumOfPlayers();
			}
			timer.cancel();
			Map<Player, ClientHandler> copy = createHandlerCopy(handlers);
			handlers = new HashMap<>();
			new Thread(){
				public void run(){
					startGame(copy);
				}
			}.start();
		}
	}
	

	private void checkNumOfPlayers(){
		if(handlers.size() == MIN_SIZE){
			timer = new Timer();
			timer.schedule(new TimerTask(){
				@Override
				public void run() {
					System.out.println("passati 15 sec");
					if(!started){
						Map<Player, ClientHandler> copy = createHandlerCopy(handlers);
						handlers = new HashMap<>();
						new Thread(){
							public void run(){
								startGame(copy);
							}
						}.start();
					}
				}
			}, 15000);
		}
	}
	
	private Map<Player, ClientHandler> createHandlerCopy(Map<Player, ClientHandler> original){
		Map<Player, ClientHandler> handlersCopy = new HashMap<>();
		for(Player p : original.keySet()){
			handlersCopy.put(p, original.get(p));
		}
		return handlersCopy;
	}


	@Override
	public void join(RMIClientInt cli) throws RemoteException {
		ClientHandler ch = new RMIClientHandler(cli);
		String name = enterName(ch);
		PlayerColor color = enterColor(ch);
		Player player = new Player(name, color);
		if(started){
			started = false;
		}
		handlers.put(player, ch);
		checkNumOfPlayers();
		if(handlers.size() == MAX_SIZE){
			Map<Player, ClientHandler> copy = createHandlerCopy(handlers);
			handlers = new HashMap<>();
			startGame(copy);
		}
	}

	
	public void startGame(Map<Player, ClientHandler> handler){
		started = true;
		usedColors.clear();
		BoardsInitializer bi = new BoardsInitializer();	
		List<Player> players = new ArrayList<>(handler.keySet());
		List<BonusTile> tileInstance = new ArrayList<BonusTile>();
		System.out.println("in game");
		for(Player p : handler.keySet()){
			System.out.println(p.getName());
		}
		try{
			for(int i = 0; i < bonusList.size(); i++){
				tileInstance.add(bonusList.get(i));
			}
			GameView game = bi.initializeBoard(players);

			game.setHandlers(handler);
			GameManager gameManager = new GameManager();
			gameManager.setView(game);
			BoardSetup bs = new BoardSetup(gameManager);
			bs.firstSetUpCards();

			List<Player> reversePlayer = new ArrayList<>();
			for(Player p : players){
				reversePlayer.add(p);
			}

			Collections.reverse(reversePlayer);
			for(Player p : reversePlayer){
				enterBonusTile(tileInstance, handler, p);
			}
			
			
			game.registerObserver(new Controller());
			game.getGameModel().registerObserver(game);

			executor.submit(gameManager);
		}catch(IOException e){
			for(Player p : players){
				handlers.get(p).send("The server didn't start the game due to an FileNotFoundException,\n"
						+ " we're trying to fix the problem asap. Thanks for your patience!");
			}
			Logger.getAnonymousLogger().log(Level.SEVERE,"Cannot start the server" + e);
		}
	}
	
	public void noStop(){
		noStop = true;
	}
	

	public String enterName(ClientHandler ch){
		ch.send("Enter your name: ");
		return ch.receive();
	}
	
	
	private PlayerColor enterColor(ClientHandler ch){


		boolean found = false;
		do{
			ch.send("Enter the color you prefer: [red / blue / green / yellow] ");
			String playerColor = ch.receive().toUpperCase();
			for(PlayerColor color : PlayerColor.values()){
				if(playerColor.equals(color.name())){
					if(!usedColors.contains(color)){
						usedColors.add(color);
						return color;
					}
					else{
						ch.send("This color has already been chosen");
						found = true;
						break;
					}
				}
			}
			if(!found){
				ch.send("Not valid input!");
			}
		}while(true);
	}
	
	private void enterBonusTile(List<BonusTile> bonusList,Map<Player,ClientHandler> handlers, Player p) throws RemoteException{
		boolean found = false;
		do{
			int i = 1;
			for(BonusTile bt : bonusList){
				handlers.get(p).send("bonusTile n°: "+ i+ "\n");
				handlers.get(p).send(bt.toString());
				i++;
			}
			handlers.get(p).send("Choose from above your personal bonusTile: [input number from 1 to " + bonusList.size()+ "]");
			try{
				Integer bonusTile = Integer.parseInt(handlers.get(p).receive());
				if(0 < bonusTile && bonusTile <= bonusList.size()){
					BonusTile bt = bonusList.get(bonusTile-1);
					System.out.println(bt.toString());
					p.getBoard().setBonusTile(bt);
					bonusList.remove(bt);
					found = true;
				}
				else{
					handlers.get(p).send("Not valid input!");
				}
			}
			catch(NumberFormatException e){
				handlers.get(p).send("Not valid input!");
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
