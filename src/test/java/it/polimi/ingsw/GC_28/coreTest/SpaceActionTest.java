package it.polimi.ingsw.GC_28.coreTest;

import static org.junit.Assert.*;

import java.awt.List;
import java.util.ArrayList;
import java.util.EnumMap;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.boards.BonusTile;
import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.boards.PlayerBoard;
import it.polimi.ingsw.GC_28.cards.Building;
import it.polimi.ingsw.GC_28.cards.Character;
import it.polimi.ingsw.GC_28.cards.ExcommunicationTile;
import it.polimi.ingsw.GC_28.cards.LeaderCard;
import it.polimi.ingsw.GC_28.components.DiceColor;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.core.SpaceAction;
import it.polimi.ingsw.GC_28.effects.Effect;
import it.polimi.ingsw.GC_28.effects.EffectType;
import it.polimi.ingsw.GC_28.effects.GoToHPEffect;
import it.polimi.ingsw.GC_28.effects.HarvestEffect;
import it.polimi.ingsw.GC_28.effects.IncrementHPEffect;
import it.polimi.ingsw.GC_28.effects.OtherEffect;
import it.polimi.ingsw.GC_28.effects.ProductionEffect;
import it.polimi.ingsw.GC_28.effects.ResourceEffect;
import it.polimi.ingsw.GC_28.model.GameModel;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.model.PlayerColor;
import it.polimi.ingsw.GC_28.spaces.MarketSpace;
import it.polimi.ingsw.GC_28.spaces.PrivilegesSpace;
import it.polimi.ingsw.GC_28.spaces.ProdHarvSpace;
import it.polimi.ingsw.GC_28.spaces.ProdHarvType;
import it.polimi.ingsw.GC_28.view.GameView;

public class SpaceActionTest {

	private SpaceAction spaceAction;
	private SpaceAction spaceActionTest;
	private GameModel gameModel;
	private GameBoard gameBoard;
	private PlayerBoard playerBoard;
	private Player player;
	private ArrayList<Player> players = new ArrayList<>();
	private GameView game;
	private TestGame testGame;
	
	private FamilyMember[] familyMembers = new FamilyMember[1];
	private FamilyMember familyMember;
	private MarketSpace coinSpace2;
	private ProdHarvSpace prodHarvSpace;
	private GoToHPEffect throughEffect;
	private ArrayList<LeaderCard> leaderCards = new ArrayList<>();
	private LeaderCard leaderCard;
	private LeaderCard leaderCard1;

	
	EnumMap<ResourceType, Integer> resources = new EnumMap<>(ResourceType.class);
	private Resource bonus;
	private ResourceEffect effect;
	
	private Boolean free = true;
	private int actionValue = 3;
	private boolean secondarySpace = true;

	private ArrayList<ExcommunicationTile> excommunicationTile = new ArrayList<>();
	private ExcommunicationTile excomm;
	private OtherEffect otherEffect;

	
	private ExcommunicationTile excommHarvest;
	private ExcommunicationTile excommProduction;
	
	private PrivilegesSpace privilegesSpace;
	private Character characterCard = new Character("bob", 2, 1);
	private IncrementHPEffect incrementHPEffect = new IncrementHPEffect();
	private BonusTile bonusTile;
	private HarvestEffect harvestEffect;
	
	private class TestGame extends GameView{
		public TestGame(GameModel gameModel) {
			super(gameModel);
		}

		@Override
		public Resource checkResourceExcommunication(Resource amount){
			return bonus;
		}
	}
	
