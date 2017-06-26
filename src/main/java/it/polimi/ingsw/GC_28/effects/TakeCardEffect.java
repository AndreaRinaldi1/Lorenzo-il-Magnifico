package it.polimi.ingsw.GC_28.effects;

import it.polimi.ingsw.GC_28.cards.CardType;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.view.GameView;

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

	@Override
	public void apply(FamilyMember familyMember, GameView game) {
		System.out.println("apply di TakeCardEffect");
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
