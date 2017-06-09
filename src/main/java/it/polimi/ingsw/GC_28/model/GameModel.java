package it.polimi.ingsw.GC_28.model;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.server.Message;
import it.polimi.ingsw.GC_28.server.Observable;

public class GameModel extends Observable<Message> {
	
	private GameBoard gameBoard;
	private List<Player> players = new ArrayList<>();
	
	public GameModel(GameBoard gameBoard, List<Player> players) {
		super();
		this.gameBoard = gameBoard;
		this.players = players;
	}

	public GameBoard getGameBoard() {
		return gameBoard;
	}
	
	/*public void setGameBoard(GameBoard gameBoard) {
		this.gameBoard = gameBoard;
	}*/
	
	public List<Player> getPlayers() {
		return players;
	}
	
	/*public void setPlayers(List<Player> players) {
		this.players = players;
	}*/
	
	

}
