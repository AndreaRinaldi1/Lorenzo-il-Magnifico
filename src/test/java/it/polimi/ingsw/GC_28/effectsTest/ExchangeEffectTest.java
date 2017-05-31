package it.polimi.ingsw.GC_28.effectsTest;

import static org.junit.Assert.*;

import java.util.EnumMap;
import java.util.HashMap;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.components.CouncilPrivilege;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.effects.ExchangeEffect;
import it.polimi.ingsw.GC_28.effects.PrivilegesEffect;
import it.polimi.ingsw.GC_28.spaces.PrivilegesSpace;

public class ExchangeEffectTest {
	private ExchangeEffect exchange;
	private Resource firstCost;
	private Resource secondCost;
	private boolean alternative;
	private Resource firstBonus;
	private Resource secondBonus;
	private Resource privilegeCost;
	private PrivilegesEffect privilegeBonus;
	EnumMap<ResourceType, Integer> resource;
	EnumMap<ResourceType, Integer> resource1;
	EnumMap<ResourceType, Integer> resource2;
	EnumMap<ResourceType, Integer> resource3;
	EnumMap<ResourceType, Integer> resource4;
	private PrivilegesSpace ps;
	private CouncilPrivilege cp;
	HashMap<Character, Resource> options;
	private Resource bonus;
	EnumMap<ResourceType, Integer> resource5;


	@Before
	public void exchangeEffect(){
		exchange = new ExchangeEffect();
	
		resource = new EnumMap<>(ResourceType.class);
		resource1 = new EnumMap<>(ResourceType.class);
		resource2 = new EnumMap<>(ResourceType.class);
		resource3 = new EnumMap<>(ResourceType.class);
		resource4 = new EnumMap<>(ResourceType.class);
		
		ps = new PrivilegesSpace(true, 1);
		cp = CouncilPrivilege.instance();

	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testApply() {
	}

	@Test
	public void testGetFirstCost() {
		resource.put(ResourceType.COIN, 2);
		firstCost = Resource.of(resource);
		exchange.setFirstCost(firstCost);
		assertEquals(this.firstCost, this.exchange.getFirstCost());
	}

	@Test
	public void testGetSecondCost() {
		resource1.put(ResourceType.FAITHPOINT, 1);
		secondCost = Resource.of(resource1);
		exchange.setSecondCost(secondCost);
		assertEquals(this.secondCost, this.exchange.getSecondCost());
	}

	@Test
	public void testIsAlternative() {
		exchange.setAlternative(alternative);
		assertEquals(this.alternative, this.exchange.isAlternative());
	}

	@Test
	public void testGetFirstBonus() {
		resource2.put(ResourceType.SERVANT, 1);
		firstBonus = Resource.of(resource2);
		exchange.setFirstBonus(firstBonus);
		assertEquals(this.firstBonus, this.exchange.getFirstBonus());
	}

	@Test
	public void testGetSecondBonus() {
		resource3.put(ResourceType.WOOD, 4);
		secondBonus = Resource.of(resource3);
		exchange.setSecondBonus(secondBonus);
		assertEquals(this.secondBonus, this.exchange.getSecondBonus());
	}

	@Test
	public void testGetPrivilegeCost() {
		resource4.put(ResourceType.COIN, 4);
		privilegeCost = Resource.of(resource4);
		exchange.setPrivilegeCost(privilegeCost);
		assertEquals(this.privilegeCost, this.exchange.getPrivilegeCost());
	}

	@Test
	public void testGetPrivilegeBonus() {
		options = new HashMap<>();
		resource = new EnumMap<>(ResourceType.class);
		resource.put(ResourceType.COIN, 3);
		bonus = Resource.of(resource);
		options.put('c', bonus);
		cp.setOptions(options);
		ps.setBonus(privilegeBonus);
	}

}
