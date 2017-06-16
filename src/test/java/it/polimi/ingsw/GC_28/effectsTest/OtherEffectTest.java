package it.polimi.ingsw.GC_28.effectsTest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.client.ClientWriter;
import it.polimi.ingsw.GC_28.components.DiceColor;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.effects.*;
import it.polimi.ingsw.GC_28.effects.OtherEffect;
import it.polimi.ingsw.GC_28.model.Game;
import it.polimi.ingsw.GC_28.model.GameModel;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.model.PlayerColor;

public class OtherEffectTest {
	private OtherEffect other;
	private EffectType type = EffectType.SKIPROUNDEFFECT;
	
	private Player  p = new Player("nome", PlayerColor.RED);
	private List<Player> l = new ArrayList<>();
	private GameModel gameModel = new GameModel(new GameBoard(),l );
	private FamilyMember fm = new FamilyMember(p, false, DiceColor.ORANGE);
	private Game g = new Game(gameModel);
	private ClientWriter w;
	
	@Before
	public void otherEffectTest(){
		other = new OtherEffect();
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testApply() {
		//fail("Not yet implemented");
		this.other.apply(p, w);
	}
	
	@Test
	public void testApply2(){
		this.other.apply(fm, w);
	}
	
	@Test
	public void testIsPresence() {
		other.setType(type);;
		assertEquals(type, this.other.getType());
	}

}
