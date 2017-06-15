package it.polimi.ingsw.GC_28.effects;


import java.io.Serializable;

import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.cards.CardType;
import it.polimi.ingsw.GC_28.client.ClientWriter;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.model.Game;
import it.polimi.ingsw.GC_28.model.Player;

public abstract class Effect implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private EffectType type;
	//public GameBoard gameBoard; //non va bene perche se non è messo static gli altri sottoeffetti non riescono a vederlo (NullPointerException)
								// mentre se static allora secondo me farà conflitto nel caso di piu partite
								// Bisogna passarlo come parametro degli apply
	

	
	public void apply(FamilyMember familyMember, ClientWriter writer){}
		//System.out.println("apply di Effect");
	public void apply(Player player,ClientWriter writer){}
	
	
	
	
//	public CardType getCardtype(){}
	//public int getActionValue(){}
}
