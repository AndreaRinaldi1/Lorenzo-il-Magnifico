package it.polimi.ingsw.GC_28.effectsTest;

import static org.junit.Assert.*;

import java.util.EnumMap;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.boards.BonusTile;
import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.boards.PlayerBoard;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.effects.PrivilegesEffect;
import it.polimi.ingsw.GC_28.model.Game;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.model.PlayerColor;

public class PrivilegesEffectTest {
	private PrivilegesEffect pe;
	private int numberOfCouncilPrivileges = 2;
	private boolean different;
	
	private	Game game;
	private FamilyMember familyMember;
	private Player player;
	private PlayerBoard pb;
	private BonusTile bt;
	private Resource res;
	private EnumMap<ResourceType, Integer> w;
	private GameBoard gb;
	
	@Before
	public void privilegesEffect(){
		pe = new PrivilegesEffect();
		game = new Game();
		player = new Player("gino", PlayerColor.GREEN);
		
		w = new EnumMap<ResourceType, Integer>(ResourceType.class);
		for(ResourceType resType : ResourceType.values()){
			w.put(resType, 0);
		}
		res = Resource.of(w);
		bt = new BonusTile();
		pb = new PlayerBoard(bt, res);
		player.setBoard(pb);
	
		gb = new GameBoard();
		game.setGameBoard(gb);
		game.setCurrentPlayer(player);
	
	}
	

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testApply() {
		pe.setNumberOfCouncilPrivileges(numberOfCouncilPrivileges);
		different = true;
		pe.setDifferent(different);
		
		pe.apply(familyMember, game);
		
		
	}

	@Test
	public void testIsDifferent() {
		pe.setDifferent(different);
		assertEquals(this.different, this.pe.isDifferent());
	}

	@Test
	public void testGetNumberOfCouncilPrivileges() {
		pe.setNumberOfCouncilPrivileges(numberOfCouncilPrivileges);
		assertEquals(this.numberOfCouncilPrivileges, this.pe.getNumberOfCouncilPrivileges());
	}

}
