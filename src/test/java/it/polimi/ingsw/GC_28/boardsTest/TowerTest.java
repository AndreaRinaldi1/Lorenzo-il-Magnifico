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
	private Cell[] cells1;
	private boolean atLeastOne = true;
	private boolean free = true;
	private Tower tower;
	private Tower tower1;
	private FamilyMember fm;
	private FamilyMember fm1;
	private FamilyMember fm2;
	private FamilyMember fm3;
	private Player p;
	private Card c0;
	private Card c1;
	private Card c2;
	private Card c3;
	private Resource bonus;
	EnumMap<ResourceType, Integer> resource;
		
	@Before
	public void tower(){
		resource = new EnumMap<ResourceType, Integer>(ResourceType.class);
		resource.put(ResourceType.STONE, 2);
		bonus = Resource.of(resource);
		c0 = new Card("Avamposto", 1, 1);
		c1 = new Card("Bosco", 2, 1);
		c2 = new Card("Zuppa", 3, 1);
		c3 = new Card("Morelli", 4, 1);
		cells = new Cell[4];
		cells[0] = new Cell(bonus, 3, free);
		cells[1] = new Cell(bonus, 5, free);
		cells[2] = new Cell(bonus, 1, free);
		cells[3] = new Cell(bonus, 7, free);
		tower = new Tower(cells);
		tower.setAtLeastOne(atLeastOne);
		p = new Player("ciao", PlayerColor.BLUE);
		fm = new FamilyMember(p, false, DiceColor.BLACK);
		fm1 = new FamilyMember(p, true, DiceColor.NEUTRAL);
		fm2 = new FamilyMember(p, false, DiceColor.ORANGE);
		fm3 = new FamilyMember(p, false, DiceColor.WHITE);
		fm.setUsed(true);
		fm1.setUsed(true);
		fm2.setUsed(true);
		fm3.setUsed(true);
		cells[0].setFamilyMember(fm);
		cells[0].setCard(c0);
		cells[1].setFamilyMember(fm1);
		cells[1].setCard(c1);
		cells[2].setFamilyMember(fm2);
		cells[2].setCard(c2);
		cells[3].setFamilyMember(fm3);
		cells[3].setCard(c3);
		tower.setCells(cells);
	}
	
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	
	//Test dopo aver messo le carte nella cella
	@Test
	public void testFindCard() {
		assertEquals(this.c0, this.tower.getCells()[0].getCard());
		assertEquals(this.c1, this.tower.getCells()[1].getCard());
		assertEquals(this.c2, this.tower.getCells()[2].getCard());
		assertEquals(this.c3, this.tower.getCells()[3].getCard());
	}
	
	//TEST senza aver posizionato le carte
	@Test
	public void testFindCardNULL(){
		cells1 = new Cell[4];		
		cells1[0] = new Cell(bonus, 3, free);
		cells1[1] = new Cell(bonus, 5, free);
		cells1[2] = new Cell(bonus, 1, free);
		cells1[3] = new Cell(bonus, 7, free);

		tower1 = new Tower(cells1);
		tower1.setCells(cells1);
		for (int i = 0; i < tower1.getCells().length; i++){
			assertEquals(null, this.tower1.getCells()[i].getCard());
		}
	}

	@Test
	public void testIsThisPlayerPresent() {
		assertEquals(this.fm.isUsed(), 
				this.tower.isThisPlayerPresent(fm.getPlayer().getColor()));
		assertEquals(this.fm1.isUsed(), 
				this.tower.isThisPlayerPresent(fm1.getPlayer().getColor()));	
		assertEquals(this.fm2.isUsed(), 
				this.tower.isThisPlayerPresent(fm2.getPlayer().getColor()));
		assertEquals(this.fm3.isUsed(), 
				this.tower.isThisPlayerPresent(fm3.getPlayer().getColor()));
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
