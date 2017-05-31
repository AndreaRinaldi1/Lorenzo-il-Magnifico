package it.polimi.ingsw.GC_28.effectsTest;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.cards.CardType;
import it.polimi.ingsw.GC_28.effects.*;

public class IncrementCardEffect {
	private it.polimi.ingsw.GC_28.effects.IncrementCardEffect ic;
	private int increment = 2;
	private boolean discountPresence;
	private DiscountEffect discount;
	
	@Before
	public void incrementCardEffect(){
		ic = new it.polimi.ingsw.GC_28.effects.IncrementCardEffect();
		discount = new DiscountEffect();
		
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
		ic.setIncrement(increment);
		assertEquals(this.increment, this.ic.getIncrement());
	}

	@Test
	public void testGetCardType() {
		ic.setCardType(CardType.BUILDING);
		assertEquals(CardType.BUILDING, this.ic.getCardType());
	}

	@Test
	public void testIsDiscountPresence() {
		ic.setDiscountPresence(discountPresence);
		assertEquals(this.discountPresence, this.ic.isDiscountPresence());
	}

	@Test
	public void testGetDiscount() {
		ic.setDiscount(discount);
		assertEquals(this.discount, this.ic.getDiscount());
	}

}
