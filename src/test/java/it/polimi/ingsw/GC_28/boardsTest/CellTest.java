package it.polimi.ingsw.GC_28.boardsTest;

import static org.junit.Assert.*;

import java.util.EnumMap;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.boards.Cell;
import it.polimi.ingsw.GC_28.cards.Card;
import it.polimi.ingsw.GC_28.components.DiceColor;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.model.PlayerColor;

public class CellTest {

	private Card card = new Card("pippo", 1, 2);
	private Cell cell;
	private FamilyMember fm;
	private Player player;
	private Resource bonus;
	EnumMap<ResourceType, Integer> resource;

	@Before
	public void cell(){
		resource = new EnumMap<ResourceType, Integer>(ResourceType.class);
		resource.put(ResourceType.COIN, 5);
		bonus = Resource.of(resource);
		cell = new Cell(bonus, 5, true);
		cell.setCard(card);
		player = new Player("ciao",PlayerColor.RED);
		fm = new FamilyMember(player, false, DiceColor.ORANGE);
		cell.setFamilyMember(fm);
		
	
	}
	
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testIsFree() {
		assertEquals(true, this.cell.isFree());
	}

	@Test
	public void testGetFamilyMember() {
		assertEquals(this.fm, this.cell.getFamilyMember());
	}

	@Test
	public void testGetCard() {
		assertEquals(this.card, this.cell.getCard());
	}

	@Test
	public void testGetActionValue() {
		assertEquals(5, this.cell.getActionValue());
	}

	@Test
	public void testGetBonus() {
		assertEquals(this.bonus, this.cell.getBonus());
	}

}
