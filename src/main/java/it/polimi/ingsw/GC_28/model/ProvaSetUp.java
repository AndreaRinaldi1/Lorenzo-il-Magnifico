package it.polimi.ingsw.GC_28.model;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.model.PlayerColor;

public class ProvaSetUp {
	
	private ProvaSetUp(){
		
	}
	
	private static ArrayList<Player> player = new ArrayList<>();
	private static BoardsInitializer bi = new BoardsInitializer();
	private static BoardSetup bs = new BoardSetup();
	
	public static void prova() {
		completePlayer();
		bi.initializeBoard();
		bs.firstSetUpCards();
	}
	
	public static void prova2(){
		bs.setUpBoard();
	}
	
	private static void completePlayer(){
		Player p1 = new Player("Nick", PlayerColor.BLUE);
		Player p2 = new Player("Andrea", PlayerColor.GREEN);
		Player p3 = new Player("Rob", PlayerColor.RED);
		Player p4 = new Player("Bruschi", PlayerColor.YELLOW);
		player.add(p1);
		player.add(p2);
		player.add(p3);
		player.add(p4);
	}
	
	public static ArrayList<Player> getPlayer(){
		return player;
	}
}
