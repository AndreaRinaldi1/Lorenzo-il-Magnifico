package it.polimi.ingsw.GC_28.effects;

import components.Resource;
import it.polimi.ingsw.GC_28.boards.PlayerBoard;
import it.polimi.ingsw.GC_28.cards.*;

public class MultiplierEffect extends Effect{
	private Resource resourceBonus;
	private CardType cardType;
	private Resource resourceCost;
	
	public final EffectType type = EffectType.MULTIPLIEREFFECT;

	public MultiplierEffect(){
		super();
	}
	
	public Resource getResourceCost() {
		return resourceCost;
	}

	public void setResourceCost(Resource resourceCost) {
		this.resourceCost = resourceCost;
	}

	

	public Resource getResourceBonus() {
		return resourceBonus;
	}

	public void setResourceBonus(Resource resourceBonus) {
		this.resourceBonus = resourceBonus;
	}

	public CardType getCardType() {
		return cardType;
	}

	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}
	
	@Override
	public void apply(PlayerBoard p){
		System.out.println("apply di MultiplierEffect");
	}
	
}
