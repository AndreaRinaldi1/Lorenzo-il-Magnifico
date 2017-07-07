package it.polimi.ingsw.GC_28.effects;

import it.polimi.ingsw.GC_28.boards.FinalBonus;
import it.polimi.ingsw.GC_28.boards.PlayerBoard;
import it.polimi.ingsw.GC_28.cards.CardType;
import it.polimi.ingsw.GC_28.cards.Venture;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.view.GameView;
/**
 * This class represent the effect that prevent the player from getting the final bonus (at the very end of the game)
 * for the number of territories or characters he achieved, or the not actuation of the permanent effects of his venture cards.
 * @author andreaRinaldi, nicoloScipione
 * @version 1.0, 07/04/2017
 */
public class NoFinalBonusEffect extends Effect {
	CardType cardType;
	final EffectType type = EffectType.NOFINALBONUSEFFECT;
	
	public CardType getCardType() {
		return cardType;
	}

	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}

	/**
	 * This method reduces the player resources according to the excommunication he received.
	 * For territories and characters, he does not get the resources specified on the playerboard, while 
	 * for ventures, he does not receives the resources corresponding to their permanent effects.
	 */
	@Override
	public void apply(Player player, GameView game) {
		FinalBonus finalBonus = FinalBonus.instance();
		PlayerBoard pb = player.getBoard();
		switch(cardType){
		case TERRITORY:
			pb.getResources().modifyResource(finalBonus.getFinalTerritoriesBonus().get(pb.getTerritories().size()), false);
			break;
		case CHARACTER:
			pb.getResources().modifyResource(finalBonus.getFinalCharactersBonus().get(pb.getCharacters().size()), false);
			break;
		case VENTURE:
			for(Venture v : pb.getVentures()){
				pb.getResources().modifyResource(v.getPermanentEffect().getResourceBonus(), false);
			}
			break;
		
		default:
			break;
		}
	}


}
