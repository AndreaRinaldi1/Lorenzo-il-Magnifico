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
import it.polimi.ingsw.GC_28.model.GameModel;
import it.polimi.ingsw.GC_28.server.Message;

public class TakeCardAction extends Action{
	private Game game;
	private GameBoard gameBoard;
	private TakeCardController takeCardController;
	private FamilyMember familyMember;
	private String name;
	private TakeCardEffect throughEffect;
	private GameModel gameModel;
	
	public TakeCardAction(Game game, GameModel gameModel){
		this.game = game;
		this.gameBoard = gameModel.getGameBoard();
		this.gameModel = gameModel;
		takeCardController = new TakeCardController(gameModel);
	}

	public void setFamilyMember(FamilyMember familyMember) {
		this.familyMember = familyMember;
	}


	public void setName(String name) {
		this.name = name;
	}


	public void setThroughEffect(TakeCardEffect throughEffect) {
		this.throughEffect = throughEffect;
	}
	
	public boolean isApplicable(){
		return takeCardController.check(game, name, familyMember, throughEffect);
	}
	
	public void apply(){
		System.out.println("apply");
		takeCardController.reduce3Coins(familyMember, false, null);
		System.out.println("step 1");
		takeCardController.lookForNoCellBonus(game, familyMember, false, null, name);
		System.out.println("step 2");
		takeCardController.lookForTakeCardDiscount(familyMember, false, null, game, throughEffect);
		System.out.println("step 3");
		takeCardController.lookForIncrementCardDiscount(familyMember, false, null, game);
		System.out.println("step 4");
		Cell cell = gameBoard.getTowers().get(takeCardController.cardType).findCard(name);
		System.out.println("step 5");
		Resource cardCost = cell.getCard().getCost();
		System.out.println("step 6");
		takeCardController.lookForPicoDellaMirandola(familyMember, cardCost, game);//modify card cost if pico is present FIXME
		System.out.println("step 7");
		familyMember.getPlayer().reduceResources(cardCost);
		System.out.println("step 8");
		
		
		if(throughEffect == null){
			cell.setFamilyMember(familyMember);
			familyMember.setUsed(true);
		}
		Card card = cell.getCard();
		if(card instanceof Territory){ 
			Territory territory = (Territory) card;
			familyMember.getPlayer().getBoard().addCard(territory);
			for(Effect e : territory.getImmediateEffect()){
				e.apply(familyMember, game);
			}
			System.out.println("territory step");
		}
		else if(card instanceof Building){ 
			Building building = (Building) card;
			familyMember.getPlayer().getBoard().addCard(building);
			building.getImmediateEffect().apply(familyMember, game);
			System.out.println("building step");
		}
		else if(card instanceof Character){ 
			Character character = (Character) card;
			familyMember.getPlayer().getBoard().addCard(character);
			for(Effect e : character.getImmediateEffect()){
				e.apply(familyMember, game);
			}
			System.out.println("character step");
		}
		else if(card instanceof Venture){ 
			Venture venture = (Venture) card;
			familyMember.getPlayer().getBoard().addCard(venture);
			for(Effect e : venture.getImmediateEffect()){
				e.apply(familyMember, game);
			}
			System.out.println("venture step");
		}
		System.out.println("step 9");
		cell.setCard(null);
		cell.setFree(false);
		System.out.println("step 10");
		gameModel.notifyObserver(new Message("Action completed successfully!", true));

	}
	
	
}
