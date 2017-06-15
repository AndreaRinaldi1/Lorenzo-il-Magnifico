package it.polimi.ingsw.GC_28.cards;

import java.io.Serializable;

import it.polimi.ingsw.GC_28.effects.Effect;

public class ExcommunicationTile implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int era;
	private Effect effect;
	
	public ExcommunicationTile(){
		
	}

	public int getEra() {
		return era;
	}

	public void setEra(int era) {
		this.era = era;
	}

	public Effect getEffect() {
		return effect;
	}

	public void setEffect(Effect effect) {
		this.effect = effect;
	}
}