	@Before
	public void spaceAction(){
		player = new Player("Mattia", PlayerColor.BLUE);
		familyMember = new FamilyMember(player, false, DiceColor.BLACK);
		this.familyMembers[0] = familyMember;
		this.player.setFamilyMembers(familyMembers);
		leaderCard = new LeaderCard();
		leaderCard.setName("Rino");
		leaderCards.add(leaderCard);
		this.player.setLeaderCards(leaderCards);
		
		throughEffect = new GoToHPEffect();
		
		effect = new ResourceEffect();
		effect.setResourceBonus(bonus);
		resources.put(ResourceType.COIN, 5);
		bonus = Resource.of(resources);
		
		bonusTile = new BonusTile();
		harvestEffect = new HarvestEffect();
		harvestEffect.setResourceHarvestBonus(effect);
		harvestEffect.setHarvestValue(actionValue);
		bonusTile.setHarvestEffect(harvestEffect);
		playerBoard = new PlayerBoard(bonusTile, bonus);
		this.player.setBoard(playerBoard);
		this.players.add(player);
		gameBoard = new GameBoard();
		
		otherEffect = new OtherEffect();
		otherEffect.setType(EffectType.NOMARKETEFFECT);
		
		excomm = new ExcommunicationTile();
		excomm.setEffect(otherEffect);
		excommunicationTile.add(excomm);
		
		
		excommHarvest = new ExcommunicationTile();
		excommProduction = new ExcommunicationTile();
		
		privilegesSpace = new PrivilegesSpace(free, actionValue);
		
		coinSpace2 = new MarketSpace(free, actionValue);
		coinSpace2.setBonus(effect);
		prodHarvSpace = new ProdHarvSpace(free, actionValue);
		prodHarvSpace.setSecondarySpace(secondarySpace);
		this.gameBoard.setProductionSpace(prodHarvSpace);
		this.gameBoard.setCoinSpace(coinSpace2);
		
		gameModel = new GameModel(gameBoard, players);
		game = new GameView(gameModel); 
		testGame = new TestGame(gameModel);
		
		spaceAction = new SpaceAction(game, gameModel);
		spaceActionTest = new SpaceAction(testGame, gameModel);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	//without the card leader ludovico ariosto and checkActionValue == false
	@Test
	public void testIsApplicable() {
		coinSpace2.setFree(free);
		spaceAction.setFamilyMember(familyMember);
		spaceAction.setSpace(coinSpace2);
		assertEquals(false, this.spaceAction.isApplicable());
	}

	//without the card leader ludovico ariosto and checkActionValue == true
	@Test
	public void testIsApplicable1() {
		familyMember.setValue(6);
		coinSpace2.setFree(free);
		spaceAction.setFamilyMember(familyMember);
		spaceAction.setSpace(coinSpace2);
		assertEquals(true, this.spaceAction.isApplicable());
	}
	
	//without the card leader ludovico ariosto and checkFree == false
	@Test
	public void testIsApplicable2() {
		familyMember.setValue(6);
		coinSpace2.setFree(!free);
		spaceAction.setFamilyMember(familyMember);
		spaceAction.setSpace(coinSpace2);
		assertEquals(false, this.spaceAction.isApplicable());
	}

	//without the card leader ludovico ariosto and checkFree == true thanks to prodHarvSpace
	@Test
	public void testIsApplicable3() {
		familyMember.setValue(6);
		spaceAction.setFamilyMember(familyMember);
		prodHarvSpace.setFree(false);
		spaceAction.setSpace(prodHarvSpace);
		
		assertEquals(true, this.spaceAction.isApplicable());
	}	
	
	//with the card leader ludovico ariosto and checkFree == true and checkAction == true
	@Test
	public void testIsApplicable4() {
		leaderCard1 = new LeaderCard();
		leaderCard1.setName("Ludovico Ariosto");
		leaderCard1.setActive(true);
		leaderCard1.setPlayed(true);
		leaderCards.add(leaderCard1);
		this.player.setLeaderCards(leaderCards);
		this.players.add(player);
		familyMember.setValue(6);
		coinSpace2.setFree(!free);
		spaceAction.setFamilyMember(familyMember);
		spaceAction.setSpace(prodHarvSpace);
		assertEquals(true, this.spaceAction.isApplicable());
	}

	//with excommunication tile, marketSpace and other effect = noMarketEfect 
	@Test
	public void testIsApplicable5() {
		coinSpace2.setFree(free);
		familyMember.getPlayer().setExcommunicationTile(excommunicationTile);
		spaceAction.setFamilyMember(familyMember);
		spaceAction.setSpace(coinSpace2);
		assertEquals(false, this.spaceAction.isApplicable());
	}
	
	//with excommunication tile, PrivilegesSpace and other effect = noMarketEfect 
	@Test
	public void testIsApplicable6() {
		familyMember.getPlayer().setExcommunicationTile(excommunicationTile);
		spaceAction.setFamilyMember(familyMember);
		spaceAction.setSpace(privilegesSpace);
		assertEquals(false, this.spaceAction.isApplicable());
	}
	
	//HARVEST APPLY
	@Test	
	public void testApplyHarvest() {
		familyMember.setValue(6);
		incrementHPEffect.setIncrement(actionValue);
		incrementHPEffect.setType(EffectType.INCREMENTHARVESTEFFECT);
		excommHarvest.setEffect(incrementHPEffect);
		characterCard.setPermanentEffect(incrementHPEffect );
		familyMember.getPlayer().getBoard().getCharacters().add(characterCard );
		
		familyMember.getPlayer().getExcommunicationTile().add(excommHarvest);
		
		spaceActionTest.setFamilyMember(familyMember);
		prodHarvSpace.setType(ProdHarvType.HARVEST);
		prodHarvSpace.setFree(false);
		prodHarvSpace.setSecondarySpace(true);
		spaceActionTest.setSpace(prodHarvSpace);
		spaceActionTest.setThroughEffect(throughEffect);
		spaceActionTest.apply();
		boolean x = this.familyMember.getValue()==9;
		assertTrue(x);
	}


	//PRODUCTION APPLY
	@Test	
	public void testApplyProduction() {
		familyMember.setValue(6);
		incrementHPEffect.setIncrement(actionValue);
		incrementHPEffect.setType(EffectType.INCREMENTPRODUCTIONEFFECT);
		excommProduction.setEffect(incrementHPEffect);

		Building buildingCard = new Building("ciao", 2, 1);
		ProductionEffect productionEffect = new ProductionEffect();
		productionEffect.setResourceBonus(effect);
		buildingCard.setPermanentEffect(productionEffect);
		familyMember.getPlayer().getBoard().getBuildings().add(buildingCard);
			
		familyMember.getPlayer().getExcommunicationTile().add(excommProduction);
			
		bonusTile.setProductionEffect(productionEffect);
		playerBoard.setBonusTile(bonusTile);
		
		spaceActionTest.setFamilyMember(familyMember);
		prodHarvSpace.setType(ProdHarvType.PRODUCTION);
		prodHarvSpace.setFree(false);
		prodHarvSpace.setSecondarySpace(true);
		spaceActionTest.setSpace(prodHarvSpace);
		spaceActionTest.apply();
		boolean x = this.familyMember.getValue()==6;
		assertTrue(x);
	}

}
