package it.polimi.ingsw.GC_28.effects;

import it.polimi.ingsw.GC_28.cards.*;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.view.GameView;

/**
 * This class represent the effect that allows to increment (or reducing, for excommunications) 
 * the value of an action of taking a card. It could also have a discount 
 * on the resource cost of the card.
 * @author andreaRinaldi
 * @version 1.0, 07/04/2017
 */
public class IncrementCardEffect extends Effect{
	private int increment;
	private CardType cardType;
	private boolean discountPresence;
	private DiscountEffect discount;
	public final EffectType type = EffectType.INCREMENTCARDEFFECT;
	
	public IncrementCardEffect(){
		super();
	}

	public int getIncrement() {
		return increment;
	}

	public void setIncrement(int increment) {
		this.increment = increment;
	}

	public CardType getCardType() {
		return cardType;
	}

	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}

	public boolean isDiscountPresence() {
		return discountPresence;
	}

	public void setDiscountPresence(boolean discountPresence) {
		this.discountPresence = discountPresence;
	}

	public DiscountEffect getDiscount() {
		return discount;
	}

	public void setDiscount(DiscountEffect discount) {
		this.discount = discount;
	}
	
	/**
	 * This method modifies the action value of the family member selected, and, if there is a discount it increments the player resources of that amount.
	 */
	@Override
	public void apply(FamilyMember familyMember, GameView game){
		familyMember.modifyValue(increment);
		if(discountPresence){
			discount.apply(familyMember, game);
		}
		
	}
	
}
