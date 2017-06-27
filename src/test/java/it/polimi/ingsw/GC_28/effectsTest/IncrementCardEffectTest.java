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
import it.polimi.ingsw.GC_28.cards.CardType;
import it.polimi.ingsw.GC_28.components.DiceColor;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.effects.*;
import it.polimi.ingsw.GC_28.model.GameModel;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.model.PlayerColor;
import it.polimi.ingsw.GC_28.view.GameView;

public class IncrementCardEffectTest {
	private IncrementCardEffect ic;
	private int increment = 2;
	private boolean discountPresence;
	private DiscountEffect discount;
	
	private	GameView g;
	private FamilyMember fm;
	private FamilyMember fm2;
	private Player player;
	private Player player2;
	private PlayerBoard pb;
	private GameModel gameModel;
	private GameBoard gameBoard; 
	private List<Player> players = new ArrayList<>();
	
	@Before
	public void incrementCardEffect(){
		ic = new IncrementCardEffect();
		discount = new DiscountEffect();
		player = new Player("gino", PlayerColor.GREEN);
		player2 = new Player("Mariangiongianela", PlayerColor.BLUE);
		players.add(player);
		players.add(player2);
		EnumMap<ResourceType, Integer> w = new EnumMap<ResourceType, Integer>(ResourceType.class);
		for(ResourceType resType : ResourceType.values()){
			w.put(resType, 0);
		}
		Resource res = Resource.of(w);
		pb = new PlayerBoard(null, res);
		gameBoard = new GameBoard();
		gameModel = new GameModel(gameBoard, players);
		player.setBoard(pb);
		g = new GameView(gameModel);
		fm = new FamilyMember(player, false, DiceColor.ORANGE);
		fm2 = new FamilyMember(player2, false, DiceColor.WHITE);
		fm.setValue(2);
		fm2.setValue(2);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testApply() {
		discountPresence = true;
		ic.setIncrement(increment);
		ic.setDiscountPresence(discountPresence);
		ic.setCardType(CardType.BUILDING);
		ic.setDiscount(discount);
		ic.apply(this.fm, this.g);
		fm2.modifyValue(increment);
		boolean x = fm2.getValue().equals(fm.getValue());
		assertTrue(x);
	}

	//ic.setDiscountPresence(false);
	@Test
	public void testApply1() {
		discountPresence = true;
		ic.setIncrement(increment);
		ic.setDiscountPresence(false);
		ic.setCardType(CardType.BUILDING);
		ic.setDiscount(discount);
		ic.apply(this.fm, this.g);
		fm2.modifyValue(increment);
		boolean x = fm2.getValue().equals(fm.getValue());
		assertTrue(x);
	}

	
	@Test
	public void testGetIncrement() {
		ic.setIncrement(increment);
		assertEquals(this.increment, this.ic.getIncrement());
	}

	@Test
	public void testGetCardType() {
		ic.setCardType(CardType.BUILDING);
		assertEquals(CardType.BUILDING, this.ic.getCardType());
	}

	@Test
	public void testIsDiscountPresence() {
		ic.setDiscountPresence(discountPresence);
		assertEquals(this.discountPresence, this.ic.isDiscountPresence());
	}

	@Test
	public void testGetDiscount() {
		ic.setDiscount(discount);
		assertEquals(this.discount, this.ic.getDiscount());
	}

}
