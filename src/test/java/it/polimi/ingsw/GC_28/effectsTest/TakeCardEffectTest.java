package it.polimi.ingsw.GC_28.effectsTest;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.*;

import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.boards.PlayerBoard;
import it.polimi.ingsw.GC_28.cards.CardType;
import it.polimi.ingsw.GC_28.components.DiceColor;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.effects.DiscountEffect;
import it.polimi.ingsw.GC_28.effects.TakeCardEffect;
import it.polimi.ingsw.GC_28.model.Game;
import it.polimi.ingsw.GC_28.model.GameModel;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.model.PlayerColor;
import it.polimi.ingsw.GC_28.server.ClientHandler;

public class TakeCardEffectTest {
	private TakeCardEffect tce;
	private int actionValue = 1;
	private boolean discountPresence;
	private DiscountEffect discount;
	private TestGame testGame;
	
	private Player player;
	private FamilyMember familyMember;
	private GameModel gameModel;
	private GameBoard gb;
	private PlayerBoard pb;
	private List<Player> players = new ArrayList<>();
	
	private class TestGame extends Game{
		public TestGame(GameModel gameModel) {
			super(gameModel);
		}
		
		@Override
		public boolean askCard(TakeCardEffect throughEffect){
			return false;
		}
	}
	
	@Before
	public void takeCardEffect(){
		tce = new TakeCardEffect();
		discount = new DiscountEffect();

		player = new Player("aiuto", PlayerColor.BLUE);
		familyMember = new FamilyMember(player, false, DiceColor.BLACK);
		gb = new GameBoard();
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

/*	@Test
	public void testApply() {
		Map<Player, ClientHandler> handlers = new HashMap<>();
		TestClientHandler value = new TestClientHandler(null);
		handlers.put(player, value );
		testGame.setHandlers(handlers );
		String s = "Are you unable to take any card and you want to skip? [y/n]";
		value.setOut(s);
		value.setIn("y");
		tce.apply(familyMember, testGame);
	}*/

	@Test
	public void testGetActionValue() {
		tce.setActionValue(actionValue);
		assertEquals(this.actionValue, this.tce.getActionValue());
	}

	@Test
	public void testGetCardType() {
		tce.setCardType(CardType.VENTURE);
		assertEquals(CardType.VENTURE, this.tce.getCardType());
	}

	@Test
	public void testIsDiscountPresence() {
		tce.setDiscountPresence(discountPresence);
		assertEquals(this.discountPresence, this.tce.isDiscountPresence());
	}

	@Test
	public void testGetDiscount() {
		tce.setDiscount(discount);
		assertEquals(this.discount, this.tce.getDiscount());
	}

}
