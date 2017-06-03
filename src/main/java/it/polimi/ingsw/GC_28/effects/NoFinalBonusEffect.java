package it.polimi.ingsw.GC_28.effects;

import it.polimi.ingsw.GC_28.boards.FinalBonus;
import it.polimi.ingsw.GC_28.boards.PlayerBoard;
import it.polimi.ingsw.GC_28.cards.CardType;
import it.polimi.ingsw.GC_28.cards.Venture;
import it.polimi.ingsw.GC_28.model.Game;
import it.polimi.ingsw.GC_28.model.Player;

public class NoFinalBonusEffect extends Effect {
	CardType cardType;
	final EffectType type = EffectType.NOFINALBONUSEFFECT;
	
	public CardType getCardType() {
		return cardType;
	}

	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}

	@Override
	public void apply(Player player, Game game) {
		System.out.println("Apply di NoFinalBonusEffect");
		FinalBonus finalBonus = FinalBonus.instance();
		PlayerBoard pb = player.getBoard();
		switch(cardType){
		case TERRITORY:
			pb.getResources().modifyResource(finalBonus.getFinalTerritoriesBonus().get(pb.getTerritories().size()), false);
			break;
		case CHARACTER:
			pb.getResources().modifyResource(finalBonus.getFinalCharactersBonus().get(pb.getTerritories().size()), false);
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
