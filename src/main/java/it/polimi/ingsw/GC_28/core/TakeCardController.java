package it.polimi.ingsw.GC_28.core;

import java.util.EnumMap;

import it.polimi.ingsw.GC_28.boards.Cell;
import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.cards.CardType;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.effects.IncrementCardEffect;
import it.polimi.ingsw.GC_28.effects.NoCellBonusEffect;
import it.polimi.ingsw.GC_28.cards.Character;

public class TakeCardController {

	private GameBoard gameBoard;
	private Cell cell;
	
	public TakeCardController(GameBoard gameBoard){
		this.gameBoard = gameBoard;
	}
	
	public boolean check(CardType cardType, String name, FamilyMember familyMember){
		if(!(checkCardExistance(cardType, name))){
			return false;
		}
		if(checkThisPlayerPresent(cardType, familyMember)){
			return false;
		}
		if(!(checkResource(familyMember, cardType))){
			return false;
		}
		if(!(checkActionValue(cardType, familyMember))){
			return false;
		}
		
		return true;
	}
	
	/**
	 * @param cardType
	 * @param name
	 * @return true if the card exists, false otherwise.
	 */
	private boolean checkCardExistance(CardType cardType, String name){
		cell = gameBoard.getTowers().get(cardType).findCard(name);
		if(cell != null){
			return true; 
		}
		return false;
	}
	
	/**
	 * @param cardType
	 * @param familyMember
	 * @return true if any familyMember (not neutral) of this player is on the tower, false otherwise.
	 */
	private boolean checkThisPlayerPresent(CardType cardType, FamilyMember familyMember){
		for(Cell c : gameBoard.getTowers().get(cardType).getCells()){
			if((c.getFamilyMember().getPlayer().getColor().equals(familyMember.getPlayer().getColor())) && !(c.getFamilyMember().isNEUTRAL())){
				return true; 
			}
		}
		return false;
	}
	
	/**
	 * @param cardType
	 * @return true if any player is on the tower, false otherwise.
	 */
	private boolean checkAnyPlayerPresent(CardType cardType){
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
	private boolean checkResource(FamilyMember familyMember, CardType cardType){
		EnumMap<ResourceType, Integer> res = new EnumMap<>(ResourceType.class);
		Resource tmp = Resource.of(res);
		tmp.modifyResource(familyMember.getPlayer().getBoard().getResources(), true);
		
		if(checkAnyPlayerPresent(cardType)){
			EnumMap<ResourceType, Integer> minus3Coin = new EnumMap<>(ResourceType.class);
			minus3Coin.put(ResourceType.COIN, 3);
			Resource r = Resource.of(minus3Coin);
			tmp.modifyResource(r, false);
		}
		
		if(tmp.getResource().get(ResourceType.COIN) < 0){
			return false;
		}
		
		boolean noCellBonus = false;
		for(Character character : familyMember.getPlayer().getBoard().getCharacters()){
			if(character.getPermanentEffect() instanceof NoCellBonusEffect){
				noCellBonus = true;
			}
		}
		if(!noCellBonus){
			tmp.modifyResource(cell.getBonus(), true);
		}
		
		tmp.modifyResource(cell.getCard().getCost(), false);
		
		for(ResourceType resType : tmp.getResource().keySet()){
			if(tmp.getResource().get(resType) < 0){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * @param cardType
	 * @param familyMember
	 * @return true if the familyMember has actionvalue >= required cell action value
	 */
	private boolean checkActionValue(CardType cardType, FamilyMember familyMember){
		int tmp = familyMember.getValue();
		for(Character character : familyMember.getPlayer().getBoard().getCharacters()){
			if(character.getPermanentEffect() instanceof IncrementCardEffect){
				IncrementCardEffect eff = (IncrementCardEffect) character.getPermanentEffect();
				if(eff.getCardType().equals(cardType)){
					tmp += eff.getIncrement();
				}
			}
		}
		if(tmp >= cell.getActionValue()){
			return true;
		}
		return false;
	}
}
