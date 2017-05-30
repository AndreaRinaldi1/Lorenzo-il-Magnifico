package it.polimi.ingsw.GC_28.core;

import it.polimi.ingsw.GC_28.boards.Cell;
import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.cards.Building;
import it.polimi.ingsw.GC_28.cards.Character;
import it.polimi.ingsw.GC_28.cards.Card;
import it.polimi.ingsw.GC_28.cards.Territory;
import it.polimi.ingsw.GC_28.cards.Venture;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.effects.Effect;
import it.polimi.ingsw.GC_28.effects.TakeCardEffect;
import it.polimi.ingsw.GC_28.model.Game;

public class TakeCardAction {
	private Game game;
	private GameBoard gameBoard;
	private TakeCardController takeCardController;
	
	public TakeCardAction(Game game, GameBoard gameBoard){
		this.game = game;
		this.gameBoard = gameBoard;
		takeCardController = new TakeCardController(gameBoard);
	}

	public boolean isApplicable(String name, FamilyMember familyMember, TakeCardEffect throughEffect){
		return takeCardController.check(game, name, familyMember, throughEffect);
	}
	
	
	
	public void apply(String name, FamilyMember familyMember, TakeCardEffect throughEffect){
		takeCardController.reduce3Coins(familyMember, false, null);
		takeCardController.lookForNoCellBonus(familyMember, false, null, name);
		takeCardController.lookForTakeCardDiscount(familyMember, false, null, game, throughEffect);
		takeCardController.lookForIncrementCardDiscount(familyMember, false, null, game);
		Cell cell = gameBoard.getTowers().get(takeCardController.cardType).findCard(name);
		Resource cardCost = cell.getCard().getCost();
		familyMember.getPlayer().getBoard().getResources().modifyResource(cardCost, false);
		familyMember.setUsed(true);

		if(throughEffect == null){
			cell.setFamilyMember(familyMember);
		}
		Card card = cell.getCard();
		if(card instanceof Territory){ 
			Territory territory = (Territory) card;
			familyMember.getPlayer().getBoard().addCard(territory);
			for(Effect e : territory.getImmediateEffect()){
				e.apply(familyMember, game);
			}
		}
		else if(card instanceof Building){ 
			Building building = (Building) card;
			familyMember.getPlayer().getBoard().addCard(building);
			building.getImmediateEffect().apply(familyMember, game);

		}
		else if(card instanceof Character){ 
			Character character = (Character) card;
			familyMember.getPlayer().getBoard().addCard(character);
			for(Effect e : character.getImmediateEffect()){
				e.apply(familyMember, game);
			}
		}
		else if(card instanceof Venture){ 
			Venture venture = (Venture) card;
			familyMember.getPlayer().getBoard().addCard(venture);
			for(Effect e : venture.getImmediateEffect()){
				e.apply(familyMember, game);
			}
		}
		cell.setCard(null);
		cell.setFree(false);
	}
	
	
}
