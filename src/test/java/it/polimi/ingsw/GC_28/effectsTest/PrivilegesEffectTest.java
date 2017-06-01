package it.polimi.ingsw.GC_28.effectsTest;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.effects.PrivilegesEffect;

public class PrivilegesEffectTest {
	private PrivilegesEffect pe;
	private int numberOfCouncilPrivileges = 2;
	private boolean different;
	
	@Before
	public void privilegesEffect(){
		pe = new PrivilegesEffect();
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testApply() {
		
	}

	@Test
	public void testIsDifferent() {
		pe.setDifferent(different);
		assertEquals(this.different, this.pe.isDifferent());
	}

	@Test
	public void testGetNumberOfCouncilPrivileges() {
		pe.setNumberOfCouncilPrivileges(numberOfCouncilPrivileges);
		assertEquals(this.numberOfCouncilPrivileges, this.pe.getNumberOfCouncilPrivileges());
	}

}
