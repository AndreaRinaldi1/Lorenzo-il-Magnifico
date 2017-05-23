package it.polimi.ingsw.GC_28.boards;

import java.util.ArrayList;

import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.model.PlayerColor;

public class ProvaSetUp {
	
	private static ArrayList<Player> player = new ArrayList<Player>();
	
	public static void prova() {
		completePlayer();
		BoardsInitializer.initializeBoard();
		BoardSetup.firstSetUpCards();
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
