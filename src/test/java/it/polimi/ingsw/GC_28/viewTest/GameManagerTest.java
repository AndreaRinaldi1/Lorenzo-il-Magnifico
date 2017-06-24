package it.polimi.ingsw.GC_28.viewTest;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.boards.PlayerBoard;
import it.polimi.ingsw.GC_28.cards.CardType;
import it.polimi.ingsw.GC_28.cards.ExcommunicationTile;
import it.polimi.ingsw.GC_28.cards.Territory;
import it.polimi.ingsw.GC_28.cards.Venture;
import it.polimi.ingsw.GC_28.components.DiceColor;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.effects.Effect;
import it.polimi.ingsw.GC_28.effects.EffectType;
import it.polimi.ingsw.GC_28.effects.ModifyDiceEffect;
import it.polimi.ingsw.GC_28.effects.OtherEffect;
import it.polimi.ingsw.GC_28.model.BoardSetup;
import it.polimi.ingsw.GC_28.model.BoardsInitializer;
import it.polimi.ingsw.GC_28.model.GameModel;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.model.PlayerColor;
import it.polimi.ingsw.GC_28.server.ClientHandler;
import it.polimi.ingsw.GC_28.view.*;

public class GameManagerTest {
	
	private GameManager gameManager;
	private int time = 2;
	private Player player;
	private Player player1;
	private TestGameView gameView;
	private GameBoard gameBoard;
	private GameModel gameModel;
	private ArrayList<Player> players = new ArrayList<>();
	private FamilyMember familyMember;
	private FamilyMember[] familyMembers = new FamilyMember[1];
	
	private PlayerBoard playerBoard;
	private EnumMap<ResourceType, Integer> resources = new EnumMap<>(ResourceType.class);
	private PlayerBoard playerBoard1;
	private EnumMap<ResourceType, Integer> resources1 = new EnumMap<>(ResourceType.class);
	private Resource resource = Resource.of(resources);
	private Resource resource1 = Resource.of(resources1);
	
	//make excommunication tile for reduce dice effect
	private ModifyDiceEffect reduceDiceEffect = new ModifyDiceEffect();
	private ArrayList<DiceColor> diceColors = new ArrayList<>();

	//make excomunication tile for skipped effect
	private OtherEffect skipRoundEffect = new OtherEffect();
	private ExcommunicationTile excommunicationTile = new ExcommunicationTile();
	private ExcommunicationTile excommunicationTile1 = new ExcommunicationTile();
	private ArrayList<ExcommunicationTile> excommunicationTiles = new ArrayList<>();

	private BoardsInitializer bi = new BoardsInitializer();
	private HashMap<Player, ClientHandler> handlers = new HashMap<>();

	
	private class TestGameView extends GameView{
		public TestGameView(GameModel gameModel) {
			super(gameModel);
		}
		
		@Override
		public void play() throws IOException, IndexOutOfBoundsException{
			
		}
		
		@Override
		public boolean checkSkipped(int currentRound){
			return false;
		}
		
	}
	
	/*
	private class TestBoardInitializer extends BoardsInitializer{
		public TestBoardInitializer(){
			super();
		}
		
		@Override
		public TestGameView initializeBoard(List<Player> players)throws FileNotFoundException,IOException{
			//try {
				this.players  = players;
				initDices();
				initCouncilPrivilege();
				initGameBoard();
				initSpaces();
				//gameModel.setGameBoard(gameBoard);
				initExcommunication();
				initPlayerBoard();
				initFinalBonus();
				initFamilyMember();
				gameModel = new GameModel(gameBoard, players);
				placeBonusTile();
				//gameModel.setPlayers(players);
				completeExcommunicationArray();
				initLeaderCard();
			} catch (FileNotFoundException e) {
				Logger.getAnonymousLogger().log(Level.SEVERE, "cannot start initialize" + e);
			}
			return new TestGameView(gameModel);
		}
	}
	*/
	
