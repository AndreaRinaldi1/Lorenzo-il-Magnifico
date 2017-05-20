package it.polimi.ingsw.GC_28.cards;

import java.util.ArrayList;
import java.awt.Color;

import effects.*;

public class Territory extends Card{
	private ArrayList<Effect> immediateEffect = new ArrayList<>();
	private HarvestEffect permanentEffect;
	
	public Territory(String name, int IDNumber, int era, CardType cardType){
		super(name, IDNumber, era, cardType);
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
