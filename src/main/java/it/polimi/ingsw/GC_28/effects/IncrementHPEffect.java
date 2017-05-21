package it.polimi.ingsw.GC_28.effects;

public class IncrementHPEffect extends Effect{
	private int increment;
	private boolean production;
	private boolean harvest;
	public final EffectType type = EffectType.INCREMENTHPEFFECT;

	public IncrementHPEffect(){
		super();
	}

	public int getIncrement() {
		return increment;
	}

	public void setIncrement(int increment) {
		this.increment = increment;
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
	
	
}
