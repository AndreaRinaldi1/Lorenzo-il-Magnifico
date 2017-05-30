package it.polimi.ingsw.GC_28.spacesTest;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.components.DiceColor;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.model.PlayerColor;
import it.polimi.ingsw.GC_28.spaces.ProductionAndHarvestSpace;

public class ProductionAndHarvestSpaceTest {
	private ProductionAndHarvestSpace phs;
	private FamilyMember firstPlayer;
	private boolean secondarySpace;
	private boolean harvest;
	private Player p;
	private boolean neutral;
	
	@Before
	public void productionAndHarvestSpace(){
		phs = new ProductionAndHarvestSpace(true, 1);
		p = new Player("Rob", PlayerColor.BLUE);
		firstPlayer = new FamilyMember(p, neutral, DiceColor.BLACK);
		phs.setFree(harvest);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testApplyBonus() {
		//fail("Not yet implemented");
	}

	@Test
	public void testIsHarvest() {
		assertEquals(this.harvest, this.phs.isHarvest());
		//fail("Not yet implemented");
	}


	@After
	public void productionAndHarvestSpace2(){
		phs.addPlayer(firstPlayer);		
	}
	
	@Test
	public void testGetFirstPlayer() {
		assertEquals(this.firstPlayer, this.phs.getFirstPlayer());
		//fail("Not yet implemented");
	}

	@Test
	public void testIsSecondarySpace() {
		assertEquals(this.secondarySpace, this.phs.isSecondarySpace());
		//fail("Not yet implemented");
	}
	
	@After
	public void productionAndHarvestSpace1(){
		phs.freeFirstPlayer();
	}
	
	@Test
	public void testFreeFirstPlayer() {
		assertEquals(null, this.phs.getFirstPlayer());
		//fail("Not yet implemented");
	}
}
