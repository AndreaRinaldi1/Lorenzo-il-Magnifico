package it.polimi.ingsw.GC_28.boardsTest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.boards.FinalBonus;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;

public class FinalBonusTest {
	
	private FinalBonus fb;
	private FinalBonus fb2;
	private List<Resource> finalTerritoriesBonus;
	private List<Resource> finalCharactersBonus;
	private List<Resource> resourceForTerritories;
	private int resourceFactor;
	
	private Resource bonus, rft;
	EnumMap<ResourceType, Integer> resource, resource1;

	private List<Resource> finalMilitaryTrack2 = new ArrayList<>();
	private List<Resource> faithPointTrack = new ArrayList<>();
	
	@Before
	public void finalBonus(){
		FinalBonus.setFinalBonus(fb);
		fb = FinalBonus.instance();
		finalTerritoriesBonus = new ArrayList<>();
		finalCharactersBonus = new ArrayList<>();
		resourceForTerritories = new ArrayList<>();
		resourceFactor = 3;
		fb.setResourceFactor(resourceFactor);
		
 		resource = new EnumMap<ResourceType, Integer>(ResourceType.class);
		resource.put(ResourceType.VICTORYPOINT, 2);
		bonus = Resource.of(resource);
		finalTerritoriesBonus.add(bonus);
		finalCharactersBonus.add(bonus);
		fb.setFinalTerritoriesBonus(finalTerritoriesBonus);
		fb.setFinalCharactersBonus(finalCharactersBonus);
		
 		resource1 = new EnumMap<ResourceType, Integer>(ResourceType.class);
		resource1.put(ResourceType.MILITARYPOINT, 2);
		rft = Resource.of(resource1);
		resourceForTerritories.add(rft);
		fb.setResourceForTerritories(resourceForTerritories);
		
		finalMilitaryTrack2.add(bonus);
		fb.setFinalMilitaryTrack(finalMilitaryTrack2);
		
		faithPointTrack.add(bonus);
		fb.setFaithPointTrack(faithPointTrack);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	@Test
	public void testGetResourceFactor() {
		assertEquals(resourceFactor, this.fb.getResourceFactor());
		//fail("Not yet implemented");
	}

	@Test
	public void testGetFinalTerritoriesBonus() {
		assertArrayEquals(this.finalTerritoriesBonus.toArray(),
				this.fb.getFinalTerritoriesBonus().toArray());
		//fail("Not yet implemented");
	}

	@Test
	public void testGetFinalCharactersBonus() {
		assertArrayEquals(this.finalCharactersBonus.toArray(),
				this.fb.getFinalCharactersBonus().toArray());
		//fail("Not yet implemented");
	}

	@Test
	public void testGetResourceForTerritories() {
		assertArrayEquals(this.resourceForTerritories.toArray(),
				this.fb.getResourceForTerritories().toArray());
		//fail("Not yet implemented");
	}
	
	@Test
	public void testGetFinalMilitaryTrack(){
		assertEquals(this.finalMilitaryTrack2, this.fb.getFinalMilitaryTrack());
	}
	
	@Test
	public void testGetFaithPointTrack(){
		assertEquals(this.faithPointTrack, this.fb.getFaithPointTrack());
	}
}
