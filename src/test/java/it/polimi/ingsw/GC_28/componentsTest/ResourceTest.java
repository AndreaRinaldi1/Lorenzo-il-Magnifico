package it.polimi.ingsw.GC_28.componentsTest;

import static org.junit.Assert.*;

import java.util.EnumMap;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.components.*;

public class ResourceTest {
	private EnumMap<ResourceType, Integer> resource;
	private Resource bonus;
	
	@Before
	public void resource(){
		resource = new EnumMap<ResourceType, Integer>(ResourceType.class);
		resource.put(ResourceType.STONE, 2);
		bonus = Resource.of(resource);
		
	}
	
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testOf() {
		assertEquals(this.bonus, Resource.of(resource));
		//fail("Not yet implemented");
	}

	@Test
	public void testGetResource() {
		//fail("Not yet implemented");
	}

	@Test
	public void testModifyResource() {
		//fail("Not yet implemented");
	}

}
