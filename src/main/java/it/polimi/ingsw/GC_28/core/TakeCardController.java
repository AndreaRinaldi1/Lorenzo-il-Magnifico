package it.polimi.ingsw.GC_28.core;

import java.util.EnumMap;

import it.polimi.ingsw.GC_28.boards.Cell;
import it.polimi.ingsw.GC_28.boards.FinalBonus;
import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.boards.Tower;
import it.polimi.ingsw.GC_28.cards.CardType;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.effects.EffectType;
import it.polimi.ingsw.GC_28.effects.IncrementCardEffect;
import it.polimi.ingsw.GC_28.effects.OtherEffect;
import it.polimi.ingsw.GC_28.effects.TakeCardEffect;
import it.polimi.ingsw.GC_28.model.Game;
import it.polimi.ingsw.GC_28.cards.Character;
import it.polimi.ingsw.GC_28.cards.Venture;

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
		
		if(checkMoreThanSix(familyMember)){
			return false;
		}
		
		
		if(throughEffect == null){
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
				if(!(throughEffect == null)){ //se ho l'effetto
					if(throughEffect.getCardType() == null){ //se posso prendere qualunque cardType
						cardType = ct;
						System.out.println("ritorno true in checkcardExistance. posso prendere qualunque cardType");
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
		System.out.println("ritorno false nel checkcardexistance perche o non esiste o cardType sbagliato");
		return false;
	}
	
	private boolean checkMoreThanSix(FamilyMember familyMember){
		switch(cardType){
		case TERRITORY:
			if(familyMember.getPlayer().getBoard().getTerritories().size() == 6){
				return true;
			}
		break;
		case BUILDING:
			if(familyMember.getPlayer().getBoard().getBuildings().size() == 6){
				return true;
			}
		break;
		case CHARACTER:
			if(familyMember.getPlayer().getBoard().getCharacters().size() == 6){
				return true;
			}
		break;
		case VENTURE:
			if(familyMember.getPlayer().getBoard().getVentures().size() == 6){
				return true;
			}
		break;
		}
		System.out.println("ritorno che ho gia sei carte di quel tipo");
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
				if((c.getFamilyMember().getPlayer().getColor().equals(familyMember.getPlayer().getColor())) && !(c.getFamilyMember().isNeutral()) && !(familyMember.isNeutral())){
					System.out.println("ritorno true in checkthisplayer");
					return true; 
				}
			}
		}
		System.out.println("ritorno false in checkthisplayer");

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
		
		if(cardType.equals(CardType.TERRITORY)){
			for(int i = 0; i < familyMember.getPlayer().getBoard().getTerritories().size(); i++){
				if(familyMember.getPlayer().getBoard().getTerritories().get(i) == null){
					for(ResourceType resType : FinalBonus.instance().getResourceForTerritories().get(i).getResource().keySet()){
						if(familyMember.getPlayer().getBoard().getResources().getResource().get(resType) < FinalBonus.instance().getResourceForTerritories().get(i).getResource().get(resType)){
							return false;
						}
					}
				}
			}
		}

		
		EnumMap<ResourceType, Integer> res = new EnumMap<>(ResourceType.class);
		Resource tmp = Resource.of(res);
		tmp.modifyResource(familyMember.getPlayer().getBoard().getResources(), true);
		
		
		reduce3Coins(familyMember, true, tmp);
		
		if(tmp.getResource().get(ResourceType.COIN) < 0){
			System.out.println("ritorno false in checkresource perche non ho montete per altro giocatore su torre");
			return false;
		}
		
		lookForNoCellBonus(familyMember, true, tmp, cardName);
		
		lookForTakeCardDiscount(familyMember, true, tmp, game, throughEffect);
		
		lookForIncrementCardDiscount(familyMember, true, tmp, game);
		
		if(cardType.equals(CardType.VENTURE)){
			Venture venture = (Venture) gameBoard.getTowers().get(cardType).findCard(cardName).getCard();
			boolean costNotZeros = false;
			for(ResourceType rt : venture.getCost().getResource().keySet()){
				if(venture.getCost().getResource().get(rt) != 0){
					costNotZeros = true;
				}
			}
			if(venture.getAlternativeCostPresence() && costNotZeros){//ho due alternative di costo
				if(venture.getMinimumRequiredMilitaryPoints() <= familyMember.getPlayer().getBoard().getResources().getResource().get(ResourceType.MILITARYPOINT)){
					//qui se avesse un numero adeguato di military points tale da chiedere quale alternativa vuole
					System.out.println("1");
					tmp.modifyResource(game.askAlternative(venture.getCost(), venture.getAlternativeCost(), "cost"), false);
				}
				else{
					System.out.println("2");

					tmp.modifyResource(venture.getCost(), false); //se non ha suff. military points gli sottraggo cost
				}
			}
			else if(!venture.getAlternativeCostPresence()){ //ho cost ma non l'alternativa coi pti militari
				System.out.println("3");
				tmp.modifyResource(venture.getCost(), false); 
			}
			else{//ho il costo (alternativa) coi punti militari ma non il costo normale (tutti 0)
				if(venture.getMinimumRequiredMilitaryPoints() <= familyMember.getPlayer().getBoard().getResources().getResource().get(ResourceType.MILITARYPOINT)){
					//qui se avesse un numero adeguato di military points 
					tmp.modifyResource(venture.getAlternativeCost(), false);
				}
				else{
					System.out.println("no abbastanza pti mil");
					return false; //se non ha suff. military points non può prendere la carta
				}
			}
		}
		else{
			tmp.modifyResource(gameBoard.getTowers().get(cardType).findCard(cardName).getCard().getCost(), false);	
		}
		
		for(ResourceType resType : tmp.getResource().keySet()){
			if(tmp.getResource().get(resType) < 0){
				System.out.println("ritorno false in checkresource perceh non ho risorse nec");
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
				familyMember.getPlayer().getBoard().getResources().modifyResource(bonus, true);
			}
		}
		
	}
	
	
	protected void lookForTakeCardDiscount(FamilyMember familyMember, boolean check, Resource tmp, Game game, TakeCardEffect throughEffect){
		if(!(throughEffect == null)){
			if(throughEffect.isDiscountPresence()){ //discount di takecardeffect
				if(throughEffect.getDiscount().getAlternativeDiscountPresence()){
					if(check){
						throughEffect.getDiscount().setChosenAlternativeDiscount(game.askAlternative(throughEffect.getDiscount().getDiscount(), throughEffect.getDiscount().getAlternativeDiscount(), "discount"));
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
				if(cardType.equals(eff.getCardType()) || eff.getCardType() == null){
					if(eff.isDiscountPresence()){ //discount di incrementCardEffect
						if(eff.getDiscount().getAlternativeDiscountPresence()){ 
							if(check){
								eff.getDiscount().setChosenAlternativeDiscount(game.askAlternative(eff.getDiscount().getDiscount(), eff.getDiscount().getAlternativeDiscount(), "discount"));
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
