package it.polimi.ingsw.GC_28.core;

import it.polimi.ingsw.GC_28.cards.ExcommunicationTile;
import it.polimi.ingsw.GC_28.cards.LeaderCard;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.effects.EffectType;
import it.polimi.ingsw.GC_28.effects.GoToHPEffect;
import it.polimi.ingsw.GC_28.effects.OtherEffect;
import it.polimi.ingsw.GC_28.model.GameModel;
import it.polimi.ingsw.GC_28.server.Message;
import it.polimi.ingsw.GC_28.spaces.MarketSpace;
import it.polimi.ingsw.GC_28.spaces.PrivilegesSpace;
import it.polimi.ingsw.GC_28.spaces.ProdHarvSpace;
import it.polimi.ingsw.GC_28.spaces.Space;

/**
 * This class controls if the action of going to the space that the player selected is applicable or not
 * @author andreaRinaldi
 * @version 1.0, 07/03/2017
 */
public class SpaceController {
	private GameModel gameModel;
	ProdHarvSpace phs;
	
	public SpaceController(GameModel gameModel){
		this.gameModel = gameModel;
	}
	
	/**
	 * this method is the general check for every possible situation that could go wrong
	 * @param familyMember the family member the player decided to use for this action
	 * @param space the space the player decided to go into
	 * @param throughEffect indicates if the action is carried out by means of an immediate effect of a card or not
	 * @return true if the action is applicable, false otherwise
	 */
	public boolean check(FamilyMember familyMember, Space space, GoToHPEffect throughEffect){
		
		if(space instanceof MarketSpace || space instanceof PrivilegesSpace){ 
			if(!checkForNoMarket(familyMember, space)){
				gameModel.notifyObserver(new Message("Due to excommunication, you can't go in this space", false));
				return false;
			}
		}
		
		if(throughEffect == null){
			if(!checkFree(familyMember, space)){
				gameModel.notifyObserver(new Message("This space is already occupied", false));
				return false;
			}
			if(!checkActionValue(familyMember, space)){
				gameModel.notifyObserver(new Message("Your action value is not high enough to go in this space", false));
				return false;
			}
		}
		
		return true;
	}
			
	/**
	 * This method control if the player action value is high enough to do this action
	 * @param familyMember the family member the player decided to use for this action
	 * @param space the space the player decided to go into
	 * @return true if it's ok, false otherwise
	 */
	private boolean checkActionValue(FamilyMember familyMember, Space space){
		if(familyMember.getValue() >= space.getActionValue()){
			return true;
		}
		return false;
	}

	/**
	 * This method checks, if the player decided to go to the market space, if he/she has the excommunication that prevent from doing this action
	 * @param familyMember the family member the player decided to use for this action
	 * @param space the space the player decided to go into
	 * @return true if it's ok, false otherwise
	 */
	private boolean checkForNoMarket(FamilyMember familyMember, Space space){
		for(ExcommunicationTile t : familyMember.getPlayer().getExcommunicationTile()){ //guardo se tra le scomuniche ha nomarketeff
			if(t.getEffect() instanceof OtherEffect){
				OtherEffect eff = (OtherEffect) t.getEffect();
				if(eff.getType().equals(EffectType.NOMARKETEFFECT)){
					return false; //allora ritorno mossa non valida
				}
			}
		}
		return true;
	}
	
	/**
	 * This method controls if the selected space is free or it's already occupied
	 * @param familyMember the family member the player decided to use for this action
	 * @param space the space the player decided to go into
	 * @return true if it's ok, false otherwise
	 */
	private boolean checkFree(FamilyMember familyMember, Space space){
		for(LeaderCard lc : familyMember.getPlayer().getLeaderCards()){ //if L.Ariosto is active the player can always go into a space
			if(lc.getName().equalsIgnoreCase("Ludovico Ariosto") && lc.getPlayed() && lc.getActive()){
				return true;
			}
		}
		if(space.isFree()){
			return true;
		}
		else{
			if(space instanceof ProdHarvSpace){
				phs = (ProdHarvSpace) space;
				if(phs.isSecondarySpace()){
					return true;
				}
			}
		}
		return false;
		
	}
}
