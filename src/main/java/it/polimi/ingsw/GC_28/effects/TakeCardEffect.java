package it.polimi.ingsw.GC_28.effects;

import it.polimi.ingsw.GC_28.cards.CardType;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.view.GameView;
/**
 * This class represent the effect that allows a player to take another card of a certain type (or any type) that is 
 * positioned in the gameboard in a tower and needing an action value less or equal to the one defined by the effect.
 * This effect can also present a discount on the cost of the card that the player is going to take.
 * @author andreaRinaldi
 * @version 1.0, 07/04/2017
 */
public class TakeCardEffect extends Effect{
	private int actionValue;
	private CardType cardType;
	private boolean discountPresence;
	private DiscountEffect discount;
	public final EffectType type = EffectType.TAKECARDEFFECT;

	public TakeCardEffect(){
		super();
	}

	public int getActionValue() {
		return actionValue;
	}

	public void setActionValue(int actionValue) {
		this.actionValue = actionValue;
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
	 * This effect asks the player for the card he/she wants to take, and it's here that the action of taking another card starts.
	 * If the action turns out to be unsuccessful, the player has an "emergency exit" if he/she cant't take any card.
	 */
	@Override
	public void apply(FamilyMember familyMember, GameView game) {
		boolean ok = false;
		do{
			ok = game.askCard(this);
			if(!ok){
				game.getHandlers().get(familyMember.getPlayer()).send("Are you unable to take any card and you want to skip? [y/n]");
				if (game.getHandlers().get(familyMember.getPlayer()).receive().equals("y")){
					return;
				}
			}
		}while(!ok);
	}
}
