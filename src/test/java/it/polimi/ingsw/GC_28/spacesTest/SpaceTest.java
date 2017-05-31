package it.polimi.ingsw.GC_28.spacesTest;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.components.DiceColor;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.model.Game;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.model.PlayerColor;
import it.polimi.ingsw.GC_28.spaces.Space;

public class SpaceTest {
	private boolean free = true;
	private boolean neutral = true;
	private ArrayList<FamilyMember> players = new ArrayList<FamilyMember>();
	private int actionValue = 1;
	private Space s;
	
	private FamilyMember fm;
	private Player p;
	
	private Game g;
	
	@Before
	public void space(){
		s = new Space(){};
		p = new Player("Rob", PlayerColor.YELLOW);
		fm = new FamilyMember(p, neutral, DiceColor.ORANGE);
		g = new Game();
		s.setFree(free);
		s.addPlayer(fm);
		players.add(fm);
		s.setActionValue(actionValue);
		s.applyBonus(g, fm);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testIsFree() {
		assertEquals(false, this.s.isFree());
		//fail("Not yet implemented");
	}

	@Test
	public void testGetPlayer() {
		assertArrayEquals(this.players.toArray(), 
				this.s.getPlayer().toArray());
		//fail("Not yet implemented");
	}

	@Test
	public void testGetActionValue() {
		assertEquals(this.actionValue, 
				this.s.getActionValue());
		//fail("Not yet implemented");
	}

}
