package it.polimi.ingsw.GC_28.effectsTest;

import static org.junit.Assert.*;

import java.util.EnumMap;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.cards.CardType;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.effects.MultiplierEffect;

public class MultiplierEffectTest {
	private MultiplierEffect me;
	private Resource resourceCost;
	EnumMap<ResourceType, Integer> resource;
	private CardType cardType;
	private Resource resourceBonus;
	EnumMap<ResourceType, Integer> resource1;
	
	@Before
	public void multiplierEffect(){
		me = new MultiplierEffect();
		resource = new EnumMap<>(ResourceType.class);
		resource1 = new EnumMap<>(ResourceType.class);		
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testApply() {
		//fail("Not yet implemented");
	}

	@Test
	public void testGetResourceCost() {
		resource.put(ResourceType.COIN, 1);
		resourceCost = Resource.of(resource); 
		me.setResourceCost(resourceCost);
		assertEquals(this.resourceCost, this.me.getResourceCost());
	}

	@Test
	public void testGetResourceBonus() {
		resource1.put(ResourceType.SERVANT, 1);
		resourceBonus = Resource.of(resource1);
		me.setResourceBonus(resourceBonus);
		assertEquals(this.resourceBonus, this.me.getResourceBonus());
	}

	@Test
	public void testGetCardType() {
		me.setCardType(CardType.BUILDING);
		assertEquals(CardType.BUILDING, this.me.getCardType());
	}

}
