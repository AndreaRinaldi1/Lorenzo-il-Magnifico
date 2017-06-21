package it.polimi.ingsw.GC_28.coreTest;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.boards.Cell;
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
import it.polimi.ingsw.GC_28.core.TakeCardController;
import it.polimi.ingsw.GC_28.effects.DiscountEffect;
import it.polimi.ingsw.GC_28.effects.Effect;
import it.polimi.ingsw.GC_28.effects.EffectType;
import it.polimi.ingsw.GC_28.effects.IncrementCardEffect;
import it.polimi.ingsw.GC_28.effects.OtherEffect;
import it.polimi.ingsw.GC_28.effects.ResourceEffect;
import it.polimi.ingsw.GC_28.effects.TakeCardEffect;
import it.polimi.ingsw.GC_28.model.BoardSetup;
import it.polimi.ingsw.GC_28.model.BoardsInitializer;
import it.polimi.ingsw.GC_28.model.GameModel;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.model.PlayerColor;
import it.polimi.ingsw.GC_28.view.GameManager;
import it.polimi.ingsw.GC_28.view.GameView;

public class TakeCardActionApplyTest {

	private TakeCardAction takeCard;
	private TestGame testGame;
	private GameModel gameModel;
	private GameBoard gameBoard;
	private PlayerBoard playerBoard;
	private Player player;
	private Player player1;
	private ArrayList<Player> players = new ArrayList<>();
	private BoardsInitializer bi = new BoardsInitializer();
	
	private Map<CardType, Tower> towers = new EnumMap<>(CardType.class);
	private Tower tower;
	private Cell[] cells = new Cell[2];
	private Cell cell = new Cell(null, 1, true);
	private Cell cell1 = new Cell(null, 2, true);
	
	private Venture v;
	private Venture v1;
	private Character c;
	private Character c1;
	private Building b;
	private Building b1;
	
	private EnumMap<ResourceType, Integer> resources = new EnumMap<>(ResourceType.class);
	private Resource resource = Resource.of(resources);
	
	private EnumMap<ResourceType, Integer> resources1 = new EnumMap<>(ResourceType.class);
	private Resource cost = Resource.of(resources1);

	private IncrementCardEffect permanentCardEffect;
	
	private FamilyMember familyMember;
	private FamilyMember familyMember1;
	private FamilyMember[] familyMembers = new FamilyMember[1];
	
	private TakeCardEffect throughEffect = new TakeCardEffect();

	private OtherEffect otherEffect;
	private OtherEffect otherEffect2;
	private DiscountEffect discount = new DiscountEffect();
	private ResourceEffect immediateEffect = new ResourceEffect();
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
		
