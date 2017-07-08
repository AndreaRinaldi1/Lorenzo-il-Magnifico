package it.polimi.ingsw.GC_28.core;

import java.util.EnumMap;

import it.polimi.ingsw.GC_28.boards.Cell;
import it.polimi.ingsw.GC_28.boards.FinalBonus;
import it.polimi.ingsw.GC_28.boards.GameBoard;

import it.polimi.ingsw.GC_28.cards.CardType;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.effects.EffectType;
import it.polimi.ingsw.GC_28.effects.IncrementCardEffect;
import it.polimi.ingsw.GC_28.effects.OtherEffect;
import it.polimi.ingsw.GC_28.effects.TakeCardEffect;
import it.polimi.ingsw.GC_28.model.GameModel;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.server.Message;
import it.polimi.ingsw.GC_28.view.GameView;
import it.polimi.ingsw.GC_28.cards.Character;
import it.polimi.ingsw.GC_28.cards.ExcommunicationTile;
import it.polimi.ingsw.GC_28.cards.LeaderCard;
import it.polimi.ingsw.GC_28.cards.Venture;

/**
 * This class controls if the action of taking the card that the player selected is applicable or not
 * @author andreaRinaldi,nicoloScipione
 * @version 1.0, 07/03/2017
 */
public class TakeCardController {
	private GameModel gameModel;
	private GameBoard gameBoard;
	protected CardType cardType;
	private final int MAX_SIZE = 6;
	
	public TakeCardController(GameModel gameModel){
		this.gameModel = gameModel;
		this.gameBoard = gameModel.getGameBoard();
	}
	
	/**
	 * this method is the general check for every possible situation that could go wrong
	 * 
	 * @param game the view of the game
	 * @param name the name of the card
	 * @param familyMember the family member the player decided to use for this action
	 * @param throughEffect indicates if the action is carried out by means of an immediate effect of a card or not
	 * @return true if it's ok, false otherwise
	 */
	public boolean check(GameView game, String name, FamilyMember familyMember, TakeCardEffect throughEffect){
		if(!(checkCardExistance(name, throughEffect))){
			gameModel.notifyObserver(new Message("this card doesn't exist", false));
			return false;
		}
	
		if(checkMoreThanSix(familyMember)){
			gameModel.notifyObserver(new Message("You already have six cards of the type " + cardType.name().toLowerCase() , false));
			return false;
		}
		
		if(throughEffect == null){
			if(checkThisPlayerPresent(familyMember)){
				gameModel.notifyObserver(new Message("You already are in this tower" , false));
				return false;
			}
		}
		
		if(!(checkResource(game, name,  familyMember, throughEffect))){
			return false;
		}
		
		if(!(checkActionValue(game, name, familyMember))){
			return false;
		}
		
		return true;
	}
	
