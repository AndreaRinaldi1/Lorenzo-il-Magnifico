package it.polimi.ingsw.GC_28.effectsTest;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import javax.crypto.interfaces.PBEKey;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.boards.PlayerBoard;
import it.polimi.ingsw.GC_28.cards.Building;
import it.polimi.ingsw.GC_28.cards.CardReader;
import it.polimi.ingsw.GC_28.cards.CardType;
import it.polimi.ingsw.GC_28.cards.Character;
import it.polimi.ingsw.GC_28.cards.Deck;
import it.polimi.ingsw.GC_28.cards.Venture;
import it.polimi.ingsw.GC_28.components.DiceColor;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.effects.FinalReduceEffect;
import it.polimi.ingsw.GC_28.model.GameModel;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.model.PlayerColor;
import it.polimi.ingsw.GC_28.view.GameView;

public class FinalReduceEffectTest {
	private FinalReduceEffect fre;
	private CardType cardType;
	private Resource resourceCost;
	private Resource resourceCost1;
	private Resource resourceBonus;
	private Resource resourceBonus1;
	private int times = 0;
	EnumMap<ResourceType, Integer> resource;
	EnumMap<ResourceType, Integer> resource1;
	private Resource amount;
	
	private FamilyMember fm;
	private GameView g;
	private Player p;
	private Player p1;
	private GameModel gameModel;
	private GameBoard gameBoard; 
	private List<Player> players = new ArrayList<>();
	private PlayerBoard pb;

	private Building b;
	private it.polimi.ingsw.GC_28.cards.Character c;
	
	@Before
	public void finalReduceEffect() throws FileNotFoundException{
		fre = new FinalReduceEffect();
		cardType = CardType.BUILDING;
		resource = new EnumMap<ResourceType, Integer>(ResourceType.class);
		resource.put(ResourceType.COIN, 5);
		resource1 = new EnumMap<ResourceType, Integer>(ResourceType.class);
		resource1.put(ResourceType.COIN, 5);
		resourceBonus = Resource.of(resource);
		resourceBonus1 = Resource.of(resource1);
		resourceCost = Resource.of(resource);
		resourceCost1 = Resource.of(resource1);
		
		fre.setCardType(cardType);
		fre.setResourceCost(resourceCost);
		fre.setResourceBonus(resourceBonus);
		
		EnumMap<ResourceType, Integer> w = new EnumMap<ResourceType, Integer>(ResourceType.class);
		for(ResourceType resType : ResourceType.values()){
			w.put(resType, 0);
		}
		Resource res = Resource.of(w);
		pb = new PlayerBoard(null, res);
		
		players.add(p);
		players.add(p1);
		gameBoard = new GameBoard();
		gameModel = new GameModel(gameBoard, players);
		g = new GameView(gameModel);
		p = new Player("bob", PlayerColor.YELLOW);
		p1 = new Player("wob", PlayerColor.BLUE);
		p.setBoard(pb);
		p1.setBoard(pb);
		fm = new FamilyMember(p, false, DiceColor.WHITE);
	
		b = new Building("casa", 2, 1);
		b.setCost(resourceCost1);
		c = new Character("io", 5, 1);
		c.setCost(resourceCost);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	//apply with building card
	@Test
	public void testApplyPlayerGameBuilding() {
		int times1 = 0;
		
		pb.addCard(b);	//random card
		p1.addResource(fre.multiplyResource(times));

		fre.apply(p, g);

		assertEquals(this.p.getBoard().getResources(), this.p1.getBoard().getResources());
	}

	//apply with Character card
	@Test
	public void testApplyPlayerGameCharacter() {
		fre.setCardType(CardType.CHARACTER);
		pb.addCard(c);	//random card
		
		p1.addResource(fre.multiplyResource(times));

		fre.apply(p1, g);
	}
	
	//apply with Other card
	@Test
	public void testApplyPlayerGameOther() {
		fre.setCardType(CardType.VENTURE);
		
		p1.addResource(fre.multiplyResource(times));
		fre.apply(p1, g);
		fre.getType();
	}
	
	@Test
	public void testSetCardType() {
		assertEquals(cardType, this.fre.getCardType());
	}

	@Test
	public void testSetResourceCost() {
		assertEquals(resourceCost, this.fre.getResourceCost());
	}

	@Test
	public void testSetResourceBonus() {
		assertEquals(resourceBonus, this.fre.getResourceBonus());
	}

	@Test
	public void testMultiplyResource() {
		times = 3;
		for(ResourceType resType : resourceBonus1.getResource().keySet()){
			resource1.put(resType, resourceBonus1.getResource().get(resType) * times);
		}
		amount = Resource.of(resource1);
		boolean x = amount.equals(fre.multiplyResource(times));
		assertTrue(x);
	}

}
