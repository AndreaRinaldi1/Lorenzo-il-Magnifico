package it.polimi.ingsw.GC_28.modelTest;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.reflect.Type;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import it.polimi.ingsw.GC_28.boards.BonusTile;
import it.polimi.ingsw.GC_28.client.Client;
import it.polimi.ingsw.GC_28.client.SocketClient;
import it.polimi.ingsw.GC_28.model.BoardSetup;
import it.polimi.ingsw.GC_28.model.BoardsInitializer;
import it.polimi.ingsw.GC_28.model.Game;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.model.PlayerColor;
import it.polimi.ingsw.GC_28.server.ClientHandler;
import it.polimi.ingsw.GC_28.server.Controller;
import it.polimi.ingsw.GC_28.server.Server;
import it.polimi.ingsw.GC_28.server.SocketClientHandler;

public class GameTest {
	
	@Test
	public void test1(){
		ScheduledThreadPoolExecutor s = new ScheduledThreadPoolExecutor(4);
		Runnable g = new Runnable() {
			PrintStream p;
			Scanner scan;
			List<PlayerColor> usedColors = new ArrayList<>();
			ServerSocket serverSock;
			Map<Player, ClientHandler> handlers = new HashMap<>();
			List<BonusTile> bonusList;
			@Override
			public void run() {
				try {
					serverSock = new ServerSocket(1339);
					Socket socket = serverSock.accept();
					p = new PrintStream(socket.getOutputStream());
					scan = new Scanner(socket.getInputStream());
					p.println("Enter your name:");
					p.flush();
					String name = scan.nextLine();
					PlayerColor color = enterColor();
					System.out.println(color);
					Player player = new Player(name, color);
					ClientHandler ch = new SocketClientHandler(socket);
					handlers.put(player, ch);
					
					bonusList = initBonusTile();
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
						System.out.println(player.getFamilyMembers()[0].toString());
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
						Thread prova = new Thread(game);
						prova.start();
						//s.awaitTermination(3, TimeUnit.SECONDS);
					}catch(IOException e){
						e.printStackTrace();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				};
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
					Integer bonusTile = Integer.parseInt(handlers.get(p).receive());
					if(0 < bonusTile && bonusTile <= bonusList.size()){
						BonusTile bt = bonusList.get(bonusTile-1);
						System.out.println(bt.toString());
						p.getBoard().setBonusTile(bt);
						bonusList.remove(bt);
						found = true;
					}else{
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
		};
		Runnable client = new Runnable() {
			private PrintStream p;
			private BufferedReader b;
			private Socket sock;
			@Override
			public void run() {
				System.out.println("prova");
				try {
					sock = new Socket(SocketClient.getLocalAddress(), 1339);
					System.out.println("connected");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("connected");
				//String prova = "prova";
				//System.setIn(new ByteArrayInputStream(prova.getBytes()));
				try{
					p = new PrintStream(sock.getOutputStream());
					b = new BufferedReader(new InputStreamReader(sock.getInputStream()));
					String socketLine = b.readLine();
					System.out.println(socketLine);
					
					p.println("stewie");
					p.flush();
					System.out.println("inviato");
					String fawd = b.readLine();
					System.out.println(fawd);
					p.println("red");
					p.flush();
					String vef = b.readLine();
					System.out.println(vef);
					p.println(1);
					p.flush();
					String move = b.readLine();
					System.out.println(move);
					p.println("gotospace");
					p.flush();
					String space = b.readLine();
					System.out.println(space);
					p.println("coinspace");
					p.flush();
					String fm = b.readLine();
					System.out.println(fm);
					p.println("orange");
					p.flush();
					String inc = b.readLine();
					System.out.println(inc);
					p.println("n");
					p.flush();
					String sa = b.readLine();
					System.out.println(sa);
					p.println("n");
					p.flush();
				}
				catch(IOException e){
					System.out.println(e.getMessage());
				}
				
			}
				
		};
		s.execute(g);
		s.schedule(client, 100, TimeUnit.MILLISECONDS);
		//s.schedule(client2, 2, TimeUnit.SECONDS);
		try {
			s.awaitTermination(1, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void Test2(){
		ScheduledThreadPoolExecutor s = new ScheduledThreadPoolExecutor(4);
		Runnable g = new Runnable() {
			PrintStream p;
			Scanner scan;
			List<PlayerColor> usedColors = new ArrayList<>();
			ServerSocket serverSock;
			Map<Player, ClientHandler> handlers = new HashMap<>();
			List<BonusTile> bonusList;
			@Override
			public void run() {
				try {
					serverSock = new ServerSocket(1340);
					Socket socket = serverSock.accept();
					p = new PrintStream(socket.getOutputStream());
					scan = new Scanner(socket.getInputStream());
					p.println("Enter your name:");
					p.flush();
					String name = scan.nextLine();
					PlayerColor color = enterColor();
					System.out.println(color);
					Player player = new Player(name, color);
					ClientHandler ch = new SocketClientHandler(socket);
					handlers.put(player, ch);
					
					bonusList = initBonusTile();
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
						System.out.println(player.getFamilyMembers()[0].toString());
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
						Thread prova = new Thread(game);
						prova.start();
						//s.awaitTermination(3, TimeUnit.SECONDS);
					}catch(IOException e){
						e.printStackTrace();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				};
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
					Integer bonusTile = Integer.parseInt(handlers.get(p).receive());
					if(0 < bonusTile && bonusTile <= bonusList.size()){
						BonusTile bt = bonusList.get(bonusTile-1);
						System.out.println(bt.toString());
						p.getBoard().setBonusTile(bt);
						bonusList.remove(bt);
						found = true;
					}else{
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
		};
		Runnable client = new Runnable() {
			private PrintStream p;
			private BufferedReader b;
			private Socket sock;
			@Override
			public void run() {
				System.out.println("prova");
				try {
					sock = new Socket(SocketClient.getLocalAddress(), 1340);
					System.out.println("connected");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("connected");
				//String prova = "prova";
				//System.setIn(new ByteArrayInputStream(prova.getBytes()));
				try{
					p = new PrintStream(sock.getOutputStream());
					b = new BufferedReader(new InputStreamReader(sock.getInputStream()));
					String socketLine = b.readLine();
					System.out.println(socketLine);
					
					p.println("stewie");
					p.flush();
					System.out.println("inviato");
					String fawd = b.readLine();
					System.out.println(fawd);
					p.println("red");
					p.flush();
					String vef = b.readLine();
					System.out.println(vef);
					p.println(1);
					p.flush();
					String move = b.readLine();
					System.out.println(move);
					p.println("gotospace");
					p.flush();
					String space = b.readLine();
					System.out.println(space);
					p.println("servantspace");
					p.flush();
					String fm = b.readLine();
					System.out.println(fm);
					p.println("orange");
					p.flush();
					String inc = b.readLine();
					System.out.println(inc);
					p.println("n");
					p.flush();
					String sa = b.readLine();
					System.out.println(sa);
					p.println("n");
					p.flush();
				}
				catch(IOException e){
					System.out.println(e.getMessage());
				}
				
			}
				
		};
		s.execute(g);
		s.schedule(client, 100, TimeUnit.MILLISECONDS);
		//s.schedule(client2, 2, TimeUnit.SECONDS);
		try {
			s.awaitTermination(1, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void test3(){
		ScheduledThreadPoolExecutor s = new ScheduledThreadPoolExecutor(4);
		Runnable g = new Runnable() {
			PrintStream p;
			Scanner scan;
			List<PlayerColor> usedColors = new ArrayList<>();
			ServerSocket serverSock;
			Map<Player, ClientHandler> handlers = new HashMap<>();
			List<BonusTile> bonusList;
			@Override
			public void run() {
				try {
					serverSock = new ServerSocket(1341);
					Socket socket = serverSock.accept();
					p = new PrintStream(socket.getOutputStream());
					scan = new Scanner(socket.getInputStream());
					p.println("Enter your name:");
					p.flush();
					String name = scan.nextLine();
					PlayerColor color = enterColor();
					System.out.println(color);
					Player player = new Player(name, color);
					ClientHandler ch = new SocketClientHandler(socket);
					handlers.put(player, ch);
					
					bonusList = initBonusTile();
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
						System.out.println(player.getFamilyMembers()[0].toString());
						List<Player> reversePlayer = new ArrayList<>();
						for(Player p : players){
							reversePlayer.add(p);
						}

						Collections.reverse(reversePlayer);
						for(Player p : reversePlayer){
							enterBonusTile(tileInstance, handlers, p);
						}
						
						game.getGameModel().getGameBoard().getMixedSpace().setFree(true);
						game.registerObserver(new Controller());
						game.getGameModel().registerObserver(game);
						Thread prova = new Thread(game);
						prova.start();
						//s.awaitTermination(3, TimeUnit.SECONDS);
					}catch(IOException e){
						e.printStackTrace();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				};
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
					Integer bonusTile = Integer.parseInt(handlers.get(p).receive());
					if(0 < bonusTile && bonusTile <= bonusList.size()){
						BonusTile bt = bonusList.get(bonusTile-1);
						System.out.println(bt.toString());
						p.getBoard().setBonusTile(bt);
						bonusList.remove(bt);
						found = true;
					}else{
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
		};
		Runnable client = new Runnable() {
			private PrintStream p;
			private BufferedReader b;
			private Socket sock;
			@Override
			public void run() {
				System.out.println("prova");
				try {
					sock = new Socket(SocketClient.getLocalAddress(), 1341);
					System.out.println("connected");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("connected");
				//String prova = "prova";
				//System.setIn(new ByteArrayInputStream(prova.getBytes()));
				try{
					p = new PrintStream(sock.getOutputStream());
					b = new BufferedReader(new InputStreamReader(sock.getInputStream()));
					String socketLine = b.readLine();
					System.out.println(socketLine);
					
					p.println("stewie");
					p.flush();
					System.out.println("inviato");
					String fawd = b.readLine();
					System.out.println(fawd);
					p.println("red");
					p.flush();
					String vef = b.readLine();
					System.out.println(vef);
					p.println(1);
					p.flush();
					String move = b.readLine();
					System.out.println(move);
					p.println("gotospace");
					p.flush();
					String space = b.readLine();
					System.out.println(space);
					p.println("mixedSpace");
					p.flush();
					String fm = b.readLine();
					System.out.println(fm);
					p.println("orange");
					p.flush();
					String inc = b.readLine();
					System.out.println(inc);
					p.println("n");
					p.flush();
					String sa = b.readLine();
					System.out.println(sa);
					p.println("n");
					p.flush();
				}
				catch(IOException e){
					System.out.println(e.getMessage());
				}
				
			}
				
		};
		s.execute(g);
		s.schedule(client, 100, TimeUnit.MILLISECONDS);
		//s.schedule(client2, 2, TimeUnit.SECONDS);
		try {
			s.awaitTermination(1, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void test4(){
		ScheduledThreadPoolExecutor s = new ScheduledThreadPoolExecutor(4);
		Runnable g = new Runnable() {
			PrintStream p;
			Scanner scan;
			List<PlayerColor> usedColors = new ArrayList<>();
			ServerSocket serverSock;
			Map<Player, ClientHandler> handlers = new HashMap<>();
			List<BonusTile> bonusList;
			@Override
			public void run() {
				try {
					serverSock = new ServerSocket(1342);
					Socket socket = serverSock.accept();
					p = new PrintStream(socket.getOutputStream());
					scan = new Scanner(socket.getInputStream());
					p.println("Enter your name:");
					p.flush();
					String name = scan.nextLine();
					PlayerColor color = enterColor();
					System.out.println(color);
					Player player = new Player(name, color);
					ClientHandler ch = new SocketClientHandler(socket);
					handlers.put(player, ch);
					
					bonusList = initBonusTile();
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
						System.out.println(player.getFamilyMembers()[0].toString());
						List<Player> reversePlayer = new ArrayList<>();
						for(Player p : players){
							reversePlayer.add(p);
						}

						Collections.reverse(reversePlayer);
						for(Player p : reversePlayer){
							enterBonusTile(tileInstance, handlers, p);
						}
						
						game.getGameModel().getGameBoard().getPrivilegesSpace().setFree(true);
						game.registerObserver(new Controller());
						game.getGameModel().registerObserver(game);
						Thread prova = new Thread(game);
						prova.start();
						//s.awaitTermination(3, TimeUnit.SECONDS);
					}catch(IOException e){
						e.printStackTrace();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				};
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
					Integer bonusTile = Integer.parseInt(handlers.get(p).receive());
					if(0 < bonusTile && bonusTile <= bonusList.size()){
						BonusTile bt = bonusList.get(bonusTile-1);
						System.out.println(bt.toString());
						p.getBoard().setBonusTile(bt);
						bonusList.remove(bt);
						found = true;
					}else{
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
		};
		Runnable client = new Runnable() {
			private PrintStream p;
			private BufferedReader b;
			private Socket sock;
			@Override
			public void run() {
				System.out.println("prova");
				try {
					sock = new Socket(SocketClient.getLocalAddress(), 1342);
					System.out.println("connected");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("connected");
				//String prova = "prova";
				//System.setIn(new ByteArrayInputStream(prova.getBytes()));
				try{
					p = new PrintStream(sock.getOutputStream());
					b = new BufferedReader(new InputStreamReader(sock.getInputStream()));
					String socketLine = b.readLine();
					System.out.println(socketLine);
					
					p.println("stewie");
					p.flush();
					System.out.println("inviato");
					String fawd = b.readLine();
					System.out.println(fawd);
					p.println("red");
					p.flush();
					String vef = b.readLine();
					System.out.println(vef);
					p.println(1);
					p.flush();
					String move = b.readLine();
					System.out.println(move);
					p.println("gotospace");
					p.flush();
					String space = b.readLine();
					System.out.println(space);
					p.println("harvestspace");
					p.flush();
					String fm = b.readLine();
					System.out.println(fm);
					p.println("orange");
					p.flush();
					String inc = b.readLine();
					System.out.println(inc);
					p.println("n");
					p.flush();
					String sa = b.readLine();
					System.out.println(sa);
					p.println("n");
					p.flush();
				}
				catch(IOException e){
					System.out.println(e.getMessage());
				}
				
			}
				
		};
		s.execute(g);
		s.schedule(client, 100, TimeUnit.MILLISECONDS);
		//s.schedule(client2, 2, TimeUnit.SECONDS);
		try {
			s.awaitTermination(1, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void test5(){
		ScheduledThreadPoolExecutor s = new ScheduledThreadPoolExecutor(4);
		Runnable g = new Runnable() {
			PrintStream p;
			Scanner scan;
			List<PlayerColor> usedColors = new ArrayList<>();
			ServerSocket serverSock;
			Map<Player, ClientHandler> handlers = new HashMap<>();
			List<BonusTile> bonusList;
			@Override
			public void run() {
				try {
					serverSock = new ServerSocket(1343);
					Socket socket = serverSock.accept();
					p = new PrintStream(socket.getOutputStream());
					scan = new Scanner(socket.getInputStream());
					p.println("Enter your name:");
					p.flush();
					String name = scan.nextLine();
					PlayerColor color = enterColor();
					System.out.println(color);
					Player player = new Player(name, color);
					ClientHandler ch = new SocketClientHandler(socket);
					handlers.put(player, ch);
					
					bonusList = initBonusTile();
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
						System.out.println(player.getFamilyMembers()[0].toString());
						List<Player> reversePlayer = new ArrayList<>();
						for(Player p : players){
							reversePlayer.add(p);
						}

						Collections.reverse(reversePlayer);
						for(Player p : reversePlayer){
							enterBonusTile(tileInstance, handlers, p);
						}
						
						game.getGameModel().getGameBoard().getPrivilegesSpace().setFree(true);
						game.getGameModel().getPlayers().get(0).getFamilyMembers()[1].setUsed(true);
						game.registerObserver(new Controller());
						game.getGameModel().registerObserver(game);
						Thread prova = new Thread(game);
						prova.start();
						//s.awaitTermination(3, TimeUnit.SECONDS);
					}catch(IOException e){
						e.printStackTrace();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				};
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
					Integer bonusTile = Integer.parseInt(handlers.get(p).receive());
					if(0 < bonusTile && bonusTile <= bonusList.size()){
						BonusTile bt = bonusList.get(bonusTile-1);
						System.out.println(bt.toString());
						p.getBoard().setBonusTile(bt);
						bonusList.remove(bt);
						found = true;
					}else{
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
		};
		Runnable client = new Runnable() {
			private PrintStream p;
			private BufferedReader b;
			private Socket sock;
			@Override
			public void run() {
				System.out.println("prova");
				try {
					sock = new Socket(SocketClient.getLocalAddress(), 1343);
					System.out.println("connected");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("connected");
				//String prova = "prova";
				//System.setIn(new ByteArrayInputStream(prova.getBytes()));
				try{
					p = new PrintStream(sock.getOutputStream());
					b = new BufferedReader(new InputStreamReader(sock.getInputStream()));
					String socketLine = b.readLine();
					System.out.println(socketLine);
					
					p.println("stewie");
					p.flush();
					System.out.println("inviato");
					String fawd = b.readLine();
					System.out.println(fawd);
					p.println("red");
					p.flush();
					String vef = b.readLine();
					System.out.println(vef);
					p.println(1);
					p.flush();
					String move = b.readLine();
					System.out.println(move);
					p.println("gotospace");
					p.flush();
					String space = b.readLine();
					System.out.println(space);
					p.println("productionspace");
					p.flush();
					String fm = b.readLine();
					System.out.println(fm);
					p.println("orange");
					p.flush();
					String fm2 = b.readLine();
					System.out.println(fm2);
					p.println("white");
					p.flush();
					String inc = b.readLine();
					System.out.println(inc);
					p.println("n");
					p.flush();
					String sa = b.readLine();
					System.out.println(sa);
					p.println("n");
					p.flush();
				}
				catch(IOException e){
					System.out.println(e.getMessage());
				}
				
			}
				
		};
		s.execute(g);
		s.schedule(client, 100, TimeUnit.MILLISECONDS);
		//s.schedule(client2, 2, TimeUnit.SECONDS);
		try {
			s.awaitTermination(1, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
