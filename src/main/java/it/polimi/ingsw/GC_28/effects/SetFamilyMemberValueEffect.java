package it.polimi.ingsw.GC_28.effects;

import it.polimi.ingsw.GC_28.cards.LeaderCard;
import it.polimi.ingsw.GC_28.components.DiceColor;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.view.GameView;

/**
 * This class represent the effect that change the value of family member for a given player.
 * @author nicoloScipione
 * @version 1.0, 07/07/2017
 */

public class SetFamilyMemberValueEffect extends Effect {
	private boolean colored;
	private int value;
	private EffectType type = EffectType.SETFAMILYMEMBERVALUEEFFECT;
	
	
	/**
	 * this methos check if the family member that will change value is related to a dice or not
	 * @return
	 */
	public boolean isColored() {
		return colored;
	}
	
	public void setColored(boolean colored) {
		this.colored = colored;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	public EffectType getType() {
		return type;
	}
	
	/**
	 * This method apply the this effect. Before apply, it checks if it checks the necessary attributes for the leader card. 
	 * 
	 */
	@Override
	public void apply(Player player, GameView game){
		if(colored){//check if it's federico da monferrato effect
			for(LeaderCard lc : player.getLeaderCards()){
				if(lc.getName().equalsIgnoreCase("Federico da Montefeltro") && lc.getPlayed() && lc.getActive()){
					FamilyMember fm = game.askFamilyMember();
					fm.setValue(value);
					return;
				}
			}
		}
		if(colored){
			for(FamilyMember fm : player.getFamilyMembers()){
				if(!(fm.getDiceColor().equals(DiceColor.NEUTRAL))){
					fm.setValue(value);
				}
			}
		}else{
			for(FamilyMember fm : player.getFamilyMembers()){
				if(fm.getDiceColor().equals(DiceColor.NEUTRAL)){
					fm.setValue(value);
				}
			}
		}
	}
	
	@Override
	public void apply(FamilyMember familyMember, GameView game){
		this.apply(familyMember.getPlayer(), game);
	}
}
