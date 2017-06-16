package it.polimi.ingsw.GC_28.effects;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.boards.PlayerBoard;
import it.polimi.ingsw.GC_28.cards.CardType;
import it.polimi.ingsw.GC_28.client.ClientWriter;
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
	public void apply(FamilyMember familyMember, ClientWriter writer) {
		System.out.println("apply di TakeCardEffect");
		//game.askCard(this);
		boolean ok = false;
		do{
			try {
				ok = writer.askCard(this);
			} catch (IOException e) {
				Logger.getAnonymousLogger().log(Level.SEVERE, e.getMessage());
			}
			if(!ok){
				System.out.println("Are you unable to take any card and you want to skip? [y/n]");
				Scanner scan = new Scanner(System.in);
				if (scan.hasNextLine() && ("y").equalsIgnoreCase(scan.nextLine())){
					scan.close();
					return;
				}
				scan.close();
			}
		}while(!ok);
	}
}
