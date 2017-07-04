package it.polimi.ingsw.GC_28.cards;

import it.polimi.ingsw.GC_28.effects.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is a subclass of the class "Card" and it characterizes the character cards, the blue ones, 
 * with the immediate effect, a list of effect that immediately it is activated when you take the card, 
 * and the permanent effect, an effect that it has activate for all the rest of the game.
 * @author robertoturi
 * @version 1.0, 07/04/2017
 */


public class Character extends Card{

	private List<Effect> immediateEffect = new ArrayList<>();
	private Effect permanentEffect;
	
	/**
	 * This constructor builds a character card with the name, the ID number and the era of the card and it 
	 * sets blue as color of the card.  
	 * @param name of the card
	 * @param idNumber of the card
	 * @param era of the card
	 */
	public Character(String name, int idNumber, int era) {
		super(name, idNumber, era);
		this.setColor(Color.blue);
	}

	/**
	 * @return the list of effect that is set as immediate effect of the card
	 */
	public List<Effect> getImmediateEffect() {
		return immediateEffect;
	}

	/**
	 * @param immediateEffect, that is the list of the effect that is the immmediate effect of the card
	 */
	public void setImmediateEffect(List<Effect> immediateEffect) {
		this.immediateEffect = immediateEffect;
	}

	/**
	 * @return the permanent effect of the card
	 */
	public Effect getPermanentEffect() {
		return permanentEffect;
	}

	/**
	 * @param permanentEffect, it is the effect that it is activate for all the rest of the game
	 */
	public void setPermanentEffect(Effect permanentEffect) {
		this.permanentEffect = permanentEffect;
	}
}
