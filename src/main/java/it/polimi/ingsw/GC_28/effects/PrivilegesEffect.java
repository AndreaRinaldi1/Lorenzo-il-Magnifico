package it.polimi.ingsw.GC_28.effects;

import java.util.List;

import it.polimi.ingsw.GC_28.components.*;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.view.GameView;
/**
 * This class represent the effect that allows to increase the player resources according to his council privilege decision.
 * A defined constraint is, if more than one council privilege is present, if they must be different one another or not necessarily. 
 * @author andreaRinaldi
 * @version 1.0, 07/04/2017
 */
public class PrivilegesEffect extends Effect{
	private int numberOfCouncilPrivileges;
	private boolean different;

	public final EffectType type = EffectType.PRIVILEGESEFFECT;

	public PrivilegesEffect(){
		super();
	}

	public boolean isDifferent() {
		return different;
	}

	public void setDifferent(boolean different) {
		this.different = different;
	}

	public int getNumberOfCouncilPrivileges() {
		return numberOfCouncilPrivileges;
	}

	public void setNumberOfCouncilPrivileges(int numberOfCouncilPrivileges) {
		this.numberOfCouncilPrivileges = numberOfCouncilPrivileges;
	}
	
	@Override
	public void apply(FamilyMember familyMember, GameView game) {
		List<Character> choices = game.askPrivilege(numberOfCouncilPrivileges, different);
		for(int i = 0; i < choices.size(); i++){
			familyMember.getPlayer().addResource(game.checkResourceExcommunication(CouncilPrivilege.instance().getOptions().get(choices.get(i))));
		}
	}
	
	/**
	 * This method asks the player for the council privilege he/she is desire to take and than increment his/her resource of the specific amount.
	 */
	@Override
	public void apply(Player player, GameView game) {
		List<Character> choices = game.askPrivilege(numberOfCouncilPrivileges, different);
		for(int i = 0; i<choices.size(); i++ ){
			player.addResource(game.checkResourceExcommunication(CouncilPrivilege.instance().getOptions().get(choices.get(i))));
		}
	}
}
