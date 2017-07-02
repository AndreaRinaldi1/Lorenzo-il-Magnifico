package it.polimi.ingsw.GC_28.model;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.server.Message;
import it.polimi.ingsw.GC_28.server.Observable;

/**
 * This class keep together the list of players and gameboard 
 * @author nicoloscipione, andrearinaldi
 * @version 1.0 , 30/06/2017
 * @see Player, GameBoard
 */ 

public class GameModel extends Observable<Message> {
	
	private GameBoard gameBoard;
	private List<Player> players = new ArrayList<>();
	
	public GameModel(GameBoard gameBoard, List<Player> players) {
		super();
		this.gameBoard = gameBoard;
		this.players = players;
	}
	
	/**
	 * 
	 * @return return the gameboard.
	 */
	public GameBoard getGameBoard() {
		return gameBoard;
	}
	
	
	/**
	 * 
	 * @return return the list of players.
	 */
	public List<Player> getPlayers() {
		return players;
	}
	
	
	

}
