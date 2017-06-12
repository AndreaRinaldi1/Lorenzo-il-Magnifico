package it.polimi.ingsw.GC_28.modelTest;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.AfterClass;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import it.polimi.ingsw.GC_28.boards.Cell;
import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.boards.Tower;
import it.polimi.ingsw.GC_28.cards.CardReader;
import it.polimi.ingsw.GC_28.cards.CardType;
import it.polimi.ingsw.GC_28.cards.Deck;
import it.polimi.ingsw.GC_28.model.BoardSetup;
import it.polimi.ingsw.GC_28.model.Game;
import it.polimi.ingsw.GC_28.model.GameModel;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.model.PlayerColor;

public class BoardSetupTest {
	
	private BoardSetup boardSetup;
	
	private Gson gson = new GsonBuilder().create();
	private Deck d = new Deck();
	private CardReader cr;
	private Map<CardType, Tower> towers = new EnumMap<>(CardType.class);
	
	private GameBoard gameboard;
	private GameModel gameModel;
	private List<Player> players = new ArrayList<>();
	private Game game;
	private Player player;
	private int currentEra = 1;
	@Test
	public void boardSetupTest() throws FileNotFoundException{
		boardSetup = new BoardSetup(game);
		cr = new CardReader();		
		d.equals(cr.startRead());
		player = new Player("bob", PlayerColor.GREEN);
		players.add(player);
		gameboard = new GameBoard();
		gameModel = new GameModel(gameboard, players);
		game = new Game(gameModel);
		game.setCurrentEra(currentEra);;
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testFirstSetUpCards() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetUpBoard() {
		fail("Not yet implemented");
	}

	@Test
	public void testPrepareTowers() {
		Cell[] cells = new Cell[4];
		int actionValue = 1;
		boolean free = true;
		int randomInt = ThreadLocalRandom.current().nextInt(0, d.getTerritories().size());
		for(int i = 0; i < cells.length; i++){
			cells[i] = new Cell(null, actionValue, free);
		}
		Tower tower = new Tower(cells);
		for(int l = 0; l < CardType.values().length; l++){
			for(int k = 0; k < tower.getCells().length; k++){
				if(CardType.values()[l].equals(CardType.TERRITORY)){
					if(game.getCurrentEra() == this.d.getTerritories().get(randomInt).getEra()){
						tower.getCells()[k].setCard(this.d.getTerritories().get(randomInt));
					}
				}
			}
			for(int k = 0; k < tower.getCells().length; k++){
				if(CardType.values()[l].equals(CardType.BUILDING)){
					if(game.getCurrentEra() == this.d.getBuildings().get(randomInt).getEra()){
						tower.getCells()[k].setCard(this.d.getBuildings().get(randomInt));
					}
				}
			}
			for(int k = 0; k < tower.getCells().length; k++){
				if(CardType.values()[l].equals(CardType.CHARACTER)){
					if(game.getCurrentEra() == this.d.getCharacters().get(randomInt).getEra()){
						tower.getCells()[k].setCard(this.d.getCharacters().get(randomInt));
					}
				}
			}
			for(int k = 0; k < tower.getCells().length; k++){
				if(CardType.values()[l].equals(CardType.VENTURE)){
					if(game.getCurrentEra() == this.d.getVentures().get(randomInt).getEra()){
						tower.getCells()[k].setCard(this.d.getVentures().get(randomInt));
					}
				}
			}
			this.towers.put(CardType.values()[l], tower);
		}
		//TODO
		gameboard.setTowers(towers);
		assertEquals(gameboard.getTowers(), boardSetup);
	}

	@Test
	public void testFreeSpace() {
		fail("Not yet implemented");
	}

}
