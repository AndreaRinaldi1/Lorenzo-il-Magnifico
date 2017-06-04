package it.polimi.ingsw.GC_28.effectsTest;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import it.polimi.ingsw.GC_28.effects.*;
import it.polimi.ingsw.GC_28.effects.OtherEffect;

public class OtherEffectTest {
	private OtherEffect other;
	private EffectType type = EffectType.SKIPROUNDEFFECT;
	@Before
	public void otherEffectTest(){
		other = new OtherEffect();
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
		other.setType(type);;
		assertEquals(type, this.other.getType());
	}

}
