package it.polimi.ingsw.GC_28.boardsTest;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.EnumMap;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.vandermeer.asciitable.AsciiTable;
import it.polimi.ingsw.GC_28.boards.*;
import it.polimi.ingsw.GC_28.cards.CardReader;
import it.polimi.ingsw.GC_28.cards.CardType;
import it.polimi.ingsw.GC_28.cards.Deck;
import it.polimi.ingsw.GC_28.cards.ExcommunicationTile;
import it.polimi.ingsw.GC_28.components.Dice;
import it.polimi.ingsw.GC_28.components.DiceColor;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;
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
	
	//copy of gameboard
	private GameBoard gb1;
	
	private ProductionAndHarvestSpace harvestSpace1;
	private ProductionAndHarvestSpace productionSpace1;
	private MarketSpace coinSpace1;
	private MarketSpace servantSpace1;
	private MarketSpace mixedSpace1;
	private PrivilegesSpace privilegesSpace1;
	private Resource bonusFaithPoints1;
	private CouncilPalace councilPalace1;
	
	private Map<CardType, Tower> towers1 = new EnumMap<>(CardType.class);
	private Dice[] dices1 = new Dice[3];
	private ExcommunicationTile[] excommunications1 = new ExcommunicationTile[3];
	
	//Cards
	//private Gson gson = new GsonBuilder().create();
	//private Deck d = new Deck();
	//private CardReader cr;
	
	@Before
	public void gameBoard() throws FileNotFoundException{
		
		//deck setup
		//cr = new CardReader();		
		//d.equals(cr.startRead());
		
		gb = new GameBoard();
		
		int actionValue = 1;
		boolean free = true;
		EnumMap<ResourceType, Integer> resource = new EnumMap<>(ResourceType.class);
		resource.put(ResourceType.FAITHPOINT, 2);
		this.productionSpace = new ProductionAndHarvestSpace(free, actionValue);
		this.harvestSpace = new ProductionAndHarvestSpace(free, actionValue);
		this.coinSpace = new MarketSpace(free, actionValue);
		this.servantSpace = new MarketSpace(free, actionValue);
		this.mixedSpace = new MarketSpace(free, actionValue);
		this.privilegesSpace = new PrivilegesSpace(free, actionValue);
		this.councilPalace = CouncilPalace.instance();
		this.bonusFaithPoints = Resource.of(resource);
		gb.setProductionSpace(productionSpace);
		gb.setHarvestSpace(harvestSpace);
		gb.setCoinSpace(coinSpace);
		gb.setServantSpace(servantSpace);
		gb.setMixedSpace(mixedSpace);
		gb.setPrivilegesSpace(privilegesSpace);
		gb.setCouncilPalace(councilPalace);
		gb.setBonusFaithPoints(bonusFaithPoints);
	
		Cell[] cells = new Cell[4];
		for(int i = 0; i < cells.length; i++){
			cells[i] = new Cell(null, actionValue, free);
		}
		Tower tower = new Tower(cells);
		for(int l = 0; l < CardType.values().length; l++){
			this.towers.put(CardType.values()[l], tower);
		}
		gb.setTowers(towers);
		for(int j = 0; j < dices.length; j++){
			dices[j] = new Dice(DiceColor.values()[j]);
			dices[j].rollDice();
		}
		gb.setDices(dices);
		for(int k = 0; k < excommunications.length; k++){
			excommunications[k] = new ExcommunicationTile();
		}
		gb.setExcommunications(excommunications);
	
		//gameboardSetup
		gb1 = new GameBoard();
		
		this.productionSpace1 = new ProductionAndHarvestSpace(free, actionValue);
		this.harvestSpace1 = new ProductionAndHarvestSpace(free, actionValue);
		this.coinSpace1 = new MarketSpace(free, actionValue);
		this.servantSpace1 = new MarketSpace(free, actionValue);
		this.mixedSpace1 = new MarketSpace(free, actionValue);
		this.privilegesSpace1 = new PrivilegesSpace(free, actionValue);
		this.councilPalace1 = CouncilPalace.instance();
		this.bonusFaithPoints1 = Resource.of(resource);
		gb1.setProductionSpace(productionSpace1);
		gb1.setHarvestSpace(harvestSpace1);
		gb1.setCoinSpace(coinSpace1);
		gb1.setServantSpace(servantSpace1);
		gb1.setMixedSpace(mixedSpace1);
		gb1.setPrivilegesSpace(privilegesSpace1);
		gb1.setCouncilPalace(councilPalace1);
		gb1.setBonusFaithPoints(bonusFaithPoints1);
		for(int l = 0; l < CardType.values().length; l++){
			this.towers1.put(CardType.values()[l], tower);
		}
		gb1.setTowers(towers1);
		gb1.setDices(dices);
		gb1.setExcommunications(excommunications);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testDisplay() {
		StringBuilder ret = new StringBuilder();
		ret.append("GAME BOARD\n");
		
		AsciiTable at = new AsciiTable();
		at.addRule();
		at.addRow("TERRITORY","BUILDING", "CHARACTER", "VENTURE");
		for(int i = 3; i >= 0; i--){
			at.addRule();
			at.addRow(towers1.get(CardType.TERRITORY).getCells()[i].getCard()  != null ? towers1.get(CardType.TERRITORY).getCells()[i].getCard().getName() :  "***",
					  towers1.get(CardType.BUILDING).getCells()[i].getCard() != null ? towers1.get(CardType.BUILDING).getCells()[i].getCard().getName() :  "***",
					  towers1.get(CardType.CHARACTER).getCells()[i].getCard() != null ? towers1.get(CardType.CHARACTER).getCells()[i].getCard().getName() : "***",
					  towers1.get(CardType.VENTURE).getCells()[i].getCard() != null ? towers1.get(CardType.VENTURE).getCells()[i].getCard().getName() :  "***" );
		}
		at.addRule();

		String board = at.render() + "\n";
		ret.append(board);
		
		//Council Palace
		AsciiTable councilTable = new AsciiTable();
		councilTable.addRule();
		councilTable.addRow("Council Palace: ");
		councilTable.addRule();
		for (int i = 0; i < councilPalace1.getPlayer().size(); i++){
			councilTable.addRow(councilPalace1.getPlayer().get(i).getPlayer().getName() + "  " +
								councilPalace1.getPlayer().get(i).getPlayer().getColor());
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
		spaces.addRow(coinSpace1.isFree() ? emptySpace : occSpace, 
					servantSpace1.isFree() ? emptySpace : occSpace,
					mixedSpace1.isFree() ? emptySpace : occSpace, 
					privilegesSpace1.isFree() ? emptySpace : occSpace);
		spaces.addRule();
		ret.append(spaces.render() + "\n");
		
		
		AsciiTable prodHarv = new AsciiTable();
		prodHarv.addRule();
		prodHarv.addRow("Production Space", "Harvest Space");
		prodHarv.addRule();
		prodHarv.addRow((productionSpace1.isFree() ? emptySpace : occSpace) + "  " + (productionSpace1.isSecondarySpace() ? bigEmptySpace : bigOccupiedSpace) , 
						(harvestSpace1.isFree() ? emptySpace : occSpace) + "  " + (harvestSpace1.isSecondarySpace() ? bigEmptySpace : bigOccupiedSpace));
		prodHarv.addRule();
		ret.append(prodHarv.render() + "\n");


		assertEquals(ret.toString(), this.gb.display());
		
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
