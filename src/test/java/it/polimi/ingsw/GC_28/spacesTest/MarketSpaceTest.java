package it.polimi.ingsw.GC_28.spacesTest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.boards.BonusTile;
import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.boards.PlayerBoard;
import it.polimi.ingsw.GC_28.components.DiceColor;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.effects.ResourceEffect;
import it.polimi.ingsw.GC_28.model.GameModel;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.model.PlayerColor;
import it.polimi.ingsw.GC_28.spaces.MarketSpace;
import it.polimi.ingsw.GC_28.view.GameView;

public class MarketSpaceTest {
	private MarketSpace ms;
	private ResourceEffect bonus;
	private Resource r;
	EnumMap<ResourceType, Integer> resource;
	
	private GameView g;
	private FamilyMember fm;
	private Player p;
	private boolean neutral;
	private PlayerBoard pb;
	private BonusTile bonusTile;
	
	
	@Before
	public void marketSpace(){
		ms = new MarketSpace(true, 1);
		bonus = new ResourceEffect();
		resource = new EnumMap<>(ResourceType.class);
		resource.put(ResourceType.COIN, 5);
		r = Resource.of(resource);
		bonus.setResourceBonus(r);
		ms.setBonus(bonus);
		
		EnumMap<ResourceType, Integer> w = new EnumMap<>(ResourceType.class);
		for(ResourceType resType : ResourceType.values()){
			w.put(resType, 0);
		}
		Resource res = Resource.of(w);

		bonusTile = new BonusTile();
		pb = new PlayerBoard(bonusTile, res);
		
		p = new Player("bob", PlayerColor.YELLOW);
		p.setBoard(pb);
		fm = new FamilyMember(p, false, DiceColor.WHITE);
		
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
		ms.applyBonus(g, fm);

		boolean x = r.equals(fm.getPlayer().getBoard().getResources());
		assertTrue(x);
	}

	@Test
	public void testGetBonus() {
		assertEquals(this.bonus, this.ms.getBonus());
	}

}

