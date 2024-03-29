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
import it.polimi.ingsw.GC_28.components.CouncilPrivilege;
import it.polimi.ingsw.GC_28.components.DiceColor;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.effects.DiscountEffect;
import it.polimi.ingsw.GC_28.model.GameModel;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.model.PlayerColor;
import it.polimi.ingsw.GC_28.view.GameView;

public class DiscountEffectTest {
	private DiscountEffect de;
	private boolean alternativeDiscountPresence;
	private Resource discount;
	private Resource alternativeDiscount;
	private Resource chosenAlternativeDiscount;
	EnumMap<ResourceType, Integer> resource;
	EnumMap<ResourceType, Integer> resource1;
	EnumMap<ResourceType, Integer> resource2;
	
	private FamilyMember fm;
	private GameView g;
	private Player p;
	private TestGame testGame;
	
	private class TestGame extends GameView{
		public TestGame(GameModel gameModel) {
			super(gameModel);
		}
		
		@Override
		public Resource askAlternative(Resource discount1, Resource discount2, String type) {
			EnumMap<ResourceType, Integer> z = new EnumMap<>(ResourceType.class);
			z.put(ResourceType.COIN, 0);
			Resource bonus = Resource.of(z);
			return bonus;
		}
		
	}
	
	@Before
	public void discountEffect(){
		de = new DiscountEffect();
		resource = new EnumMap<>(ResourceType.class);
		resource.put(ResourceType.STONE, 2);
		resource1 = new EnumMap<>(ResourceType.class);
		resource1.put(ResourceType.SERVANT, 1);
		resource2 = new EnumMap<>(ResourceType.class);
		resource2.put(ResourceType.WOOD, 2);

		
		EnumMap<ResourceType, Integer> w = new EnumMap<ResourceType, Integer>(ResourceType.class);
		for(ResourceType resType : ResourceType.values()){
			w.put(resType, 0);
		}
		Resource res = Resource.of(w);
		PlayerBoard pb = new PlayerBoard(null, res);
		
		p = new Player("bob", PlayerColor.YELLOW);
		p.setBoard(pb);
		fm = new FamilyMember(p, false, DiceColor.WHITE);
		
		List<Player> players = new ArrayList<>();
		players.add(p);
		GameBoard gb = new GameBoard();
		GameModel gm = new GameModel(gb,players);
		
		g = new GameView(gm);
		testGame = new TestGame(gm);
		g.setCurrentPlayer(p);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testApply() {
		discount = Resource.of(resource);
		de.setDiscount(discount);
		de.setAlternativeDiscountPresence(false);
		de.apply(fm, g);
		boolean x = discount.equals(fm.getPlayer().getBoard().getResources());
		assertTrue(x);
//		assertEquals(this.fm.getPlayer().getBoard().get, de);
	}

	@Test
	public void testApply1() {
		discount = Resource.of(resource);
		de.setDiscount(discount);
		de.setAlternativeDiscountPresence(true);
		de.apply(fm, testGame);
		boolean x = discount.equals(fm.getPlayer().getBoard().getResources());
		assertFalse(x);
//		assertEquals(this.fm.getPlayer().getBoard().get, de);
	}
	
	@Test
	public void testGetAlternativeDiscount() {
		alternativeDiscount = Resource.of(resource1);
		de.setAlternativeDiscount(alternativeDiscount);
		assertEquals(this.alternativeDiscount, this.de.getAlternativeDiscount());
		//fail("Not yet implemented");
	}

	@Test
	public void testGetAlternativeDiscountPresence() {
		de.setAlternativeDiscountPresence(alternativeDiscountPresence);
		assertEquals(this.alternativeDiscountPresence, this.de.getAlternativeDiscountPresence());
	}

	@Test
	public void testGetDiscount() {
		discount = Resource.of(resource);
		de.setDiscount(discount);
		assertEquals(this.discount, this.de.getDiscount());
		//fail("Not yet implemented");
	}

	@Test
	public void testGetChosenAlternativeDiscount() {
		chosenAlternativeDiscount = Resource.of(resource2);
		de.setChosenAlternativeDiscount(chosenAlternativeDiscount);
		assertEquals(this.chosenAlternativeDiscount, this.de.getChosenAlternativeDiscount());	
	}

}