	/**
	 * @param cardType the type of the card
	 * @param name the name of the card
	 * @return true if the card exists, false otherwise.
	 */
	private boolean checkCardExistance(String name, TakeCardEffect throughEffect){
		for(CardType ct : gameBoard.getTowers().keySet()){
			if(gameBoard.getTowers().get(ct).findCard(name) != null){ //if I found the card
				if(!(throughEffect == null)){ 
					if(throughEffect.getCardType() == null){ //if I can take a card of any cardType
						cardType = ct;
						return true; 
					}
					else{
						if(throughEffect.getCardType().equals(ct)){ //if I can take only a card of a specific cardType, and it is equals to the one i've chosen
							cardType = ct;
							return true;
						}
					}
				}
				else{
					cardType = ct;
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * @param familyMember the family member the player decided to use for this action
	 * @return true if I already have six cards of the type of card I wanto to take
	 */
	private boolean checkMoreThanSix(FamilyMember familyMember){
		switch(cardType){
		case TERRITORY:
			if(familyMember.getPlayer().getBoard().getTerritories().size() == MAX_SIZE){
				return true;
			}
		break;
		case BUILDING:
			if(familyMember.getPlayer().getBoard().getBuildings().size() == MAX_SIZE){
				return true;
			}
		break;
		case CHARACTER:
			if(familyMember.getPlayer().getBoard().getCharacters().size() == MAX_SIZE){
				return true;
			}
		break;
		case VENTURE:
			if(familyMember.getPlayer().getBoard().getVentures().size() == MAX_SIZE){
				return true;
			}
		break;
		default:
			
		}
		return false;
	}
	
	
	/**
	 * @param cardType the type of the card I chose to take
	 * @param familyMember the family member the player decided to use for this action
	 * @return true if any familyMember (not neutral) of this player is on the tower, false otherwise.
	 */
	private boolean checkThisPlayerPresent(FamilyMember familyMember){
		for(Cell c : gameBoard.getTowers().get(cardType).getCells()){
			if(!c.isFree()){
				if(c.getFamilyMember() != null){
					if((c.getFamilyMember().getPlayer().getColor().equals(familyMember.getPlayer().getColor())) && !(c.getFamilyMember().isNeutral()) && !(familyMember.isNeutral())){
						return true; 
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * @param cardType the type of the card I chose to take
	 * @return true if any player is on the tower, false otherwise.
	 */
	protected boolean checkAnyPlayerPresent(){
		for(Cell c : gameBoard.getTowers().get(cardType).getCells()){
			if(!(c.isFree())){
				if(c.getFamilyMember() != null){
					return true; 
				}
			}
		}
		return false;
	}
	
	/**
	 * @param familyMember the family member the player decided to use for this action
	 * @param cardType the type of the card I chose to take
	 * @return true if the player has enough resources to take the card
	 */
	private boolean checkResource(GameView game, String cardName, FamilyMember familyMember, TakeCardEffect throughEffect){
		if(cardType.equals(CardType.TERRITORY)){
			for(int i = 0; i < familyMember.getPlayer().getBoard().getTerritories().size(); i++){
				if(familyMember.getPlayer().getBoard().getTerritories().size() > 1){
					boolean active = checkForNoMilitaryForTerritoryEffect(familyMember.getPlayer());
					if(!active){

						int size = familyMember.getPlayer().getBoard().getTerritories().size();
						//I look at the resources indicated on the playerboard necessary to take a territory
						for(ResourceType resType : FinalBonus.instance().getResourceForTerritories().get(size).getResource().keySet()){
							if(familyMember.getPlayer().getBoard().getResources().getResource().get(resType) < FinalBonus.instance().getResourceForTerritories().get(size+1).getResource().get(resType)){
								gameModel.notifyObserver(new Message("You don'have the requested resources to take another " + cardType.name().toLowerCase() , false));
								return false;
							}
						}
					}
				}
			}
		}
		
		EnumMap<ResourceType, Integer> res = new EnumMap<>(ResourceType.class);
		Resource tmp = Resource.of(res);
		tmp.modifyResource(familyMember.getPlayer().getBoard().getResources(), true);
		
		boolean active = checkForNoExtraCostInTowerEffect(familyMember.getPlayer());
		
		if(!active){
			reduce3Coins(familyMember, true, tmp);
		}
		
		if(tmp.getResource().get(ResourceType.COIN) < 0){
			gameModel.notifyObserver(new Message("You don't have the three coins to enter this tower, since there's already another player", false));
			return false;
		}
		
		lookForNoCellBonus(game, familyMember, true, tmp, cardName);
		
		lookForTakeCardDiscount(familyMember, true, tmp, game, throughEffect);
		
		lookForIncrementCardDiscount(familyMember, true, tmp, game);
		
		if(cardType.equals(CardType.VENTURE)){
			Venture venture = (Venture) gameBoard.getTowers().get(cardType).findCard(cardName).getCard();
			boolean costNotZeros = false;
			Resource chosenCost;
			for(ResourceType rt : venture.getCost().getResource().keySet()){
				if(venture.getCost().getResource().get(rt) != 0){
					costNotZeros = true;
					break;
				}
			}
			if(venture.getAlternativeCostPresence() && costNotZeros){ //if I have two cost alternatives
				if(venture.getMinimumRequiredMilitaryPoints() <= familyMember.getPlayer().getBoard().getResources().getResource().get(ResourceType.MILITARYPOINT)){
					//if I have enough military points
					chosenCost = game.askAlternative(venture.getCost(), venture.getAlternativeCost(), "cost");
					tmp.modifyResource(chosenCost, false);
				}
				else{ //if I don't have the necesary military points
					chosenCost = venture.getCost();
					tmp.modifyResource(venture.getCost(), false); 
				}
			}
			else if(!venture.getAlternativeCostPresence()){ //I have the resource cost and not the cost with military points
				chosenCost = venture.getCost();
				tmp.modifyResource(venture.getCost(), false); 
			}
			else{ // i have the military points cost but not the resource cost
				if(venture.getMinimumRequiredMilitaryPoints() <= familyMember.getPlayer().getBoard().getResources().getResource().get(ResourceType.MILITARYPOINT)){
					//if I have enough military points
					chosenCost = venture.getAlternativeCost();
					tmp.modifyResource(venture.getAlternativeCost(), false);
				}
				else{
					gameModel.notifyObserver(new Message("You don't have the necessary military points to pay for this card" , false));
					return false; 
				}
			}
			venture.setChosenCost(chosenCost);
		}
		else{
			tmp.modifyResource(gameBoard.getTowers().get(cardType).findCard(cardName).getCard().getCost(), false);	
		}
		
		for(ResourceType resType : tmp.getResource().keySet()){
			if(tmp.getResource().get(resType) < 0){
				gameModel.notifyObserver(new Message("You don't have the necessary resources to pay for this card" , false));
				return false;
			}
		}
		return true;
	}
	
	/**
	 * This method checks if Cesare Borgia leader is active for the currentPlayer,
	 * if so skip the check of his resources requested for territories
	 * @return true if the player can avoid to have the military points in order to take another territory
	 */
	private boolean checkForNoMilitaryForTerritoryEffect(Player player){
		for(LeaderCard ls : player.getLeaderCards()){
			if(ls.getEffect().getClass().equals(OtherEffect.class)){
				OtherEffect e = (OtherEffect)ls.getEffect();
				if(e.getType().equals(EffectType.NOMILITARYFORTERRITORYEFFECT) && ls.getPlayed() && ls.getActive()){
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * This method checks if the player can avoid to pay the 3 coins thanks to an activated leader effect
	 * @param player the currentPlayer
	 * @return true if the player can avoid to pay the 3 coins, false otherwise
	 */
	private boolean checkForNoExtraCostInTowerEffect(Player player){
		for(LeaderCard lc : player.getLeaderCards()){ //check if current player can avoid to pay the 3 coins 
			if(lc.getEffect().getClass().equals(OtherEffect.class)){
				OtherEffect e = (OtherEffect)lc.getEffect();
				if(e.getType().equals(EffectType.NOEXTRACOSTINTOWEREFFECT) && lc.getPlayed() && lc.getActive()){
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * This method reduces the player resources by three coins dince there is already another player on the tower
	 * @param familyMember the family member the player decided to use for this action
	 * @param check if this method is called in the controller or in the application of the action
	 * @param tmp the temporary resources of the player if the action was executed
	 */
	protected void reduce3Coins(FamilyMember familyMember, boolean check, Resource tmp){
		if(checkAnyPlayerPresent()){
			EnumMap<ResourceType, Integer> minus3Coin = new EnumMap<>(ResourceType.class);
			minus3Coin.put(ResourceType.COIN, 3);
			Resource r = Resource.of(minus3Coin);
			if(check){
				tmp.modifyResource(r, false);
			}
			else{
				familyMember.getPlayer().getBoard().getResources().modifyResource(r, false);
			}
		}

	}
	
	/**
	 * this method controls if, in the player characters, there is a card that prevent from taking the
	 * bonuses on the tower when picking a card
	 * @param game the view of the game
	 * @param familyMember the family member the player decided to use for this action
	 * @param check if this method is called in the controller or in the application of the action
	 * @param tmp the temporary resources of the player if the action was executed
	 * @param cardName the name of the chosen card
	 */
	protected void lookForNoCellBonus(GameView game, FamilyMember familyMember, boolean check, Resource tmp, String cardName){
		boolean noCellBonus = false;
		for(Character character : familyMember.getPlayer().getBoard().getCharacters()){
			if(character.getPermanentEffect() instanceof OtherEffect){
				OtherEffect otherEffect = (OtherEffect) character.getPermanentEffect();
				if(otherEffect.getType().equals(EffectType.NOCELLBONUS)){
					noCellBonus = true;
				}
			}
		}
		if(!noCellBonus){
			Resource bonus = gameBoard.getTowers().get(cardType).findCard(cardName).getBonus();
			if(check){
				tmp.modifyResource(bonus, true);
			}
			else{
				familyMember.getPlayer().addResource(game.checkResourceExcommunication(bonus));
			}
		}
	}
	
	/**
	 * This method control, if I am taking a card thanks to an immediate effect of another card, if I also have a discount on the cost
	 * @param familyMember the family member the player decided to use for this action
	 * @param check if this method is called in the controller or in the application of the action
	 * @param tmp the temporary resources of the player if the action was executed
	 * @param game the view of the game
	 * @param throughEffect indicates if the action is carried out by means of an immediate effect of a card or not
	 */
	protected void lookForTakeCardDiscount(FamilyMember familyMember, boolean check, Resource tmp, GameView game, TakeCardEffect throughEffect){
		if(!(throughEffect == null)){
			if(throughEffect.isDiscountPresence()){ 
				if(throughEffect.getDiscount().getAlternativeDiscountPresence()){
					if(check){
						throughEffect.getDiscount().setChosenAlternativeDiscount(game.askAlternative(throughEffect.getDiscount().getDiscount(), throughEffect.getDiscount().getAlternativeDiscount(), "discount"));
						tmp.modifyResource(throughEffect.getDiscount().getChosenAlternativeDiscount(), true);
					}
					else{
						familyMember.getPlayer().addResource(throughEffect.getDiscount().getChosenAlternativeDiscount());
					}
				}
				else{
					if(check){
						tmp.modifyResource(throughEffect.getDiscount().getDiscount(), true);
					}
					else{
						familyMember.getPlayer().addResource(throughEffect.getDiscount().getDiscount());
					}
				}
			}
		}
	}
	
	/**
	 * This method controls if the current player has a character that has a discount for the cost of the card it wants to take
	 * @param familyMember the family member the player decided to use for this action
	 * @param check if this method is called in the controller or in the application of the action
	 * @param tmp the temporary resources of the player if the action was executed
	 * @param game the view of the game
	 */
	protected void lookForIncrementCardDiscount(FamilyMember familyMember, boolean check, Resource tmp, GameView game){
		for(Character character : familyMember.getPlayer().getBoard().getCharacters()){
			if(character.getPermanentEffect() instanceof IncrementCardEffect){
				IncrementCardEffect eff = (IncrementCardEffect) character.getPermanentEffect();
				if(cardType.equals(eff.getCardType()) || eff.getCardType() == null){
					if(eff.isDiscountPresence()){ 
						if(eff.getDiscount().getAlternativeDiscountPresence()){ 
							if(check){
								eff.getDiscount().setChosenAlternativeDiscount(game.askAlternative(eff.getDiscount().getDiscount(), eff.getDiscount().getAlternativeDiscount(), "discount"));
								tmp.modifyResource(eff.getDiscount().getChosenAlternativeDiscount(), true);
							}
							else{
								familyMember.getPlayer().addResource(eff.getDiscount().getChosenAlternativeDiscount());
							}
							
						}
						else{
							if(check){
								tmp.modifyResource(eff.getDiscount().getDiscount(), true);
							}
							else{
								familyMember.getPlayer().addResource(eff.getDiscount().getDiscount());
							}
						}
					}
				}
			}
		}
		
	}
	
	/**
	 * This method controls if the value of the action exploited by the player is high enough to do this action
	 * @param cardType the type of the chosen card
	 * @param familyMember the family member the player decided to use for this action
	 * @return true if the familyMember has actionvalue >= required cell action value
	 */
	private boolean checkActionValue(GameView game, String cardName, FamilyMember familyMember){
		IncrementCardEffect eff;
		int tmp = familyMember.getValue();
		//I control if there is a character among my cards that increments the action value
		for(Character character : familyMember.getPlayer().getBoard().getCharacters()){
			if(character.getPermanentEffect() instanceof IncrementCardEffect){
				eff = (IncrementCardEffect) character.getPermanentEffect();
				if(eff.getCardType().equals(cardType)){
					tmp += eff.getIncrement();
				}
			}
		}
		
		//I control if, among my excommunications, I have one that lowers the action value 
		for(ExcommunicationTile t : familyMember.getPlayer().getExcommunicationTile()){ 
			if(t.getEffect() instanceof IncrementCardEffect){
				eff = (IncrementCardEffect) t.getEffect();
				if(eff.getCardType().equals(cardType)){ 
					tmp += eff.getIncrement();
				}
			}
		}
					
					
		if(tmp >= gameBoard.getTowers().get(cardType).findCard(cardName).getActionValue()){
			return true;
		}
		gameModel.notifyObserver(new Message("Your action value is not high enough to take this card", false));
		return false;
	}
	
	/**
	 * This method reduces the cost of the chosen card by 3 coins if Pico della Mirandola is played and active
	 * @param familyMember the family member the player decided to use for this action
	 * @param tmp the card cost
	 */
	protected void lookForPicoDellaMirandola(FamilyMember familyMember, Resource tmp){
		for(LeaderCard lc : familyMember.getPlayer().getLeaderCards()){//check if the player has the card and  if it's played and activate 
			if(("Pico della Mirandola").equalsIgnoreCase(lc.getName()) && lc.getPlayed() && lc.getActive()){
				EnumMap<ResourceType, Integer> temp = new EnumMap<>(ResourceType.class);
				temp.put(ResourceType.COIN, 3);
				Resource tempResource = Resource.of(temp);
				tmp.modifyResource(tempResource, false);
				if(tmp.getResource().get(ResourceType.COIN) < 0){ //if the coin cost go below zero, it's reset to zero
					tmp.getResource().put(ResourceType.COIN, 0);
				}
			}
		}
	}
}
