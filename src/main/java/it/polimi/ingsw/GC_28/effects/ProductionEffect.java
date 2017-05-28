package it.polimi.ingsw.GC_28.effects;

import it.polimi.ingsw.GC_28.boards.GameBoard;

import it.polimi.ingsw.GC_28.components.FamilyMember;

import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.model.Game;

public class ProductionEffect extends Effect{
	private int productionValue;
	private ResourceEffect resourceBonus;
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
		return resourceBonus;
	}

	public void setResourceBonus(ResourceEffect resourceBonus) {
		this.resourceBonus = resourceBonus;
	}

	public ExchangeEffect getExchangeBonus() {
		return exchangeBonus;
	}

	public void setExchangeBonus(ExchangeEffect exchangeBonus) {
		this.exchangeBonus = exchangeBonus;
	}
	

	@Override
	public void apply(FamilyMember familyMember, Game game) {
		System.out.println("apply di ProductionEffect");
		if(familyMember.getValue() >= productionValue){
			if(resourceBonus != null){
				resourceBonus.apply(familyMember, game);
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
		else{
			System.out.println("You don't have the necessary action value to activate this effect");
		}
		
	}
}
