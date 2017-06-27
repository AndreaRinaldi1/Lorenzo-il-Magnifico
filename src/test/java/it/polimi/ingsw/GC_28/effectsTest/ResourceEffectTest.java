package it.polimi.ingsw.GC_28.effectsTest;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.boards.PlayerBoard;
import it.polimi.ingsw.GC_28.cards.LeaderCard;
import it.polimi.ingsw.GC_28.components.CouncilPrivilege;
import it.polimi.ingsw.GC_28.components.DiceColor;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.effects.ResourceEffect;
import it.polimi.ingsw.GC_28.model.BoardsInitializer;
import it.polimi.ingsw.GC_28.model.GameModel;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.model.PlayerColor;
import it.polimi.ingsw.GC_28.view.GameView;

public class ResourceEffectTest {
	private ResourceEffect re;
	private Resource resourceBonus;
	EnumMap<ResourceType, Integer> resource;
	private Player p = new Player("stan", PlayerColor.BLUE);
	private PlayerBoard playerBoard;
	private FamilyMember familyMember;
	FamilyMember[] fm = new FamilyMember[4];
	private LeaderCard santaRita;
	
		
	private TestGame game;
	
	private class TestGame extends GameView{
		public TestGame(GameModel gameModel) {
			super(gameModel);
		}

		@Override
		public Resource checkResourceExcommunication(Resource amount){
			EnumMap<ResourceType, Integer> resource1 = new EnumMap<>(ResourceType.class);
			resource1.put(ResourceType.COIN, 1);
			Resource bonus = Resource.of(resource1);
			return bonus;
		}

		
	}
	
	@Before
	public void resourceEffect(){
		re = new ResourceEffect();
		
		resource = new EnumMap<>(ResourceType.class);
		resource.put(ResourceType.STONE, 1);
		resource.put(ResourceType.WOOD, 1);
		resource.put(ResourceType.SERVANT, 1);
		resourceBonus = Resource.of(resource);
		re.setResourceBonus(resourceBonus);
		int i = 0;
		
		for(DiceColor dc : DiceColor.values()){
			if(dc != DiceColor.NEUTRAL){
				FamilyMember f = new FamilyMember(p, false, dc);
				f.setValue(6);
				fm[i] = f;
			}else{
				FamilyMember f = new FamilyMember(p, true, dc);
				f.setValue(6);
				fm[i] = f;
			}
			i++;
		}
		familyMember = new FamilyMember(p, false, DiceColor.BLACK); 
		fm[0] = familyMember;
		playerBoard = new PlayerBoard(null, resourceBonus);
		p.setFamilyMembers(fm);
		List<Player> players = new ArrayList<>();
		players.add(p);
		GameBoard gb = new GameBoard();
		GameModel gm = new GameModel(gb,players);
		
		game = new TestGame(gm);
		game.setCurrentPlayer(p);
		santaRita = new LeaderCard();
		
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testApply() {
		santaRita.setName("Santa Rita");
		santaRita.setActive(true);
		santaRita.setPlayed(true);
		fm[0].getPlayer().setBoard(playerBoard);
		fm[0].getPlayer().getLeaderCards().add(santaRita);
		re.apply(fm[0], game);
	}
	
	@Test
	public void testApply2(){
		p.setBoard(playerBoard);
		re.apply(p, game);
	}

	@Test
	public void testGetResourceBonus() {
		assertEquals(this.resourceBonus, this.re.getResourceBonus());
	}

}
