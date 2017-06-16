package it.polimi.ingsw.GC_28.spacesTest;

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
import it.polimi.ingsw.GC_28.model.Game;
import it.polimi.ingsw.GC_28.model.GameModel;
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
	private ClientWriter w;
	
	@Before
	public void space(){
		s = new Space(){

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;};
		p = new Player("Rob", PlayerColor.YELLOW);
		fm = new FamilyMember(p, neutral, DiceColor.ORANGE);
		
		List<Player> players = new ArrayList<>();
		players.add(p);
		GameBoard gb = new GameBoard();
		GameModel gm = new GameModel(gb,players);
		
		g = new Game(gm);
	
		g.setCurrentPlayer(p);
		
		s.setFree(free);
		s.addPlayer(fm);

		s.setActionValue(actionValue);
		s.applyBonus(w, fm);
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
		players.add(fm);
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
