package it.polimi.ingsw.GC_28.effects;


import it.polimi.ingsw.GC_28.boards.*;
import it.polimi.ingsw.GC_28.model.Game;

public class Effect {	
	//public GameBoard gameBoard; //non va bene perche se non è messo static gli altri sottoeffetti non riescono a vederlo (NullPointerException)
								// mentre se static allora secondo me farà conflitto nel caso di piu partite
								// Bisogna passarlo come parametro degli apply
	
	public Effect(){
	}

	
	public void apply(PlayerBoard p, GameBoard gameBoard, Game game){
		System.out.println("apply di Effect");
	}
}
