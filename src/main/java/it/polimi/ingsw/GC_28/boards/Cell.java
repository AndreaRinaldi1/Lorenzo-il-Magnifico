package it.polimi.ingsw.GC_28.boards;

import java.io.Serializable;

import it.polimi.ingsw.GC_28.cards.*;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.components.Resource;


public class Cell  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean free;
	private FamilyMember player;
	private Card card;
	private int actionValue;
	private Resource bonus;
	
	public Cell(Resource bonus, int actionValue, boolean free){
		this.bonus = bonus;
		this.actionValue = actionValue;
		this.free = free;
	}

	public boolean isFree() {
		return free;
	}

	public void setFree(boolean free) {
		this.free = free;
	}

	public FamilyMember getFamilyMember() {
		return player;
	}

	public void setFamilyMember(FamilyMember player) {
		this.player = player;
	}

	public Card getCard(){
		if(card == null){
			return null;
		}
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	public int getActionValue() {
		return actionValue;
	}

	public void setActionValue(int actionValue) {
		this.actionValue = actionValue;
	}

	public Resource getBonus() {
		return bonus;
	}

	public void setBonus(Resource bonus) {
		this.bonus = bonus;
	}
}
