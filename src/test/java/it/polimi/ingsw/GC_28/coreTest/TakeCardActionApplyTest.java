package it.polimi.ingsw.GC_28.coreTest;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.boards.Cell;
import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.boards.PlayerBoard;
import it.polimi.ingsw.GC_28.boards.Tower;
import it.polimi.ingsw.GC_28.cards.Card;
import it.polimi.ingsw.GC_28.cards.CardType;
import it.polimi.ingsw.GC_28.cards.Character;
import it.polimi.ingsw.GC_28.cards.ExcommunicationTile;
import it.polimi.ingsw.GC_28.cards.LeaderCard;
import it.polimi.ingsw.GC_28.cards.Venture;
import it.polimi.ingsw.GC_28.components.DiceColor;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.core.TakeCardAction;
import it.polimi.ingsw.GC_28.effects.IncrementCardEffect;
import it.polimi.ingsw.GC_28.effects.OtherEffect;
import it.polimi.ingsw.GC_28.model.BoardSetup;
import it.polimi.ingsw.GC_28.model.BoardsInitializer;
import it.polimi.ingsw.GC_28.model.Game;
import it.polimi.ingsw.GC_28.model.GameModel;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.model.PlayerColor;

public class TakeCardActionApplyTest {

	private TakeCardAction takeCard;
	private TestGame testGame;
	private GameModel gameModel;
	private GameBoard gameBoard;
	private PlayerBoard playerBoard;
	private Player player;
	private Player player1;
	private ArrayList<Player> players = new ArrayList<>();
	private BoardsInitializer bi = new BoardsInitializer();
	
	private BoardSetup setUp;
	
	private Map<CardType, Tower> towers = new EnumMap<>(CardType.class);
	private Tower tower;
	private Cell[] cells = new Cell[2];
	private Cell cell = new Cell(null, 1, true);
	private Cell cell1 = new Cell(null, 2, true);
	private Card card0;
	private Card card1;

	private Venture v;
	private Venture v1;
	private Character c;
	private Character c1;
	
	
	private EnumMap<ResourceType, Integer> resources = new EnumMap<>(ResourceType.class);
	private Resource resource = Resource.of(resources);
	
	private EnumMap<ResourceType, Integer> w = new EnumMap<>(ResourceType.class);
	private Resource alternativeCost = Resource.of(resources);

	private EnumMap<ResourceType, Integer> resources1 = new EnumMap<>(ResourceType.class);
	private Resource cost = Resource.of(resources1);

	private IncrementCardEffect permanentCardEffect;
	
	private FamilyMember familyMember;
	private FamilyMember familyMember1;
	private FamilyMember[] familyMembers = new FamilyMember[1];
	
	private OtherEffect otherEffect;
	private LeaderCard leaderCard = new LeaderCard();
	private ArrayList<LeaderCard> leaderCards = new ArrayList<>();

	private ExcommunicationTile excommunicationTile = new ExcommunicationTile();
	private ArrayList<ExcommunicationTile> excommunicationTiles = new ArrayList<>();
	
	private class TestGame extends Game{
		public TestGame(GameModel gameModel) {
			super(gameModel);
		}
		
		
	}

	@Before
	public void testTakeCardActionApply(){
		player = new Player("bob", PlayerColor.BLUE);
		player1 = new Player("lolo", PlayerColor.GREEN);
		familyMember = new FamilyMember(player, false, DiceColor.BLACK);
		familyMember1 = new FamilyMember(player1, false, DiceColor.BLACK);
		this.familyMembers[0] = familyMember;
		this.player.setFamilyMembers(familyMembers);
		players.add(player);
		players.add(player1);
		
		permanentCardEffect = new IncrementCardEffect();
		
		resources.put(ResourceType.COIN, 2);
		resource = Resource.of(resources);
		
		playerBoard = new PlayerBoard(null, null);
		gameBoard = new GameBoard();
		gameModel = new GameModel(gameBoard, players);
				
		testGame = new TestGame(gameModel);
		takeCard = new TakeCardAction(testGame, gameModel);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testApply() throws FileNotFoundException, IOException {
		card0 = new Venture("bob", 1, 1);
		card1 = new Venture("bob", 2, 1);
		cell.setCard((Venture)card0);
		cell.setCard((Venture)card1);
		cells[0] = cell;
		cells[1] = cell1;
		tower = new Tower(cells);
		tower.setCells(cells);
		towers.put(CardType.VENTURE, tower);
				
		Game game = bi.initializeBoard(players);
		BoardSetup bs = new BoardSetup(game);
		bs.firstSetUpCards();
			
		for(ResourceType resType : ResourceType.values()){
			resources.put(resType, 1);
		}
		Resource res = Resource.of(resources);
		playerBoard.setResources(res);
		playerBoard.addCard((Venture)card0);
		playerBoard.addCard((Venture)card1);
		playerBoard.setResources(resource);
		player.setBoard(playerBoard);
		
			
		gameBoard.setTowers(towers);
		gameBoard.getTowers().get(CardType.VENTURE).getCells()[0].setFree(false);
		gameBoard.getTowers().get(CardType.VENTURE).getCells()[0].setFamilyMember(familyMember1);

		takeCard.setFamilyMember(familyMember);
		takeCard.setName("bob");
		takeCard.setThroughEffect(null);
		
		takeCard.apply();
	}

}
