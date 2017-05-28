package it.polimi.ingsw.GC_28.boardsTest;

import static org.junit.Assert.*;

import java.util.EnumMap;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.boards.*;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.effects.HarvestEffect;
import it.polimi.ingsw.GC_28.effects.ProductionEffect;
import it.polimi.ingsw.GC_28.effects.ResourceEffect;

public class BonusTileTest {
	
	private BonusTile bt;
	private HarvestEffect harvestEffect;
	private ProductionEffect productionEffect;
	private ResourceEffect re;
	private Resource resourceBonus;
	EnumMap<ResourceType, Integer> resource;
	
	@Before
	public void bonusTile(){
		resource = new EnumMap<ResourceType, Integer>(ResourceType.class);
		resource.put(ResourceType.COIN, 5);
		resourceBonus = Resource.of(resource);
		bt = new BonusTile();
		re = new ResourceEffect();
		re.setResourceBonus(resourceBonus);
		harvestEffect = new HarvestEffect();
		productionEffect = new ProductionEffect();
		harvestEffect.setResourceHarvestBonus(re);
		productionEffect.setResourceBonus(re);
		bt.setHarvestEffect(harvestEffect);
		bt.setProductionEffect(productionEffect);
	
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testGetProductionEffect() {
		assertEquals(this.productionEffect, this.bt.getProductionEffect());
	}

	@Test
	public void testGetHarvestEffect() {
		assertEquals(this.harvestEffect, this.bt.getHarvestEffect());
	}

}
