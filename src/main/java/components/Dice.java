package components;

import java.awt.Color;
import java.util.Random;

public class Dice {
	private Color color;
	private int value;
	
	public Dice(Color color){
		this.color = color;
	}

	public Color getColor() {
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
