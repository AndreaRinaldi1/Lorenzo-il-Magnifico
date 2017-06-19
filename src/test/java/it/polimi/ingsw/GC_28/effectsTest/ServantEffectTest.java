package it.polimi.ingsw.GC_28.effectsTest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.boards.PlayerBoard;
import it.polimi.ingsw.GC_28.components.DiceColor;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.effects.ServantEffect;
import it.polimi.ingsw.GC_28.model.Game;
import it.polimi.ingsw.GC_28.model.GameModel;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.model.PlayerColor;

public class ServantEffectTest {

	private ServantEffect servantEffect;
	private int numberOfServant;
	private int increment;
	
	private Player player;
	private FamilyMember familyMember;
	private GameModel gameModel;
	private GameBoard gb;
	private PlayerBoard pb;
	private List<Player> players = new ArrayList<>();
	private Game game;
	
	@Before
	public void servantEffect(){
		
		player = new Player("aiuto", PlayerColor.BLUE);
		familyMember = new FamilyMember(player, false, DiceColor.BLACK);
		gb = new GameBoard();
		FamilyMember[] familyMembers = new FamilyMember[1];
		familyMembers[0] = familyMember;
		player.setFamilyMembers(familyMembers);
		players.add(player);
		gameModel = new GameModel(gb, players);
		game = new Game(gameModel);
		
		servantEffect = new ServantEffect();
		servantEffect.setIncrement(increment);
		servantEffect.setNumberOfServant(numberOfServant);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testApplyPlayerGame() {
		servantEffect.apply(player, game);
	}

	@Test
	public void testGetNumberOfServant() {
		assertEquals(this.numberOfServant, this.servantEffect.getNumberOfServant());
	}

	@Test
	public void testGetIncrement() {
		assertEquals(this.increment, this.servantEffect.getIncrement());
	}

}
