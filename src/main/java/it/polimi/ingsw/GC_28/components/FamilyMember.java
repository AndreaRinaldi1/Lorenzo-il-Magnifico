package it.polimi.ingsw.GC_28.components;


import it.polimi.ingsw.GC_28.model.Player;

public class FamilyMember {
	private Player Player;
	private int value;
	private boolean used;
	private final boolean NEUTRAL;
	private DiceColor diceColor;
	
	public FamilyMember(Player player, boolean neutral, DiceColor diceColor){
		this.Player = player;
		this.NEUTRAL = neutral;
		this.diceColor = diceColor;
		if(this.NEUTRAL == true){
			value = 0;
		}
	}

	public Player getPlayer() {
		return Player;
	}
	
	public boolean isNEUTRAL() {
		return NEUTRAL;
	}

	public int getValue() {
		return value;
	}
	
	public void incrementValue(int amount){
		this.value += amount;
	}
	
	
	public void setValue(Dice[] dices) {
		for(Dice dice : dices){
			if(dice.getColor() == this.diceColor){
				this.value = dice.getValue();
			}
		}
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}	
}
