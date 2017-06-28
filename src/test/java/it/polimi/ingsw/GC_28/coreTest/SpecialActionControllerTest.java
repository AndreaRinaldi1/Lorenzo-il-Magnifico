package it.polimi.ingsw.GC_28.coreTest;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.boards.PlayerBoard;
import it.polimi.ingsw.GC_28.cards.Building;
import it.polimi.ingsw.GC_28.cards.Card;
import it.polimi.ingsw.GC_28.cards.CardType;
import it.polimi.ingsw.GC_28.cards.LeaderCard;
import it.polimi.ingsw.GC_28.cards.Territory;
import it.polimi.ingsw.GC_28.cards.Venture;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.core.SpecialAction;
import it.polimi.ingsw.GC_28.core.SpecialActionController;
import it.polimi.ingsw.GC_28.model.BoardSetup;
import it.polimi.ingsw.GC_28.model.BoardsInitializer;
import it.polimi.ingsw.GC_28.model.GameModel;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.model.PlayerColor;
import it.polimi.ingsw.GC_28.view.GameManager;
import it.polimi.ingsw.GC_28.view.GameView;

public class SpecialActionControllerTest {
	private SpecialAction specialAction;
	private SpecialAction specialAction1;
	private BoardsInitializer bi = new BoardsInitializer();
	private GameModel gameModel;
	private Player player;
	private Player player1;
	private ArrayList<Player> players = new ArrayList<>();
	
	private LeaderCard leaderCard1 = new LeaderCard();
	private LeaderCard leaderCard = new LeaderCard();
	private ArrayList<LeaderCard> leaderCards = new ArrayList<>();
	private GameBoard gameBoard;
	private GameView gameView;
	private GameManager gameManager;
	
	private EnumMap<ResourceType, Integer> resources = new EnumMap<>(ResourceType.class);
	private Resource resource = Resource.of(resources);
	private EnumMap<ResourceType, Integer> resources1 = new EnumMap<>(ResourceType.class);
	private Resource resource1 = Resource.of(resources1);
	private HashMap<CardType, Integer> cardCost = new HashMap<>();
	private PlayerBoard playerBoard;
	
	private Territory t0;
	private Territory t1;
	private Territory t2;
	private Territory t3;
	private Territory t4;
	private Territory t5;
	
	private it.polimi.ingsw.GC_28.cards.Character c0;
	private it.polimi.ingsw.GC_28.cards.Character c1;
	private it.polimi.ingsw.GC_28.cards.Character c2;
	private it.polimi.ingsw.GC_28.cards.Character c3;
	private it.polimi.ingsw.GC_28.cards.Character c4;
	private it.polimi.ingsw.GC_28.cards.Character c5;

	private Building b0;
	private Building b1;
	private Building b2;
	private Building b3;
	private Building b4;
	private Building b5;
	
	private Venture v0; 
	private Venture v1; 
	private Venture v2; 
	private Venture v3; 
	private Venture v4; 
	private Venture v5; 
	
	private TestGame testGame;

	private class TestGame extends GameView{
		public TestGame(GameModel gameModel) {
			super(gameModel);
		}
		
		@Override
		public ArrayList<Character> askPrivilege(int numberOfCouncilPrivileges, boolean different){
			ArrayList<Character> w = new ArrayList<>();
			w.add('c');
			return w;
		}
		
		@Override
		public Resource checkResourceExcommunication(Resource amount){
			EnumMap<ResourceType, Integer> z = new EnumMap<>(ResourceType.class);
			z.put(ResourceType.COIN, 0);
			Resource bonus = Resource.of(z);
			return bonus;
		}
				
	}
	
