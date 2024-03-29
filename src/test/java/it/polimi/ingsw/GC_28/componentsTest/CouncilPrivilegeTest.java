package it.polimi.ingsw.GC_28.componentsTest;

import static org.junit.Assert.*;

import java.lang.Character;

import java.util.EnumMap;
import java.util.HashMap;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;


import it.polimi.ingsw.GC_28.components.*;

public class CouncilPrivilegeTest {
	private HashMap<Character, Resource> options;
	private CouncilPrivilege cp;
	private Resource resourceBonus;
	EnumMap<ResourceType, Integer> resource;
	
	@Before
	public void councilPrivilege(){
		CouncilPrivilege.setCouncilPrivilege(cp);
		cp = CouncilPrivilege.instance();
		resource = new EnumMap<>(ResourceType.class);
		resource.put(ResourceType.COIN, 5);
		resourceBonus = Resource.of(resource);
		options = new HashMap<Character, Resource>();
		options.put('c', resourceBonus);
		cp.setOptions(options);
	}
	
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	@Test
	public void testInstance() {
		assertEquals(this.cp, cp = CouncilPrivilege.instance());
	}
	

	@Test
	public void testGetOptions() {
		assertEquals(this.options, this.cp.getOptions());
		//assertEquals(this.options, this.cp.getOptions());
		//assertEquals(true, this.instance.getOptions().equals(options));
	}
}
