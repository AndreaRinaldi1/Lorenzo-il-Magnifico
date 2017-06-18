package it.polimi.ingsw.GC_28.cards;

import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.effects.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Venture extends Card{
	private List<Effect> immediateEffect = new ArrayList<>();
	private ResourceEffect permanentEffect;
	private boolean alternativeCostPresence;
	private Resource alternativeCost;
	private int minimumRequiredMilitaryPoints;
	private Resource chosenCost;


	public Venture(String name, int idNumber, int era) {
		super(name, idNumber, era);
		this.setColor(Color.pink);
	}
	

	public Resource getChosenCost() {
		return chosenCost;
	}


	public void setChosenCost(Resource chosenCost) {
		this.chosenCost = chosenCost;
	}


	public List<Effect> getImmediateEffect() {
		return immediateEffect;
	}

	public void setImmediateEffect(List<Effect> immediateEffect) {
		this.immediateEffect = immediateEffect;
	}

	public ResourceEffect getPermanentEffect() {
		return permanentEffect;
	}

	public void setPermanentEffect(ResourceEffect permanentEffect) {
		this.permanentEffect = permanentEffect;
	}

	public boolean getAlternativeCostPresence() {
		return alternativeCostPresence;
	}

	public void setAlternativeCostPresence(boolean alternativeCostPresence) {
		this.alternativeCostPresence = alternativeCostPresence;
	}

	public Resource getAlternativeCost() {
		return alternativeCost;
	}

	public void setAlternativeCost(Resource alternativeCost) {
		this.alternativeCost = alternativeCost;
	}

	public int getMinimumRequiredMilitaryPoints() {
		return minimumRequiredMilitaryPoints;
	}

	public void setMinimumRequiredMilitaryPoints(int minimumRequiredMilitaryPoints) {
		this.minimumRequiredMilitaryPoints = minimumRequiredMilitaryPoints;
	}
		
}
