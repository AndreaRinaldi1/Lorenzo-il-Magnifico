package it.polimi.ingsw.GC_28.effects;

import it.polimi.ingsw.GC_28.cards.LeaderCard;
import it.polimi.ingsw.GC_28.client.ClientWriter;
import it.polimi.ingsw.GC_28.components.DiceColor;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.model.Game;
import it.polimi.ingsw.GC_28.model.Player;

public class SetFamilyMemberValueEffect extends Effect {
	private boolean colored;
	private int value;
	private EffectType type = EffectType.SETFAMILYMEMBERVALUEEFFECT;
	
	
	
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

	public void apply(Player player, ClientWriter writer){
		if(colored){//check if it's federico da monferrato effect
			for(LeaderCard lc : player.getLeaderCards()){
				if(lc.getName().equalsIgnoreCase("Federico da Montefeltro") && lc.getPlayed() && lc.getActive()){
					FamilyMember fm = writer.askFamilyMember();
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
	
	public void apply(FamilyMember familyMember, ClientWriter writer){
		this.apply(familyMember.getPlayer(), writer);
	}
}
