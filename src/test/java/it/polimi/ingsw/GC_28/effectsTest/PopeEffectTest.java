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
import it.polimi.ingsw.GC_28.effects.PopeEffect;
import it.polimi.ingsw.GC_28.model.GameModel;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.model.PlayerColor;
import it.polimi.ingsw.GC_28.view.GameView;

public class PopeEffectTest {

	private PopeEffect popeEffect;
	
	private Player player;
	private FamilyMember familyMember;
	private GameModel gameModel;
	private GameBoard gb;
	private PlayerBoard pb;
	private List<Player> players = new ArrayList<>();
	private TestGame testGame;
	
	private Resource bonus;
	EnumMap<ResourceType, Integer> resource;
	
	private class TestGame extends GameView{
		public TestGame(GameModel gameModel) {
			super(gameModel);
		}
		
	}
	
	@Before
	public void popeEffect(){
		popeEffect = new PopeEffect();
		
		resource = new EnumMap<>(ResourceType.class);
		resource.put(ResourceType.COIN, 3);
		bonus = Resource.of(resource);
		
		popeEffect.setBonus(bonus);
		
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
		popeEffect.apply(player, testGame);
	}

	@Test
	public void testGetBonus() {
		assertEquals(this.bonus, this.popeEffect.getBonus());
	}

}
