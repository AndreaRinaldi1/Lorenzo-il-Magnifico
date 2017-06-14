package it.polimi.ingsw.GC_28.spacesTest;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.spaces.CouncilPalace;
import it.polimi.ingsw.GC_28.spaces.EverySpace;
import it.polimi.ingsw.GC_28.spaces.MarketSpace;
import it.polimi.ingsw.GC_28.spaces.PrivilegesSpace;
import it.polimi.ingsw.GC_28.spaces.ProdHarvSpace;


public class EverySpaceTest {
	private MarketSpace coinSpace;
	private MarketSpace servantSpace;
	private MarketSpace mixedSpace;
	private PrivilegesSpace privilegesSpace;
	private CouncilPalace councilPalace;
	private ProdHarvSpace productionSpace;
	private ProdHarvSpace harvestSpace;
	
	private EverySpace es;
	
	@Before
	public void everySpace(){
		es = new EverySpace();
		
		coinSpace = new MarketSpace(true, 1);
		servantSpace = new MarketSpace(true, 1);
		mixedSpace = new MarketSpace(true, 1);
		privilegesSpace = new PrivilegesSpace(true, 1);
		councilPalace = CouncilPalace.instance();
		productionSpace = new ProdHarvSpace(true, 1);
		harvestSpace = new ProdHarvSpace(true, 1);
	
		es.setCoinSpace(coinSpace);
		es.setCouncilPalace(councilPalace);
		es.setPrivilegesSpace(privilegesSpace);
		es.setHarvest(harvestSpace);
		es.setMixedSpace(mixedSpace);
		es.setProduction(productionSpace);
		es.setServantSpace(servantSpace);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testGetPrivilegesSpace() {
		assertEquals(this.privilegesSpace, this.es.getPrivilegesSpace());;
	}

	@Test
	public void testGetCouncilPalace() {
		assertEquals(this.councilPalace, this.es.getCouncilPalace());
	}

	@Test
	public void testGetProduction() {
		assertEquals(this.productionSpace, this.es.getProduction());
	}

	@Test
	public void testGetHarvest() {
		assertEquals(this.harvestSpace, this.es.getHarvest());
	}

	@Test
	public void testGetCoinSpace() {
		assertEquals(this.coinSpace, this.es.getCoinSpace());
	}

	@Test
	public void testGetServantSpace() {
		assertEquals(this.servantSpace, this.es.getServantSpace());
	}

	@Test
	public void testGetMixedSpace() {
		assertEquals(this.mixedSpace, this.es.getMixedSpace());
	}

}
