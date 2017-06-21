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
import it.polimi.ingsw.GC_28.effects.HarvestEffect;
import it.polimi.ingsw.GC_28.effects.PrivilegesEffect;
import it.polimi.ingsw.GC_28.effects.ResourceEffect;
import it.polimi.ingsw.GC_28.model.GameModel;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.model.PlayerColor;
import it.polimi.ingsw.GC_28.view.GameView;

public class HarvestEffectTest {
	private HarvestEffect he;
	private int harvestValue = 1;
	private ResourceEffect resourceHarvestBonus;
	private PrivilegesEffect councilPrivilegeBonus;
	private CouncilPrivilege cp;
	HashMap<Character, Resource> options;
	private Resource bonus;
	EnumMap<ResourceType, Integer> resource;
	
	private Player player;
	private FamilyMember familyMember;
	private GameModel gameModel;
	private GameBoard gb;
	private PlayerBoard pb;
	private List<Player> players = new ArrayList<>();
	private TestGame testGame;
	
	private class TestGame extends GameView{
		public TestGame(GameModel gameModel) {
			super(gameModel);
		}
		
		@Override
		public Resource checkResourceExcommunication(Resource amount){
			resource = new EnumMap<>(ResourceType.class);
			resource.put(ResourceType.COIN, 3);
			bonus = Resource.of(resource);
			return bonus;
		}
		
		@Override
		public ArrayList<Character> askPrivilege(int numberOfCouncilPrivileges, boolean different){
			ArrayList<Character> tmp = new ArrayList<>();
			tmp.add('c');
			return tmp;
		}
	}
	
	@Before
	public void harvestEffect(){
		he = new HarvestEffect();
		resourceHarvestBonus = new ResourceEffect();
		councilPrivilegeBonus = new PrivilegesEffect();
		
		options = new HashMap<>();
		resource = new EnumMap<>(ResourceType.class);
		resource.put(ResourceType.COIN, 3);
		bonus = Resource.of(resource);
		resourceHarvestBonus.setResourceBonus(bonus);
		options.put('c', bonus);
		//cp.setOptions(options);
		
		player = new Player("aiuto", PlayerColor.BLUE);
		familyMember = new FamilyMember(player, false, DiceColor.BLACK);
		gb = new GameBoard();
		pb = new PlayerBoard(null, bonus);
		player.setBoard(pb);
		FamilyMember[] familyMembers = new FamilyMember[1];
		familyMembers[0] = familyMember;
		player.setFamilyMembers(familyMembers);
		players.add(player);
		gameModel = new GameModel(gb, players);
		testGame = new TestGame(gameModel);
		
		
		//he.setCouncilPrivilegeBonus(councilPrivilegeBonus);
		he.setHarvestValue(harvestValue);
		he.setResourceHarvestBonus(resourceHarvestBonus);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	//test Resource Harvest Bonus
	@Test
	public void testApply() {
		he.setCouncilPrivilegeBonus(null);
		familyMember.setValue(3);
		he.apply(familyMember, testGame);
		he.apply(familyMember.getPlayer(), testGame);
	}
	
	//test council bonus
	@Test
	public void testApply1(){
		he.setCouncilPrivilegeBonus(councilPrivilegeBonus);
		familyMember.setValue(3);
		he.apply(familyMember, testGame);
		he.apply(familyMember.getPlayer(), testGame);
	}
	
	//test else
	@Test
	public void testApply2(){
		he.setCouncilPrivilegeBonus(null);
		familyMember.setValue(0);
		he.apply(familyMember, testGame);
		he.apply(familyMember.getPlayer(), testGame);
	}

	@Test
	public void testGetHarvestValue() {
		assertEquals(this.harvestValue, this.he.getHarvestValue());
	}

	@Test
	public void testGetResourceHarvestBonus() {
		assertEquals(this.resourceHarvestBonus, this.he.getResourceHarvestBonus());
	}

	@Test
	public void testGetCouncilPrivilegeBonus() {
		he.setCouncilPrivilegeBonus(councilPrivilegeBonus);
		assertEquals(this.councilPrivilegeBonus, this.he.getCouncilPrivilegeBonus());
	}

}
