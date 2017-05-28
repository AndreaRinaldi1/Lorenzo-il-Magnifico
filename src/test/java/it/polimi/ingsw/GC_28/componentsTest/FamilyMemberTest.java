package it.polimi.ingsw.GC_28.componentsTest;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.components.Dice;
import it.polimi.ingsw.GC_28.components.DiceColor;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.model.PlayerColor;

public class FamilyMemberTest {

	private Player player1;
	private Player player2;
	private FamilyMember fm;
	private FamilyMember fm2;
	private Dice dice1 ;

	@Before
	public void familyMember(){
		player1 = new Player(PlayerColor.BLUE);
		fm = new FamilyMember(player1, false, DiceColor.BLACK);
		player2 = new Player(PlayerColor.RED);
		fm2 = new FamilyMember(player2, true, DiceColor.NEUTRAL);
		dice1 = new Dice(DiceColor.BLACK);

		
		fm2.modifyValue(3);

		fm.setUsed(false);
		fm2.setUsed(true);
	}

	@Test
	public void testGetPlayer() {
		assertEquals(player1, fm.getPlayer());
		assertEquals(player2, fm2.getPlayer());		
		//fail("Not yet implemented");
	}

	@Test
	public void testIsNeutral() {
		assertEquals(false, fm.isNeutral());
		assertEquals(true, fm2.isNeutral());		
		//fail("Not yet implemented");
	}

	@Test
	public void testGetValue() {
		Integer i = 3;
		assertEquals(i , fm2.getValue()); 
		//fail("Not yet implemented");
	}

	@Test
	public void testIsUsed() {
		assertEquals(false, fm.isUsed());
		assertEquals(true, fm2.isUsed());
		//fail("Not yet implemented");
	}

	@Test
	public void testGetDiceColor(){
		assertEquals(this.dice1.getColor(), this.fm.getDiceColor());
	}
}
