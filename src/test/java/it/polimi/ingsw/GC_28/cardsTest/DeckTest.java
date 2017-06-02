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
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.effects.Effect;
import it.polimi.ingsw.GC_28.effects.HarvestEffect;
import it.polimi.ingsw.GC_28.effects.ProductionEffect;
import it.polimi.ingsw.GC_28.effects.ResourceEffect;
import it.polimi.ingsw.GC_28.model.Game;

public class DeckTest {
	
	private Deck d;
	
	private List<Territory> territories = new ArrayList<>();
	private List<Building> buildings = new ArrayList<>();
	private List<Character> characters = new ArrayList<>();
	private List<Venture> ventures = new ArrayList<>();
	
	private Territory t = new Territory("terra", 1, 2);
	private HarvestEffect permanentEffect;
	private Building b = new Building("Palazzo", 42, 1);
	private Character c = new Character("Ciccio", 41, 1);
	private Venture v = new Venture("Ammazzo Tutti", 66, 2);
	
	private String s;
	private String s1;
	private String s2;
	private String s3;
	
	@Before
	public void deck(){
		d = new Deck();
		
		
		permanentEffect =new HarvestEffect();
		territories.add(t);
		t.setPermanentEffect(permanentEffect);
		d.getTerritories().add(t);
		buildings.add(b);
		
		ResourceEffect immediateEffect = new ResourceEffect();
		b.setImmediateEffect(immediateEffect);
		ProductionEffect permanentEffect1 = new ProductionEffect();
		b.setPermanentEffect(permanentEffect1);
		d.getBuildings().add(b);
		
		c.setPermanentEffect(immediateEffect);;
		characters.add(c);
		d.getCharacters().add(c);
		
		v.setPermanentEffect(immediateEffect);
		ventures.add(v);
		d.getVentures().add(v);
		
		s= new String();
		s.equals(this.d.toString());
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testGetTerritories() {
		assertEquals(this.t, 
				this.d.getTerritories().get(0));
		//fail("Not yet implemented");
	}

	@Test
	public void testGetBuildings() {
		assertEquals(this.b, 
				this.d.getBuildings().get(0));
		//fail("Not yet implemented");
	}

	@Test
	public void testGetCharacters() {
		assertEquals(this.c, 
				this.d.getCharacters().get(0));
		//fail("Not yet implemented");
	}

	@Test
	public void testGetVentures() {
		assertEquals(this.v, 
				this.d.getVentures().get(0));
		//fail("Not yet implemented");
	}

	@Test
	public void testToString() {
		assertEquals(this.s, this.d.toString());
	}

}
