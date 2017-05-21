package it.polimi.ingsw.GC_28;

import cards.Card;
import effects.ProductionEffect;
import effects.ResourceEffect;

public class Building extends Card{
	private ResourceEffect immediateEffect;
	private ProductionEffect permanentEffect;
	
	public Building (){
		super();
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
