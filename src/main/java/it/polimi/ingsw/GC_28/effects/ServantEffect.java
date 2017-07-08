package it.polimi.ingsw.GC_28.effects;

import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.view.GameView;
/**
 * This class represent the effect that modifies the number of servants a player has to pay (by default is 1) 
 * in order to increment the action value of his/her action by the specified increment (by default is 1).
 * @author andreaRinaldi, nicoloScipione
 * @version 1.0, 07/04/2017
 */
public class ServantEffect  extends Effect{
	private int numberOfServant;
	private int increment;
	public final EffectType type = EffectType.SERVANTEFFECT;
	
	public void setNumberOfServant(int numberOfServant) {
		this.numberOfServant = numberOfServant;
	}

	public void setIncrement(int increment) {
		this.increment = increment;
	}
	
	public int getNumberOfServant() {
		return numberOfServant;
	}

	public int getIncrement() {
		return increment;
	}

	@Override
	public void apply(Player player, GameView game){

	}
	
	
}
