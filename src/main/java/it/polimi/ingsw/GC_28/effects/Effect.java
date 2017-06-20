package it.polimi.ingsw.GC_28.effects;


import java.rmi.RemoteException;

import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.cards.CardType;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.view.GameView;

public abstract class Effect {
	//private EffectType type;
	//public GameBoard gameBoard; //non va bene perche se non è messo static gli altri sottoeffetti non riescono a vederlo (NullPointerException)
								// mentre se static allora secondo me farà conflitto nel caso di piu partite
								// Bisogna passarlo come parametro degli apply
	

	
	public void apply(FamilyMember familyMember, GameView game){}
		//System.out.println("apply di Effect");
	public void apply(Player player, GameView game){}
	
	
	
	
//	public CardType getCardtype(){}
	//public int getActionValue(){}
}
