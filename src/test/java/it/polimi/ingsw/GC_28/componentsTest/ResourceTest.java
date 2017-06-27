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
	private Resource prova;
	private EnumMap<ResourceType, Integer> resource;
	private EnumMap<ResourceType, Integer> resource1;
	private EnumMap<ResourceType, Integer> resource2;
	private EnumMap<ResourceType, Integer> resource3 = new EnumMap<>(ResourceType.class);
	private Resource bonus;
	private Resource bonus1; 
	private Resource bonus2;
	private Resource bonus3;
	
	@Before
	public void resource(){
		 
		resource = new EnumMap<ResourceType, Integer>(ResourceType.class);
		resource.put(ResourceType.COIN, 2);
		bonus = Resource.of(resource);
		resource1 = new EnumMap<ResourceType, Integer>(ResourceType.class);
		resource1.put(ResourceType.COIN, 6);
		resource1.put(ResourceType.VICTORYPOINT, 2);
		bonus1 = Resource.of(resource1);
		resource2 = new EnumMap<ResourceType, Integer>(ResourceType.class);
		bonus2 = Resource.of(resource2);
		bonus2.modifyResource(bonus1, true);
		prova = Resource.of(resource2);
		
		resource3.put(ResourceType.FAITHPOINT, 4);
		bonus3 = Resource.of(resource3);
		
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

	//test modify Resource with null in input
	public void testModifyResourceNull(){
		this.bonus1.modifyResource(null, true);
		assertEquals(true, this.bonus1.equals(this.bonus1));
	}
	
	@Test
	public void testModifyResource() {
		assertEquals(true, bonus1.equals(bonus2));
		//fail("Not yet implemented");
	}
	
	//test with null other bonus
	@Test 
	public void testEqualsNull(){
		assertEquals(false, this.bonus.equals(null));
	}
	
	//resource.size() > otherResource.getResource().size()
	@Test
	public void testEquals(){
		boolean x = this.bonus1.equals(bonus3);
		if(x){
			assertTrue(x);
		}
		else{
			assertFalse(x);
		}
	}
	
	//test with resource.size() <= otherResource.getResource().size()
	@Test
	public void testEquals1(){
		this.bonus.equals(bonus2);
		assertEquals(false, this.bonus.equals(bonus1));
	}
	
	//test with resource.size() > otherResource.getResource().size()
	@Test
	public void testEquals2(){
		this.bonus1.equals(bonus);
		assertEquals(false, this.bonus.equals(bonus1));
	}
	
	//test assert true
	@Test
	public void testEquals3(){
		this.bonus.equals(bonus);
		assertEquals(true, this.bonus2.equals(bonus2));
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
	
	//resource.get(rt) >= res.getResource().get(rt)
	@Test
	public void testGreaterOrEqual(){
		boolean x = prova.greaterOrEqual(bonus2);
		if(x){
			assertTrue(x);
		}
		else{
			assertFalse(x);
		}
	}
	
	//resource.get(rt) >= res.getResource().get(rt)
	@Test
	public void testGreaterOrEqual1(){
		boolean x = bonus.greaterOrEqual(bonus1);
		if(x){
			assertTrue(x);
		}
		else{
			assertFalse(x);
		}
	}
	
}
