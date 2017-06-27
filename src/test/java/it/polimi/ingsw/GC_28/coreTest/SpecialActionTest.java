package it.polimi.ingsw.GC_28.coreTest;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.boards.PlayerBoard;
import it.polimi.ingsw.GC_28.cards.LeaderCard;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.core.SpecialAction;
import it.polimi.ingsw.GC_28.effects.ServantEffect;
import it.polimi.ingsw.GC_28.model.BoardSetup;
import it.polimi.ingsw.GC_28.model.BoardsInitializer;
import it.polimi.ingsw.GC_28.model.GameModel;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.model.PlayerColor;
import it.polimi.ingsw.GC_28.view.GameManager;
import it.polimi.ingsw.GC_28.view.GameView;

public class SpecialActionTest {

	private SpecialAction specialAction;
	private SpecialAction specialAction1;
	private BoardsInitializer bi = new BoardsInitializer();
	private GameModel gameModel;
	private Player player;
	private Player player1;
	private ArrayList<Player> players = new ArrayList<>();
	
	private LeaderCard leaderCard = new LeaderCard();
	private ArrayList<LeaderCard> leaderCards = new ArrayList<>();
	private GameBoard gameBoard;
	private GameView gameView;
	private GameManager gameManager;
	
	private EnumMap<ResourceType, Integer> resources = new EnumMap<>(ResourceType.class);
	private Resource resource = Resource.of(resources);
	
	private PlayerBoard playerBoard;
	
	private TestGame testGame;

	private class TestGame extends GameView{
		public TestGame(GameModel gameModel) {
			super(gameModel);
		}
		
		@Override
		public ArrayList<Character> askPrivilege(int numberOfCouncilPrivileges, boolean different){
			ArrayList<Character> w = new ArrayList<>();
			w.add('c');
			return w;
		}
		
		@Override
		public Resource checkResourceExcommunication(Resource amount){
			EnumMap<ResourceType, Integer> z = new EnumMap<>(ResourceType.class);
			z.put(ResourceType.COIN, 0);
			Resource bonus = Resource.of(z);
			return bonus;
		}
				
	}
	
	@Before
	public void specialActionTest() throws FileNotFoundException, IOException{
		gameBoard = new GameBoard();
		player = new Player("Buddy", PlayerColor.BLUE);
		player1 = new Player("Renato", PlayerColor.BLUE);
		players.add(player);
		gameModel = new GameModel(gameBoard, players);
		gameView = new GameView(gameModel);
		gameView = bi.initializeBoard(players);
		gameManager = new GameManager();
		gameManager.setView(gameView);
		BoardSetup bs = new BoardSetup(gameManager);
		bs.firstSetUpCards();
		
		for(ResourceType resType : ResourceType.values()){
			resources.put(resType, -1);
		}
		resource = Resource.of(resources);
		playerBoard = new PlayerBoard(null, resource);
		playerBoard.setResources(resource);
		
		leaderCard.setName("Gino");		
		leaderCards.add(leaderCard);
	
		player1.setBoard(playerBoard);
		
		testGame = new TestGame(gameModel);
		
		specialAction = new SpecialAction(player, gameModel, gameView);
		specialAction1 = new SpecialAction(player1, gameModel, testGame);
	} 
		
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	//special action "discard"
	@Test
	public void testApply() {
		player1.setBoard(playerBoard);
		player1.setLeaderCards(leaderCards);
		this.specialAction1.setActionType("discard");
		this.specialAction1.setLeaderName("Gino");
		this.specialAction1.apply();
		
	}

	//special action "play"
	@Test
	public void testApply1() throws FileNotFoundException, IOException{
		this.specialAction.setActionType("play");
		GameView game = bi.initializeBoard(players);
		GameManager gameM = new GameManager();
		gameM.setView(game);
		BoardSetup bs = new BoardSetup(gameM);
		bs.firstSetUpCards();
		this.specialAction.setLeaderName(gameM.getView().getGameModel().getPlayers().get(0).getLeaderCards().get(0).getName());
		this.specialAction.apply();		
	}
	
	//special action "activate"
	@Test
	public void testApply2() throws FileNotFoundException, IOException{
		player1.setBoard(playerBoard);
		player1.setLeaderCards(leaderCards);
		ServantEffect servantEffect = new ServantEffect();
		player1.getLeaderCards().get(0).setEffect(servantEffect);
		player1.getLeaderCards().get(0).setActive(false);
		this.specialAction1.setActionType("play");
		this.specialAction1.setLeaderName("Gino");
		this.specialAction1.apply();
		this.specialAction1.setActionType("activate");
		this.specialAction1.apply();
	}
		
	
	@Test
	public void testGetLeaderName() {
		this.specialAction.setLeaderName(gameManager.getView().getGameModel().getPlayers().get(0).getLeaderCards().get(0).getName());
		assertEquals(this.gameManager.getView().getGameModel().getPlayers().get(0).getLeaderCards().get(0).getName(),
				this.specialAction.getLeaderName());
	}

	@Test
	public void testGetActionType() {
		this.specialAction.setActionType("Discard");
		assertEquals("Discard", this.specialAction.getActionType());
	}

	@Test
	public void testApply3(){
		this.specialAction1.setActionType("discard");
		this.specialAction1.apply();
		this.specialAction1.setActionType("play");
		this.specialAction1.apply();
		this.specialAction1.setActionType("activate");
		this.specialAction1.apply();
	}
	
}
