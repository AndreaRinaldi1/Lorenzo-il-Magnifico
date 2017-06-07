package it.polimi.ingsw.GC_28.effectsTest;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
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
import it.polimi.ingsw.GC_28.components.DiceColor;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.effects.NoEffect;
import it.polimi.ingsw.GC_28.model.Game;
import it.polimi.ingsw.GC_28.model.GameModel;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.model.PlayerColor;

public class NoEffectTest {
	private NoEffect ne;
	private Game g;
	private FamilyMember fm;
	private Player player;
	
	private Resource res;
	EnumMap<ResourceType, Integer> w;
	private GameBoard gb;
	private GameModel gameModel;
	private List<Player> players = new ArrayList<>();
	
	@Before
	public void noEffect(){
		player = new Player("bob", PlayerColor.BLUE); 
		ne = new NoEffect();
		fm = new FamilyMember(player, false, DiceColor.ORANGE);
		w = new EnumMap<ResourceType, Integer>(ResourceType.class);
		for(ResourceType resType : ResourceType.values()){
			w.put(resType, 1);
		}
		res = Resource.of(w);
		BonusTile bt = new BonusTile();
		PlayerBoard pb = new PlayerBoard(bt, res);
		player.setBoard(pb);
		players.add(player);
		gameModel = new GameModel(gb, players);
		g = new Game(gameModel);
		g.setCurrentPlayer(player);
		gb = new GameBoard();
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testApply() {
		System.out.println(res);
		System.out.println(fm.getPlayer().getBoard().getResources());
		ne.apply(fm, g);

		System.out.println(res);
		System.out.println(fm.getPlayer().getBoard().getResources());
		boolean x = res.equals(fm.getPlayer().getBoard().getResources());
		assertTrue(x);
	}

}
