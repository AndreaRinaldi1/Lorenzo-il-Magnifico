package it.polimi.ingsw.GC_28.boardsTest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.boards.BonusTile;
import it.polimi.ingsw.GC_28.boards.PlayerBoard;
import it.polimi.ingsw.GC_28.cards.Building;
import it.polimi.ingsw.GC_28.cards.Character;
import it.polimi.ingsw.GC_28.cards.ExcommunicationTile;
import it.polimi.ingsw.GC_28.cards.Territory;
import it.polimi.ingsw.GC_28.cards.Venture;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;

public class PlayerBoardTest {
	private PlayerBoard pb;
	private BonusTile bonusTile;
	private Resource resource;
	EnumMap<ResourceType, Integer> resources;
	
	private List<Territory> territories;
	private List<Building> buildings = new ArrayList<>();
	private List<Character> characters = new ArrayList<>();
	private List<Venture> ventures = new ArrayList<>();
	
	private Territory t = new Territory("terra", 1, 2);
	private Building b = new Building("Palazzo", 42, 1);
	private Character c = new Character("Ciccio", 41, 1);
	private Venture v = new Venture("Ammazzo Tutti", 66, 2);
	//private ArrayList<ExcommunicationTile> et = new ArrayList<>(); 	TODO ExcommunicationTile class	
	
	private List<Resource> resourceForTerritories = new ArrayList<>();
	private List<Resource> finalBonusTerritories = new ArrayList<>();
	private List<Resource> finalBonusCharacters = new ArrayList<>();
	private int finalBonusResourceFactor;
	
	@Before
	public void playerBoard(){
		bonusTile = new BonusTile();
 		resources = new EnumMap<ResourceType, Integer>(ResourceType.class);
		resources.put(ResourceType.STONE, 2);
		resource = Resource.of(resources);
		pb = new PlayerBoard(bonusTile, resource);
		territories = new ArrayList<>();
		territories.add(t);
		pb.addCard(t);
		buildings.add(b);
		pb.addCard(b);
		characters.add(c);
		pb.addCard(c);
		ventures.add(v);
		pb.addCard(v);
		
		finalBonusTerritories.add(resource);
		pb.setFinalBonusTerritories(finalBonusTerritories);
		finalBonusCharacters.add(resource);
		pb.setFinalBonusCharacters(finalBonusCharacters);
		finalBonusResourceFactor = 3;
		pb.setFinalBonusResourceFactor(finalBonusResourceFactor);
		resourceForTerritories.add(resource);
		pb.setResourceForTerritories(resourceForTerritories);
		
		pb.setResources(resource);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testGetTerritories() {
		assertArrayEquals(this.territories.toArray(), this.pb.getTerritories().toArray());
		//fail("Not yet implemented");
	}

	@Test
	public void testGetBuildings() {
		assertArrayEquals(this.buildings.toArray(), this.pb.getBuildings().toArray());
		//fail("Not yet implemented");
	}

	@Test
	public void testGetCharacters() {
		assertArrayEquals(this.characters.toArray(), this.pb.getCharacters().toArray());
		//fail("Not yet implemented");
	}

	@Test
	public void testGetVentures() {
		assertArrayEquals(this.ventures.toArray(), this.pb.getVentures().toArray());
		//fail("Not yet implemented");
	}

	@Test
	public void testGetBonusTile() {
		assertEquals(true, this.pb.getBonusTile().equals(bonusTile));
		//fail("Not yet implemented");
	}

	@Test
	public void testGetFinalBonusTerritories() {
		assertArrayEquals(this.finalBonusTerritories.toArray(), 
				this.pb.getFinalBonusTerritories().toArray());
		//fail("Not yet implemented");
	}

	@Test
	public void testGetFinalBonusCharacters() {
		assertArrayEquals(this.finalBonusCharacters.toArray(), 
				this.pb.getFinalBonusCharacters().toArray());
		//fail("Not yet implemented");
	}

	@Test
	public void testGetResources() {
		assertEquals(this.resource, this.pb.getResources());
		//fail("Not yet implemented");
	}

	@Test
	public void testGetExcommunicationTile() {
		//fail("Not yet implemented");
	}

	@Test
	public void testGetFinalBonusResourceFactor() {
		assertEquals(3, this.pb.getFinalBonusResourceFactor());
		//fail("Not yet implemented");
	}

	@Test
	public void testGetResourceForTerritories() {
		assertArrayEquals(this.resourceForTerritories.toArray(),
				this.pb.getResourceForTerritories().toArray());
		//fail("Not yet implemented");
	}

}
