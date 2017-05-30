package it.polimi.ingsw.GC_28.cardsTest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.EnumMap;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.cards.*;
import it.polimi.ingsw.GC_28.components.*;
import it.polimi.ingsw.GC_28.effects.*;

public class VentureTest {
	private Venture venture;
	private ArrayList<Effect> immediateEffect = new ArrayList<Effect>();
	private ResourceEffect permanentEffect;
	private boolean alternativeCostPresence = true;
	private Resource alternativeCost;
	private int minimumRequiredMilitaryPoints = 2;
	private Resource cost1;
	private Resource cost2;
	private ResourceEffect r;
	EnumMap<ResourceType, Integer> resource1;
	EnumMap<ResourceType, Integer> resource2;
	
	
	@Before
	public void venture(){
		venture = new Venture("Rob", 3, 1);
		r = new ResourceEffect();
		resource1 = new EnumMap<>(ResourceType.class);
		resource1.put(ResourceType.STONE, 2);
		cost1 = Resource.of(resource1);
		r.setResourceBonus(cost1);
		immediateEffect.add(r);
		this.venture.setImmediateEffect(immediateEffect);
		permanentEffect = r;
		this.venture.setPermanentEffect(permanentEffect);
		this.venture.setAlternativeCostPresence(alternativeCostPresence);
		resource2 = new EnumMap<>(ResourceType.class);
		resource2.put(ResourceType.MILITARYPOINT, 5);
		cost2 = Resource.of(resource2);
		alternativeCost = cost2;
		this.venture.setAlternativeCost(alternativeCost);
		this.venture.setMinimumRequiredMilitaryPoints(minimumRequiredMilitaryPoints);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testGetImmediateEffect() {
		assertEquals(this.immediateEffect, this.venture.getImmediateEffect());
	}

	@Test
	public void testGetPermanentEffect() {
		assertEquals(this.permanentEffect, this.venture.getPermanentEffect());
	}

	@Test
	public void testGetAlternativeCostPresence() {
		assertEquals(this.alternativeCostPresence, this.venture.getAlternativeCostPresence());
	}

	@Test
	public void testGetAlternativeCost() {
		assertEquals(this.alternativeCost, this.venture.getAlternativeCost());
	}

	@Test
	public void testGetMinimumRequiredMilitaryPoints() {
		assertEquals(this.minimumRequiredMilitaryPoints, this.venture.getMinimumRequiredMilitaryPoints());
	}

}
