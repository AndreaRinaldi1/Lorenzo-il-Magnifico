package it.polimi.ingsw.GC_28.effectsTest;

import static org.junit.Assert.*;


import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.cards.CardType;

import it.polimi.ingsw.GC_28.effects.DiscountEffect;
import it.polimi.ingsw.GC_28.effects.TakeCardEffect;




public class TakeCardEffectTest {
	private TakeCardEffect tce;
	private int actionValue = 1;
	private boolean discountPresence;
	private DiscountEffect discount;

	
	
	@Before
	public void takeCardEffect(){
		tce = new TakeCardEffect();
		discount = new DiscountEffect();
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}


	@Test
	public void testGetActionValue() {
		tce.setActionValue(actionValue);
		assertEquals(this.actionValue, this.tce.getActionValue());
	}

	@Test
	public void testGetCardType() {
		tce.setCardType(CardType.VENTURE);
		assertEquals(CardType.VENTURE, this.tce.getCardType());
	}

	@Test
	public void testIsDiscountPresence() {
		tce.setDiscountPresence(discountPresence);
		assertEquals(this.discountPresence, this.tce.isDiscountPresence());
	}

	@Test
	public void testGetDiscount() {
		tce.setDiscount(discount);
		assertEquals(this.discount, this.tce.getDiscount());
	}

}
