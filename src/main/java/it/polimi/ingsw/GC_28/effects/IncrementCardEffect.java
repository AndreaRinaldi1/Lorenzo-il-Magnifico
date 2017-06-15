package it.polimi.ingsw.GC_28.effects;

import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.boards.PlayerBoard;
import it.polimi.ingsw.GC_28.cards.*;
import it.polimi.ingsw.GC_28.client.ClientWriter;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.model.Game;


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
	
	@Override
	public void apply(FamilyMember familyMember, ClientWriter writer){
		System.out.println("apply di IncrementCardEffect");
		//Quando un controller, prima di effettuare l'azione di takeCard da tower, deve controllare se ci sono incrementcard effects
		//chiama (per ogni incrementCard effect che trova) getCardType e guarda se è uguale al cardType della carta scelta dal giocatore. 
		//Se sì chiama questo apply (che quindi non fa controlli su cardType ma aumenta solo actionValue del familyMember), se no non lo chiama.
		familyMember.modifyValue(increment);
		if(discountPresence){
			discount.apply(familyMember, writer);
		}
		
	}
	
}
