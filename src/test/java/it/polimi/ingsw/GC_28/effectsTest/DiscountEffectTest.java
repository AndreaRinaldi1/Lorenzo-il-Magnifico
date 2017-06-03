package it.polimi.ingsw.GC_28.effectsTest;

import static org.junit.Assert.*;

import java.util.EnumMap;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.components.DiceColor;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.effects.DiscountEffect;
import it.polimi.ingsw.GC_28.model.Game;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.model.PlayerColor;

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
	private Game g;
	private Player p;
	private GameBoard gb;
	
	@Before
	public void discountEffect(){
		de = new DiscountEffect();
		resource = new EnumMap<>(ResourceType.class);
		resource1 = new EnumMap<>(ResourceType.class);
		resource2 = new EnumMap<>(ResourceType.class);
		
		g = new Game();
		p = new Player("bob", PlayerColor.YELLOW);
		fm = new FamilyMember(p, false, DiceColor.WHITE);
		gb = new GameBoard();
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testApply() {
		
//		assertEquals(this.fm.getPlayer().getBoard().get, de);
	}

	@Test
	public void testGetAlternativeDiscount() {
		resource1.put(ResourceType.SERVANT, 1);
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
		resource.put(ResourceType.STONE, 2);
		discount = Resource.of(resource);
		de.setDiscount(discount);
		assertEquals(this.discount, this.de.getDiscount());
		//fail("Not yet implemented");
	}

	@Test
	public void testGetChosenAlternativeDiscount() {
		resource2.put(ResourceType.WOOD, 2);
		chosenAlternativeDiscount = Resource.of(resource2);
		de.setChosenAlternativeDiscount(chosenAlternativeDiscount);
		assertEquals(this.chosenAlternativeDiscount, this.de.getChosenAlternativeDiscount());	
	}

}
