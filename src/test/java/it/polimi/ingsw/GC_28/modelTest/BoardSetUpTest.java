package it.polimi.ingsw.GC_28.modelTest;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.cards.Building;
import it.polimi.ingsw.GC_28.cards.Character;
import it.polimi.ingsw.GC_28.cards.Territory;
import it.polimi.ingsw.GC_28.cards.Venture;
import it.polimi.ingsw.GC_28.model.BoardSetup;
import it.polimi.ingsw.GC_28.model.BoardsInitializer;
import it.polimi.ingsw.GC_28.model.Game;
import it.polimi.ingsw.GC_28.model.GameModel;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.model.PlayerColor;

public class BoardSetUpTest {
	
	Player p1;
	Player p2;
	Player p3;
	Player p4;
	
	private List<Player> players = new ArrayList<>();
	//private GameBoard gameBoard = new GameBoard();
	//private GameModel gameModel = new GameModel(gameBoard, players);
	private Game g ;
	
	private BoardsInitializer bi = new BoardsInitializer();
	
	BoardSetup bs;
	
	@Before
	public void boardSetup(){
		
		p1 = new Player("jhon", PlayerColor.RED);
		p2 = new Player("karl", PlayerColor.BLUE);
		p3 = new Player("Lenny", PlayerColor.GREEN);
		p4 = new Player("Homer", PlayerColor.YELLOW);
		players.add(p1);
		players.add(p2);
		players.add(p3);
		players.add(p4);
		try {
			g = bi.initializeBoard(players);
		} catch (IOException e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, e.getMessage());
		}
		bs = new BoardSetup(g);
	}
	
	@Test
	public void testFirstSetUpCard(){
		g.getGameModel().getGameBoard().display();
		try {
			bs.firstSetUpCards();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	@Test
	public void testSetUpBoard(){
		bs.setUpBoard();
		
		try {
			bs.firstSetUpCards();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		g.getGameModel().getGameBoard().getCoinSpace().addPlayer(p1.getFamilyMembers()[0]);
		g.getGameModel().getGameBoard().getCoinSpace().setFree(false);
		g.getGameModel().getGameBoard().getServantSpace().addPlayer(p2.getFamilyMembers()[0]);
		g.getGameModel().getGameBoard().getServantSpace().setFree(false);
		g.getGameModel().getGameBoard().getMixedSpace().addPlayer(p3.getFamilyMembers()[0]);
		g.getGameModel().getGameBoard().getMixedSpace().setFree(false);
		g.getGameModel().getGameBoard().getHarvestSpace().addPlayer(p1.getFamilyMembers()[1]);
		g.getGameModel().getGameBoard().getHarvestSpace().setFree(false);
		g.getGameModel().getGameBoard().getProductionSpace().addPlayer(p2.getFamilyMembers()[1]);
		g.getGameModel().getGameBoard().getProductionSpace().setFree(false);
		g.getGameModel().getGameBoard().getPrivilegesSpace().addPlayer(p3.getFamilyMembers()[1]);
		g.getGameModel().getGameBoard().getPrivilegesSpace().setFree(false);
		g.getGameModel().getGameBoard().getCouncilPalace().addPlayer(p4.getFamilyMembers()[0]);
		g.getGameModel().getPlayers().get(0).getLeaderCards().get(0).setPermanent(true);
		g.getGameModel().getPlayers().get(0).getLeaderCards().get(0).setActive(true);
		
		bs.setUpBoard();
		
	}
	
	@Test
	public void playerBoardDisplayTest(){
		g.getGameModel().getPlayers().get(0).getBoard().display();
		Building b = new Building("building", 1, 1);
		Character c = new Character("char", 2, 1);
		Territory t = new Territory("terr", 3, 1);
		Venture v = new Venture("venti", 4, 1);
		p1.getBoard().addCard(b);
		p1.getBoard().addCard(c);
		p1.getBoard().addCard(t);
		p1.getBoard().addCard(v);
		g.getGameModel().getPlayers().get(0).getBoard().display();
	}
	
	@Test
	public void gameBoardDisplayTest(){
		g.getGameModel().getGameBoard().display();
		
		g.getGameModel().getGameBoard().getCoinSpace().addPlayer(p1.getFamilyMembers()[0]);
		g.getGameModel().getGameBoard().getCoinSpace().setFree(false);
		g.getGameModel().getGameBoard().getServantSpace().addPlayer(p2.getFamilyMembers()[0]);
		g.getGameModel().getGameBoard().getServantSpace().setFree(false);
		g.getGameModel().getGameBoard().getMixedSpace().addPlayer(p3.getFamilyMembers()[0]);
		g.getGameModel().getGameBoard().getMixedSpace().setFree(false);
		g.getGameModel().getGameBoard().getHarvestSpace().addPlayer(p1.getFamilyMembers()[1]);
		g.getGameModel().getGameBoard().getHarvestSpace().setFree(false);
		g.getGameModel().getGameBoard().getProductionSpace().addPlayer(p2.getFamilyMembers()[1]);
		g.getGameModel().getGameBoard().getProductionSpace().setFree(false);
		g.getGameModel().getGameBoard().getPrivilegesSpace().addPlayer(p3.getFamilyMembers()[1]);
		g.getGameModel().getGameBoard().getPrivilegesSpace().setFree(false);
		
		g.getGameModel().getGameBoard().display();
	}
}
