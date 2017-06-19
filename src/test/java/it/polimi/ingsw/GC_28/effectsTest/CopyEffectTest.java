package it.polimi.ingsw.GC_28.effectsTest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.boards.PlayerBoard;
import it.polimi.ingsw.GC_28.components.DiceColor;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.effects.CopyEffect;
import it.polimi.ingsw.GC_28.effects.PopeEffect;
import it.polimi.ingsw.GC_28.model.Game;
import it.polimi.ingsw.GC_28.model.GameModel;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.model.PlayerColor;

public class CopyEffectTest {

	private CopyEffect copyEffect;
	
	private Resource bonus;
	EnumMap<ResourceType, Integer> resource;
	
	private Player player;
	private FamilyMember familyMember;
	private GameModel gameModel;
	private GameBoard gb;
	private PlayerBoard pb;
	private List<Player> players = new ArrayList<>();
	private TestGame testGame;
	
	private class TestGame extends Game{
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
	public void copyEffect(){
		copyEffect = new CopyEffect();
		
		resource = new EnumMap<>(ResourceType.class);
		resource.put(ResourceType.COIN, 3);
		bonus = Resource.of(resource);
		
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
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testApplyPlayerGame() {
		
	}

}
