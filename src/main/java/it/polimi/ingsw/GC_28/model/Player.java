package it.polimi.ingsw.GC_28.model;

import it.polimi.ingsw.GC_28.boards.PlayerBoard;

public class Player {
	private PlayerColor color;
	private PlayerBoard board;

	public PlayerBoard getBoard() {
		return board;
	}

	public void setBoard(PlayerBoard board) {
		this.board = board;
	}

	public Player(PlayerColor color) {
		this.color = color;
	}

	public PlayerColor getColor() {
		return color;
	}

	public void setColor(PlayerColor color) {
		this.color = color;
	}
	
	
	
}
