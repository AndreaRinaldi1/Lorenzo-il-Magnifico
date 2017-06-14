package it.polimi.ingsw.GC_28.modelTest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.model.GameModel;
import it.polimi.ingsw.GC_28.model.Player;

public class GameModelTest {

	private GameModel gameModel;
	private GameBoard gameBoard;
	private Player player;
	private List<Player> players = new ArrayList<>();

	@Before
	public void gameModel(){
		gameBoard = new GameBoard();
		players.add(player);
		gameModel = new GameModel(gameBoard, players);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testGetGameBoard() {
		assertEquals(this.gameBoard, this.gameModel.getGameBoard());
	}

	@Test
	public void testGetPlayers() {
		assertEquals(this.players, this.gameModel.getPlayers());
	}

}
