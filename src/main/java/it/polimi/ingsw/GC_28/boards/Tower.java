package it.polimi.ingsw.GC_28.boards;

import java.awt.Color;

import it.polimi.ingsw.GC_28.model.PlayerColor;

public class Tower {
	private final int MAX_SIZE = 4;
	private Cell[] cells = new Cell[MAX_SIZE];
	private boolean atLeastOne;
	
	public Tower(Cell[] cells){
		this.cells = cells;
	}

	public Cell findCard(String cardName){
		for(Cell cell : cells){
			if(cell.getCard().getName().equals(cardName)){
				return cell;
			}
		}
		return null;
	}
	
	public boolean isThisPlayerPresent(PlayerColor playerColor){
		// FIXME doesn't work for the neutral member
		for(int i = 0; i < MAX_SIZE; i++){
			if(cells[i].getFamilyMember().getPlayer().getColor() == playerColor){		//serve la classe Player per poter compiere questo metodo
				return true;
			}
		}
		return false;
	}
	
	public Cell[] getCells() {
		return cells;
	}

	public void setCells(Cell[] cells) {
		this.cells = cells;
	}

	public boolean getAtLeastOne() {
		return atLeastOne;
	}

	public void setAtLeastOne(boolean atLeastOne) {
		this.atLeastOne = atLeastOne;
	}
}
