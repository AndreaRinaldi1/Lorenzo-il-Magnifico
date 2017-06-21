package it.polimi.ingsw.GC_28.effectsTest;

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
import it.polimi.ingsw.GC_28.effects.ExchangeEffect;
import it.polimi.ingsw.GC_28.effects.PrivilegesEffect;
import it.polimi.ingsw.GC_28.model.GameModel;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.model.PlayerColor;
import it.polimi.ingsw.GC_28.spaces.PrivilegesSpace;
import it.polimi.ingsw.GC_28.view.GameView;

public class ExchangeEffectTest {
	private ExchangeEffect exchange;
	private Resource firstCost;
	private Resource secondCost;
	private boolean alternative;
	private Resource firstBonus;
	private Resource secondBonus;
	private Resource privilegeCost;
	private PrivilegesEffect privilegeBonus;
	EnumMap<ResourceType, Integer> resource;
	EnumMap<ResourceType, Integer> resource1;
	EnumMap<ResourceType, Integer> resource2;
	EnumMap<ResourceType, Integer> resource3;
	EnumMap<ResourceType, Integer> resource4;
	private PrivilegesSpace ps;
	private CouncilPrivilege cp;
	HashMap<Character, Resource> options;
	private Resource bonus;
	EnumMap<ResourceType, Integer> resource5;
	EnumMap<ResourceType, Integer> w = new EnumMap<ResourceType, Integer>(ResourceType.class);


	private FamilyMember fm;
	private Player p;
	private TestGame testGame;
	private TestGame1 testGame1;
	
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
	
	private class TestGame1 extends GameView{
		public TestGame1(GameModel gameModel) {
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
			return 6;
		}
	}
	
