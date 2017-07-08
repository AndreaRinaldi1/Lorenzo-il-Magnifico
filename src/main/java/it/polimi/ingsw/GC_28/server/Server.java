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

/**
 * This class represents the server and is responsible for the initialization of the connections of the clients
 * and for preparing and starting the actual game with the connected clients.
 * @author andreaRinaldi, nicoloScipione
 * @version 1.0, 07/04/2017
 */
public class Server extends UnicastRemoteObject implements ServerInt{

	private static final long serialVersionUID = 1L;
	private static final int MIN_SIZE = 2;
	private static final int MAX_SIZE = 2;
	private static final int PORT = 1337;
	private int waitTime;
	private transient ServerSocket serverSocket;
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

	/**
	 * In this method the RMI registry is created and published. 
	 * @param args
	 * @throws RemoteException
	 * @throws AlreadyBoundException
	 */
	public static void main(String[] args) throws RemoteException, AlreadyBoundException {
		LocateRegistry.createRegistry(8080);
        Registry reg = LocateRegistry.getRegistry(8080);
        Server server = new Server();
        reg.bind("rmiServer", server);
            
		try{
			server.startServer();
		}catch(IOException e){
			Logger.getAnonymousLogger().log(Level.SEVERE,"Cannot start the server" + e); 
			
		}
	}

	/**
	 * This method accepts the incoming socket connection requests, controls if the maximum number of players has been reached, 
	 * or if the timer expired; if so, the game starts, else it continues accepting socket connection requests.
	 * @throws IOException
	 */
	public void startServer() throws IOException{
		executor = Executors.newCachedThreadPool();
		serverSocket = new ServerSocket(PORT);
		bonusList = initBonusTile(); 
		waitTime = setWaitTime(0)*1000;
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
				@Override
				public void run(){
					startGame(copy);
				}
			}.start();
		}
	}
	

	/**
	 * This method checks if the minimum number of players to start the game has been reached and, if so, it schedules a timer.
	 */
	private void checkNumOfPlayers(){
		if(handlers.size() == MIN_SIZE){
			timer = new Timer();
			timer.schedule(new TimerTask(){
				@Override
				public void run() {
					if(!started){
						Map<Player, ClientHandler> copy = createHandlerCopy(handlers);
						handlers = new HashMap<>();
						new Thread(){
							@Override
							public void run(){
								startGame(copy);
							}
						}.start();
					}
				}
			}, waitTime);
		}
	}
	
	/**
	 * This method creates a copy of the map containing players and the respective client handlers, 
	 * so that we don't share the original map.
	 * @param original the original map
	 * @return a copy of the original map
	 */
	private Map<Player, ClientHandler> createHandlerCopy(Map<Player, ClientHandler> original){
		Map<Player, ClientHandler> handlersCopy = new HashMap<>();
		for(Player p : original.keySet()){
			handlersCopy.put(p, original.get(p));
		}
		return handlersCopy;
	}


	/**
	 * This method enables the RMI client to connect to the server and be correctly initialized
	 * @param cli the interface of the RMI client visible to the server
	 * @throws RemoteException
	 */
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

	/**
	 * This method prepares the actual game (i.e. towers, spaces, players, playerboards...) and starts it.
	 * @param handler the map containing the players and the respective client handlers
	 */
	public void startGame(Map<Player, ClientHandler> handler){
		started = true;
		usedColors.clear();
		BoardsInitializer bi = new BoardsInitializer();	
		List<Player> players = new ArrayList<>(handler.keySet());
		List<BonusTile> tileInstance = new ArrayList<>();
		
		try{
			for(int i = 0; i < bonusList.size(); i++){
				tileInstance.add(bonusList.get(i));
			}
			GameView game = bi.initializeBoard(players);

			game.setHandlers(handler);
			int moveTimer = setWaitTime(1);
			GameManager gameManager = new GameManager(moveTimer);
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
	
	/**
	 * @param ch the client handler of the player we want to set the name
	 * @return the name chosen by the client
	 */
	public String enterName(ClientHandler ch){
		ch.send("Enter your name: ");
		return ch.receive();
	}
	
	/**
	 * @param ch the client handler of the player we want to set the color
	 * @return the color chosen by the player
	 */
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
	
	/**
	 * This method allows the player to choose the bonus tile they prefer (if available)
	 * @param bonusList the list of available bonus tiles 
	 * @param handlers the map containing the players and the respective client handlers
	 * @param p the player we are interacting with 
	 * @throws RemoteException
	 */
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
	
	/**
	 * This method reads the json file containing all the personalized bonus tiles
	 * @return a list of the bonus tiles
	 * @throws FileNotFoundException
	 */
	public List<BonusTile> initBonusTile() throws FileNotFoundException{
		Gson gson = new GsonBuilder().create();
		Type bonusTileListType = new TypeToken<ArrayList<BonusTile>>() {}.getType();
		JsonReader reader = new JsonReader(new FileReader("bonusTile2.json"));
		List<BonusTile> bonusTileList = gson.fromJson(reader, bonusTileListType);
		return bonusTileList;
	}
	
	
	/*
	 * SetWaitTime take the position of the timer in the array list: the first one (position 0) is 
	 * the timer for the server, the second one(position 1) is the timer for the player move,
	 * used in GameManager class.
	 * Both integer in the timer.json file represent the time in seconds, so they both need to be multiply
	 * by a 1000 factor to be used in milliseconds representation. 
	 */
	/**
	 * This method reads the json file containing the two timer (one for the connection time limit once the minimum number of
	 * players is reached, and the other for the maximum time for the player move)
	 * @param position the reference to the specific timer
	 * @return the timer in milliseconds
	 * @throws FileNotFoundException
	 */
	private int setWaitTime(int position) throws FileNotFoundException{
		Gson gson = new GsonBuilder().create();
		Type readType = new TypeToken<ArrayList<Integer>>() {}.getType();
		JsonReader reader = new JsonReader( new FileReader("timer.json"));
		List<Integer> waitTimeList = gson.fromJson(reader,readType);
		return waitTimeList.get(position);
	}

}
