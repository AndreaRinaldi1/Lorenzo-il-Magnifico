package it.polimi.ingsw.GC_28.effectsTest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.boards.BonusTile;
import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.boards.PlayerBoard;
import it.polimi.ingsw.GC_28.components.DiceColor;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.effects.ModifyDiceEffect;
import it.polimi.ingsw.GC_28.model.GameModel;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.model.PlayerColor;
import it.polimi.ingsw.GC_28.view.GameView;

public class ModifyDiceEffectTest {
	
	private ModifyDiceEffect modifyDice;
	private List<DiceColor> diceColor = new ArrayList<>();
	private int reduce = 1;
	private Player player;
	private Player player1;
	
	private GameBoard gb;
	private PlayerBoard pb;
	
	private GameModel gameModel;
	private List<Player> players = new ArrayList<>();
	private GameView game;
	private FamilyMember[] familyMembers = new FamilyMember[4];
	private FamilyMember[] familyMembers1 = new FamilyMember[4];
	private FamilyMember fm;
	private FamilyMember fm1;
	@Before
	public void modifyDiceEffect(){
		modifyDice = new ModifyDiceEffect();
		diceColor.add(DiceColor.BLACK);
		diceColor.add(DiceColor.NEUTRAL);
		diceColor.add(DiceColor.ORANGE);
		diceColor.add(DiceColor.WHITE);
		modifyDice.setDiceColor(diceColor);
		modifyDice.setReduce(reduce);

		player = new Player("gino", PlayerColor.GREEN);
		player1 = new Player("gino1", PlayerColor.BLUE);
		
		players.add(player);
		players.add(player1);
		gameModel = new GameModel(gb, players);
		game = new GameView(gameModel);
		player.setFamilyMembers(familyMembers);
		player1.setFamilyMembers(familyMembers1);
		for(int i = 0; i < DiceColor.values().length; i ++){
			fm = new FamilyMember(player, false, DiceColor.values()[i]);
			fm1 = new FamilyMember(player1, false, DiceColor.values()[i]);
			familyMembers[i] = fm;
			familyMembers1[i] = fm;
		}
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testApplyPlayerGame() {
		modifyDice.apply(player1, game);
		for(FamilyMember fm : player.getFamilyMembers()){
			for(DiceColor color : diceColor){
				if(fm.getDiceColor().equals(color)){
					fm.modifyValue(reduce);
				}
			}
		}
		for(int l = 0; l < diceColor.size(); l++){
			for (int k = 0; k < diceColor.size(); k++){
				if(this.player.getFamilyMembers()[l].getDiceColor().equals(this.player1.getFamilyMembers()[k].getDiceColor()))
				assertEquals(this.player.getFamilyMembers()[l].getValue(), this.player1.getFamilyMembers()[k].getValue());
			}
		} 
		
	}

}
