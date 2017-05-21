package it.polimi.ingsw.GC_28.effects;

import it.polimi.ingsw.GC_28.cards.*;
import it.polimi.ingsw.GC_28.core.PlayerBoard;

public class HarvestEffect extends Effect{
	private int harvestValue;
	private Resource resourceHarvestBonus;
	private CouncilPrivilege councilPrivilegeBonus;
	public final EffectType type = EffectType.HARVESTEFFECT;

	public HarvestEffect() {
		super();
	}

	public int getHarvestValue() {
		return harvestValue;
	}

	public void setHarvestValue(int harvestValue) {
		this.harvestValue = harvestValue;
	}

	public Resource getResourceHarvestBonus() {
		return resourceHarvestBonus;
	}

	public void setResourceHarvestBonus(Resource resourceHarvestBonus) {
		this.resourceHarvestBonus = resourceHarvestBonus;
	}

	public CouncilPrivilege getCouncilPrivilegeBonus() {
		return councilPrivilegeBonus;
	}

	public void setCouncilPrivilegeBonus(CouncilPrivilege councilPrivilegeBonus) {
		this.councilPrivilegeBonus = councilPrivilegeBonus;
	}
	
	@Override
	public void apply(PlayerBoard p){
		System.out.println("apply di HarvestEffect");
	}
}
