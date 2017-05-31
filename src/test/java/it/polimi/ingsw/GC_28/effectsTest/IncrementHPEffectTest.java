package it.polimi.ingsw.GC_28.effectsTest;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.effects.IncrementHPEffect;

public class IncrementHPEffectTest {
	private IncrementHPEffect incHP;
	private int increment = 3;
	private boolean production;
	private boolean harvest;
	
	@Before
	public void incrementHPEffect(){
		incHP = new IncrementHPEffect();
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testApply() {
		//fail("Not yet implemented");
	}

	@Test
	public void testGetIncrement() {
		incHP.setIncrement(increment);
		assertEquals(this.increment, this.incHP.getIncrement());
	}

	@Test
	public void testIsProduction() {
		incHP.setProduction(production);
		assertEquals(this.production, this.incHP.isHarvest());
	}

	@Test
	public void testIsHarvest() {
		incHP.setHarvest(harvest);
		assertEquals(this.harvest, this.incHP.isHarvest());
	}

}
