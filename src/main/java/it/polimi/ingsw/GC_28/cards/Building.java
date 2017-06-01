package it.polimi.ingsw.GC_28.cards;
import java.awt.Color;

import it.polimi.ingsw.GC_28.effects.*;

public class Building extends Card{
	private ResourceEffect immediateEffect;
	private ProductionEffect permanentEffect;

	public Building(String name, int idNumber, int era) {
		super(name, idNumber, era);
		this.setColor(Color.yellow);
	}

	public ResourceEffect getImmediateEffect() {
		return immediateEffect;
	}

	public void setImmediateEffect(ResourceEffect immediateEffect) {
		this.immediateEffect = immediateEffect;
	}

	public ProductionEffect getPermanentEffect() {
		return permanentEffect;
	}

	public void setPermanentEffect(ProductionEffect permanentEffect) {
		this.permanentEffect = permanentEffect;
	}
	
	
}
