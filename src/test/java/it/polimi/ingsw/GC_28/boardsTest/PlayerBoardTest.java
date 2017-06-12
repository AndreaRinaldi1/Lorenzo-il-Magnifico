package it.polimi.ingsw.GC_28.boardsTest;

import static org.junit.Assert.*;
import de.vandermeer.asciitable.AsciiTable;

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
import it.polimi.ingsw.GC_28.components.*;

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
	
	


	
	@Before
	public void playerBoard(){
		bonusTile = new BonusTile();
		territories = new ArrayList<>();
		
		resources = new EnumMap<ResourceType, Integer>(ResourceType.class);
		for(ResourceType resType : ResourceType.values()){
			resources.put(resType, 0);
		}
		resource = Resource.of(resources);
		this.pb = new PlayerBoard(bonusTile, resource);
		
		
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	@Test
	public void testDisplay(){
		StringBuilder ret = new StringBuilder();
		String retLine = "------------------------\n";
		ret.append("PLAYER BOARD\n");
		
		AsciiTable territoryTab = new AsciiTable();
		territoryTab.addRule();
		territoryTab.addRow("Territory Cards: ");
		for(int j = 0; j < territories.size(); j++){
			territoryTab.addRule();
			territoryTab.addRow(territories.get(j).toString());
		}
		territoryTab.addRule();
		String terTab = territoryTab.render() + "\n";
		ret.append(terTab);
		
		AsciiTable buildTab = new AsciiTable();
		buildTab.addRule();
		buildTab.addRow("Building Cards: ");
		for(int j = 0; j < buildings.size(); j++){
			buildTab.addRule();
			buildTab.addRow(buildings.get(j).toString());
		}
		buildTab.addRule();
		String build = buildTab.render() + "\n";
		ret.append(build);
		
		AsciiTable charTab = new AsciiTable();
		charTab.addRule();
		charTab.addRow("Character Cards: ");
		for(int j = 0; j < characters.size(); j++){
			charTab.addRule();
			charTab.addRow(characters.get(j).toString());
		}
		charTab.addRule();
		String charT = charTab.render() + "\n";
		ret.append(charT);
		
		AsciiTable ventTab = new AsciiTable();
		ventTab.addRule();
		ventTab.addRow("Ventures Cards: ");
		for(int j = 0; j < ventures.size(); j++){
			ventTab.addRule();
			ventTab.addRow(ventures.get(j).toString());
		}
		ventTab.addRule();
		String vent = ventTab.render() + "\n";
		ret.append(vent);
		
		ret.append("Resources: \n");
		AsciiTable res = new AsciiTable();
		res.addRule();
		res.addRow(ResourceType.COIN, ResourceType.WOOD, ResourceType.STONE, ResourceType.SERVANT, ResourceType.MILITARYPOINT, ResourceType.VICTORYPOINT, ResourceType.FAITHPOINT );
		res.addRule();
		res.addRow(pb.getResources().getResource().get(ResourceType.COIN), 
				pb.getResources().getResource().get(ResourceType.WOOD), 
				pb.getResources().getResource().get(ResourceType.STONE), 
				pb.getResources().getResource().get(ResourceType.SERVANT), 
				pb.getResources().getResource().get(ResourceType.MILITARYPOINT), 
				pb.getResources().getResource().get(ResourceType.VICTORYPOINT), 
				pb.getResources().getResource().get(ResourceType.FAITHPOINT));
		res.addRule();
		ret.append(res.render() + "\n");
		//ret.append(resources.toString()); 
		ret.append("\n" + retLine);
		
		assertEquals(ret.toString(), this.pb.display());
	}
	
	@Test
	public void testGetTerritories() {
		territories.add(t);
		pb.addCard(t);
		assertArrayEquals(this.territories.toArray(), this.pb.getTerritories().toArray());
		//fail("Not yet implemented");
	}

	@Test
	public void testGetBuildings() {
		buildings.add(b);
		pb.addCard(b);
		assertArrayEquals(this.buildings.toArray(), this.pb.getBuildings().toArray());
		//fail("Not yet implemented");
	}

	@Test
	public void testGetCharacters() {
		characters.add(c);
		pb.addCard(c);
		assertArrayEquals(this.characters.toArray(), this.pb.getCharacters().toArray());
		//fail("Not yet implemented");
	}

	@Test
	public void testGetVentures() {
		ventures.add(v);
		pb.addCard(v);
		assertArrayEquals(this.ventures.toArray(), this.pb.getVentures().toArray());
		//fail("Not yet implemented");
	}

	@Test
	public void testGetBonusTile() {
		assertEquals(true, this.pb.getBonusTile().equals(bonusTile));
		//fail("Not yet implemented");
	}


	@Test
	public void testGetResources() {
		pb.setResources(resource);
		assertEquals(this.resource, this.pb.getResources());
		//fail("Not yet implemented");
	}

}
