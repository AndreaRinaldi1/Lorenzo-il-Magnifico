package it.polimi.ingsw.GC_28.cardsTest;

import static org.junit.Assert.*;

import java.util.EnumMap;

import org.junit.*;

import it.polimi.ingsw.GC_28.cards.*;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.effects.*;

public class BuildingTest {
	private Building building;
	private ResourceEffect immediateEffect, resourceProductionBonus;
	private ProductionEffect permanentEffect;
	private String name = "Pippo";
	private int IDNumber = 2;
	private int era = 1;
	private Resource resourceBonus;
	EnumMap<ResourceType, Integer> resource;
	
	@Before
	public void building(){
		this.building = new Building(this.name, this.IDNumber, this.era);
		resource = new EnumMap<ResourceType, Integer>(ResourceType.class);
		resource.put(ResourceType.COIN, 5);
		resourceBonus = Resource.of(resource);
		immediateEffect = new ResourceEffect();
		permanentEffect = new ProductionEffect();
		this.immediateEffect.setResourceBonus(resourceBonus);
		resourceProductionBonus = immediateEffect;
		this.permanentEffect.setResourceBonus(resourceProductionBonus);
		this.building.setImmediateEffect(immediateEffect);
		this.building.setPermanentEffect(permanentEffect);
	}
	
	@Test
	public void testGetImmediateEffect() {
		assertEquals(this.immediateEffect, this.building.getImmediateEffect());
	}

	@Test
	public void testGetPermanentEffect() {
		assertEquals(this.permanentEffect, this.building.getPermanentEffect());
	}

}