	@Before
	public void specialActionTest() throws FileNotFoundException, IOException{
		gameBoard = new GameBoard();
		player = new Player("Buddy", PlayerColor.BLUE);
		player1 = new Player("Renato", PlayerColor.BLUE);
		players.add(player);
		gameModel = new GameModel(gameBoard, players);
		gameView = new GameView(gameModel);
		gameView = bi.initializeBoard(players);
		gameManager = new GameManager(60000);
		gameManager.setView(gameView);
		BoardSetup bs = new BoardSetup(gameManager);
		bs.firstSetUpCards();
		
		for(ResourceType resType : ResourceType.values()){
			resources.put(resType, -1);
		}
		resource = Resource.of(resources);
		for(ResourceType resType : ResourceType.values()){
			resources1.put(resType, 10);
		}
		resource1 = Resource.of(resources1);
		playerBoard = new PlayerBoard(null, resource);
		playerBoard.setResources(resource);
		
		leaderCards.add(leaderCard);

		player1.setLeaderCards(leaderCards);
		player1.setBoard(playerBoard);
		player.setBoard(playerBoard);
		testGame = new TestGame(gameModel);
		
		specialAction = new SpecialAction(player, gameModel, gameView);
		specialAction1 = new SpecialAction(player1, gameModel, testGame);
	} 

	
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	//action = discard checkForCardPresence = true
	@Test
	public void testCheck() {
		this.specialAction.setActionType("discard");
		this.specialAction.setLeaderName(gameManager.getView().getGameModel().getPlayers().get(0).getLeaderCards().get(0).getName());
		boolean x = this.specialAction.isApplicable();
		assertTrue(x);
	}

	//action = discard checkForCardPresence = false
	@Test
	public void testCheck1() {
		this.leaderCard.setName("Gino");
		this.specialAction1.setActionType("discard");
		this.specialAction1.setLeaderName("Lete");
		boolean x = this.specialAction1.isApplicable();
		assertFalse(x);
	}
	
	//action = discard checkPlayAction = true Lucrezia Borgia Card territory
	@Test
	public void testCheck2() {
		t0 = new Territory("ciao", 1, 2);
		t1 = new Territory("llsla", 2, 2);
		t2 = new Territory("ugo", 3, 2);
		t3 = new Territory("pico", 4, 2);
		t4 = new Territory("nano", 5, 2);
		t5 = new Territory("hola", 6, 2);
		
		this.specialAction1.setActionType("play");
		cardCost.put(CardType.TERRITORY, 1);
		leaderCard.setCardCost(cardCost);
		leaderCard.setResourceCost(resource);
		leaderCard.setName("Lucrezia Borgia");
		this.specialAction1.setLeaderName("lucrezia borgia");
		this.gameManager.getView().getGameModel().getPlayers().get(0).getLeaderCards().add(leaderCard1 );
		this.gameManager.getView().getGameModel().getPlayers().get(0).getBoard().getTerritories().add((t0));
		this.gameManager.getView().getGameModel().getPlayers().get(0).getBoard().getTerritories().add((t1));
		this.gameManager.getView().getGameModel().getPlayers().get(0).getBoard().getTerritories().add((t2));
		this.gameManager.getView().getGameModel().getPlayers().get(0).getBoard().getTerritories().add((t3));
		this.gameManager.getView().getGameModel().getPlayers().get(0).getBoard().getTerritories().add((t4));
		this.gameManager.getView().getGameModel().getPlayers().get(0).getBoard().getTerritories().add((t5));
		boolean x = this.specialAction1.isApplicable();
		assertTrue(x);
	}
	
	//action = discard checkPlayAction = true Lucrezia Borgia Card building
	@Test
	public void testCheck3() {
		b0 = new Building("ciao", 1, 2);
		b1 = new Building("ciao", 1, 2);
		b2 = new Building("ciao", 1, 2);
		b3 = new Building("ciao", 1, 2);
		b4 = new Building("ciao", 1, 2);
		b5 = new Building("ciao", 1, 2);
		
		this.specialAction1.setActionType("play");
		cardCost.put(CardType.BUILDING, 1);
		leaderCard.setCardCost(cardCost);
		leaderCard.setResourceCost(resource);
		leaderCard.setName("Lucrezia Borgia");
		this.specialAction1.setLeaderName("lucrezia borgia");
		this.gameManager.getView().getGameModel().getPlayers().get(0).getLeaderCards().add(leaderCard1 );
		this.gameManager.getView().getGameModel().getPlayers().get(0).getBoard().getBuildings().add(b0);
		this.gameManager.getView().getGameModel().getPlayers().get(0).getBoard().getBuildings().add(b1);
		this.gameManager.getView().getGameModel().getPlayers().get(0).getBoard().getBuildings().add(b2);
		this.gameManager.getView().getGameModel().getPlayers().get(0).getBoard().getBuildings().add(b3);
		this.gameManager.getView().getGameModel().getPlayers().get(0).getBoard().getBuildings().add(b4);
		this.gameManager.getView().getGameModel().getPlayers().get(0).getBoard().getBuildings().add(b5);
		boolean x = this.specialAction1.isApplicable();
		assertTrue(x);
	}
		
