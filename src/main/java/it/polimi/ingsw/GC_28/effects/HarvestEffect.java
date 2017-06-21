package it.polimi.ingsw.GC_28.effects;

import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.boards.PlayerBoard;
import it.polimi.ingsw.GC_28.cards.*;
import it.polimi.ingsw.GC_28.components.CouncilPrivilege;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.view.GameView;

public class HarvestEffect extends Effect{
	private int harvestValue;
	private ResourceEffect resourceHarvestBonus;
	private PrivilegesEffect councilPrivilegeBonus;
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

	
	public ResourceEffect getResourceHarvestBonus() {
		return resourceHarvestBonus;
	}

	public void setResourceHarvestBonus(ResourceEffect resourceHarvestBonus) {
		this.resourceHarvestBonus = resourceHarvestBonus;
	}

	public PrivilegesEffect getCouncilPrivilegeBonus() {
		return councilPrivilegeBonus;
	}

	public void setCouncilPrivilegeBonus(PrivilegesEffect councilPrivilegeBonus) {
		this.councilPrivilegeBonus = councilPrivilegeBonus;
	}

	@Override
	public void apply(FamilyMember familyMember, GameView game) {
		System.out.println("apply di HarvestEffect");
		if(familyMember.getValue() >= harvestValue){
			if(councilPrivilegeBonus != null){
				councilPrivilegeBonus.apply(familyMember, game);
			}
			else{
				resourceHarvestBonus.apply(familyMember, game);
			}
		}
		else{
			System.out.println("You don't have the necessary action value to activate this effect");
		}
	}
}
