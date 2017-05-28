package it.polimi.ingsw.GC_28.model;


import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.GC_28.cards.CardType;

public class Main {
	
	private Main(){
		
	}
	
	
	public static void main(String[] args) {
		List<Player> players = new ArrayList<>();
		players = completePlayer(players);
		
		BoardsInitializer bi = new BoardsInitializer();
		
		Game game = bi.initializeBoard(players);
		System.out.println(game.getCurrentEra());
		
		BoardSetup bs = new BoardSetup(game);
		System.out.println(bs.gameBoard.getDices()[0]);
		
		bs.firstSetUpCards();
		System.out.println(bs.gameBoard.getTowers().get(CardType.BUILDING).getCells()[0].getCard().toString());
		game.getGameBoard().display();
		
		game.getGameBoard().getHarvestSpace().addPlayer(game.getPlayers().get(0).getFamilyMembers()[1]);
		
		game.getGameBoard().display();
		bs.setUpBoard();
		game.getGameBoard().display();
		
		/*bs.setUpBoard();
		System.out.println(game.getGameBoard().display());
		
		
		bs.setUpBoard();
		System.out.println(game.getGameBoard().display());*/
	}
	
	private static List<Player> completePlayer(List<Player> players){
		Player p1 = new Player("Nick", PlayerColor.BLUE);
		Player p2 = new Player("Andrea", PlayerColor.GREEN);
		Player p3 = new Player("Rob", PlayerColor.RED);
		Player p4 = new Player("Bruschi", PlayerColor.YELLOW);
		players.add(p1);
		players.add(p2);
		players.add(p3);
		players.add(p4);
		return players;
	}
	
	
}
