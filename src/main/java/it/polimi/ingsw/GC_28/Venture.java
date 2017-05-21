package it.polimi.ingsw.GC_28;

import java.util.ArrayList;

import effects.Effect;
import effects.ResourceEffect;

public class Venture extends Card{
	private ArrayList<Effect> immediateEffect = new ArrayList<Effect>();
	private ResourceEffect permanentEffect;
	private boolean alternativeCostPresence;
	private Resource alternativeCost;
	private int minimumRequiredMilitaryPoints;
	
	public Venture(){
		super();
	}

	public ArrayList<Effect> getImmediateEffect() {
		return immediateEffect;
	}

	public void setImmediateEffect(ArrayList<Effect> immediateEffect) {
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
