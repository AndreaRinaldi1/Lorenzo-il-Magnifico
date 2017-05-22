package it.polimi.ingsw.GC_28.effects;

import components.ResourceType;
import it.polimi.ingsw.GC_28.boards.PlayerBoard;

public class GoToHPEffect extends Effect{
	private int actionValue;
	private boolean production;
	private boolean harvest;
	public final EffectType type = EffectType.GOTOHP;

	public GoToHPEffect(){
		super();
	}

	public int getActionValue() {
		return actionValue;
	}

	public void setActionValue(int actionValue) {
		this.actionValue = actionValue;
	}

	public boolean isProduction() {
		return production;
	}

	public void setProduction(boolean production) {
		this.production = production;
	}

	public boolean isHarvest() {
		return harvest;
	}

	public void setHarvest(boolean harvest) {
		this.harvest = harvest;
	}
	
	@Override
	public void apply(PlayerBoard p){
		System.out.println("apply di GoToHPEffect");
	}
	
	
}
