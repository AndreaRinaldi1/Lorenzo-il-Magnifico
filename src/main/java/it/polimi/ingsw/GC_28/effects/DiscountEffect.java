package it.polimi.ingsw.GC_28.effects;

import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.cards.*;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.model.Game;
import it.polimi.ingsw.GC_28.model.Player;

public class DiscountEffect extends Effect{
	private boolean alternativeDiscountPresence;
	private Resource discount;
	private Resource alternativeDiscount;
	private Resource chosenAlternativeDiscount;
	
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
	
	
	public Resource getChosenAlternativeDiscount() {
		return chosenAlternativeDiscount;
	}


	public void setChosenAlternativeDiscount(Resource chosenAlternativeDiscount) {
		this.chosenAlternativeDiscount = chosenAlternativeDiscount;
	}

	@Override
	public void apply(FamilyMember familyMember, Game game){
		apply(familyMember.getPlayer(), game);
	}

	@Override
	public void apply(Player player, Game game){
		System.out.println("apply di DiscountEffect");
		if(alternativeDiscountPresence == true){
			player.addResource(game.askAlternative(discount, alternativeDiscount, "discount")); 
		}
		else{
			player.addResource(discount);
		}
	}
	
}
