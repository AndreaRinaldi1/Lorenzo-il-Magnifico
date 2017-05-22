package components;
import java.awt.Color;

import model.Player;

public class FamilyMember {
	private Player Player;
	private Color DiceColor;
	private int value;
	private boolean used;
	
	public FamilyMember(Player player, Color diceColor){
		this.Player = player;
		this.DiceColor = diceColor;
	}

	public Player getPlayer() {
		return Player;
	}

	public Color getDiceColor() {
		return DiceColor;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}	
}
