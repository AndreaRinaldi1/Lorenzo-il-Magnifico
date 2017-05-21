package cards;
import java.awt.Color;

import effects.*;

public class Building extends Card{
	private ResourceEffect immediateEffect;
	private ProductionEffect permanentEffect;

	public Building(String name, int IDNumber, int era) {
		super(name, IDNumber, era);
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
