package it.polimi.ingsw.GC_28.effects;

import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.boards.PlayerBoard;
import it.polimi.ingsw.GC_28.cards.*;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.model.Game;

public class ProductionEffect extends Effect{
	private int productionValue;
	private Resource resourceBonus;
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

	public Resource getResourceBonus() {
		return resourceBonus;
	}

	public void setResourceBonus(Resource resourceBonus) {
		this.resourceBonus = resourceBonus;
	}


	public ExchangeEffect getExchangeBonus() {
		return exchangeBonus;
	}

	public void setExchangeBonus(ExchangeEffect exchangeBonus) {
		this.exchangeBonus = exchangeBonus;
	}
	

	@Override
	public void apply(FamilyMember familyMember, GameBoard gameBoard, Game game) {
		System.out.println("apply di ProductionEffect");
		if(resourceBonus != null){
			familyMember.getPlayer().getBoard().addResource(resourceBonus);
		}
		if(privilegeEffect != null){
			privilegeEffect.apply(familyMember, gameBoard, game);
		}
		if(multiplierEffect != null){
			multiplierEffect.apply(familyMember, gameBoard, game);
		}
		if(exchangeBonus != null){
			exchangeBonus.apply(familyMember, gameBoard, game);
		}
	}
}
