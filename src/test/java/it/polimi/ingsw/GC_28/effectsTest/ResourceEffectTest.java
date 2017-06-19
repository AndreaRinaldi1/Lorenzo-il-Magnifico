package it.polimi.ingsw.GC_28.effectsTest;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.components.DiceColor;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.effects.ResourceEffect;
import it.polimi.ingsw.GC_28.model.BoardsInitializer;
import it.polimi.ingsw.GC_28.model.Game;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.model.PlayerColor;

public class ResourceEffectTest {
	private ResourceEffect re;
	private Resource resourceBonus;
	EnumMap<ResourceType, Integer> resource;
	private Player p = new Player("stan", PlayerColor.BLUE);
	FamilyMember[] fm = new FamilyMember[4];
	
	private Game game;
	private BoardsInitializer bi = new BoardsInitializer();
	@Before
	public void resourceEffect(){
		re = new ResourceEffect();
		
		resource = new EnumMap<>(ResourceType.class);
		resource.put(ResourceType.STONE, 1);
		resourceBonus = Resource.of(resource);
		re.setResourceBonus(resourceBonus);
		int i = 0;
		
		for(DiceColor dc : DiceColor.values()){
			if(dc != DiceColor.NEUTRAL){
				FamilyMember f = new FamilyMember(p, false, dc);
				f.setValue(6);
				fm[i] = f;
			}else{
				FamilyMember f = new FamilyMember(p, true, dc);
				f.setValue(6);
				fm[i] = f;
			}
			i++;
		}
		p.setFamilyMembers(fm);
		List<Player> players = new ArrayList<>();
		players.add(p);
		try {
			game = bi.initializeBoard(players);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		game.setCurrentPlayer(p);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testApply() {
		re.apply(fm[0], game);
	}
	
	@Test
	public void testApply2(){
		re.apply(p, game);
	}

	@Test
	public void testGetResourceBonus() {
		assertEquals(this.resourceBonus, this.re.getResourceBonus());
	}

}