	//action = discard checkPlayAction = true Lucrezia Borgia Card character
	@Test
	public void testCheck4() {
		c0 = new it.polimi.ingsw.GC_28.cards.Character("ugo", 1, 1);
		c1 = new it.polimi.ingsw.GC_28.cards.Character("ugo", 1, 1);
		c2 = new it.polimi.ingsw.GC_28.cards.Character("ugo", 1, 1);
		c3 = new it.polimi.ingsw.GC_28.cards.Character("ugo", 1, 1);
		c4 = new it.polimi.ingsw.GC_28.cards.Character("ugo", 1, 1);
		c5 = new it.polimi.ingsw.GC_28.cards.Character("ugo", 1, 1);
		
		this.specialAction1.setActionType("play");
		cardCost.put(CardType.CHARACTER, 1);
		leaderCard.setCardCost(cardCost);
		leaderCard.setResourceCost(resource);
		leaderCard.setName("Lucrezia Borgia");
		this.specialAction1.setLeaderName("lucrezia borgia");
		this.gameManager.getView().getGameModel().getPlayers().get(0).getLeaderCards().add(leaderCard1 );
		this.gameManager.getView().getGameModel().getPlayers().get(0).getBoard().getCharacters().add(c0);
		this.gameManager.getView().getGameModel().getPlayers().get(0).getBoard().getCharacters().add(c1);
		this.gameManager.getView().getGameModel().getPlayers().get(0).getBoard().getCharacters().add(c2);
		this.gameManager.getView().getGameModel().getPlayers().get(0).getBoard().getCharacters().add(c3);
		this.gameManager.getView().getGameModel().getPlayers().get(0).getBoard().getCharacters().add(c4);
		this.gameManager.getView().getGameModel().getPlayers().get(0).getBoard().getCharacters().add(c5);
		boolean x = this.specialAction1.isApplicable();
		assertTrue(x);
	}
		
	//action = discard checkPlayAction = true Lucrezia Borgia Card venture
	@Test
	public void testCheck5() {
		v0 = new Venture("Blle", 1, 1);
		v1 = new Venture("Blle", 1, 1);
		v2 = new Venture("Blle", 1, 1);
		v3 = new Venture("Blle", 1, 1);
		v4 = new Venture("Blle", 1, 1);
		v5 = new Venture("Blle", 1, 1);
		
		this.specialAction1.setActionType("play");
		cardCost.put(CardType.VENTURE, 1);
		leaderCard.setCardCost(cardCost);
		leaderCard.setResourceCost(resource);
		leaderCard.setName("Lucrezia Borgia");
		this.specialAction1.setLeaderName("lucrezia borgia");
		this.gameManager.getView().getGameModel().getPlayers().get(0).getLeaderCards().add(leaderCard1 );
		this.gameManager.getView().getGameModel().getPlayers().get(0).getBoard().getVentures().add(v0);
		this.gameManager.getView().getGameModel().getPlayers().get(0).getBoard().getVentures().add(v1);
		this.gameManager.getView().getGameModel().getPlayers().get(0).getBoard().getVentures().add(v2);
		this.gameManager.getView().getGameModel().getPlayers().get(0).getBoard().getVentures().add(v3);
		this.gameManager.getView().getGameModel().getPlayers().get(0).getBoard().getVentures().add(v4);
		this.gameManager.getView().getGameModel().getPlayers().get(0).getBoard().getVentures().add(v5);
		boolean x = this.specialAction1.isApplicable();
		assertTrue(x);
	}
		
	//action = discard checkPlayAction = true gli enough true 
	@Test
	public void testCheck6() {
		v0 = new Venture("Blle", 1, 1);	
		this.specialAction1.setActionType("play");
		cardCost.put(CardType.TERRITORY, 0);
		cardCost.put(CardType.BUILDING, 0);
		cardCost.put(CardType.CHARACTER, 0);
		cardCost.put(CardType.VENTURE, 0);
		leaderCard.setCardCost(cardCost);
		leaderCard.setResourceCost(resource);
		leaderCard.setName("Aldo");
		this.specialAction1.setLeaderName("Aldo");
		this.gameManager.getView().getGameModel().getPlayers().get(0).getBoard().getVentures().add(v0);
		boolean x = this.specialAction1.isApplicable();
		assertTrue(x);
	}
		
