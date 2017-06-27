package it.polimi.ingsw.GC_28.core;

import java.util.Map;

import it.polimi.ingsw.GC_28.cards.CardType;
import it.polimi.ingsw.GC_28.cards.LeaderCard;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.model.GameModel;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.server.Message;

public class SpecialActionController {
	
	private GameModel gameModel;
	private Player player;
	private SpecialAction action;
	
	public SpecialActionController(GameModel gameModel, Player player,SpecialAction action){
		this.gameModel = gameModel;
		this.player = player;
		this.action = action;
	}
	
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
	
	private boolean checkForCardPresence(){
		for(LeaderCard ld : player.getLeaderCards()){
			if(ld.getName().equalsIgnoreCase(action.getLeaderName())){
				return true;
			}
		}
		return false;
	}
	
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
