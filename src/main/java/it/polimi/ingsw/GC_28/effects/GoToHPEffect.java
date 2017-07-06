package it.polimi.ingsw.GC_28.effects;

import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.spaces.ProdHarvType;
import it.polimi.ingsw.GC_28.view.GameView;

/**
 * This class represent the effect that allow the player to go to the harvest or production space with the action value specified.
 * @author andreaRinaldi
 * @version 1.0, 07/04/2017
 */
public class GoToHPEffect extends Effect{
	private int actionValue;
	public final EffectType type = EffectType.GOTOHP;
	private ProdHarvType specificType;


	public GoToHPEffect(){
		super();
	}

	public int getActionValue() {
		return actionValue;
	}

	public void setActionValue(int actionValue) {
		this.actionValue = actionValue;
	}

	
	public ProdHarvType getSpecificType() {
		return specificType;
	}

	public void setSpecificType(ProdHarvType specificType) {
		this.specificType = specificType;
	}

	/**
	 * this method calls the usual method necessary to start an harvest or production action 
	 * @param player the current player 
	 * @param game the view of the game
	 */
	@Override
	public void apply(FamilyMember familyMember, GameView game){
		game.goToSpace(this);
	}
	
	/**
	 * this method calls the usual method necessary to start an harvest or production action 
	 * @param player the current player 
	 * @param game the view of the game
	 */
	@Override
	public void apply(Player player, GameView game) {
		game.goToSpace(this);
	}
	
	
	
}
