package it.polimi.ingsw.GC_28.boards;

/**
 * This class represent the Tower that is represented in the gameboard.
 * Every tower is made of four cells where the cards are actually placed.
 * @author andreaRinaldi, nicoloScipione, robertoTuri
 * @version 1.0,  06/28/2017
 * @see Cell
 */

public class Tower {
	private static final int MAX_SIZE = 4;
	private Cell[] cells = new Cell[MAX_SIZE];
	
	/**
	 * The constructor builds a tower given the four cells that it is made up with.
	 * @param cells The array of four cells where there are the bonus and where cards are placed
	 */
	public Tower(Cell[] cells){
		this.cells = cells;
	}

	/**
	 * @param cardName The name of the card the player wants to take
	 * @return the cell in which the card with that name is placed, if any, null otherwise.
	 */
	public Cell findCard(String cardName){
		for(Cell cell : cells){
			if(cell.getCard() == null){
				continue;
			}
			if(cell.getCard().getName().equalsIgnoreCase(cardName)){
				return cell;
			}
		}
		return null;
	}
	
	/**
	 * @return The array of four cells that make up this tower
	 */
	public Cell[] getCells() {
		return cells;
	}

	/**
	 * @param cells The array of four cells that make up this tower
	 */
	public void setCells(Cell[] cells) {
		this.cells = cells;
	}
}
