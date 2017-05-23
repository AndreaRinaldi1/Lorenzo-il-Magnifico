package it.polimi.ingsw.GC_28.effects;

import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.cards.*;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.model.Game;

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
	public void apply(FamilyMember familyMember, GameBoard gameBoard, Game game){
		System.out.println("apply di DiscountEffect");
		if(alternativeDiscountPresence == true){
			familyMember.getPlayer().getBoard().addResource(game.askAlternativeDiscount(discount, alternativeDiscount)); //Considero il discount come aumento risorse nella playerboard (?)
		}
		else{
			familyMember.getPlayer().getBoard().addResource(discount);
		}
	}
	
}