	@Before
	public void exchangeEffect(){
		exchange = new ExchangeEffect();
		privilegeBonus = new PrivilegesEffect();
		
		resource = new EnumMap<>(ResourceType.class);
		resource1 = new EnumMap<>(ResourceType.class);
		resource2 = new EnumMap<>(ResourceType.class);
		resource3 = new EnumMap<>(ResourceType.class);
		resource4 = new EnumMap<>(ResourceType.class);
		
		ps = new PrivilegesSpace(true, 1);
		cp = CouncilPrivilege.instance();

		options = new HashMap<>();
		resource = new EnumMap<>(ResourceType.class);
		resource.put(ResourceType.COIN, 3);
		bonus = Resource.of(resource);
		options.put('c', bonus);
		cp.setOptions(options);
		
		privilegeCost = Resource.of(resource);
		privilegeBonus.setDifferent(false);
		privilegeBonus.setNumberOfCouncilPrivileges(1);
		ps.setBonus(privilegeBonus);
		exchange.setPrivilegeBonus(privilegeBonus);	
		exchange.setPrivilegeCost(privilegeCost);
		
		for(ResourceType resType : ResourceType.values()){
			w.put(resType, 5);
		}
		Resource res = Resource.of(w);
		PlayerBoard pb = new PlayerBoard(null, res);
		p = new Player("bob", PlayerColor.YELLOW);
		p.setBoard(pb);
		fm = new FamilyMember(p, false, DiceColor.WHITE);
		//g.setCurrentPlayer(p);
		List<Player> players = new ArrayList<>();
		players.add(p);
		GameBoard gb = new GameBoard();
		GameModel gm = new GameModel(gb,players);
		
		testGame = new TestGame(gm);
		testGame1 = new TestGame1(gm);
		testGame.setCurrentPlayer(p);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	//privilegeBonus != null and alternative false
	@Test
	public void testApply() {
		resource.put(ResourceType.COIN, 2);
		firstCost = Resource.of(resource);
		exchange.setFirstCost(firstCost);
		resource2.put(ResourceType.SERVANT, 1);
		firstBonus = Resource.of(resource2);
		exchange.setFirstBonus(firstBonus);
		exchange.apply(fm, testGame);
		Resource res = Resource.of(w);
		res.modifyResource(firstCost, false);
		res.modifyResource(firstBonus, true);

		boolean x = res.equals(fm.getPlayer().getBoard().getResources());
		
		assertTrue(x);
	}

	//privilegeBonus == null and alternative false
	@Test
	public void testApply1() {
		resource.put(ResourceType.COIN, 2);
		firstCost = Resource.of(resource);
		exchange.setFirstCost(firstCost);
		resource2.put(ResourceType.SERVANT, 1);
		firstBonus = Resource.of(resource2);
		exchange.setPrivilegeBonus(null);
		exchange.setFirstBonus(firstBonus);
		exchange.apply(fm, testGame);
		Resource res = Resource.of(w);
		res.modifyResource(firstCost, false);
		res.modifyResource(firstBonus, true);

		boolean x = res.equals(fm.getPlayer().getBoard().getResources());
		
		assertTrue(x);
	}
	
	//privilegeBonus != null, game.askAlternativeExchange(firstCost, firstBonus, secondCost, secondBonus) == 1 and alternative true
	@Test
	public void testApply3() {
		resource.put(ResourceType.COIN, 2);
		firstCost = Resource.of(resource);
		exchange.setFirstCost(firstCost);
		resource2.put(ResourceType.SERVANT, 1);
		firstBonus = Resource.of(resource2);
		exchange.setAlternative(true);
		exchange.setFirstBonus(firstBonus);
		exchange.apply(fm, testGame);
		Resource res = Resource.of(w);
		res.modifyResource(firstCost, false);
		res.modifyResource(firstBonus, true);

		boolean x = res.equals(fm.getPlayer().getBoard().getResources());
		
		assertTrue(x);
	}
	
	//privilegeBonus != null, game.askAlternativeExchange(firstCost, firstBonus, secondCost, secondBonus) != 1 and alternative true
	@Test
	public void testApply4() {
		resource.put(ResourceType.COIN, 1);
		firstCost = Resource.of(resource);
		secondCost = Resource.of(resource);
		exchange.setFirstCost(firstCost);
		exchange.setSecondCost(secondCost);
		resource2.put(ResourceType.SERVANT, 1);
		firstBonus = Resource.of(resource2);
		exchange.setAlternative(true);
		exchange.setFirstBonus(firstBonus);
		exchange.apply(fm, testGame1);
		Resource res = Resource.of(w);
		res.modifyResource(firstCost, false);
		res.modifyResource(firstBonus, true);

		boolean x = res.equals(fm.getPlayer().getBoard().getResources());
		
		assertTrue(x);
	}
	
	@Test
	public void testGetFirstCost() {
		resource.put(ResourceType.COIN, 2);
		firstCost = Resource.of(resource);
		exchange.setFirstCost(firstCost);
		assertEquals(this.firstCost, this.exchange.getFirstCost());
	}

	@Test
	public void testGetSecondCost() {
		resource1.put(ResourceType.FAITHPOINT, 1);
		secondCost = Resource.of(resource1);
		exchange.setSecondCost(secondCost);
		assertEquals(this.secondCost, this.exchange.getSecondCost());
	}

	@Test
	public void testIsAlternative() {
		exchange.setAlternative(alternative);
		assertEquals(this.alternative, this.exchange.isAlternative());
	}

	@Test
	public void testGetFirstBonus() {
		resource2.put(ResourceType.SERVANT, 1);
		firstBonus = Resource.of(resource2);
		exchange.setFirstBonus(firstBonus);
		assertEquals(this.firstBonus, this.exchange.getFirstBonus());
	}

	@Test
	public void testGetSecondBonus() {
		resource3.put(ResourceType.WOOD, 4);
		secondBonus = Resource.of(resource3);
		exchange.setSecondBonus(secondBonus);
		assertEquals(this.secondBonus, this.exchange.getSecondBonus());
	}

	@Test
	public void testGetPrivilegeCost() {
		resource4.put(ResourceType.COIN, 4);
		privilegeCost = Resource.of(resource4);
		exchange.setPrivilegeCost(privilegeCost);
		assertEquals(this.privilegeCost, this.exchange.getPrivilegeCost());
	}

	@Test
	public void testGetPrivilegeBonus() {
		assertEquals(this.privilegeBonus, this.exchange.getPrivilegeBonus());
	}

}