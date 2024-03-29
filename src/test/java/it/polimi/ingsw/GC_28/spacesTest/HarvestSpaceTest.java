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
import it.polimi.ingsw.GC_28.effects.HarvestEffect;
import it.polimi.ingsw.GC_28.effects.ProductionEffect;
import it.polimi.ingsw.GC_28.effects.ResourceEffect;
import it.polimi.ingsw.GC_28.model.GameModel;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.model.PlayerColor;
import it.polimi.ingsw.GC_28.spaces.ProdHarvSpace;
import it.polimi.ingsw.GC_28.spaces.ProdHarvType;
import it.polimi.ingsw.GC_28.view.GameView;

public class HarvestSpaceTest {

		private ProdHarvSpace phs;
		private FamilyMember firstPlayer;
		private boolean secondarySpace;
		private Player p;
		private boolean neutral;
		
		private GameView g;
		private GameView g1;
		private FamilyMember fm;
		private FamilyMember fm1;
		private PlayerBoard pb;
		private BonusTile bt;
		EnumMap<ResourceType, Integer> resource;
		private Resource resources;
		
		private Territory b;
		
		@Before
		public void productionAndHarvestSpace(){
			phs = new ProdHarvSpace(true, 1);
			phs.setType(ProdHarvType.HARVEST);
			p = new Player("Rob", PlayerColor.BLUE);
			firstPlayer = new FamilyMember(p, true, DiceColor.NEUTRAL);
			
			resource = new EnumMap<>(ResourceType.class);
			resource.put(ResourceType.COIN, 3);
			resources = Resource.of(resource);
			
			fm = new FamilyMember(p, false, DiceColor.BLACK);
			fm1 = new FamilyMember(p, false, DiceColor.ORANGE);
			bt = new BonusTile();
			
			EnumMap<ResourceType, Integer> w = new EnumMap<ResourceType, Integer>(ResourceType.class);

			for(ResourceType resType : ResourceType.values()){
				w.put(resType, 0);
			}
			Resource res = Resource.of(w);
			
			pb = new PlayerBoard(bt, res);
			b = new Territory("bob", 2, 2);
			
		
			
			HarvestEffect e = new HarvestEffect();
			e.setHarvestValue(1);
			fm.setValue(2);
			ResourceEffect eff = new ResourceEffect();
			eff.setResourceBonus(resources);
			e.setResourceHarvestBonus(eff);
			b.setPermanentEffect(e);
			
			EnumMap<ResourceType, Integer> m = new EnumMap<>(ResourceType.class);
			m.put(ResourceType.COIN, 3);
			Resource t = Resource.of(m);
			ResourceEffect ff = new ResourceEffect();
			ff.setResourceBonus(t);
			HarvestEffect u = new HarvestEffect();
			u.setResourceHarvestBonus(ff);
			bt.setHarvestEffect(u);
			p.setBoard(pb);
			List<Player> players = new ArrayList<>();
			players.add(p);
			
			GameBoard gb = new GameBoard();
			GameModel gm = new GameModel(gb,players);
			
			g = new GameView(gm);
		
			g.setCurrentPlayer(p);
			
			
		}
		
		@AfterClass
		public static void tearDownAfterClass() throws Exception {
		}

		@Test
		public void testApplyBonus() {
			System.out.println(fm.getPlayer().getBoard().getResources().toString());
			pb.addCard(b);
			phs.applyBonus(g, fm);

			EnumMap<ResourceType, Integer> map = new EnumMap<>(ResourceType.class);
			map.put(ResourceType.COIN, 6);
			Resource r = Resource.of(map);
			System.out.println(fm.getPlayer().getBoard().getResources().toString());
			boolean x = r.equals(fm.getPlayer().getBoard().getResources());
			assertTrue(x);
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


