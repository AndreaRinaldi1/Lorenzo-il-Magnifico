package it.polimi.ingsw.GC_28.core;

import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.effects.TakeCardEffect;
import it.polimi.ingsw.GC_28.model.Game;

public class TakeCardAction {
	private Game game;
	private GameBoard gameBoard;
	private TakeCardController takeCardController = new TakeCardController(gameBoard);
	
	public TakeCardAction(Game game, GameBoard gameBoard){
		this.game = game;
		this.gameBoard = gameBoard;
	}

	public boolean isApplicable(String name, FamilyMember familyMember, TakeCardEffect throughEffect){
		return takeCardController.check(game, name, familyMember, throughEffect);
	}
	
	public void apply(String name, FamilyMember familyMember, TakeCardEffect throughEffect){
	}
	
	
}
