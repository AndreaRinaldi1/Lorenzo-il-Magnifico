package it.polimi.ingsw.GC_28.cardsTest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.EnumMap;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.cards.Character;
import it.polimi.ingsw.GC_28.components.*;
import it.polimi.ingsw.GC_28.effects.*;

public class CharacterTest {
	private Character c;
	ArrayList<Effect> immediateEffect; 
	private Effect permanentEffect;
	private DiscountEffect e;
	private Resource discount;
	EnumMap<ResourceType, Integer> resource;
	
	
	@Before
	public void character(){
		e = new DiscountEffect();
		resource = new EnumMap<ResourceType, Integer>(ResourceType.class);
		resource.put(ResourceType.STONE, 2);
		discount = Resource.of(resource);
		e.setDiscount(discount);
		c = new Character("Andrea", 9, 3);
		immediateEffect = new ArrayList<Effect>();
		immediateEffect.add(e);
		c.setImmediateEffect(immediateEffect);
		permanentEffect = e;
		c.setPermanentEffect(permanentEffect);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testGetImmediateEffect() {
		assertEquals(this.immediateEffect, this.c.getImmediateEffect());
	}

	@Test
	public void testGetPermanentEffect() {
		assertEquals(this.permanentEffect, this.c.getPermanentEffect());
	}

}
