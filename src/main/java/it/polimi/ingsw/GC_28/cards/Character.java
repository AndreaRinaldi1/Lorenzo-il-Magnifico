package it.polimi.ingsw.GC_28.cards;

import java.util.ArrayList;
import effects.Effect;

import java.awt.Color;

public class Character extends Card{
	private ArrayList<Effect> immediateEffect = new ArrayList<Effect>();
	private Effect permanentEffect;
	
	public Character(String name, int IDNumber, int era) {
		super(name, IDNumber, era);
		this.setColor(Color.blue);
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
