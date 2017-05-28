package it.polimi.ingsw.GC_28.componentsTest;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.components.*;

public class ResourceTest {
	private Map<ResourceType, Integer> resource;
	
	
	@Before
	public void resource(){
		resource.put(ResourceType.MILITARYPOINT, 1);
	}
	
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testOf() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetResource() {
		fail("Not yet implemented");
	}

	@Test
	public void testModifyResource() {
		fail("Not yet implemented");
	}

}
