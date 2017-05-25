package it.polimi.ingsw.GC_28.cardsTest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;

import org.junit.*;

import it.polimi.ingsw.GC_28.cards.*;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.effects.*;

public class BuildingTest {
	private Building building;
	private ResourceEffect immediateEffect;
	private ProductionEffect permanentEffect;
	private Card card;
	private String name = "Pippo";
	private int IDNumber = 2;
	private int era = 1;
	ArrayList<Effect> effects;
	
	@Before
	public void building(){
		this.building = new Building(this.name, this.IDNumber, this.era);
		//effects = new ArrayList<Effect>();
		
		
		this.building.setImmediateEffect(re);
		this.building.setPermanentEffect(permanentEffect);
	}
	
	@Test
	public void testGetImmediateEffect() {
		assertEquals(effects.get(0), this.building.getImmediateEffect());
	}
/*
	@Test
	public void testGetPermanentEffect() {
		assertEquals(permanentEffect.getResourceProductionBonus(), this.building.getPermanentEffect());
	}
*/
}
