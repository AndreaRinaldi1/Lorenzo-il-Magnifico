package it.polimi.ingsw.GC_28.effectsTest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.components.DiceColor;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.effects.EffectType;
import it.polimi.ingsw.GC_28.effects.SetFamilyMemberValueEffect;
import it.polimi.ingsw.GC_28.model.Game;
import it.polimi.ingsw.GC_28.model.GameModel;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.model.PlayerColor;

public class SetFamilyMemberValueEffectTest {

	private SetFamilyMemberValueEffect setValueEffect;
	private boolean colored;
	private int value = 3;
	private EffectType type = EffectType.SETFAMILYMEMBERVALUEEFFECT;
	private FamilyMember familyMember;
	private FamilyMember familyMember1;
	private Game game;
	private Player player;
	private Player player1;
	private GameModel gameModel;
	private GameBoard gameBoard;
	private List<Player> players = new ArrayList<>();
	
	@Before
	public void setFamilyMemberValueEffect(){
		setValueEffect = new SetFamilyMemberValueEffect();
		setValueEffect.setValue(value);
		player = new Player("bob", PlayerColor.BLUE);
		player1 = new Player("2ob", PlayerColor.GREEN);
		familyMember = new FamilyMember(player, false, DiceColor.BLACK);
		familyMember1 = new FamilyMember(player1, false, DiceColor.NEUTRAL);
		players.add(player);
		players.add(player1);
		gameBoard = new GameBoard();
		gameModel = new GameModel(gameBoard, players);
		game = new Game(gameModel);
		
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	//TODO TEST WITH FEDERICO DA MONFERRATO EFFECT AND COLORED = TRUE
	
	//test without federico da monferrato effect
	@Test
	public void testApplyFamilyMemberGame() {
		colored = false;
		setValueEffect.setColored(colored);
		FamilyMember[] familyMembers = new FamilyMember[1];
		familyMembers[0] = familyMember1;
		this.player1.setFamilyMembers(familyMembers);
		this.setValueEffect.apply(this.familyMember1, game);		
		boolean x = this.player1.getFamilyMembers()[0].getValue().equals(this.value);
		assertTrue(x);
	}

	//test without federico da monferrato effect
	@Test
	public void testApplyPlayerGame() {
		colored = false;
		setValueEffect.setColored(colored);
		FamilyMember[] familyMembers = new FamilyMember[1];
		familyMembers[0] = familyMember1;
		this.player1.setFamilyMembers(familyMembers);
		this.setValueEffect.apply(this.player1, game);		
		boolean x = this.player1.getFamilyMembers()[0].getValue().equals(this.value);
		assertTrue(x);
		//assertEquals(this.value, this.player1.getFamilyMembers()[0].getValue());
	}

	@Test
	public void testIsColored() {
		setValueEffect.setColored(colored);
		assertEquals(this.colored, this.setValueEffect.isColored());
	}

	@Test
	public void testGetValue() {
		assertEquals(this.value, this.setValueEffect.getValue());
	}

	@Test
	public void testGetType() {
		assertEquals(this.type, this.setValueEffect.getType());
	}

}