		@Override
		public Resource checkResourceExcommunication(Resource amount){
			EnumMap<ResourceType, Integer> z = new EnumMap<>(ResourceType.class);
			z.put(ResourceType.COIN, 0);
			Resource bonus = Resource.of(z);
			return bonus;
		}
		
	}
	
	@Before
	public void testTakeCardActionApply(){
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
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	
	@Test
	public void testApply1() throws FileNotFoundException, IOException {
		v = new Venture("bob", 1, 1);
		v1 = new Venture("bob", 2, 1);
		
		c = new Character("bob", 1, 1);
		c1 = new Character("bob", 1, 1);
		otherEffect2 = new OtherEffect();
		otherEffect2.setType(EffectType.NOCELLBONUS);
		c.setPermanentEffect(otherEffect2);
		
		discount.setAlternativeDiscountPresence(true);
		discount.setAlternativeDiscount(cost);
		
		permanentCardEffect.setCardType(CardType.VENTURE);
		permanentCardEffect.setIncrement(1);
		permanentCardEffect.setDiscountPresence(true);
		permanentCardEffect.setDiscount(discount);
		c1.setPermanentEffect(permanentCardEffect);
		
		
		List<Effect> immediateEffects = new ArrayList<>();
		immediateEffects.add(immediateEffect);
		v.setImmediateEffect(immediateEffects );
		v.setCost(cost);
		v1.setCost(cost);
		cell.setCard(v);
		cell1.setCard(v1);
		cell.setActionValue(-1);
		cell1.setActionValue(-1);
		cells[0] = cell;
		cells[1] = cell1;
		tower = new Tower(cells);
		tower.setCells(cells);
		towers.put(CardType.VENTURE, tower);
			
		otherEffect = new OtherEffect();
		otherEffect.setType(EffectType.NOEXTRACOSTINTOWEREFFECT);
		leaderCard.setEffect(otherEffect);
		leaderCard.setActive(true);
		leaderCard.setPlayed(true);
		leaderCard.setName("Pico della Mirandola");
		leaderCards.add(leaderCard);
		player.setLeaderCards(leaderCards);
		
		
		GameView game = bi.initializeBoard(players);
		GameManager gameM = new GameManager();
		gameM.setView(game);
		BoardSetup bs = new BoardSetup(gameM);
		bs.firstSetUpCards();
			
		for(ResourceType resType : ResourceType.values()){
			resources.put(resType, 9);
		}
		Resource res = Resource.of(resources);
		playerBoard.setResources(res);
		playerBoard.addCard(v);
		playerBoard.addCard(v1);
		playerBoard.addCard(c);
		playerBoard.addCard(c1);
		playerBoard.setResources(resource);
		player.setBoard(playerBoard);
		
			
		gameBoard.setTowers(towers);
		gameBoard.getTowers().get(CardType.VENTURE).getCells()[0].setFree(false);
		gameBoard.getTowers().get(CardType.VENTURE).getCells()[0].setFamilyMember(familyMember1);

	
		
		takeCard.setFamilyMember(familyMember);
		takeCard.setName("bob");
		throughEffect.setCardType(CardType.VENTURE);
		throughEffect.setDiscountPresence(true);
		throughEffect.setDiscount(discount );
		takeCard.setThroughEffect(throughEffect );
		takeCard.isApplicable();
		takeCard.apply();
	}
	
	@Test
	public void testApply2() throws FileNotFoundException, IOException {
		v = new Venture("bob", 1, 1);
		v1 = new Venture("bob", 2, 1);
		
		c = new Character("bob", 1, 1);
		c1 = new Character("bob", 1, 1);
		
		discount.setAlternativeDiscountPresence(false);
		discount.setAlternativeDiscount(cost);
		
		permanentCardEffect.setCardType(CardType.VENTURE);
		permanentCardEffect.setIncrement(1);
		permanentCardEffect.setDiscountPresence(true);
		permanentCardEffect.setDiscount(discount);
		
		
		c.setPermanentEffect(permanentCardEffect);
		c1.setPermanentEffect(permanentCardEffect);
		
		v.setCost(cost);
		v1.setCost(cost);
		cell.setCard(v);
		cell1.setCard(v1);
		cell.setActionValue(-1);
		cell1.setActionValue(-1);
		cells[0] = cell;
		cells[1] = cell1;
		tower = new Tower(cells);
		tower.setCells(cells);
		towers.put(CardType.VENTURE, tower);
			
		otherEffect = new OtherEffect();
		otherEffect.setType(EffectType.NOEXTRACOSTINTOWEREFFECT);
		leaderCard.setEffect(otherEffect);
		leaderCard.setActive(true);
		leaderCard.setPlayed(true);
		leaderCard.setName("Pico della Mirandola");
		leaderCards.add(leaderCard);
		player.setLeaderCards(leaderCards);
		
		
		GameView game = bi.initializeBoard(players);
		GameManager gameM = new GameManager();
		gameM.setView(game);
		BoardSetup bs = new BoardSetup(gameM);
		bs.firstSetUpCards();
			
		for(ResourceType resType : ResourceType.values()){
			resources.put(resType, 9);
		}
		Resource res = Resource.of(resources);
		playerBoard.setResources(res);
		playerBoard.addCard(v);
		playerBoard.addCard(v1);
		playerBoard.addCard(c);
		playerBoard.addCard(c1);
		playerBoard.setResources(resource);
		player.setBoard(playerBoard);
		
			
		gameBoard.setTowers(towers);
		gameBoard.getTowers().get(CardType.VENTURE).getCells()[0].setFree(false);
		gameBoard.getTowers().get(CardType.VENTURE).getCells()[0].setFamilyMember(familyMember1);

	
		
		takeCard.setFamilyMember(familyMember);
		takeCard.setName("bob");
		takeCard.setThroughEffect(null);
		takeCard.isApplicable();
		takeCard.apply();
	}

	//take territory Card
	/*@Test
	public void testApply3() throws FileNotFoundException, IOException {
		card0 = new Territory("bob", 1, 1);
		card1 = new Territory("bob", 2, 1);
		
		c = new Character("bob", 1, 1);
		c1 = new Character("bob", 1, 1);
		
		discount.setAlternativeDiscountPresence(false);
		discount.setAlternativeDiscount(cost);
		
		permanentCardEffect.setCardType(CardType.TERRITORY);
		permanentCardEffect.setIncrement(1);
		permanentCardEffect.setDiscountPresence(true);
		permanentCardEffect.setDiscount(discount);
		
		
		c.setPermanentEffect(permanentCardEffect);
		c1.setPermanentEffect(permanentCardEffect);
		
		card0.setCost(cost);
		card1.setCost(cost);
		cell.setCard((Territory)card0);
		cell1.setCard((Territory)card1);
		cell.setActionValue(-1);
		cell1.setActionValue(-1);
		cells[0] = cell;
		cells[1] = cell1;
		tower = new Tower(cells);
		tower.setCells(cells);
		towers.put(CardType.TERRITORY, tower);
			
		otherEffect = new OtherEffect();
		otherEffect.setType(EffectType.NOEXTRACOSTINTOWEREFFECT);
		leaderCard.setEffect(otherEffect);
		leaderCard.setActive(true);
		leaderCard.setPlayed(true);
		leaderCard.setName("Pico della Mirandola");
		leaderCards.add(leaderCard);
		player.setLeaderCards(leaderCards);
		
		
		GameView game = bi.initializeBoard(players);
		GameManager gameM = new GameManager();
		gameM.setView(game);
		BoardSetup bs = new BoardSetup(gameM);
		bs.firstSetUpCards();
			
		for(ResourceType resType : ResourceType.values()){
			resources.put(resType, 9);
		}
		Resource res = Resource.of(resources);
		playerBoard.setResources(res);
		playerBoard.addCard((Territory)card0);
		playerBoard.addCard((Territory)card1);
		playerBoard.addCard(c);
		playerBoard.addCard(c1);
		playerBoard.setResources(resource);
		player.setBoard(playerBoard);
		
			
		gameBoard.setTowers(towers);
		gameBoard.getTowers().get(CardType.TERRITORY).getCells()[0].setFree(false);
		gameBoard.getTowers().get(CardType.TERRITORY).getCells()[0].setFamilyMember(familyMember1);

	
		
		takeCard.setFamilyMember(familyMember);
		takeCard.setName("bob");
		throughEffect.setCardType(CardType.TERRITORY);
		throughEffect.setDiscountPresence(true);
		throughEffect.setDiscount(discount );
		takeCard.setThroughEffect(throughEffect );
		takeCard.isApplicable();
		takeCard.apply();
<<<<<<< HEAD
	
=======
>>>>>>> refs/remotes/origin/master
	
	}*/
	
	//take Character Card
	@Test
	public void testApply4() throws FileNotFoundException, IOException {
		
		c = new Character("bob", 1, 1);
		c1 = new Character("bob", 1, 1);
		
		discount.setAlternativeDiscountPresence(false);
		discount.setAlternativeDiscount(cost);
		
		permanentCardEffect.setCardType(CardType.CHARACTER);
		permanentCardEffect.setIncrement(1);
		permanentCardEffect.setDiscountPresence(true);
		permanentCardEffect.setDiscount(discount);
		
		List<Effect> immediateEffects = new ArrayList<>();
		immediateEffects.add(immediateEffect);
		c.setImmediateEffect(immediateEffects);
		c.setPermanentEffect(permanentCardEffect);
		c1.setPermanentEffect(permanentCardEffect);
		
		c.setCost(cost);
		c1.setCost(cost);
		cell.setCard(c);
		cell1.setCard(c1);
		cell.setActionValue(-1);
		cell1.setActionValue(-1);
		cells[0] = cell;
		cells[1] = cell1;
		tower = new Tower(cells);
		tower.setCells(cells);
		towers.put(CardType.CHARACTER, tower);
			
		otherEffect = new OtherEffect();
		otherEffect.setType(EffectType.NOEXTRACOSTINTOWEREFFECT);
		leaderCard.setEffect(otherEffect);
		leaderCard.setActive(true);
		leaderCard.setPlayed(true);
		leaderCard.setName("Pico della Mirandola");
		leaderCards.add(leaderCard);
		player.setLeaderCards(leaderCards);
		
		
		GameView game = bi.initializeBoard(players);
		GameManager gameM = new GameManager();
		gameM.setView(game);
		BoardSetup bs = new BoardSetup(gameM);
		bs.firstSetUpCards();
			
		for(ResourceType resType : ResourceType.values()){
			resources.put(resType, 9);
		}
		Resource res = Resource.of(resources);
		playerBoard.setResources(res);
		playerBoard.addCard(c);
		playerBoard.addCard(c1);
		playerBoard.setResources(resource);
		player.setBoard(playerBoard);
		
			
		gameBoard.setTowers(towers);
		gameBoard.getTowers().get(CardType.CHARACTER).getCells()[0].setFree(false);
		gameBoard.getTowers().get(CardType.CHARACTER).getCells()[0].setFamilyMember(familyMember1);
		
		
		takeCard.setFamilyMember(familyMember);
		takeCard.setName("bob");
		throughEffect.setCardType(CardType.CHARACTER);
		throughEffect.setDiscountPresence(true);
		throughEffect.setDiscount(discount );
		takeCard.setThroughEffect(throughEffect );
		takeCard.isApplicable();
		takeCard.apply();
	}
	
	//take Building Card
	@Test
	public void testApply5() throws FileNotFoundException, IOException {
		c = new Character("bob", 1, 1);
		c1 = new Character("bob", 1, 1);
		
		b = new Building("bob", 1, 2);
		b1 = new Building("bob", 1, 2);
		discount.setAlternativeDiscountPresence(false);
		discount.setAlternativeDiscount(cost);
		
		permanentCardEffect.setCardType(CardType.CHARACTER);
		permanentCardEffect.setIncrement(1);
		permanentCardEffect.setDiscountPresence(true);
		permanentCardEffect.setDiscount(discount);
		
		b.setImmediateEffect(immediateEffect);
		
		c.setPermanentEffect(permanentCardEffect);
		c1.setPermanentEffect(permanentCardEffect);
		
		b.setCost(cost);
		b1.setCost(cost);
		cell.setCard(b);
		cell1.setCard(b1);
		cell.setActionValue(-1);
		cell1.setActionValue(-1);
		cells[0] = cell;
		cells[1] = cell1;
		tower = new Tower(cells);
		tower.setCells(cells);
		towers.put(CardType.BUILDING, tower);
			
		otherEffect = new OtherEffect();
		otherEffect.setType(EffectType.NOEXTRACOSTINTOWEREFFECT);
		leaderCard.setEffect(otherEffect);
		leaderCard.setActive(true);
		leaderCard.setPlayed(true);
		leaderCard.setName("Pico della Mirandola");
		leaderCards.add(leaderCard);
		player.setLeaderCards(leaderCards);
		
		
		GameView game = bi.initializeBoard(players);
		GameManager gameM = new GameManager();
		gameM.setView(game);
		BoardSetup bs = new BoardSetup(gameM);
		bs.firstSetUpCards();
			
		for(ResourceType resType : ResourceType.values()){
			resources.put(resType, 9);
		}
		Resource res = Resource.of(resources);
		playerBoard.setResources(res);
		playerBoard.addCard(b);
		playerBoard.addCard(b1);
		playerBoard.addCard(c);
		playerBoard.addCard(c1);
		playerBoard.setResources(resource);
		player.setBoard(playerBoard);
		
			
		gameBoard.setTowers(towers);
		gameBoard.getTowers().get(CardType.BUILDING).getCells()[0].setFree(false);
		gameBoard.getTowers().get(CardType.BUILDING).getCells()[0].setFamilyMember(familyMember1);
		
		
		takeCard.setFamilyMember(familyMember);
		takeCard.setName("bob");
		throughEffect.setCardType(CardType.BUILDING);
		throughEffect.setDiscountPresence(true);
		throughEffect.setDiscount(discount );
		takeCard.setThroughEffect(throughEffect );
		takeCard.isApplicable();
		takeCard.apply();
	}
}