	//action = discard checkPlayAction = true Lucrezia Borgia Card false, enoughCard venture false
	@Test
	public void testCheck7() {
		v0 = new Venture("Blle", 1, 1);	
		this.specialAction1.setActionType("play");
		cardCost.put(CardType.TERRITORY, 0);
		cardCost.put(CardType.BUILDING, 0);
		cardCost.put(CardType.CHARACTER, 0);
		cardCost.put(CardType.VENTURE, 5);
		leaderCard.setCardCost(cardCost);
		leaderCard.setResourceCost(resource);
		leaderCard.setName("Aldo");
		this.specialAction1.setLeaderName("Aldo");
		this.gameManager.getView().getGameModel().getPlayers().get(0).getBoard().getVentures().add(v0);
		boolean x = this.specialAction1.isApplicable();
		assertFalse(x);
	}
			
	//action = discard checkPlayAction = true Lucrezia Borgia Card false, enough Card territory false
	@Test
	public void testCheck8() {
		t0 = new Territory("Giacomo", 2, 1);	
		this.specialAction1.setActionType("play");
		cardCost.put(CardType.TERRITORY, 8);
		cardCost.put(CardType.BUILDING, 0);
		cardCost.put(CardType.CHARACTER, 0);
		cardCost.put(CardType.VENTURE, 0);
		leaderCard.setCardCost(cardCost);
		leaderCard.setResourceCost(resource);
		leaderCard.setName("Aldo");
		this.specialAction1.setLeaderName("Aldo");
		this.gameManager.getView().getGameModel().getPlayers().get(0).getBoard().getTerritories().add(t0);
		boolean x = this.specialAction1.isApplicable();
		assertFalse(x);
	}

	//action = discard checkPlayAction = true Lucrezia Borgia Card false, enough Card Building false
	@Test
	public void testCheck9() {
		b0 = new Building("terrazzo", 3, 3);
		this.specialAction1.setActionType("play");
		cardCost.put(CardType.TERRITORY, 0);
		cardCost.put(CardType.BUILDING, 8);
		cardCost.put(CardType.CHARACTER, 0);
		cardCost.put(CardType.VENTURE, 0);
		leaderCard.setCardCost(cardCost);
		leaderCard.setResourceCost(resource);
		leaderCard.setName("Aldo");
		this.specialAction1.setLeaderName("Aldo");
		this.gameManager.getView().getGameModel().getPlayers().get(0).getBoard().getBuildings().add(b0);
		boolean x = this.specialAction1.isApplicable();
		assertFalse(x);
	}
	
	//action = discard checkPlayAction = true Lucrezia Borgia Card false, enough Card Character false
	@Test
	public void testCheck10() {
		c0 = new it.polimi.ingsw.GC_28.cards.Character("Conte", 2, 3);
		this.specialAction1.setActionType("play");
		cardCost.put(CardType.TERRITORY, 0);
		cardCost.put(CardType.BUILDING, 0);
		cardCost.put(CardType.CHARACTER, 9);
		cardCost.put(CardType.VENTURE, 0);
		leaderCard.setCardCost(cardCost);
		leaderCard.setResourceCost(resource);
		leaderCard.setName("Aldo");
		this.specialAction1.setLeaderName("Aldo");
		this.gameManager.getView().getGameModel().getPlayers().get(0).getBoard().getCharacters().add(c0);
		boolean x = this.specialAction1.isApplicable();
		assertFalse(x);
	}

	//action = activate checkPlayAction = true Lucrezia Borgia Card false, enough Card territory false
	@Test
	public void testCheck11() {
		t0 = new Territory("Giacomo", 2, 1);	
		this.specialAction1.setActionType("activate");
		cardCost.put(CardType.TERRITORY, 8);
		cardCost.put(CardType.BUILDING, 0);
		cardCost.put(CardType.CHARACTER, 0);
		cardCost.put(CardType.VENTURE, 0);
		leaderCard.setCardCost(cardCost);
		leaderCard.setResourceCost(resource);
		leaderCard.setName("Aldo");
		leaderCard.setActive(false);
		leaderCard.setPlayed(true);
		this.specialAction1.setLeaderName("Aldo");
		this.gameManager.getView().getGameModel().getPlayers().get(0).getBoard().getTerritories().add(t0);
		boolean x = this.specialAction1.isApplicable();
		assertTrue(x);
	}
	
		
	
