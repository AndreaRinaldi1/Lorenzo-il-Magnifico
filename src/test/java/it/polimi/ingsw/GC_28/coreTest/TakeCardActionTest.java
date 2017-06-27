package it.polimi.ingsw.GC_28.coreTest;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.boards.BonusTile;
import it.polimi.ingsw.GC_28.boards.Cell;
import it.polimi.ingsw.GC_28.boards.FinalBonus;
import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.boards.PlayerBoard;
import it.polimi.ingsw.GC_28.boards.Tower;
import it.polimi.ingsw.GC_28.cards.Building;
import it.polimi.ingsw.GC_28.cards.Card;
import it.polimi.ingsw.GC_28.cards.CardType;
import it.polimi.ingsw.GC_28.cards.Character;
import it.polimi.ingsw.GC_28.cards.ExcommunicationTile;
import it.polimi.ingsw.GC_28.cards.LeaderCard;
import it.polimi.ingsw.GC_28.cards.Territory;
import it.polimi.ingsw.GC_28.cards.Venture;
import it.polimi.ingsw.GC_28.components.DiceColor;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.core.TakeCardAction;
import it.polimi.ingsw.GC_28.effects.Effect;
import it.polimi.ingsw.GC_28.effects.EffectType;
import it.polimi.ingsw.GC_28.effects.GoToHPEffect;
import it.polimi.ingsw.GC_28.effects.HarvestEffect;
import it.polimi.ingsw.GC_28.effects.IncrementCardEffect;
import it.polimi.ingsw.GC_28.effects.IncrementHPEffect;
import it.polimi.ingsw.GC_28.effects.OtherEffect;
import it.polimi.ingsw.GC_28.effects.ResourceEffect;
import it.polimi.ingsw.GC_28.effects.TakeCardEffect;
import it.polimi.ingsw.GC_28.model.BoardSetup;
import it.polimi.ingsw.GC_28.model.BoardsInitializer;
import it.polimi.ingsw.GC_28.model.GameModel;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.model.PlayerColor;
import it.polimi.ingsw.GC_28.spaces.MarketSpace;
import it.polimi.ingsw.GC_28.spaces.PrivilegesSpace;
import it.polimi.ingsw.GC_28.spaces.ProdHarvSpace;
import it.polimi.ingsw.GC_28.view.GameManager;
import it.polimi.ingsw.GC_28.view.GameView;

public class TakeCardActionTest {

	private TakeCardAction takeCard;
	private TestGame testGame;
	private GameView game1;
	private GameModel gameModel;
	private GameBoard gameBoard;
	private PlayerBoard playerBoard;
	private Player player;
	private Player player1;
	private ArrayList<Player> players = new ArrayList<>();
	private BoardsInitializer bi = new BoardsInitializer();
	
	private BoardSetup bs;
	private Map<CardType, Tower> towers = new EnumMap<>(CardType.class);
	private Tower tower;
	private Cell[] cells = new Cell[2];
	private Cell cell = new Cell(null, 1, true);
	private Cell cell1 = new Cell(null, 2, true);
	private Card card0;
	private Card card1;
	private Card card2;
	private Card card3;
	private Card card4;
	private Card card5;
	
	private Venture v;
	private Venture v1;
	private Character c;
	private Character c1;
	
	
	private EnumMap<ResourceType, Integer> resources = new EnumMap<>(ResourceType.class);
	private Resource resource = Resource.of(resources);
	
	private EnumMap<ResourceType, Integer> w = new EnumMap<>(ResourceType.class);
	private Resource alternativeCost = Resource.of(resources);

	private EnumMap<ResourceType, Integer> resources1 = new EnumMap<>(ResourceType.class);
	private Resource cost = Resource.of(resources1);

	private EnumMap<ResourceType, Integer> resources2 = new EnumMap<>(ResourceType.class);
	private Resource resource1 = Resource.of(resources2);
	
	private IncrementCardEffect permanentCardEffect;
	
	private FamilyMember familyMember;
	private FamilyMember familyMember1;
	private FamilyMember[] familyMembers = new FamilyMember[1];
	
	private OtherEffect otherEffect;
	private LeaderCard leaderCard = new LeaderCard();
	private ArrayList<LeaderCard> leaderCards = new ArrayList<>();

	private ExcommunicationTile excommunicationTile = new ExcommunicationTile();
	private ArrayList<ExcommunicationTile> excommunicationTiles = new ArrayList<>();
	
	private class TestGame extends GameView{
		public TestGame(GameModel gameModel) {
			super(gameModel);
		}
		
