package it.polimi.ingsw.GC_28.effectsTest;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.boards.BonusTile;
import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.boards.PlayerBoard;
import it.polimi.ingsw.GC_28.components.CouncilPrivilege;
import it.polimi.ingsw.GC_28.components.DiceColor;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.effects.PrivilegesEffect;
import it.polimi.ingsw.GC_28.model.Game;
import it.polimi.ingsw.GC_28.model.GameModel;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.model.PlayerColor;

public class PrivilegesEffectTest {
	private PrivilegesEffect pe;
	private int numberOfCouncilPrivileges = 2;
	private boolean different;
	
	private	TestGame testGame;
	private FamilyMember familyMember;
	private Player player;
	private PlayerBoard pb;
	private BonusTile bt;
	private Resource bonus;
	private EnumMap<ResourceType, Integer> resource;
	private HashMap<Character, Resource> options;

	private GameBoard gb;
	private GameModel gameModel;
	private ArrayList<Player> players;
	

	private class TestGame extends Game{
		public TestGame(GameModel gameModel) {
			super(gameModel);
		}
		
		@Override
		public Resource checkResourceExcommunication(Resource amount){
			CouncilPrivilege councilPrivilege = CouncilPrivilege.instance();
			councilPrivilege.setOptions(options);
			return options.get('c');
		}
		
		@Override
		public ArrayList<Character> askPrivilege(int numberOfCouncilPrivileges, boolean different){
			ArrayList<Character> tmp = new ArrayList<>();
			tmp.add('c');
			return tmp;
		}
		
		@Override
		public boolean askPermission(){
			return false;
		}
	}
	
	@Before
	public void privilegesEffect()throws IOException{
		pe = new PrivilegesEffect();
		player = new Player("gino", PlayerColor.GREEN);

		players = new ArrayList<>();
		players.add(player);
		
		resource = new EnumMap<ResourceType, Integer>(ResourceType.class);
		for(ResourceType resType : ResourceType.values()){
			resource.put(resType, 0);
		}
		bonus = Resource.of(resource);
		bt = new BonusTile();
		pb = new PlayerBoard(bt, bonus);
		player.setBoard(pb);
		
		familyMember = new FamilyMember(player, false, DiceColor.BLACK);
		
		options = new HashMap<>();
		options.put('c', bonus);
		options.put('v', bonus);
		
		gb = new GameBoard();
		gameModel  = new GameModel(gb, players);  
		testGame = new TestGame(gameModel);
		testGame.setCurrentPlayer(player);
		
	
	}
	

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testApply(){
		pe.apply(familyMember, testGame);
		pe.apply(familyMember.getPlayer(), testGame);
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
