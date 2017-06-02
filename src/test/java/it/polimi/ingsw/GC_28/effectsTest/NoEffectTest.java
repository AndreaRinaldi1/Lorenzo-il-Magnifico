package it.polimi.ingsw.GC_28.effectsTest;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.components.DiceColor;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.effects.NoEffect;
import it.polimi.ingsw.GC_28.model.Game;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.model.PlayerColor;

public class NoEffectTest {
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private NoEffect ne;
	private Game g;
	private FamilyMember fm;
	private Player player;
	@Before
	public void noEffect(){
		player = new Player("bob", PlayerColor.BLUE); 
		System.setOut(new PrintStream(outContent));
		ne = new NoEffect();
		fm = new FamilyMember(player, false, DiceColor.ORANGE);
	}
	
	@After
	public void cleanUpStreams() {
	    System.setOut(null);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

/*	@Test
	public void testApply() {
		System.out.println("apply di NoEffect");
		String s = "apply di NoEffect";
		assertEquals(s, "a");
	}
*/
}
