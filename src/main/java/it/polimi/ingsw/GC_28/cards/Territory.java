package it.polimi.ingsw.GC_28.cards;
import it.polimi.ingsw.GC_28.effects.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is a subclass of the class "Card" and characterizes the territory cards (green), 
 * with the immediate effect, an arraylist of effect that it's immediately activated when you take the card, 
 * and the permanent effect, an harvest effect that it has an action value, activable only if
 * the value of the family member that is set on the harvest space is equal or greater than the 
 * action value of the permanent effect.
 * @author robertoTuri
 * @version 1.0, 07/06/2017
 */

public class Territory extends Card{
	private List<Effect> immediateEffect = new ArrayList<>();
	private HarvestEffect permanentEffect;
	
	/**
	 * This constructor builds a territory card with the name, the ID number and the era of the card and it 
	 * sets green as color of the card.  
	 * @param name of the card
	 * @param idNumber of the card
	 * @param era of the card
	 */
	public Territory(String name, int idNumber, int era){
		super(name, idNumber, era);
		this.setColor(Color.green);
	}

	/**
	 * @return the list of effects that is set as immediate effect
	 */
	public List<Effect> getImmediateEffect() {
		return immediateEffect;
	}

	/**
	 * @param immediateEffect it is the list of effects 
	 */
	public void setImmediateEffect(List<Effect> immediateEffect) {
		this.immediateEffect = immediateEffect;
	}

	/**
	 * @return the permanent effect of the card
	 */
	public HarvestEffect getPermanentEffect() {
		return permanentEffect;
	}

	/**
	 * @param permanentEffect it is an harvest effect 
	 */
	public void setPermanentEffect(HarvestEffect permanentEffect) {
		this.permanentEffect = permanentEffect;
	}

}
