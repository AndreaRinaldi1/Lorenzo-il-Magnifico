package it.polimi.ingsw.GC_28.spacesTest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.boards.GameBoard;
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
	private ArrayList<FamilyMember> players1 = new ArrayList<FamilyMember>();
	private int actionValue = 1;
	private Space s;
	
	private FamilyMember fm;
	private FamilyMember fm1;
	private Player p;
	private Player p1;
	private GameModel gameModel;
	private GameBoard gameBoard; 
	private List<Player> players = new ArrayList<>();
	private Game g;
	
	@Before
	public void space(){
		s = new Space(){};
		p = new Player("Rob", PlayerColor.YELLOW);
		p1 = new Player("Rob", PlayerColor.BLUE);
		fm = new FamilyMember(p, neutral, DiceColor.ORANGE);
		fm1 = new FamilyMember(p, neutral, DiceColor.ORANGE);
		
		players.add(p);
		players.add(p1);
		gameBoard = new GameBoard();
		gameModel = new GameModel(gameBoard, players);
		g = new Game(gameModel); 
		s.setFree(free);
		s.addPlayer(fm);
		players1.add(fm);
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
		assertArrayEquals(this.players1.toArray(), 
				this.s.getPlayer().toArray());
		//fail("Not yet implemented");
	}
	
	//Test with space not free
	@Test
	public void testGetPlayer1(){
		s.addPlayer(fm1);
		assertArrayEquals(this.players1.toArray(), this.s.getPlayer().toArray());
	}
	
	@Test
	public void testGetActionValue() {
		assertEquals(this.actionValue, 
				this.s.getActionValue());
		//fail("Not yet implemented");
	}

}
