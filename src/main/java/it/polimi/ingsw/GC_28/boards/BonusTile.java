package it.polimi.ingsw.GC_28.boards;

import it.polimi.ingsw.GC_28.effects.HarvestEffect;
import it.polimi.ingsw.GC_28.effects.ProductionEffect;

public class BonusTile {
	private ProductionEffect productionEffect;
	private HarvestEffect harvestEffect;
	
	public BonusTile(){
		
	}
	
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

}