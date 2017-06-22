package it.polimi.ingsw.GC_28.cardsTest;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.cards.Building;
import it.polimi.ingsw.GC_28.cards.Character;
import it.polimi.ingsw.GC_28.cards.Deck;
import it.polimi.ingsw.GC_28.cards.Territory;
import it.polimi.ingsw.GC_28.cards.Venture;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.effects.Effect;
import it.polimi.ingsw.GC_28.effects.HarvestEffect;
import it.polimi.ingsw.GC_28.effects.ProductionEffect;
import it.polimi.ingsw.GC_28.effects.ResourceEffect;
import it.polimi.ingsw.GC_28.view.GameView;

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
	private Venture v = new Venture("Lala", 66, 2);

	private	List<Effect> immediateEffect = new ArrayList<>();
	private ResourceEffect testEffect = new ResourceEffect();
	private EnumMap<ResourceType, Integer> resource = new EnumMap<>(ResourceType.class);
	private Resource bonus;
	private ResourceEffect immediateResourceEffect = new ResourceEffect();
	private	ProductionEffect permanentEffect1 = new ProductionEffect();

	
	private String s;
	private String s1;
	private String s2;
	private String s3;
	
	//for the test of system.out.println
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	
	@Before
	public void deck(){
		d = new Deck();

		resource.put(ResourceType.COIN, 2);
		bonus = Resource.of(resource);
		testEffect.setResourceBonus(bonus);
		immediateEffect.add(testEffect);
		permanentEffect =new HarvestEffect();
		t.setImmediateEffect(immediateEffect);
		t.setPermanentEffect(permanentEffect);
		territories.add(t);
		this.d.getTerritories().add(t);
		
		
		buildings.add(b);
		immediateResourceEffect.setResourceBonus(bonus);
		permanentEffect1.setResourceBonus(immediateResourceEffect);
		b.setImmediateEffect(immediateResourceEffect);
		b.setPermanentEffect(permanentEffect1);
		this.d.getBuildings().add(b);
		
		c.setImmediateEffect(immediateEffect);
		c.setPermanentEffect(immediateResourceEffect);;
		characters.add(c);
		this.d.getCharacters().add(c);
		
		v.setImmediateEffect(immediateEffect);
		v.setPermanentEffect(immediateResourceEffect);
		ventures.add(v);
		this.d.getVentures().add(v);
		
	
		//test System.out
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
		s= new String();
		s.equals(this.d.toString());
		assertEquals(this.s, this.d.toString());
	}

	//System.out for a territory card
/*	public void testTerritoryToString(){
		this.d.getTerritories().add(t);
		System.out.println(this.d.getTerritories().toString());
		
	}
	*/
}