	//action = Activate activate and played true
	@Test
	public void testCheck12() {
		t0 = new Territory("Giacomo", 2, 1);	
		this.specialAction1.setActionType("activate");
		cardCost.put(CardType.TERRITORY, 8);
		cardCost.put(CardType.BUILDING, 0);
		cardCost.put(CardType.CHARACTER, 0);
		cardCost.put(CardType.VENTURE, 0);
		leaderCard.setCardCost(cardCost);
		leaderCard.setResourceCost(resource);
		leaderCard.setName("Aldo");
		leaderCard.setActive(true);
		leaderCard.setPlayed(true);
		this.specialAction1.setLeaderName("Aldo");
		this.gameManager.getView().getGameModel().getPlayers().get(0).getBoard().getTerritories().add(t0);
		boolean x = this.specialAction1.isApplicable();
		assertFalse(x);
	}

	//action = Activate activate, no familyMembers
	@Test
	public void testCheck15() {
		this.specialAction1.setActionType("discard");
		t0 = new Territory("Giacomo", 2, 1);	
		this.specialAction1.setActionType("activate");
		cardCost.put(CardType.TERRITORY, 8);
		cardCost.put(CardType.BUILDING, 0);
		cardCost.put(CardType.CHARACTER, 0);
		cardCost.put(CardType.VENTURE, 0);
		leaderCard.setCardCost(cardCost);
		leaderCard.setResourceCost(resource);
		leaderCard.setName("Aldo");
		leaderCard.setActive(true);
		leaderCard.setPlayed(true);
		this.specialAction1.setLeaderName("Aldo");
		this.gameManager.getView().getGameModel().getPlayers().get(0).getBoard().getTerritories().add(t0);
		boolean x = this.specialAction1.isApplicable();
		assertFalse(x);
	}

	
	//action = activate played false
	@Test
	public void testCheck13() {
		t0 = new Territory("Giacomo", 2, 1);	
		this.specialAction1.setActionType("activate");
		cardCost.put(CardType.TERRITORY, 8);
		cardCost.put(CardType.BUILDING, 0);
		cardCost.put(CardType.CHARACTER, 0);
		cardCost.put(CardType.VENTURE, 0);
		leaderCard.setCardCost(cardCost);
		leaderCard.setResourceCost(resource);
		leaderCard.setName("Aldo");
		leaderCard.setActive(false);
		leaderCard.setPlayed(false);
		this.specialAction1.setLeaderName("Aldo");
		this.gameManager.getView().getGameModel().getPlayers().get(0).getBoard().getTerritories().add(t0);
		boolean x = this.specialAction1.isApplicable();
		assertFalse(x);
	}
	
	//action = activate, nomi diversi
	@Test
	public void testCheck14() {
		t0 = new Territory("Giacomo", 2, 1);	
		this.specialAction1.setActionType("activate");
		cardCost.put(CardType.TERRITORY, 8);
		cardCost.put(CardType.BUILDING, 0);
		cardCost.put(CardType.CHARACTER, 0);
		cardCost.put(CardType.VENTURE, 0);
		leaderCard.setCardCost(cardCost);
		leaderCard.setResourceCost(resource);
		leaderCard.setName("Sisto IV");
		leaderCard.setActive(false);
		leaderCard.setPlayed(true);
		this.specialAction1.setLeaderName("Sisto IV");
		this.gameManager.getView().getGameModel().getPlayers().get(0).getBoard().getTerritories().add(t0);
		boolean x = this.specialAction1.isApplicable();
		assertFalse(x);
	}
	
