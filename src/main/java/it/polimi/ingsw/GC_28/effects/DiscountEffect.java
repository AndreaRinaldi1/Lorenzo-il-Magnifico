package it.polimi.ingsw.GC_28.effects;

import it.polimi.ingsw.GC_28.boards.PlayerBoard;
import it.polimi.ingsw.GC_28.cards.*;
import it.polimi.ingsw.GC_28.components.Resource;

public class DiscountEffect extends Effect{
	private boolean alternativeDiscountPresence;
	private Resource discount;
	private Resource alternativeDiscount;
	public final EffectType type = EffectType.DISCOUNTEFFECT;
	
	public DiscountEffect(){
		super();
	}

	
	public Resource getAlternativeDiscount() {
		return alternativeDiscount;
	}

	public void setAlternativeDiscount(Resource alternativeDiscount) {
		this.alternativeDiscount = alternativeDiscount;
	}


	public boolean getAlternativeDiscountPresence() {
		return alternativeDiscountPresence;
	}

	public void setAlternativeDiscountPresence(boolean alternativeDiscount) {
		this.alternativeDiscountPresence = alternativeDiscount;
	}

	public Resource getDiscount() {
		return discount;
	}

	public void setDiscount(Resource discount) {
		this.discount = discount;
	}
	
	@Override
	public void apply(PlayerBoard p){
		System.out.println("apply di DiscoutEffect");
	}
	
}
