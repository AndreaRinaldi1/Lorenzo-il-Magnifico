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
	
	public boolean check(Player player, GameModel gameModel, SpecialAction action){
		
		if(!checkForCardPresence()){
			gameModel.notifyObserver(new Message("You don't have this LeaderCard", false));
			return false;
		}
		
		if("discard".equalsIgnoreCase(action.getActionType())){
			return true;
		}else{
			if("play".equalsIgnoreCase(action.getActionType())){
				if(!checkPlayAction()){
					//gameModel.notifyObserver(new Message("you cannot play this Leader card", false));
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
					//l.setPlayed(true);
					return true;
				}
				else if(enoughResources(cardResourceCost) && enoughCard(cardCost)){
					//l.setPlayed(true);
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
						if(!(l.getName().equalsIgnoreCase("Sisto IV")) && !(l.getName().equalsIgnoreCase("Santa Rita"))){
							//l.getEffect().apply(player, );//FIXME change all the possible apply(familyMember,game) to apply(player,game) if possible
							return true;
							}
					}else{
						gameModel.notifyObserver(new Message("You can't activate this card because you already played it in this turn", false));
						return false;
					}
				}else{
					//handlers.get(currentPlayer).send("You can't activate this card because you've not played it yet");
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
				//handlers.get(player).send("You haven't enough resources to play this Leader");
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
			//handlers.get(currentPlayer).send("You haven't enough Territories cards  to play this Leader");
			gameModel.notifyObserver(new Message("You haven't enough Territories cards  to play this Leader", false));
			return false;
		}
		if(player.getBoard().getBuildings().size() < cardCost.get(CardType.BUILDING).intValue()){
			//handlers.get(currentPlayer).send("You haven't enough Building cards  to play this Leader");
			gameModel.notifyObserver(new Message("You haven't enough Buildings cards  to play this Leader", false));
			return false;
		}
		if(player.getBoard().getCharacters().size() < cardCost.get(CardType.CHARACTER).intValue()){
			//handlers.get(currentPlayer).send("You haven't enough Characters cards  to play this Leader");
			gameModel.notifyObserver(new Message("You haven't enough Characters cards  to play this Leader", false));
			return false;
		}
		if(player.getBoard().getVentures().size() < cardCost.get(CardType.VENTURE).intValue()){
			//handlers.get(currentPlayer).send("You haven't enough Ventures cards  to play this Leader");
			gameModel.notifyObserver(new Message("You haven't enough Venture cards  to play this Leader", false));
			return false;
		}
		return true;
	}

	private boolean checkForLucreziaBorgiaCost(LeaderCard lc) {
		if(lc.getName().equalsIgnoreCase("Lucrezia Borgia")){
			if(player.getBoard().getTerritories().size() == 6){
				return true;
			}else if(player.getBoard().getBuildings().size() == 6){
				return true;
			}else if(player.getBoard().getCharacters().size() == 6){
				return true;
			}else if(player.getBoard().getVentures().size() == 6){
				return true;
			}else{
				//handlers.get(currentPlayer).send("You haven't enough card to play thi leader");
				gameModel.notifyObserver(new Message("You haven't enough card to play thi leader", false));
				return false;
			}
		}
		return false;
	}
}