	//action = discard checkPlayAction = true Lucrezia Borgia Card player.getBoard().getResources().getResource().get(rt) < resourceCost.getResource().get(rt) 
	@Test
	public void testCheck16(){
		v0 = new Venture("Blle", 1, 1);
		v1 = new Venture("Blle", 1, 1);
		v2 = new Venture("Blle", 1, 1);
		
		this.specialAction1.setActionType("play");
		cardCost.put(CardType.TERRITORY, 8);
		cardCost.put(CardType.BUILDING, 0);
		cardCost.put(CardType.CHARACTER, 0);
		cardCost.put(CardType.VENTURE, 0);
		leaderCard.setCardCost(cardCost);
		leaderCard.setResourceCost(resource1);
		leaderCard.setName("Lucrezia Borgia");
		this.specialAction1.setLeaderName("lucrezia borgia");
		this.gameManager.getView().getGameModel().getPlayers().get(0).getLeaderCards().add(leaderCard1 );
		this.gameManager.getView().getGameModel().getPlayers().get(0).getBoard().getVentures().add(v0);
		this.gameManager.getView().getGameModel().getPlayers().get(0).getBoard().getVentures().add(v1);
		this.gameManager.getView().getGameModel().getPlayers().get(0).getBoard().getVentures().add(v2);
		boolean x = this.specialAction1.isApplicable();
		assertFalse(x);
	}

	//action = discard checkPlayAction = true Lucrezia Borgia Card cost null 
	@Test
	public void testCheck17(){
		v0 = new Venture("Blle", 1, 1);
		v1 = new Venture("Blle", 1, 1);
		v2 = new Venture("Blle", 1, 1);
		
		this.specialAction1.setActionType("play");
		leaderCard.setCardCost(null);
		leaderCard.setResourceCost(null);
		leaderCard.setName("Lucrezia Borgia");
		this.specialAction1.setLeaderName("lucrezia borgia");
		this.gameManager.getView().getGameModel().getPlayers().get(0).getLeaderCards().add(leaderCard1 );
		this.gameManager.getView().getGameModel().getPlayers().get(0).getBoard().getVentures().add(v0);
		this.gameManager.getView().getGameModel().getPlayers().get(0).getBoard().getVentures().add(v1);
		this.gameManager.getView().getGameModel().getPlayers().get(0).getBoard().getVentures().add(v2);
		boolean x = this.specialAction1.isApplicable();
		assertTrue(x);
	}

	//action = discard checkPlayAction = true Lucrezia Borgia Card player.getBoard().getResources().getResource().get(rt) = resourceCost.getResource().get(rt) 
	@Test
	public void testCheck18(){
		v0 = new Venture("Blle", 1, 1);
		v1 = new Venture("Blle", 1, 1);
		v2 = new Venture("Blle", 1, 1);
			
		this.specialAction1.setActionType("play");
		cardCost.put(CardType.TERRITORY, 8);
		cardCost.put(CardType.BUILDING, 0);
		cardCost.put(CardType.CHARACTER, 0);
		cardCost.put(CardType.VENTURE, 0);
		leaderCard.setCardCost(cardCost);
		leaderCard.setResourceCost(resource);
		leaderCard.setName("Lucrezia Borgia");
		this.specialAction1.setLeaderName("lucrezia borgia");
		this.gameManager.getView().getGameModel().getPlayers().get(0).getLeaderCards().add(leaderCard1 );
		this.gameManager.getView().getGameModel().getPlayers().get(0).getBoard().getVentures().add(v0);
		this.gameManager.getView().getGameModel().getPlayers().get(0).getBoard().getVentures().add(v1);
		this.gameManager.getView().getGameModel().getPlayers().get(0).getBoard().getVentures().add(v2);
		boolean x = this.specialAction1.isApplicable();
		assertFalse(x);
	}

	//action = discard checkPlayAction = true Lucrezia Borgia Card cardCost=null
	@Test
	public void testCheck19() {
		v0 = new Venture("Blle", 1, 1);
		
		this.specialAction1.setActionType("play");
		cardCost.put(CardType.VENTURE, 1);
		leaderCard.setCardCost(null);
		leaderCard.setResourceCost(resource);
		leaderCard.setName("Lucrezia Borgia");
		this.specialAction1.setLeaderName("lucrezia borgia");
		this.gameManager.getView().getGameModel().getPlayers().get(0).getLeaderCards().add(leaderCard1 );
		this.gameManager.getView().getGameModel().getPlayers().get(0).getBoard().getVentures().add(v0);
		boolean x = this.specialAction1.isApplicable();
		assertTrue(x);
	}

	
}
