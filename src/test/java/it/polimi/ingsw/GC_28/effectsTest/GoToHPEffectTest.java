package it.polimi.ingsw.GC_28.effectsTest;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.components.DiceColor;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.effects.GoToHPEffect;
import it.polimi.ingsw.GC_28.model.Game;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.model.PlayerColor;
import it.polimi.ingsw.GC_28.spaces.ProductionAndHarvestSpace;

public class GoToHPEffectTest {
	private GoToHPEffect gt;
	private int actionValue = 4;
	private boolean production;
	private boolean harvest;
	
	private FamilyMember fm;
	private Player player;
	private Game g;
	private GameBoard gb;
	private ProductionAndHarvestSpace productionSpace2;

	
	@Before
	public void goToHPEffect(){
		gt = new GoToHPEffect();
		player = new Player("Gino", PlayerColor.BLUE);
		fm = new FamilyMember(player, false, DiceColor.BLACK);
		g = new Game();
		g.setCurrentPlayer(player);
		gb = new GameBoard();
		productionSpace2 = new ProductionAndHarvestSpace(true, 1);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testApply() {
		harvest = true;
		gt.setActionValue(actionValue);
		gt.setHarvest(harvest);
		gt.setProduction(production);
		gt.apply(this.fm, this.g);
		assertTrue(this.g.goToSpace(gt));
	}

	@Test
	public void testGetActionValue() {
		gt.setActionValue(actionValue);
		assertEquals(this.actionValue, this.gt.getActionValue());
	}

	@Test
	public void testIsProduction() {
		gt.setProduction(production);
		assertEquals(false, this.gt.isProduction());
	}

	@Test
	public void testIsHarvest() {
		gt.setHarvest(true);
		assertEquals(true, this.gt.isHarvest());
	}

}
