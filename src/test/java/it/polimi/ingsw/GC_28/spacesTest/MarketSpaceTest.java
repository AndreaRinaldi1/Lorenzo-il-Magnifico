package it.polimi.ingsw.GC_28.spacesTest;

import static org.junit.Assert.*;

import java.util.EnumMap;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.effects.ResourceEffect;
import it.polimi.ingsw.GC_28.spaces.MarketSpace;

public class MarketSpaceTest {
	private MarketSpace ms;
	private ResourceEffect bonus;
	private Resource r;
	EnumMap<ResourceType, Integer> resource;
	
	@Before
	public void marketSpace(){
		ms = new MarketSpace(true, 1);
		bonus = new ResourceEffect();
		resource = new EnumMap<>(ResourceType.class);
		resource.put(ResourceType.COIN, 5);
		r = Resource.of(resource);
		bonus.setResourceBonus(r);
		ms.setBonus(bonus);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testApplyBonus() {
		//fail("Not yet implemented");
	}

	@Test
	public void testGetBonus() {
		assertEquals(this.bonus, this.ms.getBonus());
	}

}
