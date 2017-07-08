package it.polimi.ingsw.GC_28.core;

import java.util.List;



import it.polimi.ingsw.GC_28.cards.LeaderCard;
import it.polimi.ingsw.GC_28.components.CouncilPrivilege;
import it.polimi.ingsw.GC_28.model.GameModel;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.view.GameView;

/**
 * This class represent all the actions related to leader cards. 
 * @author nicoloScipione
 * @version 1.0, 30/06/2017
 * @see LeaderCard,Action,SpecialActionController
 */
public class SpecialAction extends Action{
	
	private String leaderName;
	private String actionType;
	private Player player;
	private GameView gameView;
	private GameModel gameModel;
	private SpecialActionController controller;
	
	
	public SpecialAction(Player player, GameModel gameModel, GameView gameView){
		this.gameModel = gameModel;
		this.gameView = gameView;
		this.player = player;
		this.controller =  new SpecialActionController(gameModel, player, this);
	}
	
	/**
	 * 
	 * @return the name of leaderCard
	 */
	public String getLeaderName() {
		return leaderName;
	}
	
	/**
	 * 
	 * @param leaderName the name of leader
	 */
	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}
	
	/**
	 * 
	 * @return the type of special action that a player want to undertake.
	 */
	public String getActionType() {
		return actionType;
	}
	
	/**
	 * 
	 * @param actionType the type of special action the player want to undertake.
	 */
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	
	@Override
	public boolean isApplicable() {
		if(controller.check(gameModel, this)){
			return true;
		}
		return false;
	}
	
	@Override
	public void apply() {
		if("discard".equalsIgnoreCase(actionType)){
			for(LeaderCard l :player.getLeaderCards()){
				if(l.getName().equalsIgnoreCase(leaderName)){
					player.getLeaderCards().remove(l);
					List<java.lang.Character> choices = gameView.askPrivilege(1, false);
					for(int i = 0; i < choices.size(); i++){
						player.addResource(gameView.checkResourceExcommunication(CouncilPrivilege.instance().getOptions().get(choices.get(i))));
					}
					return;
				}
			}	
		}
		else if("play".equalsIgnoreCase(actionType)){
			for(LeaderCard l : player.getLeaderCards()){
				if(l.getName().equalsIgnoreCase(leaderName)){
					l.setPlayed(true);
					return;
				}
			}
		}
		else if("activate".equalsIgnoreCase(actionType)){
			for(LeaderCard l : player.getLeaderCards()){
				if(l.getName().equalsIgnoreCase(leaderName)){
					if(l.getPlayed()){
						if(!(l.getActive())){
							l.setActive(true);
							if(!(("Sisto IV").equalsIgnoreCase(l.getName())) && !(("Santa Rita").equalsIgnoreCase(l.getName()))){
								l.getEffect().apply(player, gameView);
								return;
							}
						}
					}
				}
			}
		}
	}
	
}
