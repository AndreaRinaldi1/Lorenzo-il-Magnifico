package it.polimi.ingsw.GC_28.effectsTest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.client.ClientWriter;
import it.polimi.ingsw.GC_28.components.DiceColor;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.effects.IncrementHPEffect;
import it.polimi.ingsw.GC_28.model.Game;
import it.polimi.ingsw.GC_28.model.GameModel;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.model.PlayerColor;

public class IncrementHPEffectTest {
	private IncrementHPEffect incHP;
	private int increment = 3;
	private boolean production;
	private boolean harvest;
	
	private	Game g;
	private FamilyMember fm;
	private FamilyMember fm2;
	private Player player;
	private Player player2;
	
	private GameModel gameModel;
	private GameBoard gameBoard; 
	private List<Player> players = new ArrayList<>();
	private ClientWriter w;
	
	@Before
	public void incrementHPEffect(){
		incHP = new IncrementHPEffect();
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
	public void testApply() {
		production = true;
		incHP.setHarvest(harvest);
		incHP.setProduction(production);
		incHP.setIncrement(increment);
		incHP.apply(fm, w);
		fm2.modifyValue(increment);
		boolean x = fm2.getValue().equals(fm.getValue());
		assertTrue(x);
	}

	@Test
	public void testGetIncrement() {
		incHP.setIncrement(increment);
		assertEquals(this.increment, this.incHP.getIncrement());
	}

	@Test
	public void testIsProduction() {
		incHP.setProduction(production);
		assertEquals(this.production, this.incHP.isProduction());
	}

	@Test
	public void testIsHarvest() {
		incHP.setHarvest(harvest);
		assertEquals(this.harvest, this.incHP.isHarvest());
	}

}
