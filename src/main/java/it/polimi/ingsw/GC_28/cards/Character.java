package it.polimi.ingsw.GC_28.cards;

import it.polimi.ingsw.GC_28.effects.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Character extends Card{

	private List<Effect> immediateEffect = new ArrayList<>();
	private Effect permanentEffect;
	
	public Character(String name, int idNumber, int era) {
		super(name, idNumber, era);
		this.setColor(Color.blue);
	}

	public List<Effect> getImmediateEffect() {
		return immediateEffect;
	}

	public void setImmediateEffect(List<Effect> immediateEffect) {
		this.immediateEffect = immediateEffect;
	}

	public Effect getPermanentEffect() {
		return permanentEffect;
	}

	public void setPermanentEffect(Effect permanentEffect) {
		this.permanentEffect = permanentEffect;
	}
}
