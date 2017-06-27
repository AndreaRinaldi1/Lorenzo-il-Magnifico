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
import it.polimi.ingsw.GC_28.boards.PlayerBoard;
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
	private TestGame game; 
	private GameModel gameModel;
	
	EnumMap<ResourceType, Integer> w = new EnumMap<ResourceType, Integer>(ResourceType.class);

	
	private class TestGame extends GameView{
		public TestGame(GameModel gameModel) {
			super(gameModel);
		}
		
		@Override
		public boolean askPermission(){
			return true;
		}
		
		@Override
		public ArrayList<Character> askPrivilege(int numberOfCouncilPrivileges, boolean different){
			ArrayList<Character> tmp = new ArrayList<>();
			tmp.add('c');
			return tmp;
		}
		
		@Override
		public Resource checkResourceExcommunication(Resource amount){
			CouncilPrivilege councilPrivilege = CouncilPrivilege.instance();
			councilPrivilege.setOptions(options);
			return options.get('c');
		}
		
		@Override
		public int askAlternativeExchange(Resource firstCost, Resource firstBonus, Resource secondCost, Resource secondBonus){
			return 1;
		}
	}
	
	
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
		
		for(ResourceType resType : ResourceType.values()){
			w.put(resType, 5);
		}
		Resource res = Resource.of(w);
		PlayerBoard pb = new PlayerBoard(null, res);
	
		
		player = new Player("ciao", PlayerColor.GREEN);
		familyMember = new FamilyMember(player , false, DiceColor.BLACK);
		players.add(player);
		gameBoard = new GameBoard();
		gameModel = new GameModel(gameBoard, players);
		game = new TestGame(gameModel); 
		
		ps.setBonus(pe);
		player.setBoard(pb);
	}
	
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testApplyBonus() {
		ps.applyBonus(game, familyMember);
	}

	@Test
	public void testGetBonus() {
		assertEquals(this.pe, 
				this.ps.getBonus());
	}

}
