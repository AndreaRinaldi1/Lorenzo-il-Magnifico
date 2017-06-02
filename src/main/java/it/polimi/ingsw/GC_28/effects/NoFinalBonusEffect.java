package it.polimi.ingsw.GC_28.effects;

import it.polimi.ingsw.GC_28.cards.CardType;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.model.Game;

public class NoFinalBonusEffect extends Effect {
	CardType cardType;

	public CardType getCardType() {
		return cardType;
	}

	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}

	@Override
	public void apply(FamilyMember familyMember, Game game) {
		System.out.println("Apply di NoFinalBonusEffect");
		
	}
}
