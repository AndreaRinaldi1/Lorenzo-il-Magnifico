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
import it.polimi.ingsw.GC_28.model.GameModel;
import it.polimi.ingsw.GC_28.server.Message;
import it.polimi.ingsw.GC_28.view.GameView;
/**
 * This class allows the player to take a card on the gameBoard
 * @author andreaRinadli
 * @version 1.0, 07/03/2017
 */
public class TakeCardAction extends Action{
	private GameView game;
	private GameBoard gameBoard;
	private TakeCardController takeCardController;
	private FamilyMember familyMember;
	private String name;
	private TakeCardEffect throughEffect;
	private GameModel gameModel;
	
	/**
	 * This constructor builds a TakeCardAction out of the gameView, necessary to apply the effects, 
	 * and the gameModel, useful for controlling if the action is possible and apply the changes
	 * @param game the view of the game
	 * @param gameModel the model of the game
	 */
	public TakeCardAction(GameView game, GameModel gameModel){
		this.game = game;
		this.gameBoard = gameModel.getGameBoard();
		this.gameModel = gameModel;
		takeCardController = new TakeCardController(gameModel);
	}

	/**
	 * This method set for the action, the family member that the player has chosen to use
	 * @param familyMember the chosen family member for this action
	 */
	public void setFamilyMember(FamilyMember familyMember) {
		this.familyMember = familyMember;
	}

	/**
	 * This method set the name of the card the player has chosen to take
	 * @param name the name of the card
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * This method defines if this action is carried out through the immediate effect of a just taken card, 
	 * and not by a common action of the player
	 * @param throughEffect the effect and its properties through which the player is exploiting this action
	 */
	public void setThroughEffect(TakeCardEffect throughEffect) {
		this.throughEffect = throughEffect;
	}
	
	/**
	 * @return if the action chosen by the player is applicable or not
	 */
	@Override
	public boolean isApplicable(){
		return takeCardController.check(game, name, familyMember, throughEffect);
	}
	
	/**
	 * this method carries out the actual final application of the action exploited by the player
	 * and notifies the view with the result
	 */
	@Override
	public void apply(){
		takeCardController.reduce3Coins(familyMember, false, null);
		takeCardController.lookForNoCellBonus(game, familyMember, false, null, name);
		takeCardController.lookForTakeCardDiscount(familyMember, false, null, game, throughEffect);
		takeCardController.lookForIncrementCardDiscount(familyMember, false, null, game);
		Cell cell = gameBoard.getTowers().get(takeCardController.cardType).findCard(name);
		Resource cardCost;
		if(cell.getCard() instanceof Venture){
			Venture venture = (Venture) cell.getCard();
			cardCost= venture.getChosenCost();
		}
		else{
			cardCost = cell.getCard().getCost();
		}
		takeCardController.lookForPicoDellaMirandola(familyMember, cardCost);//modify card cost if this card is present
		familyMember.getPlayer().reduceResources(cardCost);
		
		
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
		gameModel.notifyObserver(new Message("Action completed successfully!", true));

	}	
}
