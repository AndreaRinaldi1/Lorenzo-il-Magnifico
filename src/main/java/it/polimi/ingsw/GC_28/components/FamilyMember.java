package it.polimi.ingsw.GC_28.components;


import it.polimi.ingsw.GC_28.model.Player;

public class FamilyMember {
	private Player player;
	private int value;
	private boolean used;
	private final boolean neutral;
	private DiceColor diceColor;
	
	public FamilyMember(Player player, boolean neutral, DiceColor diceColor){
		this.player = player;
		this.neutral = neutral;
		this.diceColor = diceColor;
		if(this.isNeutral()){
			value = 0;
		}
	}
	
	public void setValue(int value){
		this.value = value;
	}

	public Player getPlayer() {
		return player;
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
	
	public String toString(){
		StringBuilder s = new StringBuilder();
		s.append(" Color: " + diceColor);
		s.append(" value: " + value);
		s.append(" used: " + used);
		return s.toString();
	}
}
