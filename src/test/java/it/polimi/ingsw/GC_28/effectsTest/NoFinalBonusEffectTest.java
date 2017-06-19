package it.polimi.ingsw.GC_28.effectsTest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.boards.BonusTile;
import it.polimi.ingsw.GC_28.boards.FinalBonus;
import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.boards.PlayerBoard;
import it.polimi.ingsw.GC_28.cards.Building;
import it.polimi.ingsw.GC_28.cards.Card;
import it.polimi.ingsw.GC_28.cards.CardType;
import it.polimi.ingsw.GC_28.cards.Character;
import it.polimi.ingsw.GC_28.cards.Territory;
import it.polimi.ingsw.GC_28.cards.Venture;
import it.polimi.ingsw.GC_28.components.DiceColor;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.effects.MultiplierEffect;
import it.polimi.ingsw.GC_28.effects.NoFinalBonusEffect;
import it.polimi.ingsw.GC_28.effects.ResourceEffect;
import it.polimi.ingsw.GC_28.model.Game;
import it.polimi.ingsw.GC_28.model.GameModel;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.model.PlayerColor;

public class NoFinalBonusEffectTest {
	private NoFinalBonusEffect nfbe;
	private Resource resourceCost;
	EnumMap<ResourceType, Integer> resource;
	private CardType cardType;
	private Resource resourceBonus;
	EnumMap<ResourceType, Integer> resource1;
	
	private TestGame testGame;
	private FamilyMember fm;
	private Player player;
	
	private GameBoard gb;
	private PlayerBoard pb;
	private GameModel gameModel;
	private List<Player> players = new ArrayList<>();
	private BonusTile bt;
	
	private Card card;

	private FinalBonus finalBonus;
	private ArrayList<Resource> finalCharactersBonus;
	
	private class TestGame extends Game{
		public TestGame(GameModel gameModel) {
			super(gameModel);
		}
		
	}
	
	@Before
	public void noFinalBonusEffect(){
		//costruisci l'effetto
		nfbe = new NoFinalBonusEffect();

		finalCharactersBonus = new ArrayList<>();
		
		finalBonus = FinalBonus.instance();
		finalBonus.setFinalCharactersBonus(finalCharactersBonus);
		
		//fai i player
		player = new Player("gino", PlayerColor.GREEN);
				
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
			
	
		//fai il game
		players.add(player);
		gameModel = new GameModel(gb, players);
		testGame = new TestGame(gameModel);
		
		//fai i familyMember
		fm = new FamilyMember(player, false, DiceColor.ORANGE);
		fm.setValue(2);
				
		//fai gb e la setti in game e setti currentPlayer in game
		gb = new GameBoard();
		testGame.setCurrentPlayer(player);
		
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	//case Territory
	@Test
	public void testApplyPlayerGame() {
		//pb.addCard(c);
		card = new Territory("aba", 2, 1);
		pb.addCard((Territory) card);
		finalCharactersBonus.add(resourceBonus);
		finalCharactersBonus.add(resourceCost);
		finalBonus.setFinalTerritoriesBonus(finalCharactersBonus);
		nfbe.setCardType(CardType.TERRITORY);
		//pb.addCard(v);
		nfbe.apply(fm, testGame);
		nfbe.apply(fm.getPlayer(), testGame);
	}

	//case Character
	@Test
	public void testApplyCharacter() {
		//pb.addCard(c);
		card = new Character("aba", 2, 1);
		pb.addCard((Character) card);
		finalCharactersBonus.add(resourceBonus);
		finalCharactersBonus.add(resourceCost);
		finalBonus.setFinalTerritoriesBonus(finalCharactersBonus);
		nfbe.setCardType(CardType.CHARACTER);
		//pb.addCard(v);
		nfbe.apply(fm, testGame);
		nfbe.apply(fm.getPlayer(), testGame);
	}

	//case Venture
	@Test
	public void testApplyVenture() {
		//pb.addCard(c);
		Venture card1 = new Venture("aba", 2, 1);
		ResourceEffect permanentEffect = new ResourceEffect();
		permanentEffect.setResourceBonus(resourceBonus);
		card1.setPermanentEffect(permanentEffect );
		pb.addCard(card1);
		
		finalCharactersBonus.add(resourceBonus);
		finalCharactersBonus.add(resourceCost);
		finalBonus.setFinalTerritoriesBonus(finalCharactersBonus);
		nfbe.setCardType(CardType.VENTURE);
		//pb.addCard(v);
		nfbe.apply(fm, testGame);
		nfbe.apply(fm.getPlayer(), testGame);
	}

	@Test
	public void testGetCardType() {
		card = new Building("casa", 2, 1);
		nfbe.setCardType(cardType);
					
		cardType = CardType.BUILDING;
		boolean x = cardType.equals(nfbe.getCardType());
		assertTrue(x);
	}

}
