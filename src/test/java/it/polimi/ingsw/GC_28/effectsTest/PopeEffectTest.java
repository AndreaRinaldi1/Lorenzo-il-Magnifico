package it.polimi.ingsw.GC_28.effectsTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.effects.PopeEffect;
import it.polimi.ingsw.GC_28.model.BoardsInitializer;
import it.polimi.ingsw.GC_28.model.Game;
import it.polimi.ingsw.GC_28.model.GameModel;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.model.PlayerColor;

public class PopeEffectTest {
	
	private PopeEffect popeEffect;
	private Resource res;
	EnumMap<ResourceType, Integer> tmp = new EnumMap<>(ResourceType.class);
	
	Player p;
	Game g;
	
	@Before
	public void popeEffect(){
		
		popeEffect = new PopeEffect();
		
		tmp.put(ResourceType.STONE, 1);
		res = Resource.of(tmp);
		popeEffect.setBonus(res);
		
		p = new Player("Anakin", PlayerColor.RED);
		List<Player> players = new ArrayList<>();
		players.add(p);
		
		BoardsInitializer bi = new BoardsInitializer();
		
		try {
			g = bi.initializeBoard(players);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetBonus(){
		Assert.assertEquals(res, popeEffect.getBonus());
	}
	
	@Test
	public void testApply(){
		popeEffect.apply(p, g);
	}
}
