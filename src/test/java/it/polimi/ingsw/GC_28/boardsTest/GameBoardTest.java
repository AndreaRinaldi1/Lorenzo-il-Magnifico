package it.polimi.ingsw.GC_28.boardsTest;

import static org.junit.Assert.*;

import java.util.EnumMap;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import de.vandermeer.asciitable.AsciiTable;
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
	
	private String emptySpace = "( )\n";
	private String occSpace = "(X)\n";
	private String bigEmptySpace = "(        )\n";
	private String bigOccupiedSpace = "(XXXXXXX)\n";
	
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

/*	@Test
	public void testDisplay() {
		StringBuilder ret = new StringBuilder();
		ret.append("GAME BOARD\n");
		
		AsciiTable at = new AsciiTable();
		at.addRule();
		at.addRow("TERRITORY","BUILDING", "CHARACTER", "VENTURE");
		for(int i = 3; i >= 0; i--){
			at.addRule();
			at.addRow(towers.get(CardType.TERRITORY).getCells()[i].getCard()  != null ? towers.get(CardType.TERRITORY).getCells()[i].getCard().getName() :  "***",
					  towers.get(CardType.BUILDING).getCells()[i].getCard() != null ? towers.get(CardType.BUILDING).getCells()[i].getCard().getName() :  "***",
					  towers.get(CardType.CHARACTER).getCells()[i].getCard() != null ? towers.get(CardType.CHARACTER).getCells()[i].getCard().getName() : "***",
					  towers.get(CardType.VENTURE).getCells()[i].getCard() != null ? towers.get(CardType.VENTURE).getCells()[i].getCard().getName() :  "***" );
		}
		at.addRule();

		String board = at.render() + "\n";
		ret.append(board);
		
		//Council Palace
		AsciiTable councilTable = new AsciiTable();
		councilTable.addRule();
		councilTable.addRow("Council Palace: ");
		councilTable.addRule();
		for (int i = 0; i < councilPalace.getPlayer().size(); i++){
			councilTable.addRow(councilPalace.getPlayer().get(i).getPlayer().getName() + "  " +
								councilPalace.getPlayer().get(i).getPlayer().getColor());
			councilTable.addRule();
		}
		String cp = councilTable.render() + "\n";
		ret.append(cp);
		
		AsciiTable church = new AsciiTable();
		church.addRule();
		church.addRow("Church", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15");
		church.addRule();
		ret.append(church.render() + "\n");			//ho bisogno delle carte scomunica
		
		
		AsciiTable spaces = new AsciiTable();
		spaces.addRule();
		spaces.addRow("Coin Space", "Servant Space", "Mixed Space", "Privileges Space");
		spaces.addRule();
		spaces.addRow(coinSpace.isFree() ? emptySpace : occSpace, 
					servantSpace.isFree() ? emptySpace : occSpace,
					mixedSpace.isFree() ? emptySpace : occSpace, 
					privilegesSpace.isFree() ? emptySpace : occSpace);
		spaces.addRule();
		ret.append(spaces.render() + "\n");
		
		
		AsciiTable prodHarv = new AsciiTable();
		prodHarv.addRule();
		prodHarv.addRow("Production Space", "Harvest Space");
		prodHarv.addRule();
		prodHarv.addRow((productionSpace.isFree() ? emptySpace : occSpace) + "  " + (productionSpace.isSecondarySpace() ? bigEmptySpace : bigOccupiedSpace) , 
						(harvestSpace.isFree() ? emptySpace : occSpace) + "  " + (harvestSpace.isSecondarySpace() ? bigEmptySpace : bigOccupiedSpace));
		prodHarv.addRule();
		ret.append(prodHarv.render() + "\n");
		
		assertEquals(ret.toString(), this.gb.toString());
		
	}
	*/
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
