package it.polimi.ingsw.GC_28.server;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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
import it.polimi.ingsw.GC_28.components.CouncilPrivilege;
import it.polimi.ingsw.GC_28.model.BoardSetup;
import it.polimi.ingsw.GC_28.model.BoardsInitializer;
import it.polimi.ingsw.GC_28.model.Game;
import it.polimi.ingsw.GC_28.model.GameModel;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.model.PlayerColor;

public class Server {
	private int port;
	private ServerSocket server;
	private ObjectOutputStream o;
	private ObjectInputStream scan;
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
	

	/*private void startServer2() throws IOException{
		executor = Executors.newCachedThreadPool();
		server = new ServerSocket(port);
		
		System.out.println("Server ready");
		while(!noStop){
			bonusList = initBonusTile();
			handlers = new HashMap<>();
			while(handlers.size() < 1){
				Socket socket = server.accept();
				o = new ObjectOutputStream(socket.getOutputStream());
				scan = new ObjectInputStream(socket.getInputStream());
				o.writeUTF("Enter your name:");
				o.flush();
				String name = scan.readUTF();
				PlayerColor color = enterColor();
				Player player = new Player(name, color);
				ClientHandler ch = new ClientHandler(o,scan);
				handlers.put(player, ch);
			}
			startGame(handlers);
		}
	}*/
	
	private void startServer()throws IOException{	
		Timer timer = new Timer();
		executor = Executors.newCachedThreadPool();
		server = new ServerSocket(port);
		//server.setReuseAddress(true);
		while(!noStop){
			bonusList = initBonusTile(); 
			handlers = new HashMap<>();
			started = false;
			
			System.out.println("Server ready");
			while(handlers.size() < 2){
				Socket socket = server.accept();
				o = new ObjectOutputStream(socket.getOutputStream());
				scan = new ObjectInputStream(socket.getInputStream());
				o.writeUTF("Enter your name:");
				o.flush();
				String name = scan.readUTF();
				PlayerColor color = enterColor();
				Player player = new Player(name, color);
				ClientHandler ch = new ClientHandler(o,scan);
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
							try {
								startGame(handlers);
							} catch (IOException e) {
								Logger.getAnonymousLogger().log(Level.SEVERE, e.getMessage());							}
						}
					}, 15000);
				}
			}

			/*
			 * QUESTE DUE RIGHE ERANO DA ANDREA
			timer.cancel();
			startGame(handlers);	*/

			timer.cancel();
			startGame(handlers);
		}
	}
	

	
	public void startGame(Map<Player, ClientHandler> handlers) throws IOException{
		usedColors.clear();
		BoardsInitializer bi = new BoardsInitializer();	
		List<Player> players = new ArrayList<>(handlers.keySet());
		
		try{
			Game game = bi.initializeBoard(players);
			System.out.println("initboad fatto");
			game.setHandlers(handlers);
			System.
			out.println("set handlers fatto");
			BoardSetup bs = new BoardSetup(game);
			System.out.println("setup board");
			bs.firstSetUpCards();
			System.out.println("setup card");
			
			
			List<Player> reversePlayer = new ArrayList<>();
			for(Player p : players){
				reversePlayer.add(p);
				
			}
			System.out.println("created reversePlayer");
			Collections.reverse(reversePlayer);
			System.out.println("reverted players");
			for(Player p : reversePlayer){
				enterBonusTile(bonusList, handlers, p);
				handlers.get(p).getOut().writeUTF("end");
				handlers.get(p).getOut().flush();
				handlers.get(p).getOut().reset();;
			}
			System.out.println("bonusTile fatto");
			
			
			for(Player p : handlers.keySet()){
				handlers.get(p).getOut().writeObject(p);
				handlers.get(p).getOut().flush();
				//handlers.get(p).getOut().reset();
				handlers.get(p).getOut().writeObject(CouncilPrivilege.instance());
				handlers.get(p).getOut().flush();
				//handlers.get(p).getOut().reset();
				handlers.get(p).getOut().writeObject(game.getGameModel().getGameBoard());
				handlers.get(p).getOut().flush();
				handlers.get(p).getOut().reset();
			}
			
			game.registerObserver(new Controller());
			game.getGameModel().registerObserver(game);

			started = true;
			executor.submit(game);
			
		}catch(FileNotFoundException e){
			for(Player p : players){
				handlers.get(p).getOut().writeUTF("The server didn't start the game due to an FileNotFoundException,\n"
						+ " we're trying to fix the problem asap. Thanks for your patience!");
				handlers.get(p).getOut().flush();
			}
			Logger.getAnonymousLogger().log(Level.SEVERE,"Cannot start the server" + e);
		}catch(IOException e){
			for(Player p : players){
				handlers.get(p).getOut().writeUTF("The server didn't start the game due to an IOException,\n"
						+ " we're trying to fix the problem asap. Thanks for your patience!");
				handlers.get(p).getOut().flush();
			}
			Logger.getAnonymousLogger().log(Level.SEVERE,"Cannot start the server" + e);
		}
	}
	
	public void noStop(){
		noStop = true;
	}
	
	private PlayerColor enterColor() throws IOException {

		boolean found = false;
		do{
			o.writeUTF("Enter the color you prefer: [red / blue / green / yellow] ");
			o.flush();
			String playerColor = scan.readUTF().toUpperCase();
			for(PlayerColor color : PlayerColor.values()){
				if(playerColor.equals(color.name())){
					if(!usedColors.contains(color)){
						usedColors.add(color);
						return color;
					}
					else{
						o.writeUTF("This color has already been choosed");
						found = true;
						break;
					}
				}
			}
			if(!found){
				o.writeUTF("Not valid input!");
			}
		}while(true);
	}
	
	private void enterBonusTile(List<BonusTile> bonusList,Map<Player,ClientHandler> handlers, Player p) throws IOException{
		boolean found = false;
		do{
			int i = 1;
			for(BonusTile bt : bonusList){
				handlers.get(p).getOut().writeUTF(i + "\n");
				handlers.get(p).getOut().writeUTF("bonusTile n°: "+ i+ "\n");
				handlers.get(p).getOut().writeUTF(bt.toString());
				i++;
			}
			handlers.get(p).getOut().writeUTF("Choose from above your personal bonusTile: [input number from 1 to " + bonusList.size()+ "]");
			handlers.get(p).getOut().flush();
			Integer bonusTile = handlers.get(p).getIn().readInt();
			//handlers.get(p).getIn().nextLine();
			if(0 < bonusTile && bonusTile <= bonusList.size()){
				BonusTile bt = bonusList.get(bonusTile-1);
				System.out.println(bt.toString());
				p.getBoard().setBonusTile(bt);
				bonusList.remove(bt);
				found = true;
			}else{
				handlers.get(p).getOut().writeUTF("Not valid input!");
			}
		}while(!found);
		//handlers.get(p).getOut().writeUTF("Type start for play!!");
		//handlers.get(p).getOut().flush();
	}
	
	private List<BonusTile> initBonusTile() throws FileNotFoundException{
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







