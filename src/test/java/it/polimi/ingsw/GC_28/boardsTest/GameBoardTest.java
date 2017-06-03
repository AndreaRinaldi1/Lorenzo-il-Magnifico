package it.polimi.ingsw.GC_28.boardsTest;

import static org.junit.Assert.*;

import java.util.EnumMap;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.boards.*;
import it.polimi.ingsw.GC_28.cards.CardType;
import it.polimi.ingsw.GC_28.cards.ExcommunicationTile;
import it.polimi.ingsw.GC_28.components.Dice;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.spaces.*;

public class GameBoardTest {
	
	private GameBoard gb;
	
	private ProductionAndHarvestSpace harvestSpace;
	private ProductionAndHarvestSpace productionSpace;
	private MarketSpace coinSpace;
	private MarketSpace servantSpace;
	private MarketSpace mixedSpace;
	private PrivilegesSpace privilegesSpace;
	private Resource bonusFaithPoints;
	private CouncilPalace councilPalace;
	
	private Map<CardType, Tower> towers = new EnumMap<>(CardType.class);
	private Dice[] dices = new Dice[3];
	private ExcommunicationTile[] excommunications = new ExcommunicationTile[3];
	
	@Before
	public void gameBoard(){
		gb = new GameBoard();
		
		gb.setProductionSpace(productionSpace);
		gb.setHarvestSpace(harvestSpace);
		gb.setCoinSpace(coinSpace);
		gb.setServantSpace(servantSpace);
		gb.setMixedSpace(mixedSpace);
		gb.setPrivilegesSpace(privilegesSpace);
		gb.setCouncilPalace(councilPalace);
		gb.setBonusFaithPoints(bonusFaithPoints);
	
		gb.setTowers(towers);
		gb.setDices(dices);
		gb.setExcommunications(excommunications);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testDisplay() {
		//questo display poi verrà tolto
	}
	
	@Test
	public void testGetTowers() {
		assertEquals(this.towers, this.gb.getTowers());
		//fail("Not yet implemented");
	}

	@Test
	public void testGetDices() {
		assertArrayEquals(this.dices, this.gb.getDices());
		//fail("Not yet implemented");
	}

	@Test
	public void testGetExcommunications() {
		assertArrayEquals(this.excommunications, this.gb.getExcommunications());
		//fail("Not yet implemented");
	}

	@Test
	public void testGetHarvestSpace() {
		assertEquals(this.harvestSpace, this.gb.getHarvestSpace());
		//fail("Not yet implemented");
	}

	@Test
	public void testGetProductionSpace() {
		assertEquals(this.productionSpace, this.gb.getProductionSpace());
		//fail("Not yet implemented");
	}

	@Test
	public void testGetCoinSpace() {
		assertEquals(this.coinSpace, this.gb.getCoinSpace());
		//fail("Not yet implemented");
	}

	@Test
	public void testGetServantSpace() {
		assertEquals(this.servantSpace, this.gb.getServantSpace());
		//fail("Not yet implemented");
	}

	@Test
	public void testGetMixedSpace() {
		assertEquals(this.mixedSpace, this.gb.getMixedSpace());
		//fail("Not yet implemented");
	}

	@Test
	public void testGetPrivilegesSpace() {
		assertEquals(this.privilegesSpace, this.gb.getPrivilegesSpace());
		//fail("Not yet implemented");
	}

	@Test
	public void testGetBonusFaithPoints() {
		assertEquals(this.bonusFaithPoints, this.gb.getBonusFaithPoints());
		//fail("Not yet implemented");
	}

	@Test
	public void testGetCouncilPalace() {
		assertEquals(this.councilPalace, this.gb.getCouncilPalace());
		//fail("Not yet implemented");
	}

}
