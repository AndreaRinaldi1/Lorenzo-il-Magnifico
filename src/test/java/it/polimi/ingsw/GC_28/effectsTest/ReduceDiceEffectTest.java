package it.polimi.ingsw.GC_28.effectsTest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.components.Dice;
import it.polimi.ingsw.GC_28.components.DiceColor;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.effects.ReduceDiceEffect;
import it.polimi.ingsw.GC_28.model.Game;
import it.polimi.ingsw.GC_28.model.GameModel;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.model.PlayerColor;

public class ReduceDiceEffectTest {
	private ReduceDiceEffect reduceDiceEffect;
	private List<DiceColor> diceColor = new ArrayList<>();
	int reduce = 1;	
	private FamilyMember familyMember1;
	private FamilyMember familyMember2;
	private FamilyMember familyMember3;
	private FamilyMember familyMember4;
	private FamilyMember familyMember5;
	private FamilyMember familyMember6;
	private FamilyMember[] familyMembers;
	private FamilyMember[] familyMembers2;
	private Player player1;
	private Player player2;
	private GameModel gameModel;
	private GameBoard gameBoard; 
	private List<Player> players = new ArrayList<>();
	private Game game;
	private Dice dice1 = new Dice(DiceColor.ORANGE);
	private Dice dice2 = new Dice(DiceColor.BLACK);
	private Dice dice3 = new Dice(DiceColor.WHITE);
	private Dice[] dices;
	
	
	@Before
	public void reduceDiceEffect(){
		reduceDiceEffect = new ReduceDiceEffect();
		player1 = new Player("gino", PlayerColor.BLUE);
		player2 = new Player("gino2", PlayerColor.GREEN);
		familyMember1 = new FamilyMember(player1, false, DiceColor.BLACK);
		familyMember2 = new FamilyMember(player1, false, DiceColor.ORANGE);
		familyMember3 = new FamilyMember(player1, false, DiceColor.WHITE);

		familyMember4 = new FamilyMember(player2, false, DiceColor.BLACK);
		familyMember5 = new FamilyMember(player2, false, DiceColor.ORANGE);
		familyMember6 = new FamilyMember(player2, false, DiceColor.WHITE);
		familyMembers = new FamilyMember[3];
		familyMembers2 = new FamilyMember[3];
		players.add(player1);
		gameBoard = new GameBoard();
		gameModel = new GameModel(gameBoard, players);
		game = new Game(gameModel);
		game.setCurrentPlayer(player1);
		dices = new Dice[3];
		
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testApplyPlayerGame() {
		
		familyMembers[0] = familyMember1;
		familyMembers[1] = familyMember2;
		familyMembers[2] = familyMember3;
		player1.setFamilyMembers(familyMembers);
		
		familyMembers2[0] = familyMember4;
		familyMembers2[1] = familyMember5;
		familyMembers2[2] = familyMember6;
		player2.setFamilyMembers(familyMembers2);;
		
		dice1.rollDice();
		dices[0] = dice1;
		dice2.rollDice();
		dices[1] = dice2;
		dice3.rollDice();
		dices[2] = dice3;

		
		for(int i = 0; i<dices.length; i++){
			diceColor.add(dices[i].getColor());
			player1.getFamilyMembers()[i].setValue(dices[i].getValue());
			player2.getFamilyMembers()[i].setValue(dices[i].getValue());
		}

		gameBoard.setDices(dices);
		reduceDiceEffect.setDiceColor(diceColor);
		reduceDiceEffect.setReduce(reduce);
		reduceDiceEffect.apply(this.player1, game);

		//provare col familymembers array
		for(int j = 0; j<familyMembers2.length; j++){
			for(int i = 0; i<diceColor.size(); i++){
				if(player2.getFamilyMembers()[j].getDiceColor().equals(diceColor.get(i))){
					player2.getFamilyMembers()[j].modifyValue(reduce);
				}

			}
			
		}
		
		for(int i = 0; i<familyMembers.length; i++){
			assertEquals(this.familyMembers2[i].getValue(),
					this.game.getCurrentPlayer().getFamilyMembers()[i].getValue());
		}
	}

}
