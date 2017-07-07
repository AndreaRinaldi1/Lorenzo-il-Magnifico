package it.polimi.ingsw.GC_28.effects;


import it.polimi.ingsw.GC_28.components.FamilyMember;

import it.polimi.ingsw.GC_28.view.GameView;
/**
 * This class represent the effect that is exploited when a player goes to production.
 * @author andreaRinaldi
 * @version 1.0, 07/04/2017
 */
public class ProductionEffect extends Effect{
	private int productionValue;
	private ResourceEffect resourceProductionBonus;
	private ExchangeEffect exchangeBonus;
	private MultiplierEffect multiplierEffect;
	private PrivilegesEffect privilegeEffect;
	public final EffectType type = EffectType.PRODUCTIONEFFECT;

	
	public ProductionEffect(){
		super();
	}

	public PrivilegesEffect getPrivilegeEffect() {
		return privilegeEffect;
	}

	public void setPrivilegeEffect(PrivilegesEffect privilegeEffect) {
		this.privilegeEffect = privilegeEffect;
	}

	public MultiplierEffect getMultiplierEffect() {
		return multiplierEffect;
	}

	public void setMultiplierEffect(MultiplierEffect multiplierEffect) {
		this.multiplierEffect = multiplierEffect;
	}


	public int getProductionValue() {
		return productionValue;
	}

	public void setProductionValue(int productionValue) {
		this.productionValue = productionValue;
	}


	public ResourceEffect getResourceBonus() {
		return resourceProductionBonus;
	}

	public void setResourceBonus(ResourceEffect resourceBonus) {
		this.resourceProductionBonus = resourceBonus;
	}

	public ExchangeEffect getExchangeBonus() {
		return exchangeBonus;
	}

	public void setExchangeBonus(ExchangeEffect exchangeBonus) {
		this.exchangeBonus = exchangeBonus;
	}
	

	/**
	 * This method controls if the action value of the player is high enough to activate a specific card effect. If so, it either
	 * activate a privileges effect (asks for the desired privilege) or resource effect (increases player resources), or 
	 * an exchange effect or a multiplier effect, according to the specific Building card permanent effect.
	 */
	@Override
	public void apply(FamilyMember familyMember, GameView game) {
		if(familyMember.getValue() >= productionValue){
			if(resourceProductionBonus != null){
				resourceProductionBonus.apply(familyMember, game);
			}
			if(privilegeEffect != null){
				privilegeEffect.apply(familyMember, game);
			}
			if(multiplierEffect != null){
				multiplierEffect.apply(familyMember, game);
			}
			if(exchangeBonus != null){
				exchangeBonus.apply(familyMember, game);
			}
		}
	}
}
