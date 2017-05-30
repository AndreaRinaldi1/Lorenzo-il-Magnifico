package it.polimi.ingsw.GC_28.spacesTest;

import static org.junit.Assert.*;

import java.util.EnumMap;
import java.util.HashMap;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.components.CouncilPrivilege;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.effects.PrivilegesEffect;
import it.polimi.ingsw.GC_28.spaces.PrivilegesSpace;

public class PrivilegesSpaceTest {
	private PrivilegesSpace ps;
	private PrivilegesEffect pe;
	private CouncilPrivilege cp;
	HashMap<Character, Resource> options;
	private Resource bonus;
	EnumMap<ResourceType, Integer> resource;
	
	@Before
	public void privilegesSpace(){
		ps = new PrivilegesSpace(true, 1);
		pe = new PrivilegesEffect();
		cp = CouncilPrivilege.instance();
		
		resource = new EnumMap<>(ResourceType.class);
		resource.put(ResourceType.COIN, 3);
		bonus = Resource.of(resource);
		options.put('c', bonus);
		cp.setOptions(options);
		ps.setBonus(pe);
	}
	
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

/*	@Test
	public void testApplyBonus() {
		fail("Not yet implemented");
	}
*/
	@Test
	public void testGetBonus() {
		assertEquals(this.options, this.);
	}

}
