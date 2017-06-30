package it.polimi.ingsw.GC_28.core;

import java.util.Map;

import it.polimi.ingsw.GC_28.cards.CardType;
import it.polimi.ingsw.GC_28.cards.LeaderCard;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.model.GameModel;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.server.Message;

/**
 * This class wrap all the control to check, before apply a special action
 * @author nicolo
 * @version 1.0, 30/06/2017
 */
public class SpecialActionController {
	
	private GameModel gameModel;
	private Player player;
	private SpecialAction action;
	
	public SpecialActionController(GameModel gameModel, Player player,SpecialAction action){
		this.gameModel = gameModel;
		this.player = player;
		this.action = action;
	}
	
	/**
	 * 
	 * @param gameModel 
	 * @param action
	 * @return true if the action respect all requested parameters of game, false if it's not applicable. 
	 */
	public boolean check(GameModel gameModel, SpecialAction action){
		
		if(!checkForCardPresence()){
			gameModel.notifyObserver(new Message("You don't have this LeaderCard", false));
			return false;
		}
		
		if("discard".equalsIgnoreCase(action.getActionType())){
			return true;
		}else{
			if("play".equalsIgnoreCase(action.getActionType())){
				if(!checkPlayAction()){
					return false;
				}
			}else if("activate".equalsIgnoreCase(action.getActionType())){
				if(!checkActivateAction()){
					return false;
				}
			}
		}
		return true;
		
	}
	
	/**
	 * Check if player really has the card
	 * @return true if the player really has that leader card, false otherwise.
	 */
	private boolean checkForCardPresence(){
		for(LeaderCard ld : player.getLeaderCards()){
			if(ld.getName().equalsIgnoreCase(action.getLeaderName())){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * check if player respect the necessary parameters to play a certain leader card. 
	 * @return true if the player can play the leader card, false otherwise.
	 */
	private boolean checkPlayAction(){
		for(LeaderCard l : player.getLeaderCards()){
			if(l.getName().equalsIgnoreCase(action.getLeaderName())){
				Resource cardResourceCost = l.getResourceCost();
				Map<CardType,Integer> cardCost = l.getCardCost();
				if(checkForLucreziaBorgiaCost(l)){
					return true;
				}
				else if(enoughResources(cardResourceCost) && enoughCard(cardCost)){
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * check if player can activate a certain leader card
	 * @return true if the player can activate a leader card, false otherwise
	 */
	private boolean checkActivateAction(){
		for(LeaderCard l : player.getLeaderCards()){
			if(l.getName().equalsIgnoreCase(action.getLeaderName())){
				if(l.getPlayed()){
					if(!(l.getActive())){
						if(!(("Sisto IV").equalsIgnoreCase(l.getName())) && !(("Santa Rita").equalsIgnoreCase(l.getName()))){
							return true;
							}
					}else{
						gameModel.notifyObserver(new Message("You can't activate this card because you already played it in this turn", false));
						return false;
					}
				}else{
					gameModel.notifyObserver(new Message("You can't activate this card because you've not played it yet", false));
					return false;
				}
			}
		}
		return false;
	}
	
	
	/**
	 * 
	 * @param resourceCost the necessary amount of resources to play a leader card
	 * @return true if the player has the resources requested, false otherwise.
	 */
	boolean enoughResources(Resource resourceCost){
		if(resourceCost == null){
			return true;
		}
		for(ResourceType rt: resourceCost.getResource().keySet()){
			if(player.getBoard().getResources().getResource().get(rt) < resourceCost.getResource().get(rt)){
				gameModel.notifyObserver(new Message("You haven't enough resources to play this Leader", false));
				return false;
			}
		}
		return true;
	}

	/**
	 * 
	 * @param cardCost the necessary number of card to play a leader card.
	 * @return true if the player has at least the minimum  number of card requested, false otherwise.
	 */
	boolean enoughCard(Map<CardType,Integer> cardCost) {
		if(cardCost == null){
			return true;
		}
		if(player.getBoard().getTerritories().size() < cardCost.get(CardType.TERRITORY).intValue()){
			gameModel.notifyObserver(new Message("You haven't enough Territories cards  to play this Leader", false));
			return false;
		}
		if(player.getBoard().getBuildings().size() < cardCost.get(CardType.BUILDING).intValue()){
			gameModel.notifyObserver(new Message("You haven't enough Buildings cards  to play this Leader", false));
			return false;
		}
		if(player.getBoard().getCharacters().size() < cardCost.get(CardType.CHARACTER).intValue()){
			gameModel.notifyObserver(new Message("You haven't enough Characters cards  to play this Leader", false));
			return false;
		}
		if(player.getBoard().getVentures().size() < cardCost.get(CardType.VENTURE).intValue()){
			gameModel.notifyObserver(new Message("You haven't enough Venture cards  to play this Leader", false));
			return false;
		}
		return true;
	}
	
	/**
	 * This method check if the card is Lucrezia Borgia if so, it check the cost conditions that are different from any other
	 *  leader card. 
	 * @param lc the leader card to check
	 * @return true if it is Lucrezia Borgia and the player can play it, false otherwise.
	 */
	private boolean checkForLucreziaBorgiaCost(LeaderCard lc) {
		if(("Lucrezia Borgia").equalsIgnoreCase(lc.getName())){
			if(player.getBoard().getTerritories().size() == 6){
				return true;
			}else if(player.getBoard().getBuildings().size() == 6){
				return true;
			}else if(player.getBoard().getCharacters().size() == 6){
				return true;
			}else if(player.getBoard().getVentures().size() == 6){
				return true;
			}else{
				gameModel.notifyObserver(new Message("You haven't enough card to play this leader", false));
				return false;
			}
		}
		return false;
	}
}