		@Override
		public Resource askAlternative(Resource discount1, Resource discount2, String type){
			
			EnumMap<ResourceType, Integer> z = new EnumMap<>(ResourceType.class);
			z.put(ResourceType.COIN, 5);
			Resource bonus = Resource.of(z);
			return bonus;
		}
	}

	
	
	
	@Before
	public void takeCardActionTest() throws FileNotFoundException, IOException{
		player = new Player("bob", PlayerColor.BLUE);
		player1 = new Player("lolo", PlayerColor.GREEN);
		familyMember = new FamilyMember(player, false, DiceColor.BLACK);
		familyMember1 = new FamilyMember(player1, false, DiceColor.BLACK);
		this.familyMembers[0] = familyMember;
		this.player.setFamilyMembers(familyMembers);
		players.add(player);
		players.add(player1);
		
		permanentCardEffect = new IncrementCardEffect();
		
		resources.put(ResourceType.COIN, 2);
		resource = Resource.of(resources);
		
		playerBoard = new PlayerBoard(null, null);
		gameBoard = new GameBoard();
		gameModel = new GameModel(gameBoard, players);
				
		testGame = new TestGame(gameModel);
		takeCard = new TakeCardAction(testGame, gameModel);
	
		for(ResourceType resType : ResourceType.values()){
			resources2.put(resType, 0);
		}
		resource1 = Resource.of(resources2);
		
		
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	//checkCardExistance == false
	@Test
	public void testIsApplicable() {
		takeCard.setFamilyMember(familyMember);
		takeCard.setName("ugo");
		takeCard.setThroughEffect(null);
		takeCard.isApplicable();
		assertFalse(this.takeCard.isApplicable());
	}

	//6 carte building
	@Test
	public void testIsApplicableBuilding() {
		card0 = new Building("bob", 1, 1);
		card1 = new Building("bob", 2, 1);
		card2 = new Building("bob", 3, 1);
		card3 = new Building("bob", 4, 1);
		card4 = new Building("bob", 5, 1);
		card5 = new Building("bob", 6, 1);
		cell.setCard((Building)card0);
		cells[0] = cell;
		tower = new Tower(cells);
		tower.setCells(cells);
		towers.put(CardType.BUILDING, tower);
			
			
		playerBoard.addCard((Building)card0);
		playerBoard.addCard((Building)card1);
		playerBoard.addCard((Building)card2);
		playerBoard.addCard((Building)card3);
		playerBoard.addCard((Building)card4);
		playerBoard.addCard((Building)card5);
		playerBoard.setResources(resource);
		player.setBoard(playerBoard);
	
		gameBoard.setTowers(towers);
		takeCard.setFamilyMember(familyMember);
		takeCard.setName("bob");
		takeCard.setThroughEffect(null);
		takeCard.isApplicable();
		assertFalse(this.takeCard.isApplicable());
	}

	
	//6 carte character
	@Test
	public void testIsApplicableCharacter() {
		card0 = new Character("bob", 1, 1);
		card1 = new Character("bob", 2, 1);
		card2 = new Character("bob", 3, 1);
		card3 = new Character("bob", 4, 1);
		card4 = new Character("bob", 5, 1);
		card5 = new Character("bob", 6, 1);
		cell.setCard((Character)card0);
		cells[0] = cell;
		tower = new Tower(cells);
		tower.setCells(cells);
		towers.put(CardType.CHARACTER, tower);
		
		
		playerBoard.addCard((Character)card0);
		playerBoard.addCard((Character)card1);
		playerBoard.addCard((Character)card2);
		playerBoard.addCard((Character)card3);
		playerBoard.addCard((Character)card4);
		playerBoard.addCard((Character)card5);
		playerBoard.setResources(resource);
		player.setBoard(playerBoard);
	
		gameBoard.setTowers(towers);
		takeCard.setFamilyMember(familyMember);
		takeCard.setName("bob");
		takeCard.setThroughEffect(null);
		takeCard.isApplicable();
		assertFalse(this.takeCard.isApplicable());
	}

	//6 carte territory
	@Test
	public void testIsApplicableTerritory() {
		card0 = new Territory("bob", 1, 1);
		card1 = new Territory("bob", 2, 1);
		card2 = new Territory("bob", 3, 1);
		card3 = new Territory("bob", 4, 1);
		card4 = new Territory("bob", 5, 1);
		card5 = new Territory("bob", 6, 1);
		cell.setCard((Territory)card0);
		cells[0] = cell;
		tower = new Tower(cells);
		tower.setCells(cells);
		towers.put(CardType.TERRITORY, tower);
		
		playerBoard.addCard((Territory)card0);
		playerBoard.addCard((Territory)card1);
		playerBoard.addCard((Territory)card2);
		playerBoard.addCard((Territory)card3);
		playerBoard.addCard((Territory)card4);
		playerBoard.addCard((Territory)card5);
		playerBoard.setResources(resource);
		player.setBoard(playerBoard);
		
		gameBoard.setTowers(towers);
		takeCard.setFamilyMember(familyMember);
		takeCard.setName("bob");
		takeCard.setThroughEffect(null);
		takeCard.isApplicable();
		assertFalse(this.takeCard.isApplicable());
	}

	//6 carte Venture
	@Test
	public void testIsApplicaleVenture() {
		card0 = new Venture("bob", 1, 1);
		card1 = new Venture("bob", 2, 1);
		card2 = new Venture("bob", 3, 1);
		card3 = new Venture("bob", 4, 1);
		card4 = new Venture("bob", 5, 1);
		card5 = new Venture("bob", 6, 1);
		cell.setCard((Venture)card0);
		cells[0] = cell;
		tower = new Tower(cells);
		tower.setCells(cells);
		towers.put(CardType.VENTURE, tower);
			
			
		playerBoard.addCard((Venture)card0);
		playerBoard.addCard((Venture)card1);
		playerBoard.addCard((Venture)card2);
		playerBoard.addCard((Venture)card3);
		playerBoard.addCard((Venture)card4);
		playerBoard.addCard((Venture)card5);
		playerBoard.setResources(resource);
		player.setBoard(playerBoard);
		gameBoard.setTowers(towers);
		takeCard.setFamilyMember(familyMember);
		takeCard.setName("bob");
		takeCard.setThroughEffect(null);
		takeCard.isApplicable();
		assertFalse(this.takeCard.isApplicable());
	}


	//"ho le risorse" checkActionValue == false
	@Test
	public void testIsApplicableActionValue() {
		card0 = new Building("bob", 1, 1);
		cell.setCard((Building)card0);
		cell.setFree(false);
		cell.setFamilyMember(familyMember);
		cells[0] = cell;
		tower = new Tower(cells);
		tower.setCells(cells);
		towers.put(CardType.BUILDING, tower);
			
			
		playerBoard.addCard((Building)card0);
		playerBoard.setResources(resource);
		player.setBoard(playerBoard);
	
		gameBoard.setTowers(towers);
		takeCard.setFamilyMember(familyMember);
		takeCard.setName("bob");
		takeCard.setThroughEffect(null);
		takeCard.isApplicable();
		assertFalse(this.takeCard.isApplicable());
	}

	//"io presente" with CardType Territory
	@Test
	public void testIsApplicableFamMem() throws IOException {
		card0 = new Territory("bob", 1, 1);
		card1 = new Territory("bob", 2, 1);
		cell.setCard((Territory)card0);
		cell.setCard((Territory)card1);
		cells[0] = cell;
		cells[1] = cell1;
		tower = new Tower(cells);
		tower.setCells(cells);
		towers.put(CardType.TERRITORY, tower);
			
		game1 = bi.initializeBoard(players);
		GameManager gameM = new GameManager();
		gameM.setView(game1);
		bs = new BoardSetup(gameM);
		bs.firstSetUpCards();
		
		for(ResourceType resType : ResourceType.values()){
			resources.put(resType, -1);
		}
		Resource res = Resource.of(resources);
		playerBoard.setResources(res);
		playerBoard.addCard((Territory)card0);
		playerBoard.addCard((Territory)card1);
		playerBoard.setResources(resource);
		player.setBoard(playerBoard);
	
		
		gameBoard.setTowers(towers);
		takeCard.setFamilyMember(familyMember);
		takeCard.setName("bob");
		takeCard.setThroughEffect(null);
		
		try{
			takeCard.isApplicable();
			assertFalse(this.takeCard.isApplicable());
		}
		catch(IndexOutOfBoundsException e){
			Logger.getAnonymousLogger().log(Level.WARNING, "index out of bounds");
		}	
		
	}

	//"io presente" with CardType Territory checkMilitary false
	@Test
	public void testIsApplicableFamMem9() throws IOException {
		card0 = new Territory("bob", 1, 1);
		card1 = new Territory("bob", 2, 1);
		cell.setCard((Territory)card0);
		cell1.setCard((Territory)card1);
		cells[0] = cell;
		cells[1] = cell1;
		tower = new Tower(cells);
		tower.setCells(cells);
		towers.put(CardType.TERRITORY, tower);
			
		game1 = bi.initializeBoard(players);
		GameManager gameM = new GameManager();
		gameM.setView(game1);
		bs = new BoardSetup(gameM);
		bs.firstSetUpCards();
		
		for(ResourceType resType : ResourceType.values()){
			resources.put(resType, -1);
		}
		Resource res = Resource.of(resources);
		playerBoard.setResources(res);
		playerBoard.addCard((Territory)card0);
		playerBoard.addCard((Territory)card1);
		playerBoard.setResources(resource);
		player.setBoard(playerBoard);
	
		otherEffect = new OtherEffect();
		otherEffect.setType(EffectType.NOMILITARYFORTERRITORYEFFECT);
		leaderCard.setEffect(otherEffect);
		leaderCards.add(leaderCard);
		leaderCard.setActive(true);
		leaderCard.setPlayed(true);
		player.setLeaderCards(leaderCards);
		
		gameBoard.setTowers(towers);
		takeCard.setFamilyMember(familyMember);
		takeCard.setName("bob");
		takeCard.setThroughEffect(null);
		takeCard.isApplicable();
		assertFalse(this.takeCard.isApplicable());
		
	}
	
	
	//"io presente" with CardType Venture with coin<3
	@Test
	public void testIsApplicableFamMemVenture2() throws IOException {
		card0 = new Venture("bob", 1, 1);
		card1 = new Venture("bob", 2, 1);
		cell.setCard((Venture)card0);
		cell.setCard((Venture)card1);
		cells[0] = cell;
		cells[1] = cell1;
		tower = new Tower(cells);
		tower.setCells(cells);
		towers.put(CardType.VENTURE, tower);
				
		GameView game = bi.initializeBoard(players);
		GameManager gameM = new GameManager();
		gameM.setView(game);
		BoardSetup bs = new BoardSetup(gameM);
		bs.firstSetUpCards();
			
		for(ResourceType resType : ResourceType.values()){
			resources.put(resType, 1);
		}
		Resource res = Resource.of(resources);
		playerBoard.setResources(res);
		playerBoard.addCard((Venture)card0);
		playerBoard.addCard((Venture)card1);
		playerBoard.setResources(resource);
		player.setBoard(playerBoard);
		
			
		gameBoard.setTowers(towers);
		gameBoard.getTowers().get(CardType.VENTURE).getCells()[0].setFree(false);
		gameBoard.getTowers().get(CardType.VENTURE).getCells()[0].setFamilyMember(familyMember1);

		takeCard.setFamilyMember(familyMember);
		takeCard.setName("bob");
		takeCard.setThroughEffect(null);
		takeCard.isApplicable();
		assertFalse(this.takeCard.isApplicable());
				
	}

	
	//"io presente" with CardType Venture with coin>3 checkActionValue false
	@Test
	public void testIsApplicableFamMemVenture3() throws IOException {
		v = new Venture("bob", 1, 1);
		v1 = new Venture("bob", 2, 1);
		v1.setCost(resource);
		v1.setAlternativeCostPresence(true);
		w = new EnumMap<>(ResourceType.class);
		w.put(ResourceType.MILITARYPOINT, 1);
		alternativeCost = Resource.of(w);
		v1.setAlternativeCost(alternativeCost );
		v1.setMinimumRequiredMilitaryPoints(1);
		
		cell.setCard(v);
		cell.setCard(v1);
		cells[0] = cell;
		cells[1] = cell1;
		tower = new Tower(cells);
		tower.setCells(cells);
		towers.put(CardType.VENTURE, tower);
				
		GameView game = bi.initializeBoard(players);
		GameManager gameM = new GameManager();
		gameM.setView(game);
		BoardSetup bs = new BoardSetup(gameM);
		bs.firstSetUpCards();
			
		for(ResourceType resType : ResourceType.values()){
			resources.put(resType, 4);
		}
		Resource res = Resource.of(resources);
		playerBoard.setResources(res);
		playerBoard.addCard(v);
		playerBoard.addCard(v1);
		playerBoard.setResources(resource);
		player.setBoard(playerBoard);
		
			
		gameBoard.setTowers(towers);
		gameBoard.getTowers().get(CardType.VENTURE).getCells()[0].setFree(false);
		gameBoard.getTowers().get(CardType.VENTURE).getCells()[0].setFamilyMember(familyMember1);

		takeCard.setFamilyMember(familyMember);
		takeCard.setName("bob");
		takeCard.setThroughEffect(null);
		takeCard.isApplicable();
		assertFalse(this.takeCard.isApplicable());
			
	}
	
	//"io presente" with CardType Venture with coin>3 tmp.getResource().get(resType) < 0
	@Test
	public void testIsApplicableFamMemVenture4() throws IOException {
		v = new Venture("bob", 1, 1);
		v1 = new Venture("bob", 2, 1);
		v1.setCost(resource);
		v1.setAlternativeCostPresence(true);
		w = new EnumMap<>(ResourceType.class);
		w.put(ResourceType.MILITARYPOINT, 1);
		alternativeCost = Resource.of(w);
		v1.setAlternativeCost(alternativeCost );
		v1.setMinimumRequiredMilitaryPoints(1);
		
		cell.setCard(v);
		cell.setCard(v1);
		cells[0] = cell;
		cells[1] = cell1;
		tower = new Tower(cells);
		tower.setCells(cells);
		towers.put(CardType.VENTURE, tower);
				
		GameView game = bi.initializeBoard(players);
		GameManager gameM = new GameManager();
		gameM.setView(game);
		BoardSetup bs = new BoardSetup(gameM);
		bs.firstSetUpCards();
			
		for(ResourceType resType : ResourceType.values()){
			resources.put(resType, 4);
		}
		Resource res = Resource.of(resources);
		playerBoard.setResources(res);
		playerBoard.addCard(v);
		playerBoard.addCard(v1);
		playerBoard.setResources(resource);
		player.setBoard(playerBoard);
		
		otherEffect = new OtherEffect();
		otherEffect.setType(EffectType.NOEXTRACOSTINTOWEREFFECT);
		leaderCard.setEffect(otherEffect);
		leaderCard.setActive(true);
		leaderCard.setPlayed(true);
		leaderCards.add(leaderCard);
		player.setLeaderCards(leaderCards);
			
		gameBoard.setTowers(towers);
		gameBoard.getTowers().get(CardType.VENTURE).getCells()[0].setFree(false);
		gameBoard.getTowers().get(CardType.VENTURE).getCells()[0].setFamilyMember(familyMember1);
		takeCard.setFamilyMember(familyMember);
		takeCard.setName("bob");
		takeCard.setThroughEffect(null);
		takeCard.isApplicable();
		assertFalse(this.takeCard.isApplicable());
			
	}
	
	//"io presente" with CardType Venture with coin>3 venture.getMinimumRequiredMilitaryPoints() <= familyMember.getPlayer().getBoard().getResources().getResource().get(ResourceType.MILITARYPOINT)
	@Test
	public void testIsApplicableFamMemVenture5() throws IOException {
		v = new Venture("bob", 1, 1);
		v1 = new Venture("bob", 2, 1);
		v1.setCost(resource);
		v1.setAlternativeCostPresence(true);
		w = new EnumMap<>(ResourceType.class);
		w.put(ResourceType.MILITARYPOINT, 1);
		alternativeCost = Resource.of(w);
		v1.setAlternativeCost(alternativeCost );
		v1.setMinimumRequiredMilitaryPoints(7);
		
		cell.setCard(v);
		cell.setCard(v1);
		cells[0] = cell;
		cells[1] = cell1;
		tower = new Tower(cells);
		tower.setCells(cells);
		towers.put(CardType.VENTURE, tower);
				
		GameView game = bi.initializeBoard(players);
		GameManager gameM = new GameManager();
		gameM.setView(game);
		BoardSetup bs = new BoardSetup(gameM);
		bs.firstSetUpCards();
			
		for(ResourceType resType : ResourceType.values()){
			resources.put(resType, 4);
		}
		Resource res = Resource.of(resources);
		playerBoard.setResources(res);
		playerBoard.addCard(v);
		playerBoard.addCard(v1);
		playerBoard.setResources(resource);
		player.setBoard(playerBoard);
		
		otherEffect = new OtherEffect();
		otherEffect.setType(EffectType.NOEXTRACOSTINTOWEREFFECT);
		leaderCard.setEffect(otherEffect);
		leaderCard.setActive(true);
		leaderCard.setPlayed(true);
		leaderCards.add(leaderCard);
		player.setLeaderCards(leaderCards);
			
		gameBoard.setTowers(towers);
		gameBoard.getTowers().get(CardType.VENTURE).getCells()[0].setFree(false);
		gameBoard.getTowers().get(CardType.VENTURE).getCells()[0].setFamilyMember(familyMember1);
		takeCard.setFamilyMember(familyMember);
		takeCard.setName("bob");
		takeCard.setThroughEffect(null);
		takeCard.isApplicable();
		assertFalse(this.takeCard.isApplicable());
			
	}
	

	//"io presente" with CardType Venture with coin>3 venture.getMinimumRequiredMilitaryPoints() <= familyMember.getPlayer().getBoard().getResources().getResource().get(ResourceType.MILITARYPOINT)
	@Test
	public void testIsApplicableFamMemVenture6() throws IOException {
		v = new Venture("bob", 1, 1);
		v1 = new Venture("bob", 2, 1);
		v1.setCost(resource);
		v1.setAlternativeCostPresence(false);
		
		cell.setCard(v);
		cell.setCard(v1);
		cells[0] = cell;
		cells[1] = cell1;
		tower = new Tower(cells);
		tower.setCells(cells);
		towers.put(CardType.VENTURE, tower);
				
		GameView game = bi.initializeBoard(players);
		GameManager gameM = new GameManager();
		gameM.setView(game);
		BoardSetup bs = new BoardSetup(gameM);
		bs.firstSetUpCards();
			
		for(ResourceType resType : ResourceType.values()){
			resources.put(resType, 4);
		}
		Resource res = Resource.of(resources);
		playerBoard.setResources(res);
		playerBoard.addCard(v);
		playerBoard.addCard(v1);
		playerBoard.setResources(resource);
		player.setBoard(playerBoard);
		
		otherEffect = new OtherEffect();
		otherEffect.setType(EffectType.NOEXTRACOSTINTOWEREFFECT);
		leaderCard.setEffect(otherEffect);
		leaderCard.setActive(true);
		leaderCard.setPlayed(true);
		leaderCards.add(leaderCard);
		player.setLeaderCards(leaderCards);
			
		gameBoard.setTowers(towers);
		gameBoard.getTowers().get(CardType.VENTURE).getCells()[0].setFree(false);
		gameBoard.getTowers().get(CardType.VENTURE).getCells()[0].setFamilyMember(familyMember1);
		takeCard.setFamilyMember(familyMember);
		takeCard.setName("bob");
		takeCard.setThroughEffect(null);
		takeCard.isApplicable();
		assertFalse(this.takeCard.isApplicable());
			
	}

	//"io presente" with CardType Venture with coin>3 venture.getMinimumRequiredMilitaryPoints() > familyMember.getPlayer().getBoard().getResources().getResource().get(ResourceType.MILITARYPOINT)
	@Test
	public void testIsApplicableFamMemVentureMax() throws IOException {
		v = new Venture("bob", 1, 1);
		v1 = new Venture("bob", 2, 1);
		v1.setCost(resource1);
		v1.setAlternativeCostPresence(true);
		v1.setMinimumRequiredMilitaryPoints(700000);
		
		cell.setCard(v);
		cell.setCard(v1);
		cells[0] = cell;
		cells[1] = cell1;
		tower = new Tower(cells);
		tower.setCells(cells);
		towers.put(CardType.VENTURE, tower);
				
		GameView game = bi.initializeBoard(players);
		GameManager gameM = new GameManager();
		gameM.setView(game);
		BoardSetup bs = new BoardSetup(gameM);
		bs.firstSetUpCards();
			
		for(ResourceType resType : ResourceType.values()){
			resources.put(resType, 4);
		}
		Resource res = Resource.of(resources);
		playerBoard.setResources(res);
		playerBoard.addCard(v);
		playerBoard.addCard(v1);
		playerBoard.setResources(resource);
		player.setBoard(playerBoard);
		
		otherEffect = new OtherEffect();
		otherEffect.setType(EffectType.NOEXTRACOSTINTOWEREFFECT);
		leaderCard.setEffect(otherEffect);
		leaderCard.setActive(true);
		leaderCard.setPlayed(true);
		leaderCards.add(leaderCard);
		player.setLeaderCards(leaderCards);
				
		gameBoard.setTowers(towers);
		gameBoard.getTowers().get(CardType.VENTURE).getCells()[0].setFree(false);
		gameBoard.getTowers().get(CardType.VENTURE).getCells()[0].setFamilyMember(familyMember1);
		takeCard.setFamilyMember(familyMember);
		takeCard.setName("bob");
		takeCard.setThroughEffect(null);
		takeCard.isApplicable();
		assertFalse(this.takeCard.isApplicable());
			
	}

	
	//"io presente" with CardType character 
	@Test
	public void testIsApplicableFamMemVenture7() throws IOException {
		
		c = new Character("bob", 1, 1);
		c1 = new Character("bob", 1, 1);
		c.setCost(resource);
		c1.setCost(resource);
		
		cell.setCard(c);
		cell.setCard(c1);
		cells[0] = cell;
		cells[1] = cell1;
		tower = new Tower(cells);
		tower.setCells(cells);
		towers.put(CardType.CHARACTER, tower);
				
		GameView game = bi.initializeBoard(players);
		GameManager gameM = new GameManager();
		gameM.setView(game);
		BoardSetup bs = new BoardSetup(gameM);
		bs.firstSetUpCards();
			
		for(ResourceType resType : ResourceType.values()){
			resources.put(resType, 4);
		}
		Resource res = Resource.of(resources);
		playerBoard.setResources(res);
		playerBoard.addCard(c);
		playerBoard.addCard(c1);
		playerBoard.setResources(resource);
		player.setBoard(playerBoard);
		
		otherEffect = new OtherEffect();
		otherEffect.setType(EffectType.NOEXTRACOSTINTOWEREFFECT);
		leaderCard.setEffect(otherEffect);
		leaderCard.setActive(true);
		leaderCard.setPlayed(true);
		leaderCards.add(leaderCard);
		player.setLeaderCards(leaderCards);
			
		gameBoard.setTowers(towers);
		gameBoard.getTowers().get(CardType.CHARACTER).getCells()[0].setFree(false);
		gameBoard.getTowers().get(CardType.CHARACTER).getCells()[0].setFamilyMember(familyMember1);
		takeCard.setFamilyMember(familyMember);
		takeCard.setName("bob");
		takeCard.setThroughEffect(null);
		takeCard.isApplicable();
		assertFalse(this.takeCard.isApplicable());
			
	}
	
	//"io presente" with CardType Venture with coin>3 venture.venture.getAlternativeCostPresence() == false
	@Test
	public void testIsApplicableFamMemVenture8() throws IOException {
		v = new Venture("bob", 1, 1);
		v1 = new Venture("bob", 2, 1);
		for(ResourceType resType : ResourceType.values()){
			resources1.put(resType, 0);
		}
		cost = Resource.of(resources1);
		v1.setCost(cost);
		v1.setAlternativeCostPresence(true);
		
		cell.setCard(v);
		cell.setCard(v1);
		cells[0] = cell;
		cells[1] = cell1;
		tower = new Tower(cells);
		tower.setCells(cells);
		towers.put(CardType.VENTURE, tower);
				
		GameView game = bi.initializeBoard(players);
		GameManager gameM = new GameManager();
		gameM.setView(game);
		BoardSetup bs = new BoardSetup(gameM);
		bs.firstSetUpCards();
			
		for(ResourceType resType : ResourceType.values()){
			resources.put(resType, 4);
		}
		Resource res = Resource.of(resources);
		playerBoard.setResources(res);
		playerBoard.addCard(v);
		playerBoard.addCard(v1);
		playerBoard.setResources(resource);
		player.setBoard(playerBoard);
		
		otherEffect = new OtherEffect();
		otherEffect.setType(EffectType.NOEXTRACOSTINTOWEREFFECT);
		leaderCard.setEffect(otherEffect);
		leaderCard.setActive(true);
		leaderCard.setPlayed(true);
		leaderCards.add(leaderCard);
		player.setLeaderCards(leaderCards);
			
		gameBoard.setTowers(towers);
		gameBoard.getTowers().get(CardType.VENTURE).getCells()[0].setFree(false);
		gameBoard.getTowers().get(CardType.VENTURE).getCells()[0].setFamilyMember(familyMember1);
		takeCard.setFamilyMember(familyMember);
		takeCard.setName("bob");
		takeCard.setThroughEffect(null);
		takeCard.isApplicable();
		assertFalse(this.takeCard.isApplicable());
			
	}
	
	//"io presente" with CardType Venture with coin>3 venture.venture.getAlternativeCostPresence() == false
	@Test
	public void testIsApplicableFamMemVenture9() throws IOException {
		v = new Venture("bob", 1, 1);
		v1 = new Venture("bob", 2, 1);
		for(ResourceType resType : ResourceType.values()){
			resources1.put(resType, 0);
		}
		cost = Resource.of(resources1);
		v1.setCost(cost);
		v1.setAlternativeCostPresence(true);
		v1.setMinimumRequiredMilitaryPoints(-1);
		cell.setCard(v);
		cell.setCard(v1);
		cells[0] = cell;
		cells[1] = cell1;
		tower = new Tower(cells);
		tower.setCells(cells);
		towers.put(CardType.VENTURE, tower);
				
		GameView game = bi.initializeBoard(players);
		GameManager gameM = new GameManager();
		gameM.setView(game);
		BoardSetup bs = new BoardSetup(gameM);
		bs.firstSetUpCards();
			
		for(ResourceType resType : ResourceType.values()){
			resources.put(resType, 4);
		}
		Resource res = Resource.of(resources);
		playerBoard.setResources(res);
		playerBoard.addCard(v);
		playerBoard.addCard(v1);
		playerBoard.setResources(resource);
		player.setBoard(playerBoard);
		
		otherEffect = new OtherEffect();
		otherEffect.setType(EffectType.NOEXTRACOSTINTOWEREFFECT);
		leaderCard.setEffect(otherEffect);
		leaderCard.setActive(true);
		leaderCard.setPlayed(true);
		leaderCards.add(leaderCard);
		player.setLeaderCards(leaderCards);
			
		gameBoard.setTowers(towers);
		gameBoard.getTowers().get(CardType.VENTURE).getCells()[0].setFree(false);
		gameBoard.getTowers().get(CardType.VENTURE).getCells()[0].setFamilyMember(familyMember1);
		takeCard.setFamilyMember(familyMember);
		takeCard.setName("bob");
		takeCard.setThroughEffect(null);
		takeCard.isApplicable();
		assertFalse(this.takeCard.isApplicable());
			
	}
	
	//"io presente" with CardType Venture with coin>3 venture.venture.getAlternativeCostPresence() == false
	@Test
	public void testIsApplicableFamMemVenture10() throws IOException {
		c = new Character("bob", 1, 1);
		c1 = new Character("bob", 2, 1);
		permanentCardEffect.setCardType(CardType.CHARACTER);
		permanentCardEffect.setIncrement(1);
		c.setPermanentEffect(permanentCardEffect);
		
		cell.setCard(c);
		cell.setCard(c1);
		cells[0] = cell;
		cells[1] = cell1;
		tower = new Tower(cells);
		tower.setCells(cells);
		towers.put(CardType.CHARACTER, tower);
				
		GameView game = bi.initializeBoard(players);
		GameManager gameM = new GameManager();
		gameM.setView(game);
		BoardSetup bs = new BoardSetup(gameM);
		bs.firstSetUpCards();
			
		for(ResourceType resType : ResourceType.values()){
			resources.put(resType, 4);
		}
		Resource res = Resource.of(resources);
		playerBoard.setResources(res);
		playerBoard.addCard(c);
		playerBoard.addCard(c1);
		playerBoard.setResources(resource);
		player.setBoard(playerBoard);

		excommunicationTile.setEffect(permanentCardEffect);
		excommunicationTiles.add(excommunicationTile);
		player.setExcommunicationTile(excommunicationTiles);
			
		otherEffect = new OtherEffect();
		otherEffect.setType(EffectType.NOEXTRACOSTINTOWEREFFECT);
		leaderCard.setEffect(otherEffect);
		leaderCard.setActive(true);
		leaderCard.setPlayed(true);
		leaderCards.add(leaderCard);
		player.setLeaderCards(leaderCards);
			
		gameBoard.setTowers(towers);
		gameBoard.getTowers().get(CardType.CHARACTER).getCells()[0].setFree(false);
		gameBoard.getTowers().get(CardType.CHARACTER).getCells()[0].setFamilyMember(familyMember1);
		takeCard.setFamilyMember(familyMember);
		takeCard.setName("bob");
		takeCard.setThroughEffect(null);
		takeCard.isApplicable();
		assertTrue(this.takeCard.isApplicable());
			
	}
		
	// checkActionValue = false tmp >= gameBoard.getTowers().get(cardType).findCard(cardName).getActionValue()
	@Test
	public void testIsApplicableFamMemVenture11() throws IOException {
		c = new Character("bob", 1, 1);
		c1 = new Character("bob", 2, 1);
		permanentCardEffect.setCardType(CardType.CHARACTER);
		permanentCardEffect.setIncrement(1);
		c.setPermanentEffect(permanentCardEffect);
		
		cell.setCard(c);
		cell.setCard(c1);
		cells[0] = cell;
		cells[0].setActionValue(999);
		cells[1] = cell1;
		cells[1].setActionValue(999);
		tower = new Tower(cells);
		tower.setCells(cells);
		towers.put(CardType.CHARACTER, tower);
				
		GameView game = bi.initializeBoard(players);
		GameManager gameM = new GameManager();
		gameM.setView(game);
		BoardSetup bs = new BoardSetup(gameM);
		bs.firstSetUpCards();
			
		for(ResourceType resType : ResourceType.values()){
			resources.put(resType, 4);
		}
		Resource res = Resource.of(resources);
		playerBoard.setResources(res);
		playerBoard.addCard(c);
		playerBoard.addCard(c1);
		playerBoard.setResources(resource);
		player.setBoard(playerBoard);

		excommunicationTile.setEffect(permanentCardEffect);
		excommunicationTiles.add(excommunicationTile);
		player.setExcommunicationTile(excommunicationTiles);
				
		otherEffect = new OtherEffect();
		otherEffect.setType(EffectType.NOEXTRACOSTINTOWEREFFECT);
		leaderCard.setEffect(otherEffect);
		leaderCard.setActive(true);
		leaderCard.setPlayed(true);
		leaderCards.add(leaderCard);
		player.setLeaderCards(leaderCards);
			
		gameBoard.setTowers(towers);
		gameBoard.getTowers().get(CardType.CHARACTER).getCells()[0].setFree(false);
		gameBoard.getTowers().get(CardType.CHARACTER).getCells()[0].setFamilyMember(familyMember1);
		takeCard.setFamilyMember(familyMember);
		takeCard.setName("bob");
		takeCard.setThroughEffect(null);
		takeCard.isApplicable();
		assertFalse(this.takeCard.isApplicable());
			
	}
	
	/*
	@Test
	public void testApply(){
		takeCard.apply();
	}
	*/
	
}
