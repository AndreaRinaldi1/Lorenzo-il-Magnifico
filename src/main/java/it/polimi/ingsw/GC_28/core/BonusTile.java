package it.polimi.ingsw.GC_28.core;

import it.polimi.ingsw.GC_28.effects.HarvestEffect;
import it.polimi.ingsw.GC_28.effects.ProductionEffect;

public class BonusTile {
	private ProductionEffect productionEffect;
	private HarvestEffect harvestEffect;
	private static BonusTile instance;
	
	private BonusTile(){
	}
	
	public static BonusTile instance(){
		if(instance == null){
			instance = new BonusTile();
		}
		return instance;
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

	public static void setBonusTile(BonusTile bt){
		if(instance == null){
			instance = bt;
		}
	}
}