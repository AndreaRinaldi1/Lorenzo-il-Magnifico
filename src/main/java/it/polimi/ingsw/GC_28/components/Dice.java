package it.polimi.ingsw.GC_28.components;

import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;

public class Dice implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DiceColor color;
	private int value;
	
	public Dice(DiceColor color){
		this.color = color;
	}

	public DiceColor getColor() {
		return color;
	}

	public int getValue() {
		return value;
	}

	
	public void rollDice(){
		int randomInt = ThreadLocalRandom.current().nextInt(1, 7);
		this.value = randomInt;
	}
	
	@Override
	public String toString(){
		return "Color: "+this.getColor() + "Value: "+ this.getValue();
	}
}
