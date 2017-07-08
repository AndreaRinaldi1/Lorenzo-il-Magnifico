package it.polimi.ingsw.GC_28.components;

import it.polimi.ingsw.GC_28.model.Player;

/**
 * This class allows to create a new pawn, called "family member". Every family member has an value, 
 * a color, two boolean values and the player to whom it belongs.
 * 
 * the value is taken  
 * @author robertoturi
 * @version 1.0, 07/07/2017
 */

public class FamilyMember {
	private Player player;
	private int value;
	private boolean used;
	private final boolean neutral;
	private DiceColor diceColor;
	
	/**
	 * This constructor creates a new family member out of the player to whom it belongs, 
	 * the boolean value "neutral" and the dice's color.  
	 * @param player to whom the family member belongs
	 * @param neutral true is the color of the family member is neutral, false otherwise
	 * @param diceColor the color of the family member 
	 */
	public FamilyMember(Player player, boolean neutral, DiceColor diceColor){
		this.player = player;
		this.neutral = neutral;
		this.diceColor = diceColor;
		if(this.neutral){
			value = 0;
		}
	}
	
	/**
	 * @param value of the selected family member
	 */
	public void setValue(int value){
		this.value = value;
	}

	/**
	 * @return the player to whom the family member belongs
	 */
	public Player getPlayer() {
		return player;
	}
	
	/**
	 * @return true is the color of the family member is neutral, false otherwise
	 */
	public boolean isNeutral() {
		return neutral;
	}

	/**
	 * @return the value of the selected family member
	 */
	public Integer getValue() {
		return value;
	}
	
	/**
	 * This method adds or decreases the value of the selected family member
	 * @param amount it is the amount to add or to reduce to the selected family member
	 */
	public void modifyValue(int amount){
		this.value += amount;
	}
	
	/**
	 * This method sets the family member's value with the dice's value with the same color
	 * @param dices it is an array with the value of the three dices
	 */
	public void setValue(Dice[] dices) {
		for(Dice dice : dices){
			if(dice.getColor() == this.diceColor){
				this.value = dice.getValue();
			}
		}
	}

	/**
	 * @return true if the selected family member is used by the player, false otherwise
	 */
	public boolean isUsed() {
		return used;
	}

	/**
	 * @param used true if the selected family member is used by the player, false otherwise
	 */
	public void setUsed(boolean used) {
		this.used = used;
	}	
	
	/**
	 * @return the color of the selected family member
	 */
	public DiceColor getDiceColor(){
		return this.diceColor;
	}
	
}
