package it.polimi.ingsw.GC_28.spacesTest;

import static org.junit.Assert.*;

import java.util.EnumMap;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.components.DiceColor;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.effects.ResourceEffect;
import it.polimi.ingsw.GC_28.model.Game;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.model.PlayerColor;
import it.polimi.ingsw.GC_28.spaces.MarketSpace;

public class MarketSpaceTest {
	private MarketSpace ms;
	private ResourceEffect bonus;
	private Resource r;
	EnumMap<ResourceType, Integer> resource;
	
	private Game g;
	private FamilyMember fm;
	private Player p;
	private boolean neutral;
	
	
	@Before
	public void marketSpace(){
		ms = new MarketSpace(true, 1);
		bonus = new ResourceEffect();
		resource = new EnumMap<>(ResourceType.class);
		resource.put(ResourceType.COIN, 5);
		r = Resource.of(resource);
		bonus.setResourceBonus(r);
		ms.setBonus(bonus);
		
		g = new Game(); 
		p = new Player("Pippo", PlayerColor.YELLOW);
		fm = new FamilyMember(p, neutral, DiceColor.ORANGE);

	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testApplyBonus() {
		//assertEquals();
		//fail("Not yet implemented");
	}

	@Test
	public void testGetBonus() {
		assertEquals(this.bonus, this.ms.getBonus());
	}

}
