package it.polimi.ingsw.GC_28.spacesTest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.components.CouncilPrivilege;
import it.polimi.ingsw.GC_28.components.DiceColor;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.effects.PrivilegesEffect;
import it.polimi.ingsw.GC_28.model.GameModel;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.model.PlayerColor;
import it.polimi.ingsw.GC_28.spaces.PrivilegesSpace;
import it.polimi.ingsw.GC_28.view.GameView;

public class PrivilegesSpaceTest {
	private PrivilegesSpace ps;
	private PrivilegesEffect pe;
	private CouncilPrivilege cp;
	HashMap<Character, Resource> options;
	private Resource bonus;
	EnumMap<ResourceType, Integer> resource;
	
	private FamilyMember familyMember;
	private Player player;
	private	List<Player> players = new ArrayList<>();
	
	private GameBoard gameBoard;
	private GameView game; 
	private GameModel gameModel;
	
	@Before
	public void privilegesSpace(){
		ps = new PrivilegesSpace(true, 1);
		pe = new PrivilegesEffect();
		cp = CouncilPrivilege.instance();
		
		options = new HashMap<>();
		resource = new EnumMap<>(ResourceType.class);
		resource.put(ResourceType.COIN, 3);
		bonus = Resource.of(resource);
		options.put('c', bonus);
		cp.setOptions(options);
		
		
		player = new Player("ciao", PlayerColor.GREEN);
		familyMember = new FamilyMember(player , false, DiceColor.BLACK);
		players.add(player);
		gameBoard = new GameBoard();
		gameModel = new GameModel(gameBoard, players);
		game = new GameView(gameModel); 
		
		ps.setBonus(pe);
	}
	
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

/*	@Test
	public void testApplyBonus() {
		ps.applyBonus(game, familyMember);
	}
*/
	@Test
	public void testGetBonus() {
		assertEquals(this.pe, 
				this.ps.getBonus());
	}

}
