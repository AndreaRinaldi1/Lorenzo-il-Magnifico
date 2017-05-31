package it.polimi.ingsw.GC_28.effectsTest;

import static org.junit.Assert.*;

import java.util.EnumMap;
import java.util.HashMap;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.components.CouncilPrivilege;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.effects.HarvestEffect;
import it.polimi.ingsw.GC_28.effects.PrivilegesEffect;
import it.polimi.ingsw.GC_28.effects.ResourceEffect;

public class HarvestEffectTest {
	private HarvestEffect he;
	private int harvestValue = 5;
	private ResourceEffect resourceHarvestBonus;
	private PrivilegesEffect councilPrivilegeBonus;
	private CouncilPrivilege cp;
	HashMap<Character, Resource> options;
	private Resource bonus;
	EnumMap<ResourceType, Integer> resource;
	
	@Before
	public void harvestEffect(){
		he = new HarvestEffect();
		resourceHarvestBonus = new ResourceEffect();
		councilPrivilegeBonus = new PrivilegesEffect();
		
		options = new HashMap<>();
		resource = new EnumMap<>(ResourceType.class);
		resource.put(ResourceType.COIN, 3);
		bonus = Resource.of(resource);
		resourceHarvestBonus.setResourceBonus(bonus);
		options.put('c', bonus);
		//cp.setOptions(options);
		
		he.setCouncilPrivilegeBonus(councilPrivilegeBonus);
		he.setHarvestValue(harvestValue);
		he.setResourceHarvestBonus(resourceHarvestBonus);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testApply() {
		//fail("Not yet implemented");
	}

	@Test
	public void testGetHarvestValue() {
		assertEquals(this.harvestValue, this.he.getHarvestValue());
	}

	@Test
	public void testGetResourceHarvestBonus() {
		assertEquals(this.resourceHarvestBonus, this.he.getResourceHarvestBonus());
	}

	@Test
	public void testGetCouncilPrivilegeBonus() {
		assertEquals(this.councilPrivilegeBonus, this.he.getCouncilPrivilegeBonus());
	}

}
