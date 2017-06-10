package it.polimi.ingsw.GC_28.effects;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.GC_28.components.DiceColor;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.model.Game;
import it.polimi.ingsw.GC_28.model.Player;

public class ReduceDiceEffect extends Effect{
	
	public final EffectType type = EffectType.REDUCEDICEEFFECT;
	
	private List<DiceColor> diceColor = new ArrayList<>();
	private int reduce;
	
	//FOR TEST
	public void setDiceColor(List<DiceColor> diceColor){
		this.diceColor = diceColor;
	}
	
	public void setReduce(int value){
		this.reduce = value;
	}
	@Override
	public void apply(Player player, Game game) {
		for(FamilyMember fm : player.getFamilyMembers()){
			for(DiceColor color : diceColor){
				if(fm.getDiceColor().equals(color)){
					fm.modifyValue(reduce);
				}
			}
		}
	
	}
	
}
