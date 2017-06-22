package it.polimi.ingsw.GC_28.viewTest;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.boards.PlayerBoard;
import it.polimi.ingsw.GC_28.cards.ExcommunicationTile;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.effects.Effect;
import it.polimi.ingsw.GC_28.effects.EffectType;
import it.polimi.ingsw.GC_28.effects.OtherEffect;
import it.polimi.ingsw.GC_28.model.GameModel;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.model.PlayerColor;
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
	
	private PlayerBoard playerBoard;
	private EnumMap<ResourceType, Integer> resources = new EnumMap<>(ResourceType.class);
	private PlayerBoard playerBoard1;
	private EnumMap<ResourceType, Integer> resources1 = new EnumMap<>(ResourceType.class);
	private Resource resource = Resource.of(resources);
	private Resource resource1 = Resource.of(resources1);
	
	//make excomunication tile for skipped effect
	private OtherEffect skipRoundEffect = new OtherEffect();
	private ExcommunicationTile excommunicationTile = new ExcommunicationTile();
	private ArrayList<ExcommunicationTile> excommunicationTiles = new ArrayList<>();

	private class TestGameView extends GameView{
		public TestGameView(GameModel gameModel) {
			super(gameModel);
		}
		
		@Override
		public void play() throws IOException, IndexOutOfBoundsException{
			
		}
	}
	
	@Before
	public void gameManagerTest(){
		gameManager = new GameManager();
		
		//make 2 new players
		player = new Player("Gino", PlayerColor.BLUE);
		player1 = new Player("Fantozzi", PlayerColor.BLUE);
		
		for(ResourceType resType : ResourceType.values()){
			resources.put(resType, 9);
			resources1.put(resType, 2);
		}
		resource = Resource.of(resources);
		resource1 = Resource.of(resources1);
		
		skipRoundEffect.setType(EffectType.SKIPROUNDEFFECT);
		excommunicationTile.setEffect(skipRoundEffect);
		excommunicationTiles.add(excommunicationTile);
		
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
		
		gameBoard = new GameBoard();
		gameModel = new GameModel(gameBoard, players);
		gameView = new TestGameView(gameModel);
		
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testRun() {
		fail("Not yet implemented");
	}

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
	public void testAssignBonusForMilitary() {
		fail("Not yet implemented");
	}

	@Test
	public void testSkipPlayers() {
		fail("Not yet implemented");
	}

	@Test
	public void testCheckSkippedPlayers() {
		gameView.setCurrentPlayer(player);
		gameManager.setPeriod(2);
		gameManager.setView(gameView);
		this.gameView.getSkipped().add(player);
		this.gameManager.checkSkippedPlayers();
	}

	@Test
	public void testCheckDiceReduction() {
		fail("Not yet implemented");
	}

	@Test
	public void testApplyFinalBonus() {
		fail("Not yet implemented");
	}

	@Test
	public void testApplyFinalMalus() {
		fail("Not yet implemented");
	}

}
