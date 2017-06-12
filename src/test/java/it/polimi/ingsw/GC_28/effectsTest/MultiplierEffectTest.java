package it.polimi.ingsw.GC_28.effectsTest;

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
import it.polimi.ingsw.GC_28.cards.Building;
import it.polimi.ingsw.GC_28.cards.CardType;
import it.polimi.ingsw.GC_28.cards.ExcommunicationTile;
import it.polimi.ingsw.GC_28.cards.Territory;
import it.polimi.ingsw.GC_28.cards.Venture;
import it.polimi.ingsw.GC_28.cards.Character;
import it.polimi.ingsw.GC_28.components.DiceColor;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.effects.MultiplierEffect;
import it.polimi.ingsw.GC_28.model.Game;
import it.polimi.ingsw.GC_28.model.GameModel;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.model.PlayerColor;

public class MultiplierEffectTest {
	private MultiplierEffect me;
	private Resource resourceCost;
	EnumMap<ResourceType, Integer> resource;
	private CardType cardType;
	private Resource resourceBonus;
	EnumMap<ResourceType, Integer> resource1;
	
	private	Game g;
	private FamilyMember fm;
	private FamilyMember fm2;
	private Player player;
	private Player player2;
	
	private GameBoard gb;
	private PlayerBoard pb;
	
	private GameModel gameModel;
	private List<Player> players = new ArrayList<>();
	
	private BonusTile bt;
	
	private Territory t;
	private Building b;
	private Character c;
	private Venture v;
	
	@Before
	public void multiplierEffect(){
		//costruisci l'effetto
		me = new MultiplierEffect();
		
		//fai i player
				player = new Player("gino", PlayerColor.GREEN);
				player2 = new Player("Mariangiongianela", PlayerColor.BLUE);
		
		//inizializzazione pb tutto a 0
		EnumMap<ResourceType, Integer> w = new EnumMap<ResourceType, Integer>(ResourceType.class);
		for(ResourceType resType : ResourceType.values()){
			w.put(resType, 0);
		}
		Resource res = Resource.of(w);
		bt = new BonusTile();
		pb = new PlayerBoard(bt, res);
		player.setBoard(pb);
		//pb.addCard(b);
		
		//fai resources o privilegi o cardtype
		resource = new EnumMap<>(ResourceType.class);
		resource1 = new EnumMap<>(ResourceType.class);	
		cardType = CardType.BUILDING;
		
		resource.put(ResourceType.SERVANT, 1);
		resourceCost = Resource.of(resource);
		resource1.put(ResourceType.SERVANT, 2);
		resourceBonus = Resource.of(resource1);
		
		b = new Building("casa", 2, 1);
		t = new Territory("test1", 1, 1);
		c = new Character("characterCard", 3, 1);
		v = new Venture("ventureCard", 4, 2);
		
		//fai il game
		players.add(player);
		players.add(player2);
		gameModel = new GameModel(gb, players);
		g = new Game(gameModel);
		
		//fai i familyMember
		fm = new FamilyMember(player, false, DiceColor.ORANGE);
		fm2 = new FamilyMember(player2, false, DiceColor.WHITE);
		fm.setValue(2);
		fm2.setValue(2);
		
		//fai gb e la setti in game e setti currentPlayer in game
		gb = new GameBoard();
		g.setCurrentPlayer(player);
		
		
	
		
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	//TEST OF APPLY(FAMILYMEMBER, GAME), con i resourceCost
	@Test
	public void testApply1() {
		//me.setCardType(cardType);
		//pb.addCard(b);
		fm.getPlayer().addResource(resourceCost);
		me.setResourceBonus(resourceBonus);
		me.setResourceCost(resourceCost);
	
		me.apply(fm, g);
		resourceBonus.modifyResource(resourceCost, true);
		
		boolean x = resourceBonus.equals(fm.getPlayer().getBoard().getResources());
		assertTrue(x);
	}
	
	//APPLY (FAMILYMEMBER, GAME) con la carta
	@Test
	public void testApply3(){
		me.setCardType(cardType);
		pb.addCard(b);
		me.setResourceBonus(resourceBonus);
		me.apply(fm, g);
		boolean x = resourceBonus.equals(fm.getPlayer().getBoard().getResources());
		assertTrue(x);
	}
	
	//TEST OF APPLY(PLAYER, GAME) CON CARD TYPE
	@Test
	public void testApply2(){
		me.setCardType(cardType);
		me.setResourceBonus(resourceBonus);
		//me.setResourceCost(resourceCost);
		pb.addCard(b);
		
		me.apply(player, g);
		boolean x = resourceBonus.equals(player.getBoard().getResources());
		assertTrue(x);
	}
	@Test
	public void testApply5(){
		me.setCardType(CardType.TERRITORY);
		me.setResourceBonus(resourceBonus);
		//me.setResourceCost(resourceCost);
		pb.addCard(t);
		
		me.apply(player, g);
		boolean x = resourceBonus.equals(player.getBoard().getResources());
		assertTrue(x);
	}
	
	@Test
	public void testApply6(){
		me.setCardType(CardType.CHARACTER);
		me.setResourceBonus(resourceBonus);
		//me.setResourceCost(resourceCost);
		pb.addCard(c);
		
		me.apply(player, g);
		boolean x = resourceBonus.equals(player.getBoard().getResources());
		assertTrue(x);
	}
	
	@Test
	public void testApply7(){
		me.setCardType(CardType.VENTURE);
		me.setResourceBonus(resourceBonus);
		//me.setResourceCost(resourceCost);
		pb.addCard(v);
		
		me.apply(player, g);
		boolean x = resourceBonus.equals(player.getBoard().getResources());
		assertTrue(x);
	}
	
	//TEST OF APPLY(PLAYER, GAME) CON RESOURCE COST
	@Test
	public void testApply4() {
		fm.getPlayer().addResource(resourceCost);
		me.setResourceCost(resourceCost);
		me.setResourceBonus(resourceBonus);

		me.apply(player, g);
		resourceBonus.modifyResource(resourceCost, true);
		boolean x = resourceBonus.equals(player.getBoard().getResources());
		assertTrue(x);
	}

	@Test
	public void testGetResourceCost() {
		resource.put(ResourceType.COIN, 1);
		resourceCost = Resource.of(resource); 
		me.setResourceCost(resourceCost);
		assertEquals(this.resourceCost, this.me.getResourceCost());
	}

	@Test
	public void testGetResourceBonus() {
		me.setResourceBonus(resourceBonus);
		assertEquals(this.resourceBonus, this.me.getResourceBonus());
	}

	@Test
	public void testGetCardType() {
		me.setCardType(cardType);
		assertEquals(CardType.BUILDING, this.me.getCardType());
	}

}
