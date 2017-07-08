package it.polimi.ingsw.GC_28.cards;

import it.polimi.ingsw.GC_28.effects.Effect;

/**
 * This class allows to create a new excommunication tile. Every excommunication tile has an era and an effect.
 * If the player at the end of the era hasn't enough faith point to avoid the excommunication, 
 * he receives a malus effect that is set on the excommunication tile that he takes.    
 * @author robertoTuri
 * @version 1.0, 07/06/2017
 */

public class ExcommunicationTile {
	private int era;
	private Effect effect;

	/**
	 * @return the era of the excommunication tile
	 */
	public int getEra() {
		return era;
	}

	/**
	 * @param era of the excommunication tile
	 */
	public void setEra(int era) {
		this.era = era;
	}

	/**
	 * @return the malus effect that is set for that excommunication tile
	 */
	public Effect getEffect() {
		return effect;
	}

	/**
	 * @param effect of the excommunication tile
	 */
	public void setEffect(Effect effect) {
		this.effect = effect;
	}
}
