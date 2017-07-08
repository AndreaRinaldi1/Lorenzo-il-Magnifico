package it.polimi.ingsw.GC_28.components;

import java.util.concurrent.ThreadLocalRandom;

/**
 * This class allows to create a new dice. Every dice has a color (that can be orange, black or white), 
 * and a random value between 1 to 6. There are three dices for each game.
 * The dice is used to give the value to the family member with the same color.  
 * @author robertoturi
 * @version 1.0, 07/07/2017
 */
public class Dice {
	private DiceColor color;
	private int value;
	
	/**
	 * This constructor creates a dice out of its color
	 * @param color of the dice
	 */
	public Dice(DiceColor color){
		this.color = color;
	}

	/**
	 * @return the color of the selected dice
	 */
	public DiceColor getColor() {
		return color;
	}

	/**
	 * @return the value of the selected dice
	 */
	public int getValue() {
		return value;
	}

	/**
	 * This method sets a random value between 1 to 6 to the selected dice
	 */
	public void rollDice(){
		int randomInt = ThreadLocalRandom.current().nextInt(1, 7);
		this.value = randomInt;
	}
	
	/**
	 * This method allows to describe the selected dice with its color and its value
	 */
	@Override
	public String toString(){
		return "Color: "+this.getColor() + "Value: "+ this.getValue();
	}
}
