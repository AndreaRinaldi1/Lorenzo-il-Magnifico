package it.polimi.ingsw.GC_28;

import java.util.ArrayList;

import effects.Effect;

public class Character extends Card{
	private ArrayList<Effect> immediateEffect = new ArrayList<Effect>();
	private Effect permanentEffect;
	
	public Character(){
		super();
	}

	public ArrayList<Effect> getImmediateEffect() {
		return immediateEffect;
	}

	public void setImmediateEffect(ArrayList<Effect> immediateEffect) {
		this.immediateEffect = immediateEffect;
	}

	public Effect getPermanentEffect() {
		return permanentEffect;
	}

	public void setPermanentEffect(Effect permanentEffect) {
		this.permanentEffect = permanentEffect;
	}
}
