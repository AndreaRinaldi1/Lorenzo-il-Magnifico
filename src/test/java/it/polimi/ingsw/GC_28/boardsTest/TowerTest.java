package it.polimi.ingsw.GC_28.boardsTest;

import static org.junit.Assert.*;

import java.util.EnumMap;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.boards.*;
import it.polimi.ingsw.GC_28.cards.*;
import it.polimi.ingsw.GC_28.components.*;
import it.polimi.ingsw.GC_28.model.*;

public class TowerTest {
	
	private Cell[] cells;
	private boolean atLeastOne = true;
	private boolean free = true;
	private Tower tower;
	private FamilyMember fm, fm2;
	private Player p;
	private Card c0, c1;
	private Resource bonus;
	EnumMap<ResourceType, Integer> resource;
	
	@Before
	public void tower(){
		resource = new EnumMap<ResourceType, Integer>(ResourceType.class);
		resource.put(ResourceType.STONE, 2);
		bonus = Resource.of(resource);
		c0 = new Card("Avamposto", 1, 1);
		c1 = new Card("Bosco", 2, 1);
		cells = new Cell[4];
		cells[0] = new Cell(bonus, 3, free);
		cells[1] = new Cell(bonus, 5, free);
		tower = new Tower(cells);
		tower.setAtLeastOne(atLeastOne);
		p = new Player("ciao", PlayerColor.BLUE);
		fm = new FamilyMember(p, false, DiceColor.BLACK);
		fm2 = new FamilyMember(p, true, DiceColor.NEUTRAL);
		fm.setUsed(true);
		cells[0].setFamilyMember(fm);
		cells[0].setCard(c0);
		cells[1].setFamilyMember(fm2);
		cells[1].setCard(c1);
		tower.setCells(cells);
	}
	
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testFindCard() {
		assertEquals(this.c0.getName(), this.tower.getCells()[0].getCard().getName());
	}

	@Test
	public void testIsThisPlayerPresent() {
		assertEquals(this.fm.isUsed(), 
				this.tower.isThisPlayerPresent(fm.getPlayer().getColor()));
	}

	@Test
	public void testGetCells() {
		assertArrayEquals(this.cells, this.tower.getCells());
	}

	@Test
	public void testGetAtLeastOne() {
		assertEquals(this.atLeastOne, this.tower.getAtLeastOne());
	}

}
