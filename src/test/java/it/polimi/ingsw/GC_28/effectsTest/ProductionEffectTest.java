package it.polimi.ingsw.GC_28.effectsTest;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.effects.ExchangeEffect;
import it.polimi.ingsw.GC_28.effects.MultiplierEffect;
import it.polimi.ingsw.GC_28.effects.PrivilegesEffect;
import it.polimi.ingsw.GC_28.effects.ProductionEffect;
import it.polimi.ingsw.GC_28.effects.ResourceEffect;

public class ProductionEffectTest {
	private ProductionEffect prd;
	private int productionValue = 4;
	private ResourceEffect resourceBonus;
	private ExchangeEffect exchangeBonus;
	private MultiplierEffect multiplierEffect;
	private PrivilegesEffect privilegeEffect;

	
	@Before
	public void productionEffect(){
		prd = new ProductionEffect();
		privilegeEffect = new PrivilegesEffect();
		multiplierEffect = new MultiplierEffect();
		exchangeBonus = new ExchangeEffect();
		resourceBonus = new ResourceEffect();
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testApply() {
		
	}

	@Test
	public void testGetPrivilegeEffect() {
		prd.setPrivilegeEffect(privilegeEffect);
		assertEquals(this.privilegeEffect, this.prd.getPrivilegeEffect());
	}

	@Test
	public void testGetMultiplierEffect() {
		prd.setMultiplierEffect(multiplierEffect);
		assertEquals(this.multiplierEffect, this.prd.getMultiplierEffect());
	}

	@Test
	public void testGetProductionValue() {
		prd.setProductionValue(productionValue);
		assertEquals(this.productionValue, this.prd.getProductionValue());
	}

	@Test
	public void testGetResourceBonus() {
		prd.setResourceBonus(resourceBonus);
		assertEquals(this.resourceBonus, this.prd.getResourceBonus());
	}

	@Test
	public void testGetExchangeBonus() {
		prd.setExchangeBonus(exchangeBonus);
		assertEquals(this.exchangeBonus, this.prd.getExchangeBonus());
	}

}
