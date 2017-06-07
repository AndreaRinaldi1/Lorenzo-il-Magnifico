package it.polimi.ingsw.GC_28.spacesTest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.boards.BonusTile;
import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.boards.PlayerBoard;
import it.polimi.ingsw.GC_28.cards.Building;
import it.polimi.ingsw.GC_28.cards.Territory;
import it.polimi.ingsw.GC_28.components.DiceColor;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.model.Game;
import it.polimi.ingsw.GC_28.model.GameModel;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.model.PlayerColor;
import it.polimi.ingsw.GC_28.spaces.ProductionAndHarvestSpace;

public class ProductionAndHarvestSpaceTest {
	private ProductionAndHarvestSpace phs;
	private FamilyMember firstPlayer;
	private boolean secondarySpace;
	private boolean harvest;
	private Player p;
	private boolean neutral;
	
	private Game g;
	private FamilyMember fm;
	private PlayerBoard pb;
	private BonusTile bt;
	EnumMap<ResourceType, Integer> resource;
	private Resource resources;
	private GameModel gameModel;
	private GameBoard gameBoard; 
	private List<Player> players = new ArrayList<>();

	
	private Building b;
	
	@Before
	public void productionAndHarvestSpace(){
		phs = new ProductionAndHarvestSpace(true, 1);
		p = new Player("Rob", PlayerColor.BLUE);
		firstPlayer = new FamilyMember(p, neutral, DiceColor.BLACK);
		
		resource = new EnumMap<>(ResourceType.class);
		resource.put(ResourceType.COIN, 3);
		resources = Resource.of(resource);
		
		players.add(p);
		gameBoard = new GameBoard();
		gameModel = new GameModel(gameBoard, players);
		g = new Game(gameModel); 
		
		fm = new FamilyMember(p, false, DiceColor.BLACK);
		bt = new BonusTile();
		pb = new PlayerBoard(bt, resources);
		b = new Building("bob", 2, 2);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testApplyBonus() {
		System.out.println(1);
		pb.addCard(b);
		phs.applyBonus(g, fm);
		System.out.println(2);
		/*if(harvest){
			phs.applyBonus(g, fm);

			fm.getPlayer().getBoard().getBonusTile().getHarvestEffect().apply(fm, g);
	
			assertEquals(this.fm.getPlayer().getBoard().getBonusTile().getHarvestEffect(), 
					phs);
		}
		else{
			phs.applyBonus(g1, fm1);

			fm.getPlayer().getBoard().getBonusTile().getProductionEffect().apply(fm, g);
			assertEquals(this.fm.getPlayer().getBoard().getBonusTile().getProductionEffect(), phs);

		}*/
		if(harvest){
			for(Territory territory : fm.getPlayer().getBoard().getTerritories()){
				territory.getPermanentEffect().apply(fm, g);
			}
			fm.getPlayer().getBoard().getBonusTile().getHarvestEffect().apply(fm, g);
			
		}
		else{
			for(Building building : fm.getPlayer().getBoard().getBuildings()){
				building.getPermanentEffect().apply(fm, g);
			}
			fm.getPlayer().getBoard().getBonusTile().getProductionEffect().apply(fm, g);
		}
		fail("Non sono capace a gestire il metodo apply");
	}

	@Test
	public void testIsHarvest() {
		phs.setFree(harvest);
		assertEquals(this.harvest, this.phs.isHarvest());
		//fail("Not yet implemented");
	}
	
	@Test
	public void testGetFirstPlayer() {
		phs.addPlayer(firstPlayer);		
		assertEquals(this.firstPlayer, this.phs.getFirstPlayer());
		//fail("Not yet implemented");
	}

	@Test
	public void testIsSecondarySpace() {
		assertEquals(this.secondarySpace, this.phs.isSecondarySpace());
		//fail("Not yet implemented");
	}
	
	@Test
	public void testFreeFirstPlayer() {
		phs.freeFirstPlayer();
		assertEquals(null, this.phs.getFirstPlayer());
		//fail("Not yet implemented");
	}
}
