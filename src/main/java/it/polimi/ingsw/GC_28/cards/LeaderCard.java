package it.polimi.ingsw.GC_28.cards;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;

import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.effects.Effect;

public class LeaderCard implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private Resource resourceCost;
	private Map<CardType,Integer> cardCost;
	private Boolean played;
	private Boolean active;
	private Boolean permanent;
	
	private Effect effect;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Resource getResourceCost() {
		return resourceCost;
	}
	
	public void setResourceCost(Resource resourceCost) {
		this.resourceCost = resourceCost;
	}
	
	public Map<CardType, Integer> getCardCost() {
		return cardCost;
	}
	
	public void setCardCost(Map<CardType, Integer> cardCost) {
		this.cardCost = cardCost;
	}
	
	public Boolean getPlayed() {
		return played;
	}
	
	public void setPlayed(Boolean played) {
		this.played = played;
	}
	
	public Boolean getActive() {
		return active;
	}
	
	public void setActive(Boolean active) {
		this.active = active;
	}
	
	public Boolean getPermanent() {
		return permanent;
	}

	public void setPermanent(Boolean permanent) {
		this.permanent = permanent;
	}

	public Effect getEffect() {
		return effect;
	}
	
	public void setEffect(Effect effect) {
		this.effect = effect;
	}
	
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(name+" ");
		s.append("played: " + played + " active: " + active);
		return s.toString();
	}
	
	
}




















