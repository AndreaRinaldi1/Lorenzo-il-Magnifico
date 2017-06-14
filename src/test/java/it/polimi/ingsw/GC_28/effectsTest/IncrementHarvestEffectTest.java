package it.polimi.ingsw.GC_28.effectsTest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.components.DiceColor;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.effects.IncrementHarvestEffect;
import it.polimi.ingsw.GC_28.model.Game;
import it.polimi.ingsw.GC_28.model.GameModel;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.model.PlayerColor;

public class IncrementHarvestEffectTest {

	private IncrementHarvestEffect incH;
	private int increment = 3;
	
	private	Game g;
	private FamilyMember fm;
	private FamilyMember fm2;
	private Player player;
	private Player player2;
	
	private GameModel gameModel;
	private GameBoard gameBoard; 
	private List<Player> players = new ArrayList<>();
	
	
	@Before
	public void incrementHarvestEffect(){
		incH = new IncrementHarvestEffect();
		player = new Player("gino", PlayerColor.GREEN);
		player2 = new Player("Mariangiongianela", PlayerColor.BLUE);
		players.add(player);
		players.add(player2);
		gameBoard = new GameBoard();
		gameModel = new GameModel(gameBoard, players);
		g = new Game(gameModel);
		fm = new FamilyMember(player, false, DiceColor.ORANGE);
		fm2 = new FamilyMember(player2, false, DiceColor.WHITE);
		fm.setValue(2);
		fm2.setValue(2);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testApplyFamilyMemberGame() {
		incH.setIncrement(increment);
		incH.apply(fm, g);
		fm2.modifyValue(increment);
		boolean x = fm2.getValue().equals(fm.getValue());
		assertTrue(x);
	}

	@Test
	public void testGetIncrement() {
		incH.setIncrement(increment);
		assertEquals(this.increment, this.incH.getIncrement());
	}

}
