package it.polimi.ingsw.GC_28.cards;

import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.effects.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is a subclass of the class "Card" and characterizes the venture cards (pink), 
 * with the immediate effect, an arraylist of effect that it's immediately activated when you take the card, 
 * and the permanent effect, it is an amount of victory point that will be added to the player's victory points at the end of the game.
 * Every venture card has also an resource's cost and, if the boolean value "alternativeCostPresence" is true, there is also
 * an alternative cost, it requires a minimum military point value to be chosen and reduces the player's military point
 * of alternative cost value. 
 * @author robertoTuri
 * @version 1.0, 07/06/2017
 */


public class Venture extends Card{
	private List<Effect> immediateEffect = new ArrayList<>();
	private ResourceEffect permanentEffect;
	private boolean alternativeCostPresence;
	private Resource alternativeCost;
	private int minimumRequiredMilitaryPoints;
	private Resource chosenCost;

	/**
	 * This constructor builds a venture card with the name, the ID number and the era of the card and it 
	 * sets pink as color of the card.  
	 * @param name of the card
	 * @param idNumber of the card
	 * @param era of the card
	 */
	public Venture(String name, int idNumber, int era) {
		super(name, idNumber, era);
		this.setColor(Color.pink);
	}
	
	/**
	 * @return the resource that the player has to pay to take the card
	 */
	public Resource getChosenCost() {
		return chosenCost;
	}

	/**
	 * @param chosenCost it is the resource that the player has to pay to take the card
	 */
	public void setChosenCost(Resource chosenCost) {
		this.chosenCost = chosenCost;
	}

	/**
	 * @return the list of effect that is set as immediate effect of the selected card
	 */
	public List<Effect> getImmediateEffect() {
		return immediateEffect;
	}

	/**
	 * @param immediateEffect it is the list of effect that is set as immediate effect of the selected card
	 */
	public void setImmediateEffect(List<Effect> immediateEffect) {
		this.immediateEffect = immediateEffect;
	}

	/**
	 * @return the permanent effect that is an amount of victory points that will be added to player's victory points at the end of the game
	 */
	public ResourceEffect getPermanentEffect() {
		return permanentEffect;
	}

	/**
	 * @param permanentEffect it is the amount of victory points that will be added to player's victory points
	 */
	public void setPermanentEffect(ResourceEffect permanentEffect) {
		this.permanentEffect = permanentEffect;
	}

	/**
	 * @return true if the selected venture card has two kind of costs, false if the venture card's cost is only the resource cost 
	 */
	public boolean getAlternativeCostPresence() {
		return alternativeCostPresence;
	}

	/**
	 * @param alternativeCostPresence set true if the selected venture card has two kind of costs, false if the venture card's cost is only the resource cost
	 */
	public void setAlternativeCostPresence(boolean alternativeCostPresence) {
		this.alternativeCostPresence = alternativeCostPresence;
	}

	/**
	 * @return the amount of military points that the player has to pay to take the card 
	 */
	public Resource getAlternativeCost() {
		return alternativeCost;
	}

	/**
	 * @param alternativeCost the amount of military points that the player has to pay to take the card
	 */
	public void setAlternativeCost(Resource alternativeCost) {
		this.alternativeCost = alternativeCost;
	}

	/**
	 * @return the minimum military points that the player has to possess to take the card
	 */
	public int getMinimumRequiredMilitaryPoints() {
		return minimumRequiredMilitaryPoints;
	}

	/**
	 * @param minimumRequiredMilitaryPoints it is the minimum military points that the player has to possess to take the card
	 */
	public void setMinimumRequiredMilitaryPoints(int minimumRequiredMilitaryPoints) {
		this.minimumRequiredMilitaryPoints = minimumRequiredMilitaryPoints;
	}
		
}
