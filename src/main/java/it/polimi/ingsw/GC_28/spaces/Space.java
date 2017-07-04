package it.polimi.ingsw.GC_28.spaces;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.view.GameView;

/**
 * This class represent all common methods and attributes of game spaces.
 * @author nicoloscipione, andrearinaldi
 * @version 1.0, 03/07/2017
 *
 */

public abstract class Space {
	private boolean free;
	private List<FamilyMember> players = new ArrayList<>();
	private int actionValue;
	
	public Space(){}
	
	/**
	 * The constructor create the space setting space's value and the status(free or not). 
	 * @param free. Boolean that indicate the status
	 * @param actionValue. int representing the action value necessary to occupy the space.
	 */
	public Space(boolean free, int actionValue){
		this.free = free;
		this.actionValue = actionValue;
	}
	
	/**
	 * 
	 * @return a boolean,true if the space is free, false otherwise.
	 */
	public boolean isFree() {
		return free;
	}
	
	/**
	 * This method set space' status.
	 * @param free. Space' status(free or not).
	 */
	public void setFree(boolean free) {
		this.free = free;
	}

	/**
	 * This method return a list of all player presence in a space.
	 * @return List of player presence.
	 */
	public List<FamilyMember> getPlayer() {
		return players;
	}
	
	/**
	 * This method a player to a space,if it's possibile and  changing the space'status.
	 * @param player. Family member that represent the player to add.
	 */
	public void addPlayer(FamilyMember player){
		if(this.isFree() == true){
			this.players.add(player);
			this.free = false;
		}
	}

	/**
	 * This method get the action value necessary to occupy the space.
	 * @return an int equals to the value.
	 */
	public int getActionValue() {
		return actionValue;
	}
	
	/**
	 * This method set the action value necessary to occupy a space.
	 * @param actionValue
	 */
	public void setActionValue(int actionValue) {
		this.actionValue = actionValue;
	} 
	
	
	public void applyBonus(GameView game, FamilyMember familyMember){}
}
