package it.polimi.ingsw.GC_28.components;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Dice {
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

	public void setValue() {
		this.value = new Random().nextInt(6) + 1;
	}
	public void rollDice(){
		int randomInt = ThreadLocalRandom.current().nextInt(1, 7);
		this.value = randomInt;
	}
	
	@Override
	public String toString(){
		return "Color: "+this.getColor() + " Value: "+ this.getValue();
	}
}
