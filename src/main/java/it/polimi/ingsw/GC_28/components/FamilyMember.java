package it.polimi.ingsw.GC_28.components;


import it.polimi.ingsw.GC_28.model.Player;

public class FamilyMember {
	private Player Player;
	private int value;
	private boolean used;
	private final boolean neutral;
	private DiceColor diceColor;
	
	public FamilyMember(Player player, boolean neutral, DiceColor diceColor){
		this.Player = player;
		this.neutral = neutral;
		this.diceColor = diceColor;
		if(this.neutral){
			value = 0;
		}
	}
	
	public void setValue(int value){
		this.value = value;
	}

	public Player getPlayer() {
		return Player;
	}
	
	public boolean isNeutral() {
		return neutral;
	}

	public Integer getValue() {
		return value;
	}
	
	public void modifyValue(int amount){
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
	
	public DiceColor getDiceColor(){
		return this.diceColor;
	}
}
