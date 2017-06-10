package it.polimi.ingsw.GC_28.boards;

import it.polimi.ingsw.GC_28.effects.HarvestEffect;
import it.polimi.ingsw.GC_28.effects.ProductionEffect;

public class BonusTile {
	private ProductionEffect productionEffect;
	private HarvestEffect harvestEffect;
	
	public ProductionEffect getProductionEffect() {
		return productionEffect;
	}

	public void setProductionEffect(ProductionEffect productionEffect) {
		this.productionEffect = productionEffect;
	}

	public HarvestEffect getHarvestEffect() {
		return harvestEffect;
	}

	public void setHarvestEffect(HarvestEffect harvestEffect) {
		this.harvestEffect = harvestEffect;
	}
	
	@Override
	public String toString(){
		StringBuilder s = new StringBuilder();
		s.append("Production Bonus of bonusTile: \n");
		s.append(this.getProductionEffect().getResourceBonus().getResourceBonus().toString());
		s.append("Harvest Bonus of bonusTile: \n");
		s.append(this.getHarvestEffect().getResourceHarvestBonus().getResourceBonus().toString()+ "\n");
		return s.toString();
	}

}