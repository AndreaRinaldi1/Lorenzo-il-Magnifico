package it.polimi.ingsw.GC_28.effectsTest;

import static org.junit.Assert.*;

import java.util.EnumMap;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.effects.ResourceEffect;

public class ResourceEffectTest {
	private ResourceEffect re;
	private Resource resourceBonus;
	EnumMap<ResourceType, Integer> resource;
	
	@Before
	public void resourceEffect(){
		re = new ResourceEffect();
		
		resource = new EnumMap<>(ResourceType.class);
		resource.put(ResourceType.STONE, 1);
		resourceBonus = Resource.of(resource);
		re.setResourceBonus(resourceBonus);
		
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testApply() {
	}

	@Test
	public void testGetResourceBonus() {
		assertEquals(this.resourceBonus, this.re.getResourceBonus());
	}

}
