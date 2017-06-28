package it.polimi.ingsw.GC_28.boards;

import it.polimi.ingsw.GC_28.cards.*;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.components.Resource;

/**
 * This class represent the cell that make up the towers. Every cell has a resource bonus associated, a card
 * and it's where the player gets located once it decides to take the card that is placed here
 * @author andrearinadli, nicoloscipione, robertoturi
 * @version 1.0, 06/28/2017
 * @see Resource, FamilyMember
 */

public class Cell {
	private boolean free;
	private FamilyMember player;
	private Card card;
	private int actionValue;
	private Resource bonus;
	
	/**
	 * This constructor builds a cell out of the resource bonus, its action value, and the attribute free
	 * @param bonus the resource the player gets if it decides to take the card that is placed in this cell
	 * @param actionValue the minimum action value necessary to take the card that is placed in this cell
	 * @param free this attribute indicates if there is already another player in this cell or not
	 */
	public Cell(Resource bonus, int actionValue, boolean free){
		this.bonus = bonus;
		this.actionValue = actionValue;
		this.free = free;
	}

	/**
	 * @return true if the cell is free, that is there is not another player here, false otherwise
	 */
	public boolean isFree() {
		return free;
	}

	/**
	 * @param free set the cell free (usually at the end of a period), or occupied when a player take the card in this cell
	 */
	public void setFree(boolean free) {
		this.free = free;
	}

	/**
	 * @return the family member of the player that is currently placed in this cell
	 */
	public FamilyMember getFamilyMember() {
		return player;
	}
	
	/**
	 * @param player the family member of the player that we want to place in this cell
	 */
	public void setFamilyMember(FamilyMember player) {
		this.player = player;
	}

	/**
	 * @return the card that is placed in this cell, null if there is no card
	 */
	public Card getCard(){
		if(card == null){
			return null;
		}
		return card;
	}

	/**
	 * @param card the card that we want to place in this cell
	 */
	public void setCard(Card card) {
		this.card = card;
	}

	/**
	 * @return the minimum action value necessary to take the card that is placed in this cell
	 */
	public int getActionValue() {
		return actionValue;
	}

	/**
	 * @return the resource the player gets if it decides to take the card that is placed in this cell
	 */
	public Resource getBonus() {
		return bonus;
	}


}
