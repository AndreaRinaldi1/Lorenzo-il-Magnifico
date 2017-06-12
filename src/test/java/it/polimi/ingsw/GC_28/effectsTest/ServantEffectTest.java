package it.polimi.ingsw.GC_28.effectsTest;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.effects.ServantEffect;

public class ServantEffectTest {

	private ServantEffect servantEffect;
	private int numberOfServant;
	private int increment;
	
	@Before
	public void servantEffect(){
		servantEffect = new ServantEffect();
		servantEffect.setIncrement(increment);
		servantEffect.setNumberOfServant(numberOfServant);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testApplyPlayerGame() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetNumberOfServant() {
		assertEquals(this.numberOfServant, this.servantEffect.getNumberOfServant());
	}

	@Test
	public void testGetIncrement() {
		assertEquals(this.increment, this.servantEffect.getIncrement());
	}

}
