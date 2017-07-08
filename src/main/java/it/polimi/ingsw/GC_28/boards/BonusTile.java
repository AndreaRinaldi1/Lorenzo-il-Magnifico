package it.polimi.ingsw.GC_28.boards;

import it.polimi.ingsw.GC_28.effects.HarvestEffect;
import it.polimi.ingsw.GC_28.effects.ProductionEffect;

/**
 * This class represent the Bonus Tile that has to be appended to the playerboard during the game. 
 * It is equipped with a production and an harvest effect that are applied when the player goes to the production and harvest space respectively.
 * @author nicoloScipione
 * @version 1.0, 06/28/2017
 * @see ProductionEffect, HarvestEffect
 */

public class BonusTile {
	private ProductionEffect productionEffect;
	private HarvestEffect harvestEffect;
	
	/**
	 * @return The effect to apply when the player goes to production space
	 */
	public ProductionEffect getProductionEffect() {
		return productionEffect;
	}

	/**
	 * @param productionEffect The effect to apply when the player goes to production space
	 */
	public void setProductionEffect(ProductionEffect productionEffect) {
		this.productionEffect = productionEffect;
	}

	/**
	 * @return The effect to apply when the player goes to harvest space
	 */
	public HarvestEffect getHarvestEffect() {
		return harvestEffect;
	}

	/**
	 * @param harvestEffect The effect to apply when the player goes to harvest space
	 */
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