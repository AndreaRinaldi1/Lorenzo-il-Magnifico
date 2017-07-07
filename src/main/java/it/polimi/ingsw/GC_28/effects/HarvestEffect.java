package it.polimi.ingsw.GC_28.effects;


import it.polimi.ingsw.GC_28.components.FamilyMember;

import it.polimi.ingsw.GC_28.view.GameView;
/**
 * This class represent the effect that is exploited when a player goes to harvest.
 * @author andreaRinaldi
 * @version 1.0, 07/04/2017
 */
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

	/**
	 * This method controls if the action value of the player is high enough to activate a specific card effect. If so, it either
	 * activate a privileges effect (asks for the desired privilege) or resource effect (increases player resources).
	 */
	@Override
	public void apply(FamilyMember familyMember, GameView game) {
		if(familyMember.getValue() >= harvestValue){
			if(councilPrivilegeBonus != null){
				councilPrivilegeBonus.apply(familyMember, game);
			}
			else{
				resourceHarvestBonus.apply(familyMember, game);
			}
		}
	}
}