	@Before
	public void gameManagerTest(){
		gameManager = new GameManager();
		
		diceColors.add(DiceColor.BLACK);
		
		//make 2 new players
		player = new Player("Gino", PlayerColor.BLUE);
		player1 = new Player("Fantozzi", PlayerColor.BLUE);
		
		for(ResourceType resType : ResourceType.values()){
			resources.put(resType, 9);
			resources1.put(resType, 2);
		}
		resource = Resource.of(resources);
		resource1 = Resource.of(resources1);
		
		reduceDiceEffect.setReduce(1);
		reduceDiceEffect.setDiceColor(diceColors);
		skipRoundEffect.setType(EffectType.SKIPROUNDEFFECT);
		excommunicationTile.setEffect(skipRoundEffect);
		excommunicationTile1.setEffect(reduceDiceEffect);
		excommunicationTiles.add(excommunicationTile);
		excommunicationTiles.add(excommunicationTile1);
		//make 2 new playerboards for sortBy test
		playerBoard = new PlayerBoard(null, resource);
		playerBoard1 = new PlayerBoard(null, resource1);
		playerBoard.setResources(resource);
		playerBoard1.setResources(resource1);
		
		player.setBoard(playerBoard);
		player1.setBoard(playerBoard1);
		player.setExcommunicationTile(excommunicationTiles);
		//make a view
		players.add(player1);
		players.add(player);
		
		familyMember = new FamilyMember(player, false, DiceColor.BLACK);
		familyMembers[0] = familyMember;
		
		gameBoard = new GameBoard();
		gameModel = new GameModel(gameBoard, players);
		gameView = new TestGameView(gameModel);
		
		ClientHandler value = new ClientHandler() {
			
			@Override
			public void send(String message) {
				
			}
			
			@Override
			public String receive() {
				return null;
			}
		};
		handlers.put(player, value );
		
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
/*
	@Test 
	public void testRun() throws FileNotFoundException, IOException{
		GameView game = bi.initializeBoard(players);
		game.setHandlers(handlers);
		GameManager gameM = new GameManager();
		gameM.setView(game);
		BoardSetup bs = new BoardSetup(gameM);
		bs.firstSetUpCards();
		gameM.run();
	}
	*/
	@Test
	public void testGetCurrentPeriod() {
		gameManager.setPeriod(time);
		assertEquals(this.time, this.gameManager.getCurrentPeriod());
	}

	@Test
	public void testGetCurrentPlayer() {
		gameManager.setCurrentPlayer(player);
		assertEquals(this.player, this.gameManager.getCurrentPlayer());
	}

	@Test
	public void testGetView() {
		gameManager.setView(gameView);
		assertEquals(this.gameView, this.gameManager.getView());
	}

	@Test
	public void testGetCurrentEra() {
		gameManager.setCurrentEra(time);
		assertEquals(this.time, this.gameManager.getCurrentEra());
	}

	@Test
	public void testSortBy() {
		gameManager.sortBy(players, ResourceType.COIN);
	}

	@Test
	public void testAssignBonusForMilitary() throws FileNotFoundException, IOException {
		GameView game = bi.initializeBoard(players);
		GameManager gameM = new GameManager();
		gameM.setView(game);
		BoardSetup bs = new BoardSetup(gameM);
		bs.firstSetUpCards();
		gameM.assignBonusForMilitary();
	}

	@Test
	public void testSkipPlayers() {
		gameManager.setView(gameView);
		this.gameManager.skipPlayers();
	}

	@Test
	public void testCheckSkippedPlayers() {
		
		gameView.setCurrentPlayer(player1);
		gameManager.setPeriod(2);
		gameManager.setView(gameView);
		this.gameView.getSkipped().add(player);
		this.gameManager.checkSkippedPlayers();
	}

	@Test
	public void testCheckDiceReduction() {
		player.setFamilyMembers(familyMembers);
		gameView.setCurrentPlayer(player);
		gameManager.setPeriod(2);
		gameManager.setView(gameView);
		this.gameManager.checkDiceReduction();
	}

	@Test
	public void testApplyFinalBonus() throws FileNotFoundException, IOException {
		GameView game = bi.initializeBoard(players);
		GameManager gameM = new GameManager();
		gameM.setView(game);
		BoardSetup bs = new BoardSetup(gameM);
		bs.firstSetUpCards();
		gameM.getView().getGameModel().getPlayers().get(0).getBoard()
		.addCard((Venture) gameM.getView().getGameModel().getGameBoard().getTowers().get(CardType.VENTURE).getCells()[0].getCard());
		gameM.applyFinalBonus();
	}

	@Test
	public void testApplyFinalMalus() {
		player1.setExcommunicationTile(excommunicationTiles);
		this.gameManager.setCurrentEra(2);
		this.gameManager.setView(gameView);
		this.gameManager.applyFinalMalus();
	}

}
