package it.polimi.ingsw.GC_28.effects;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.GC_28.components.DiceColor;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.view.GameView;
/**
 * This class represent the effect that allows to increment (or reducing, for excommunications)
 * the value of the dice for a specific player and, as a consequence, the value of the corresponding family members. 
 * @author andreaRinaldi, nicoloScipione
 * @version 1.0, 07/04/2017
 */
public class ModifyDiceEffect extends Effect{
	
	public final EffectType type = EffectType.MODIFYDICEEFFECT;
	
	private List<DiceColor> diceColor = new ArrayList<>();
	private int reduce;
	
	//FOR TEST
	public void setDiceColor(List<DiceColor> diceColor){
		this.diceColor = diceColor;
	}
	
	public void setReduce(int reduce){
		this.reduce = reduce;
	}
	
	/**
	 * This method applies the effect modifying the value of the family members of the colors specified by the effect
	 * by the value specified by the effect.
	 */
	@Override
	public void apply(Player player, GameView game) {
		for(FamilyMember fm : player.getFamilyMembers()){
			for(DiceColor color : diceColor){
				if(fm.getDiceColor().equals(color)){
					fm.modifyValue(reduce);
				}
			}
		}	
	}
	
}
