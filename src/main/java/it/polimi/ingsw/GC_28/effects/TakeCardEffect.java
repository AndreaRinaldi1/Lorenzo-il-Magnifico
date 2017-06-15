package it.polimi.ingsw.GC_28.effects;

import java.io.IOException;

import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.boards.PlayerBoard;
import it.polimi.ingsw.GC_28.cards.CardType;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.core.Action;
import it.polimi.ingsw.GC_28.model.Game;
import it.polimi.ingsw.GC_28.model.Player;

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
			Player p = game.getCurrentPlayer();
			try {
				//game.getHandlers().get(familyMember.getPlayer()).getOut().reset();
				System.out.println("entro nel try");
				game.getHandlers().get(p).getOut().writeUTF("takeCard");
				game.getHandlers().get(p).getOut().flush();
				System.out.println("inviato takecard");
				game.getHandlers().get(p).getOut().writeObject(this);
				game.getHandlers().get(p).getOut().flush();
				System.out.println("inviato oggetto");
				//game.getHandlers().get(p).getIn().readUTF();
				Object obj = game.getHandlers().get(p).getIn().readObject();
				System.out.println("ricevuto oggetto");
				if(obj == null){
					return;
				}
				Action action = (Action)obj;
				action.setGame(game);
				action.setGameModel(game.getGameModel());
				if(action.isApplicable()){
					action.apply();
					ok = true;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}while(!ok);
	}
}
