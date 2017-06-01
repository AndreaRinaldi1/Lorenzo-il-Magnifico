package it.polimi.ingsw.GC_28.effectsTest;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.effects.NoCellBonusEffect;

public class NoCellBonusEffectTest {
	private NoCellBonusEffect nce;
	private boolean presence;
	
	@Before
	public void noCellBonus(){
		nce = new NoCellBonusEffect();
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

/*	@Test
	public void testApply() {
		fail("Not yet implemented");
	}
*/
	
	@Test
	public void testIsPresence() {
		nce.setPresence(presence);
		assertEquals(this.presence, this.nce.isPresence());
	}

}
