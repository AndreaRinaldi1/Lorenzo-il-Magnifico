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
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

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
	Boolean noStop = false;
	
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
	
	private void startServer()throws IOException{
		ExecutorService executor = Executors.newCachedThreadPool();
		server = new ServerSocket(port);
		//server.setReuseAddress(true);
		List<BonusTile> bonusList = initBonusTile(); 
		while(!noStop){
			Map<Player, ClientHandler> handlers = new HashMap<>();
			
			System.out.println("Server ready");
			while(handlers.size() < 2){
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
			}
			usedColors.clear();
			BoardsInitializer bi = new BoardsInitializer();	
			List<Player> players = new ArrayList<>(handlers.keySet());
			try{
				Game game = bi.initializeBoard(players);
				game.setHandlers(handlers);
				BoardSetup bs = new BoardSetup(game);
				bs.firstSetUpCards();
				for(Player p : players){
					enterBonusTile(bonusList, handlers, p);
				}
				
				game.registerObserver(new Controller());
				game.getGameModel().registerObserver(game);
				//game.getGameBoard().display();
				//game.setCurrentPlayer(gameModel.getPlayers().get(0));
				//game.getCurrentPlayer().getBoard().display();
				executor.submit(game);
			}catch(IOException e){
				for(Player p : players){
					handlers.get(p).getOut().println("The server didn't start the game due to an IOException,\n"
							+ " we're trying to fix the problem asap. Thanks for your patience!");
					handlers.get(p).getOut().flush();
				}
				Logger.getAnonymousLogger().log(Level.SEVERE,"Cannot start the server" + e);
				e.printStackTrace();
			}

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
			handlers.get(p).getOut().println("Choose from above your personal bonusTile: [1/2/3/4]");
			handlers.get(p).getOut().flush();
			Integer bonusTile = handlers.get(p).getIn().nextInt();
			handlers.get(p).getIn().nextLine();
			if(0 < bonusTile && bonusTile <= bonusList.size()){
				BonusTile bt = bonusList.get(bonusTile-1);
				System.out.println(bt.toString());
				p.getBoard().getBonusTile().setHarvestEffect(bt.getHarvestEffect());
				p.getBoard().getBonusTile().setProductionEffect(bt.getProductionEffect());
				bonusList.remove(bonusTile-1);
				found = true;
			}else{
				handlers.get(p).getOut().println("Not valid input!");
			}
		}while(!found);
	}
	
	private List<BonusTile> initBonusTile() throws FileNotFoundException{
		Gson gson = new GsonBuilder().create();
		Type bonusTileListType = new TypeToken<ArrayList<BonusTile>>() {}.getType();
		JsonReader reader = new JsonReader(new FileReader("bonusTile2.json"));
		List<BonusTile> bonusList = gson.fromJson(reader, bonusTileListType);
		return bonusList;
	}
	
}














