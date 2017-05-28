package it.polimi.ingsw.GC_28.cardsTest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.EnumMap;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.cards.*;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.effects.*;

public class TerritoryTest {
	
	private Territory t;
	ArrayList<Effect> immediateEffect;
	private HarvestEffect permanentEffect;
	private Resource bonus;
	private ResourceEffect r;
	EnumMap<ResourceType, Integer> resource;

	
	@Before
	public void territory(){
		r = new ResourceEffect();
 		resource = new EnumMap<ResourceType, Integer>(ResourceType.class);
		resource.put(ResourceType.STONE, 2);
		bonus = Resource.of(resource);
		r.setResourceBonus(bonus);
		t = new Territory("Nick", 2, 1);
		immediateEffect = new ArrayList<Effect>();
		immediateEffect.add(r);
		permanentEffect = new HarvestEffect();
		permanentEffect.setResourceHarvestBonus(bonus);
		t.setImmediateEffect(immediateEffect);
		t.setPermanentEffect(permanentEffect);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testGetImmediateEffect() {
		assertEquals(this.immediateEffect, this.t.getImmediateEffect());
	}

	@Test
	public void testGetPermanentEffect() {
		assertEquals(this.permanentEffect, this.t.getPermanentEffect());
	}

}
