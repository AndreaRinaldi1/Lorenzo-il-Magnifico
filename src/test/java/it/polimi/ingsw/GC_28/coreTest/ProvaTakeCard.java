package it.polimi.ingsw.GC_28.coreTest;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.boards.Tower;
import it.polimi.ingsw.GC_28.cards.CardType;
import it.polimi.ingsw.GC_28.core.TakeCardAction;
import it.polimi.ingsw.GC_28.effects.DiscountEffect;
import it.polimi.ingsw.GC_28.effects.TakeCardEffect;
import it.polimi.ingsw.GC_28.model.BoardSetup;
import it.polimi.ingsw.GC_28.model.BoardsInitializer;
import it.polimi.ingsw.GC_28.model.Game;
import it.polimi.ingsw.GC_28.model.GameModel;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.model.PlayerColor;

public class ProvaTakeCard {

	private TakeCardAction takeCard;
	
	Player p1;
	Player p2;
	Player p3;
	Player p4;
	
	private List<Player> players = new ArrayList<>();
	private GameBoard gameBoard = new GameBoard();
	private GameModel gameModel;
	private Game g;
	
	private BoardsInitializer bi = new BoardsInitializer();
	
	BoardSetup bs;
	
	@Before
	public void TakeCard() throws FileNotFoundException{
		
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
		//bs.setUpBoard();
		
		try {
			bs.firstSetUpCards();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bs.setUpBoard();

		
		gameModel = new GameModel(gameBoard, players);

	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testIsApplicable() {
		this.gameBoard = this.g.getGameModel().getGameBoard();
		this.gameModel = this.g.getGameModel();
		takeCard = new TakeCardAction(g, gameModel);
		String name = this.g.getGameModel().getGameBoard().getTowers().get(CardType.TERRITORY).getCells()[0].getCard().getName();
		this.takeCard.setName(name);
		this.takeCard.setThroughEffect(null);
		this.takeCard.isApplicable();
	}

	@Test
	public void testApply() {

	}

}
