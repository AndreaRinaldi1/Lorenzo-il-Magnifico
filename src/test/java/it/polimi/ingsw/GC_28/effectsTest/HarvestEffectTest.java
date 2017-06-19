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
import it.polimi.ingsw.GC_28.components.CouncilPrivilege;
import it.polimi.ingsw.GC_28.components.DiceColor;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.effects.HarvestEffect;
import it.polimi.ingsw.GC_28.effects.PrivilegesEffect;
import it.polimi.ingsw.GC_28.effects.ResourceEffect;
import it.polimi.ingsw.GC_28.model.Game;
import it.polimi.ingsw.GC_28.model.GameModel;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.model.PlayerColor;

public class HarvestEffectTest {
	private HarvestEffect he;
	private int harvestValue = 5;
	private ResourceEffect resourceHarvestBonus;
	private PrivilegesEffect councilPrivilegeBonus;
	private CouncilPrivilege cp;
	HashMap<Character, Resource> options;
	private Resource bonus;
	EnumMap<ResourceType, Integer> resource;
	
	private Player player;
	private FamilyMember familyMember;
	private Game game;
	private GameModel gameModel;
	private GameBoard gb;
	private List<Player> players = new ArrayList<>();
	
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
		
		player = new Player("bart", PlayerColor.BLUE);
		familyMember = new FamilyMember(player, false, DiceColor.BLACK);
		gb = new GameBoard();
		players.add(player);
		gameModel = new GameModel(gb, players);
		game = new Game(gameModel);
		
		
		he.setCouncilPrivilegeBonus(councilPrivilegeBonus);
		he.setHarvestValue(harvestValue);
		he.setResourceHarvestBonus(resourceHarvestBonus);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testApply() {
	
		he.apply(familyMember, game);
	
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
		assertEquals(this.councilPrivilegeBonus, this.he.getCouncilPrivilegeBonus());
	}
}
