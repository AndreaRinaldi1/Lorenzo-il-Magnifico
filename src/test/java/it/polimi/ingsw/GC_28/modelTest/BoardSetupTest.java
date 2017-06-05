package it.polimi.ingsw.GC_28.modelTest;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.boards.BonusTile;
import it.polimi.ingsw.GC_28.boards.Cell;
import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.boards.PlayerBoard;
import it.polimi.ingsw.GC_28.boards.Tower;
import it.polimi.ingsw.GC_28.cards.Building;
import it.polimi.ingsw.GC_28.cards.Card;
import it.polimi.ingsw.GC_28.cards.CardReader;
import it.polimi.ingsw.GC_28.cards.CardType;
import it.polimi.ingsw.GC_28.cards.Character;
import it.polimi.ingsw.GC_28.cards.Deck;
import it.polimi.ingsw.GC_28.cards.ExcommunicationTile;
import it.polimi.ingsw.GC_28.cards.Territory;
import it.polimi.ingsw.GC_28.cards.Venture;
import it.polimi.ingsw.GC_28.components.Dice;
import it.polimi.ingsw.GC_28.components.DiceColor;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.effects.ResourceEffect;
import it.polimi.ingsw.GC_28.model.BoardSetup;
import it.polimi.ingsw.GC_28.model.Game;
import it.polimi.ingsw.GC_28.spaces.MarketSpace;


public class BoardSetupTest {
	private BoardSetup bs;
	private Game g;
	private Deck deck;

	private GameBoard gameBoard1;
	private GameBoard gameBoard;
	private Game game1;
	private EnumMap<CardType, Tower> towers;
	private Cell[] cells = new Cell[4];
	private Card[] cards = new Card[4];
	private CardReader cr = new CardReader();
	
	private Resource bonus;
	EnumMap<ResourceType, Integer> resource;
	@Before
	public void boardSetup() throws FileNotFoundException{
		g = new Game(); 
		bs = new BoardSetup(g);
		g.setGameBoard(gameBoard);
		deck = new Deck();
		deck = cr.startRead();
		gameBoard1 = new GameBoard();
		gameBoard = new GameBoard();
		game1 = new Game();
		this.towers = new EnumMap<>(CardType.class);
		resource = new EnumMap<>(ResourceType.class);
		resource.put(ResourceType.COIN, 2);
		bonus = Resource.of(resource);
		this.cells[0] = new Cell(null, 1, true);
		this.cells[1] = new Cell(null, 3, true);
		this.cells[2] = new Cell(bonus, 5, true);
		this.cells[3] = new Cell(bonus, 7, true);
		towers.put(CardType.BUILDING, new Tower(cells));
		towers.put(CardType.CHARACTER, new Tower(cells));
		towers.put(CardType.TERRITORY, new Tower(cells));
		towers.put(CardType.VENTURE, new Tower(cells));
		towers.get(CardType.BUILDING).setCells(this.cells);
		towers.get(CardType.CHARACTER).setCells(this.cells);
		towers.get(CardType.TERRITORY).setCells(this.cells);
		towers.get(CardType.VENTURE).setCells(this.cells);
		gameBoard.setTowers(towers);
		System.out.println(gameBoard.getTowers());
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testFirstSetUpCards() throws FileNotFoundException {
		/*bs.firstSetUpCards();
		deck = cardReader.startRead();
		*/	
	}

	@Test
	public void testSetUpBoard() {
		Game g = new Game();
		bs.setUpBoard();
		GameBoard gb = new GameBoard();
		gb.setTowers(towers);
		Dice[] dices = new Dice[3];
		dices[0] = new Dice(DiceColor.BLACK);
		dices[0].rollDice();
		dices[1] = new Dice(DiceColor.ORANGE);
		dices[1].rollDice();
		dices[2] = new Dice(DiceColor.WHITE);
		dices[2].rollDice();
		gb.setDices(dices);
		
		ExcommunicationTile[] excommunications = new ExcommunicationTile[3];
		excommunications[0] = new ExcommunicationTile();
		excommunications[1] = new ExcommunicationTile();
		excommunications[2] = new ExcommunicationTile();
		gb.setExcommunications(excommunications);
		
		BonusTile bonusTile = new BonusTile();
		PlayerBoard pb = new PlayerBoard(bonusTile , bonus);

		ResourceEffect re = new ResourceEffect();
		re.setResourceBonus(bonus);
		MarketSpace ms = new MarketSpace(true, 1);
		ms.setBonus(re);
		assertEquals(this.gameBoard, this.g.getGameBoard());
	}

	/*@Test
	public void testPrepareTowers() {
		System.out.println(1);
		bs.prepareTowers();
		System.out.println(2);
		
		
		Cell [] cell = gameBoard1.getTowers().get(CardType.TERRITORY).getCells();
		for(int i = 0; i < cell.length; i++){
			boolean x = false;
			while(!x){
				int randomInt = ThreadLocalRandom.current().nextInt(0, deck.getTerritories().size());*/
				/*the condition check if the era of the randomly selected card is correct and
				 *  if the card has already been drafted, otherwise the choice of the card is repeated*/
				/*if(deck.getTerritories().get(randomInt).getEra() == game1.getCurrentEra()){ 
					Territory t = deck.getTerritories().get(randomInt);
					cell[i].setCard(t);
					deck.getTerritories().remove(t);
					x = true;
				}
			}
		}	
		cell = gameBoard1.getTowers().get(CardType.BUILDING).getCells();
		for(int i = 0; i < cell.length; i++){
			boolean x = false;
			while(!x){
				int randomInt = ThreadLocalRandom.current().nextInt(0, deck.getBuildings().size());*/
				/*the condition check if the era of the randomly selected card is correct and
				 *  if the card has already been drafted, otherwise the choice of the card is repeated*/
				/*if(deck.getBuildings().get(randomInt).getEra() == game1.getCurrentEra()){ 
					Building b = deck.getBuildings().get(randomInt);
					cell[i].setCard(b);
					deck.getBuildings().remove(b);
					x = true;
				}
			}
		}
		cell = gameBoard1.getTowers().get(CardType.CHARACTER).getCells();
		for(int i = 0; i < cell.length; i++){
			boolean x = false;
			while(!x){
				int randomInt = ThreadLocalRandom.current().nextInt(0, deck.getCharacters().size());
				/*the condition check if the era of the randomly selected card is correct and
				 *  if the card has already been drafted, otherwise the choice of the card is repeated*/
				/*if(deck.getCharacters().get(randomInt).getEra() == game1.getCurrentEra()){ 
					Character c = deck.getCharacters().get(randomInt);
					cell[i].setCard(c);
					deck.getCharacters().remove(c);
					x = true;
				}
			}
		}
		cell = gameBoard1.getTowers().get(CardType.VENTURE).getCells();
		for(int i = 0; i < cell.length; i++){
			boolean x = false;
			while(!x){
				int randomInt = ThreadLocalRandom.current().nextInt(0, deck.getVentures().size());
				/*the condition check if the era of the randomly selected card is correct and
				 *  if the card has already been drafted, otherwise the choice of the card is repeated*/
				/*if(deck.getVentures().get(randomInt).getEra() == game1.getCurrentEra()){ 
					Venture v = deck.getVentures().get(randomInt);
					cell[i].setCard(v);
					deck.getVentures().remove(v);
					x = true;
				}
			}
		}
		assertEquals(this.gameBoard1.getTowers(), this.g.getGameBoard().getTowers());
		
	}*/

	@Test
	public void testFreeSpace() {
		fail("Not yet implemented");
	}

}
