package it.polimi.ingsw.GC_28.effectsTest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.boards.PlayerBoard;
import it.polimi.ingsw.GC_28.cards.Card;
import it.polimi.ingsw.GC_28.cards.CardType;
import it.polimi.ingsw.GC_28.cards.Territory;
import it.polimi.ingsw.GC_28.components.DiceColor;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.effects.ExchangeEffect;
import it.polimi.ingsw.GC_28.effects.MultiplierEffect;
import it.polimi.ingsw.GC_28.effects.PrivilegesEffect;
import it.polimi.ingsw.GC_28.effects.ProductionEffect;
import it.polimi.ingsw.GC_28.effects.ResourceEffect;
import it.polimi.ingsw.GC_28.model.GameModel;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.model.PlayerColor;
import it.polimi.ingsw.GC_28.view.GameView;

public class ProductionEffectTest {
	private ProductionEffect prd;
	private int productionValue = 4;
	private ResourceEffect resourceBonus;
	private ExchangeEffect exchangeBonus;
	private MultiplierEffect multiplierEffect;
	private PrivilegesEffect privilegeEffect;

	private Resource bonus;
	EnumMap<ResourceType, Integer> resource;
	
	private Player player;
	private FamilyMember familyMember;
	private GameModel gameModel;
	private GameBoard gb;
	private PlayerBoard pb;
	private List<Player> players = new ArrayList<>();
	private TestGame testGame;
	
	private Card card;
	
	private class TestGame extends GameView{
		public TestGame(GameModel gameModel) {
			super(gameModel);
		}
		
		@Override
		public Resource checkResourceExcommunication(Resource amount){
			resource = new EnumMap<>(ResourceType.class);
			resource.put(ResourceType.COIN, 3);
			bonus = Resource.of(resource);
			return bonus;
		}
		
		@Override
		public ArrayList<Character> askPrivilege(int numberOfCouncilPrivileges, boolean different){
			ArrayList<Character> tmp = new ArrayList<>();
			tmp.add('c');
			return tmp;
		}
		
		@Override
		public boolean askPermission(){
			return false;
		}
	}
	
	
	@Before
	public void productionEffect(){
		prd = new ProductionEffect();
		privilegeEffect = new PrivilegesEffect();
		multiplierEffect = new MultiplierEffect();
		exchangeBonus = new ExchangeEffect();
		resourceBonus = new ResourceEffect();
		
		resource = new EnumMap<>(ResourceType.class);
		resource.put(ResourceType.COIN, 3);
		bonus = Resource.of(resource);
		
		//resource Effect
		resourceBonus.setResourceBonus(bonus);
		
		//PrivilegeEffect
		privilegeEffect.setNumberOfCouncilPrivileges(2);
		privilegeEffect.setDifferent(true);
		
		//Multiplier Effect
		multiplierEffect.setCardType(CardType.TERRITORY);
		multiplierEffect.setResourceCost(null);
		multiplierEffect.setResourceBonus(bonus);
		
		//exchange effect
		exchangeBonus.setFirstBonus(bonus);
		player = new Player("aiuto", PlayerColor.BLUE);
		familyMember = new FamilyMember(player, false, DiceColor.BLACK);
		gb = new GameBoard();
		pb = new PlayerBoard(null, bonus);
		player.setBoard(pb);
		FamilyMember[] familyMembers = new FamilyMember[1];
		familyMembers[0] = familyMember;
		player.setFamilyMembers(familyMembers);
		players.add(player);
		gameModel = new GameModel(gb, players);
		testGame = new TestGame(gameModel);
		
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	//resourceProductionBonus != null
	@Test
	public void testApply() {
		prd.setResourceBonus(resourceBonus);
		prd.apply(familyMember, testGame);
		prd.apply(familyMember.getPlayer(), testGame);
	}

	//privilegeEffect != null
	@Test
	public void testApply1(){
		prd.setPrivilegeEffect(privilegeEffect);
		prd.apply(familyMember, testGame);
		prd.apply(familyMember.getPlayer(), testGame);
	}
	
	//multiplierEffect != null
	@Test
	public void testApply2(){
		card = new Territory("io", 2, 2);
		pb.addCard((Territory)card);
		prd.setMultiplierEffect(multiplierEffect);
		prd.apply(familyMember, testGame);
		prd.apply(familyMember.getPlayer(), testGame);
	}
	
	//exchangeBonus != null
	@Test
	public void testApply3(){
		prd.setExchangeBonus(exchangeBonus);
		prd.apply(familyMember, testGame);
		prd.apply(familyMember.getPlayer(), testGame);
	}
	
	//familyMember.getValue() < productionValue
	@Test
	public void testApply4(){
		familyMember.setValue(2);
		prd.setProductionValue(productionValue);
		prd.apply(familyMember, testGame);
		prd.apply(familyMember.getPlayer(), testGame);
	}
	
	@Test
	public void testGetPrivilegeEffect() {
		prd.setPrivilegeEffect(privilegeEffect);
		assertEquals(this.privilegeEffect, this.prd.getPrivilegeEffect());
	}

	@Test
	public void testGetMultiplierEffect() {
		prd.setMultiplierEffect(multiplierEffect);
		assertEquals(this.multiplierEffect, this.prd.getMultiplierEffect());
	}

	@Test
	public void testGetProductionValue() {
		prd.setProductionValue(productionValue);
		assertEquals(this.productionValue, this.prd.getProductionValue());
	}

	@Test
	public void testGetResourceBonus() {
		prd.setResourceBonus(resourceBonus);
		assertEquals(this.resourceBonus, this.prd.getResourceBonus());
	}

	@Test
	public void testGetExchangeBonus() {
		prd.setExchangeBonus(exchangeBonus);
		assertEquals(this.exchangeBonus, this.prd.getExchangeBonus());
	}

}
