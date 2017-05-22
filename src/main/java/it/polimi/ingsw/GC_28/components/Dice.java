package it.polimi.ingsw.GC_28.components;

import java.util.Random;

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
	
	@Override
	public String toString(){
		return "Color: "+this.getColor() + " Value: "+ this.getValue();
	}
}
