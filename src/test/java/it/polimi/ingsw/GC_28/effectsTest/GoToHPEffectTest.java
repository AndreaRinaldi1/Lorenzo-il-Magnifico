  package it.polimi.ingsw.GC_28.effectsTest;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.effects.GoToHPEffect;

public class GoToHPEffectTest {
	private GoToHPEffect gt;
	private int actionValue = 4;
	private boolean production;
	private boolean harvest;
	
	@Before
	public void goToHPEffect(){
		gt = new GoToHPEffect();
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testApply() {
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
