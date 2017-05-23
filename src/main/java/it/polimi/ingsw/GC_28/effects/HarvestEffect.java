package it.polimi.ingsw.GC_28.effects;

import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.boards.PlayerBoard;
import it.polimi.ingsw.GC_28.cards.*;
import it.polimi.ingsw.GC_28.components.CouncilPrivilege;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.model.Game;

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
	public void apply(PlayerBoard p, GameBoard gameBoard, Game game) {
		System.out.println("apply di HarvestEffect");
		
	}
}
