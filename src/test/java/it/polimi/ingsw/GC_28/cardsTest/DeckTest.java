package it.polimi.ingsw.GC_28.cardsTest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.cards.Building;
import it.polimi.ingsw.GC_28.cards.Character;
import it.polimi.ingsw.GC_28.cards.Deck;
import it.polimi.ingsw.GC_28.cards.Territory;
import it.polimi.ingsw.GC_28.cards.Venture;

public class DeckTest {
	
	private Deck d;
	
	private List<Territory> territories = new ArrayList<>();
	private List<Building> buildings = new ArrayList<>();
	private List<Character> characters = new ArrayList<>();
	private List<Venture> ventures = new ArrayList<>();
	
	private Territory t = new Territory("terra", 1, 2);
	private Building b = new Building("Palazzo", 42, 1);
	private Character c = new Character("Ciccio", 41, 1);
	private Venture v = new Venture("Ammazzo Tutti", 66, 2);
	
	@Before
	public void deck(){
		d = new Deck();
		territories.add(t);
		d.getTerritories().add(t);
		buildings.add(b);
		d.getBuildings().add(b);
		characters.add(c);
		d.getCharacters().add(c);
		ventures.add(v);
		d.getVentures().add(v);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testGetTerritories() {
		assertArrayEquals(this.territories.toArray(), 
				this.d.getTerritories().toArray());
		//fail("Not yet implemented");
	}

	@Test
	public void testGetBuildings() {
		assertArrayEquals(this.buildings.toArray(), 
				this.d.getBuildings().toArray());
		//fail("Not yet implemented");
	}

	@Test
	public void testGetCharacters() {
		assertArrayEquals(this.characters.toArray(), 
				this.d.getCharacters().toArray());
		//fail("Not yet implemented");
	}

	@Test
	public void testGetVentures() {
		assertArrayEquals(this.ventures.toArray(), 
				this.d.getVentures().toArray());
		//fail("Not yet implemented");
	}

}
