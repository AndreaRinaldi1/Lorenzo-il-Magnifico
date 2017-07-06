package it.polimi.ingsw.GC_28.cards;

import java.util.Map;

import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.effects.Effect;

/**
 * This class allows to create a new leader card. Every leader card has a name; three boolean value 
 * and two cost's type.
 * The three boolean value are: "played", that reports if the selected leader card is played; "activate", that 
 * reports if the selected leader card is activated; "permanent", that reports if the effect of 
 * the selected leader card is valid for all the rest of the game.
 * One of the cost is expressed as a resource ("resourceCost"), the other one is expressed as a 
 * Map<CardType, Integer>, that is for each type of card, there is a number (cardCost).
 * To play the selected leader card, you must have the resources of the "resourceCost" 
 * or the number of the card asked, when the player decides to activate the leader card. 
 * The cost will not be scaled after activated the card.
 * The player can activate the card once for round, so the effect of the leader card is activated and, if the 
 * boolean value "permanent" is true, it is activated for all the rest of the game.
 * @author robertoturi
 * @version 1.0, 07/06/2017
 */


public class LeaderCard {
	
	private String name;
	private Resource resourceCost;
	private Map<CardType,Integer> cardCost;
	private Boolean played;
	private Boolean active;
	private Boolean permanent;
	
	private Effect effect;

	/**
	 * @return the name of the selected leader card
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param name of the selected leader card
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return the resource's cost of the selected leader card
	 */
	public Resource getResourceCost() {
		return resourceCost;
	}
	
	/**
	 * @param resourceCost of the selected leader card
	 */
	public void setResourceCost(Resource resourceCost) {
		this.resourceCost = resourceCost;
	}
	
	/**
	 * @return the cost's map of the selected leader card 
	 */
	public Map<CardType, Integer> getCardCost() {
		return cardCost;
	}
	
	/**
	 * @param cardCost of the selected leader card
	 */
	public void setCardCost(Map<CardType, Integer> cardCost) {
		this.cardCost = cardCost;
	}
	
	/**
	 * @return true if the selected leader card is played, false if it isn't 
	 */
	public Boolean getPlayed() {
		return played;
	}
	
	/**
	 * @param played set if the selected leader card is played or it isn't 
	 */
	public void setPlayed(Boolean played) {
		this.played = played;
	}
	
	/**
	 * @return true if the selected leader card is activated, false if it isn't
	 */
	public Boolean getActive() {
		return active;
	}
	
	/**
	 * @param active set if the selected leader card is activated or it isn't
	 */
	public void setActive(Boolean active) {
		this.active = active;
	}
	
	/**
	 * @return true if the selected leader card' effect is permanent, false if it isn't
	 */
	public Boolean getPermanent() {
		return permanent;
	}

	/**
	 * @param permanent set if the selected leader card's effect is permanent or it isn't
	 */
	public void setPermanent(Boolean permanent) {
		this.permanent = permanent;
	}

	/**
	 * @return the selected leader card's effect
	 */
	public Effect getEffect() {
		return effect;
	}
	
	/**
	 * @param effect of the selected leader card
	 */
	public void setEffect(Effect effect) {
		this.effect = effect;
	}
	
	/**
	 * It allows to describe the selected leader card with its name and with its boolean values "played" and "activate"
	 */
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(name+" ");
		s.append("played: " + played + " active: " + active);
		return s.toString();
	}
	
	
}




















