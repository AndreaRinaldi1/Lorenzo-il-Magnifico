package it.polimi.ingsw.GC_28.componentsTest;

import static org.junit.Assert.*;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.components.*;

public class ResourceTest {
	private EnumMap<ResourceType, Integer> resource, resource1, resource2;
	private Resource bonus, bonus1, bonus2, bonus3;
	
	@Before
	public void resource(){
		 
		resource = new EnumMap<ResourceType, Integer>(ResourceType.class);
		resource.put(ResourceType.STONE, 2);
		bonus = Resource.of(resource);
		bonus3 = Resource.of(resource);
		resource1 = new EnumMap<ResourceType, Integer>(ResourceType.class);
		resource1.put(ResourceType.FAITHPOINT, 6);
		bonus1 = Resource.of(resource1);
		resource2 = new EnumMap<ResourceType, Integer>(ResourceType.class);
		bonus2 = Resource.of(resource2);
		bonus2.modifyResource(bonus1, true);
	
	}
	
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testOf(){
		assertEquals(true, this.bonus.equals(Resource.of(resource)));
	}
	
	@Test
	public void testGetResource() {
		assertEquals(this.resource, this.bonus.getResource());
		//fail("Not yet implemented");
	}

	@Test
	public void testModifyResource() {
		assertEquals(true, bonus1.equals(bonus2));
		//fail("Not yet implemented");
	}
	
	@Test 
	public void testEquals(){
		assertEquals(true, this.bonus.equals(bonus3));
	}
	
	@Test
	public void testToString(){
		Set<ResourceType> keySet = resource.keySet();
		StringBuilder s = new StringBuilder();
		
		for(ResourceType resType : keySet){
			s.append(resType.name() + ": " + resource.get(resType) +"\n");
		}
		assertEquals(s.toString(), this.bonus.toString());
	}
}
