package it.polimi.ingsw.GC_28.spacesTest;

import static org.junit.Assert.*;

import java.util.EnumMap;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.components.CouncilPrivilege;
import it.polimi.ingsw.GC_28.components.DiceColor;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.effects.PrivilegesEffect;
import it.polimi.ingsw.GC_28.effects.ResourceEffect;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.model.PlayerColor;
import it.polimi.ingsw.GC_28.spaces.CouncilPalace;

public class CouncilPalaceTest {
	private ResourceEffect bonus1;
	private PrivilegesEffect bonus2;
	private CouncilPalace cp = CouncilPalace.instance();
	
	private Resource r;
	EnumMap<ResourceType, Integer> resource;
	private int numberOfCouncilPrivileges=1;
	
	private FamilyMember fm1;
	private Player p1;
	private FamilyMember fm2;
	private Player p2;
	private FamilyMember[] fmOrder;

	EnumMap<ResourceType, Integer> resource1;
	
	
	@Before
	public void councilPalace(){
		bonus1 = new ResourceEffect();
		bonus2 = new PrivilegesEffect();
		
		resource = new EnumMap<>(ResourceType.class);
		resource.put(ResourceType.COIN, 1);
		r = Resource.of(resource);
		bonus1.setResourceBonus(r);
		cp.setBonus1(bonus1);
		
		resource1 = new EnumMap<>(ResourceType.class);
		cp.setBonus2(bonus2);
		
		fmOrder = new FamilyMember[2];
		p1 = new Player("Rob", PlayerColor.YELLOW);
		fm1 = new FamilyMember(p1, true, DiceColor.NEUTRAL);
		p2 = new Player("A", PlayerColor.YELLOW);
		fm2 = new FamilyMember(p2, true, DiceColor.NEUTRAL);
		
		fmOrder[0] = fm1;
		fmOrder[1] = fm2;
		
		cp.addPlayer(fm1);
		cp.addPlayer(fm2);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testApplyBonus() {
		//fail("Not yet implemented");
	}

	@Test
	public void testGetBonus1() {
		assertEquals(this.bonus1, this.cp.getBonus1());
		//fail("Not yet implemented");
	}

	@Test
	public void testGetBonus2() {
		assertEquals(this.bonus2, this.cp.getBonus2());
		//fail("Not yet implemented");
	}

	@Test
	public void testGetPlayerOrder() {
		assertArrayEquals(this.fmOrder, this.cp.getPlayerOrder().toArray());
		//fail("Not yet implemented");
	}

}
