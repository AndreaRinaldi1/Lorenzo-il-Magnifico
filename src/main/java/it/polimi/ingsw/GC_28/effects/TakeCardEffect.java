package it.polimi.ingsw.GC_28.effects;

import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.boards.PlayerBoard;
import it.polimi.ingsw.GC_28.cards.CardType;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.model.Game;

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
	public void apply(FamilyMember familyMember, Game game) {
		System.out.println("apply di TakeCardEffect");
		//game.askCard(this);
		boolean ok = false;
		do{
			ok = game.askCard(this);
			if(!ok){
				game.getHandlers().get(familyMember.getPlayer()).getOut().println("Are you unable to take any card and you want to skip? [y/n]");
				game.getHandlers().get(familyMember.getPlayer()).getOut().flush();
				if (game.getHandlers().get(familyMember.getPlayer()).getIn().nextLine().equals("y")){
					return;
				}
			}
		}while(!ok);
	}
}
