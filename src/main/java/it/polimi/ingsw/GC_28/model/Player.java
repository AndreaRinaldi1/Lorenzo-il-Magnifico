package it.polimi.ingsw.GC_28.model;

import it.polimi.ingsw.GC_28.boards.PlayerBoard;

public class Player {
	private String name;
	private PlayerColor color;
	private PlayerBoard board;

	public Player(String name, PlayerColor color){
		this.name = name;
		this.color = color;
	}
		
	public String getName() {
		return name;
	}

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
