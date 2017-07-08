package it.polimi.ingsw.GC_28.effects;

import it.polimi.ingsw.GC_28.components.FamilyMember;

import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.view.GameView;
/**
 * This class represent several different effects that we just need to know if the player is, in some way related with 
 * (either through cards effect or excommunications), and, therefore, we are just interested in their effective precesence
 * other than their effect, that must be specified elsewhere, because too specific.
 * @author andreaRinaldi, nicoloScipione
 * @version 1.0, 07/04/2017
 */
public class OtherEffect extends Effect{
	public final EffectType type = EffectType.OTHEREFFECT;
	public EffectType specificType;


	public OtherEffect(){
		super();
	}
	

	public void setType(EffectType type) {
		this.specificType = type;
	}	
	
	
	
	public EffectType getType() {
		return specificType;
	}


	@Override
	public void apply(FamilyMember familyMember, GameView game) {
		
	}
	
	@Override
	public void apply(Player player, GameView game) {
		super.apply(player, game);
	}
}
