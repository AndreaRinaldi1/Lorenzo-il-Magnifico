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

public class TakeCardController {
	private GameModel gameModel;
	private GameBoard gameBoard;
	protected CardType cardType;
	private final int MAX_SIZE = 6;
	
	public TakeCardController(GameModel gameModel){
		this.gameModel = gameModel;
		this.gameBoard = gameModel.getGameBoard();
	}
	
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
	 * @param cardType
	 * @param name
	 * @return true if the card exists, false otherwise.
	 */
	private boolean checkCardExistance(String name, TakeCardEffect throughEffect){
		for(CardType ct : gameBoard.getTowers().keySet()){
			if(gameBoard.getTowers().get(ct).findCard(name) != null){ //se ho trovato la carta
				if(!(throughEffect == null)){ //se ho l'effetto
					if(throughEffect.getCardType() == null){ //se posso prendere qualunque cardType
						cardType = ct;
						return true; 
					}
					else{
						if(throughEffect.getCardType().equals(ct)){ //se posso prendere un cardTYpe specifico e coincide
							cardType = ct;
							return true;
						}
					}
				}
				else{
					cardType = ct; //se non ho l'effetto e ho trovato la cella
					return true;
				}
			}
		}
		return false;
	}
	
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
		}
		return false;
	}
	
	
	/**
	 * @param cardType
	 * @param familyMember
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
	 * @param cardType
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
	 * @param familyMember
	 * @param cardType
	 * @return true if the player has enough resources to take the card
	 */
	private boolean checkResource(GameView game, String cardName, FamilyMember familyMember, TakeCardEffect throughEffect){
		if(cardType.equals(CardType.TERRITORY)){
			for(int i = 0; i < familyMember.getPlayer().getBoard().getTerritories().size(); i++){
				if(familyMember.getPlayer().getBoard().getTerritories().size() > 1){
					boolean active = checkForNoMilitaryForTerritoryEffect(familyMember.getPlayer());
					if(!active){

						int size = familyMember.getPlayer().getBoard().getTerritories().size();
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
			if(venture.getAlternativeCostPresence() && costNotZeros){//ho due alternative di costo
				if(venture.getMinimumRequiredMilitaryPoints() <= familyMember.getPlayer().getBoard().getResources().getResource().get(ResourceType.MILITARYPOINT)){
					//qui se avesse un numero adeguato di military points tale da chiedere quale alternativa vuole
					chosenCost = game.askAlternative(venture.getCost(), venture.getAlternativeCost(), "cost");
					tmp.modifyResource(chosenCost, false);
				}
				else{
					chosenCost = venture.getCost();
					tmp.modifyResource(venture.getCost(), false); //se non ha suff. military points gli sottraggo cost
				}
			}
			else if(!venture.getAlternativeCostPresence()){//ho cost ma non l'alternativa coi pti militari
				chosenCost = venture.getCost();
				tmp.modifyResource(venture.getCost(), false); 
			}
			else{//ho il costo (alternativa) coi punti militari ma non il costo normale (tutti 0)
				if(venture.getMinimumRequiredMilitaryPoints() <= familyMember.getPlayer().getBoard().getResources().getResource().get(ResourceType.MILITARYPOINT)){
					//qui se avesse un numero adeguato di military points 
					chosenCost = venture.getAlternativeCost();
					tmp.modifyResource(venture.getAlternativeCost(), false);
				}
				else{
					gameModel.notifyObserver(new Message("You don't have the necessary military points to pay for this card" , false));
					return false; //se non ha suff. military points non può prendere la carta
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
	/*check if Cesare Borgia leader is active for the currentPlayer,
	 *  if so skip the check of his resources requested for territories
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
	
	
	protected void lookForTakeCardDiscount(FamilyMember familyMember, boolean check, Resource tmp, GameView game, TakeCardEffect throughEffect){
		if(!(throughEffect == null)){
			if(throughEffect.isDiscountPresence()){ //discount di takecardeffect
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
	
	protected void lookForIncrementCardDiscount(FamilyMember familyMember, boolean check, Resource tmp, GameView game){
		for(Character character : familyMember.getPlayer().getBoard().getCharacters()){
			if(character.getPermanentEffect() instanceof IncrementCardEffect){
				IncrementCardEffect eff = (IncrementCardEffect) character.getPermanentEffect();
				if(cardType.equals(eff.getCardType()) || eff.getCardType() == null){
					if(eff.isDiscountPresence()){ //discount di incrementCardEffect
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
	 * @param cardType
	 * @param familyMember
	 * @return true if the familyMember has actionvalue >= required cell action value
	 */
	private boolean checkActionValue(GameView game, String cardName, FamilyMember familyMember){
		IncrementCardEffect eff;
		int tmp = familyMember.getValue();
		for(Character character : familyMember.getPlayer().getBoard().getCharacters()){
			if(character.getPermanentEffect() instanceof IncrementCardEffect){
				eff = (IncrementCardEffect) character.getPermanentEffect();
				if(eff.getCardType().equals(cardType)){
					tmp += eff.getIncrement();
				}
			}
		}
		
		
		for(ExcommunicationTile t : familyMember.getPlayer().getExcommunicationTile()){ //guardo se tra le scomuniche ha incrementcardeff
			if(t.getEffect() instanceof IncrementCardEffect){
				eff = (IncrementCardEffect) t.getEffect();
				if(eff.getCardType().equals(cardType)){ //se il cardType coincide allora gli tolgo il valore della scomun
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
	
	protected void lookForPicoDellaMirandola(FamilyMember familyMember, Resource tmp, GameView game){
		for(LeaderCard lc : familyMember.getPlayer().getLeaderCards()){//check if the player has the card and  if it's played and activate 
			if(lc.getName().equalsIgnoreCase("Pico della Mirandola") && lc.getPlayed() && lc.getActive()){
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
