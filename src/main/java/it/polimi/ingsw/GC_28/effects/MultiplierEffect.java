package it.polimi.ingsw.GC_28.effects;

import it.polimi.ingsw.GC_28.cards.*;

public class MultiplierEffect extends Effect{
	private Resource resourceBonus;
	private CardType cardType;
	public final EffectType type = EffectType.MULTIPLIEREFFECT;

	public MultiplierEffect(){
		super();
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
	
}
