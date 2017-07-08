package it.polimi.ingsw.GC_28.cards;
import java.awt.Color;

import it.polimi.ingsw.GC_28.effects.*;

/**
 * This class is a subclass of the class "Card" and characterizes the building cards (yellow), 
 * with the immediate effect, a resource effect that it's immediately activated when you take the card, 
 * and the permanent effect, a production effect that it has an action value, activable only if
 * the value of the family member that is set on the production space is equal or greater than the 
 * action value of the permanent effect.
 * @author robertoTuri
 * @version 1.0, 07/04/2017
 */

public class Building extends Card{
	private ResourceEffect immediateEffect;
	private ProductionEffect permanentEffect;

	/**
	 * This constructor builds a building card with the name, the ID number and the era of the card and it 
	 * sets yellow as color of the card.  
	 * @param name of the card
	 * @param idNumber of the card
	 * @param era of the card
	 */
	public Building(String name, int idNumber, int era) {
		super(name, idNumber, era);
		this.setColor(Color.yellow);
	}

	/**
	 * @return the resource effect that is set as immediate effect 
	 */
	public ResourceEffect getImmediateEffect() {
		return immediateEffect;
	}

	/**
	 * @param immediateEffect, this is the resource effect that is set as immediate effect of the card
	 */
	public void setImmediateEffect(ResourceEffect immediateEffect) {
		this.immediateEffect = immediateEffect;
	}

	/**
	 * @return the production effect of the card
	 */
	public ProductionEffect getPermanentEffect() {
		return permanentEffect;
	}

	/**
	 * @param permanentEffect, this is the production effect that is set as permanent effect of the card
	 */
	public void setPermanentEffect(ProductionEffect permanentEffect) {
		this.permanentEffect = permanentEffect;
	}
	
	
}
