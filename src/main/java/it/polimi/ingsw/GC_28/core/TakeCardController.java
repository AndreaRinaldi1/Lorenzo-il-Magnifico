package it.polimi.ingsw.GC_28.core;

import java.util.EnumMap;

import it.polimi.ingsw.GC_28.boards.Cell;
import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.boards.Tower;
import it.polimi.ingsw.GC_28.cards.CardType;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.effects.IncrementCardEffect;
import it.polimi.ingsw.GC_28.effects.NoCellBonusEffect;
import it.polimi.ingsw.GC_28.effects.TakeCardEffect;
import it.polimi.ingsw.GC_28.model.Game;
import it.polimi.ingsw.GC_28.cards.Character;

public class TakeCardController {

	private GameBoard gameBoard;
	protected CardType cardType;
	//private Cell cell;
	
	public TakeCardController(GameBoard gameBoard){
		this.gameBoard = gameBoard;
	}
	
	public boolean check(Game game, String name, FamilyMember familyMember, TakeCardEffect throughEffect){
		/*if(throughEffect.equals(null)){ //non ho effetto 
			cardType = null;
		}
		else{
			if(throughEffect.getCardType().equals(null)){ //con l'effetto posso prendere ogni carta
				cardType = null;
			}
			else{
				cardType = throughEffect.getCardType(); //con l'effetto posso prendere un solo tipo di carta
			}
		}*/
		if(!(checkCardExistance(name, throughEffect))){
			return false;
		}
		if(throughEffect.equals(null)){
			if(checkThisPlayerPresent(familyMember)){
				return false;
			}
		}
		if(!(checkResource(game, name,  familyMember, throughEffect))){
			return false;
		}
		if(!(checkActionValue(name, familyMember))){
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
				if(!(throughEffect.equals(null))){ //se ho l'effetto
					if(throughEffect.getCardType().equals(null)){ //se posso prendere qualunque cardType
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
	
	
	/**
	 * @param cardType
	 * @param familyMember
	 * @return true if any familyMember (not neutral) of this player is on the tower, false otherwise.
	 */
	private boolean checkThisPlayerPresent(FamilyMember familyMember){
		for(Cell c : gameBoard.getTowers().get(cardType).getCells()){
			if((c.getFamilyMember().getPlayer().getColor().equals(familyMember.getPlayer().getColor())) && !(c.getFamilyMember().isNEUTRAL()) && !(familyMember.isNEUTRAL())){
				return true; 
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
				return true; 
			}
		}
		return false;
	}
	
	/**
	 * @param familyMember
	 * @param cardType
	 * @return true if the player has enough resources to take the card
	 */
	private boolean checkResource(Game game, String cardName, FamilyMember familyMember, TakeCardEffect throughEffect){
		EnumMap<ResourceType, Integer> res = new EnumMap<>(ResourceType.class);
		Resource tmp = Resource.of(res);
		tmp.modifyResource(familyMember.getPlayer().getBoard().getResources(), true);
		
		reduce3Coins(familyMember, true, tmp);
		
		if(tmp.getResource().get(ResourceType.COIN) < 0){
			return false;
		}
		
		lookForNoCellBonus(familyMember, true, tmp, cardName);
		
		lookForTakeCardDiscount(familyMember, true, tmp, game, throughEffect);
		
		lookForIncrementCardDiscount(familyMember, true, tmp, game);
		
		tmp.modifyResource(gameBoard.getTowers().get(cardType).findCard(cardName).getCard().getCost(), false);
		
		for(ResourceType resType : tmp.getResource().keySet()){
			if(tmp.getResource().get(resType) < 0){
				return false;
			}
		}
		return true;
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
	
	protected void lookForNoCellBonus(FamilyMember familyMember, boolean check, Resource tmp, String cardName){
		boolean noCellBonus = false;
		for(Character character : familyMember.getPlayer().getBoard().getCharacters()){
			if(character.getPermanentEffect() instanceof NoCellBonusEffect){
				noCellBonus = true;
			}
		}
		if(!noCellBonus){
			Resource bonus = gameBoard.getTowers().get(cardType).findCard(cardName).getBonus();
			if(check){
				tmp.modifyResource(bonus, true);
			}
			else{
				familyMember.getPlayer().getBoard().getResources().modifyResource(bonus, true);
			}
		}
		
	}
	
	
	protected void lookForTakeCardDiscount(FamilyMember familyMember, boolean check, Resource tmp, Game game, TakeCardEffect throughEffect){
		if(!(throughEffect.equals(null))){
			if(throughEffect.isDiscountPresence()){ //discount di takecardeffect
				if(throughEffect.getDiscount().getAlternativeDiscountPresence()){
					if(check){
						throughEffect.getDiscount().setChosenAlternativeDiscount(game.askAlternativeDiscount(throughEffect.getDiscount().getDiscount(), throughEffect.getDiscount().getAlternativeDiscount()));
						tmp.modifyResource(throughEffect.getDiscount().getChosenAlternativeDiscount(), true);
					}
					else{
						familyMember.getPlayer().getBoard().getResources().modifyResource(throughEffect.getDiscount().getChosenAlternativeDiscount(), true);
					}
				}
				else{
					if(check){
						tmp.modifyResource(throughEffect.getDiscount().getDiscount(), true);
					}
					else{
						familyMember.getPlayer().getBoard().getResources().modifyResource(throughEffect.getDiscount().getDiscount(), true);
					}
				}
			}
		}
	}
	
	protected void lookForIncrementCardDiscount(FamilyMember familyMember, boolean check, Resource tmp, Game game){
		for(Character character : familyMember.getPlayer().getBoard().getCharacters()){
			if(character.getPermanentEffect() instanceof IncrementCardEffect){
				IncrementCardEffect eff = (IncrementCardEffect) character.getPermanentEffect();
				if(cardType.equals(eff.getCardType()) || eff.getCardType().equals(null)){
					if(eff.isDiscountPresence()){ //discount di incrementCardEffect
						if(eff.getDiscount().getAlternativeDiscountPresence()){ 
							if(check){
								eff.getDiscount().setChosenAlternativeDiscount(game.askAlternativeDiscount(eff.getDiscount().getDiscount(), eff.getDiscount().getAlternativeDiscount()));
								tmp.modifyResource(eff.getDiscount().getChosenAlternativeDiscount(), true);
							}
							else{
								familyMember.getPlayer().getBoard().getResources().modifyResource(eff.getDiscount().getChosenAlternativeDiscount(), true);
							}
							
						}
						else{
							if(check){
								tmp.modifyResource(eff.getDiscount().getDiscount(), true);
							}
							else{
								familyMember.getPlayer().getBoard().getResources().modifyResource(eff.getDiscount().getDiscount(), true);
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
	private boolean checkActionValue(String cardName, FamilyMember familyMember){
		int tmp = familyMember.getValue();
		for(Character character : familyMember.getPlayer().getBoard().getCharacters()){
			if(character.getPermanentEffect() instanceof IncrementCardEffect){
				IncrementCardEffect eff = (IncrementCardEffect) character.getPermanentEffect();
				if(eff.getCardType().equals(cardType)){
					tmp += eff.getIncrement();
				}
			}
		}
		if(tmp >= gameBoard.getTowers().get(cardType).findCard(cardName).getActionValue()){
			return true;
		}
		return false;
	}
}
