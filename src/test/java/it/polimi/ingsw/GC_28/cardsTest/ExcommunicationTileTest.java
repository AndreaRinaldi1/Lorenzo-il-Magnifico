package it.polimi.ingsw.GC_28.cardsTest;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.cards.ExcommunicationTile;
import it.polimi.ingsw.GC_28.effects.DiscountEffect;
import it.polimi.ingsw.GC_28.effects.Effect;

public class ExcommunicationTileTest {

	private ExcommunicationTile et;
	private int era;
	private Effect effect;
	
	@Before
	public void excommunicationTile(){
		et = new ExcommunicationTile();
		era = 2;
		effect = new DiscountEffect();
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testGetEra() {
		et.setEra(era);
		assertEquals(era, this.et.getEra());
	}

	@Test
	public void testGetEffect() {
		et.setEffect(effect);
		assertEquals(effect, this.et.getEffect());
	}

}
