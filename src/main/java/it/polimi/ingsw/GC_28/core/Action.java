package it.polimi.ingsw.GC_28.core;

import java.io.Serializable;

import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.model.Game;
import it.polimi.ingsw.GC_28.model.GameModel;
import it.polimi.ingsw.GC_28.model.Player;

public abstract class Action implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Game game;
	protected GameModel gameModel;
	
	private Player player;

	
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	public void setGame(Game game) {
		this.game = game;
	}
	public void setGameModel(GameModel gameModel) {
		this.gameModel = gameModel;
	}
	
	
	public Player getPlayer() {
		return player;
	}
	public abstract boolean isApplicable();
	public abstract void apply();
}
