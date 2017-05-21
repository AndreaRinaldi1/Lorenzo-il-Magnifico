package it.polimi.ingsw.GC_28;

import java.awt.Color;
import java.util.ArrayList;

import effects.Effect;
import effects.HarvestEffect;

public class Territory extends Card{
	private ArrayList<Effect> immediateEffect = new ArrayList<Effect>();
	private HarvestEffect permanentEffect;
	
	public Territory(){
		super();
		this.setColor(Color.green);
	}

	public ArrayList<Effect> getImmediateEffect() {
		return immediateEffect;
	}

	public void setImmediateEffect(ArrayList<Effect> immediateEffect) {
		this.immediateEffect = immediateEffect;
	}

	public HarvestEffect getPermanentEffect() {
		return permanentEffect;
	}

	public void setPermanentEffect(HarvestEffect permanentEffect) {
		this.permanentEffect = permanentEffect;
	}

}
