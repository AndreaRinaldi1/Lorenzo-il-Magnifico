package it.polimi.ingsw.GC_28.cardsTest;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.EnumMap;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.cards.*;
import it.polimi.ingsw.GC_28.components.*;

public class CardTest { 
	private Card card;
	private String name = "Pippo";
	private int IDNumber = 2;
	private int era = 1;
	private Resource cost;
	EnumMap<ResourceType, Integer> resource;
	

	@Before
	public void card ()	{
		this.card = new Card(this.name, this.IDNumber, this.era);
		this.card.setColor(Color.BLACK);
		resource = new EnumMap<ResourceType, Integer>(ResourceType.class);
		resource.put(ResourceType.COIN, 5);
		cost = Resource.of(resource);
		this.card.setCost(cost);
	}
	
	@Test
	public void testGetName() {
		assertEquals("Pippo", this.card.getName());
	}

	@Test
	public void testGetIDNumber() {
		assertEquals(2, this.card.getIDNumber());
	}

	@Test
	public void testGetEra() {
		assertEquals(1, this.card.getEra());
	}

	@Test
	public void testGetColor() {
		assertEquals(Color.BLACK, this.card.getColor());
	}

	@Test
	public void testGetCost() {
		assertEquals(cost, this.card.getCost());
	}

	@Test
	public void testToString() {
		assertEquals("Name: Pippo" + "\n" /*+ "IDNumber: 2" + "\n" + "Color: "
				+ this.card.getColor().toString() + "\n" 
				+ this.card.getCost().toString()*/, 
				this.card.toString());
	}
	
}
