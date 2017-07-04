package it.polimi.ingsw.GC_28.core;

import it.polimi.ingsw.GC_28.cards.Character;
import it.polimi.ingsw.GC_28.cards.ExcommunicationTile;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.effects.EffectType;
import it.polimi.ingsw.GC_28.effects.GoToHPEffect;
import it.polimi.ingsw.GC_28.effects.IncrementHPEffect;
import it.polimi.ingsw.GC_28.model.GameModel;
import it.polimi.ingsw.GC_28.server.Message;
import it.polimi.ingsw.GC_28.spaces.ProdHarvSpace;
import it.polimi.ingsw.GC_28.spaces.ProdHarvType;
import it.polimi.ingsw.GC_28.spaces.Space;
import it.polimi.ingsw.GC_28.view.GameView;

/**
 * This class allows the player to go to any space of the gameBoard, provided that this action is actually possible.
 * @author andreaRinaldi
 * @version 1.0, 07/03/2017
 */
public class SpaceAction extends Action{
	private GameView game;
	private SpaceController spaceController;
	private FamilyMember familyMember;
	private Space space;
	private GoToHPEffect throughEffect;
	private GameModel gameModel;
	
	/**
	 * This constructor builds a SpaceAction out of the gameView, necessary to apply the effects, 
	 * and the gameModel, useful for controlling if the action is possible and apply the changes
	 * @param game the view of the game
	 * @param gameModel the model of the game
	 */
	public SpaceAction(GameView game, GameModel gameModel){
		this.gameModel = gameModel;
		this.game = game;
		spaceController = new SpaceController(gameModel);

	}

	/**
	 * @return if the action chosen by the player is applicable or not
	 */
	public boolean isApplicable(){
		return spaceController.check(familyMember, space, throughEffect);
	}
	
	/**
	 * This method set for the action, the family member that the player has chosen to use
	 * @param familyMember the chosen family member for this action
	 */
	public void setFamilyMember(FamilyMember familyMember) {
		this.familyMember = familyMember;
	}

	/**
	 * This method set for the action, the space that the player has chosen to go into
	 * @param space the chosen space to go into
	 */
	public void setSpace(Space space) {
		this.space = space;
	}

	/**
	 * This method defines if this action is carried out through the immediate effect of a just taken card, 
	 * and not by a common action of the player
	 * @param throughEffect the effect and its properties through which the player is exploiting this action
	 */
	public void setThroughEffect(GoToHPEffect throughEffect) {
		this.throughEffect = throughEffect;
	}

	/**
	 * this method carries out the actual final application of the action exploited by the player
	 * and notifies the view with the result
	 */
	public void apply(){
		ProdHarvSpace ph;
		
		if(space instanceof ProdHarvSpace){
			ph = (ProdHarvSpace) space;
			if(!(ph.isFree()) && ph.isSecondarySpace()){
				familyMember.modifyValue(-3);
			}
			//I look if there are increments for the value of this action in the Characters immediate effects I have on my playerboard
			for(Character character : familyMember.getPlayer().getBoard().getCharacters()){ 
				if(character.getPermanentEffect() instanceof IncrementHPEffect){
					IncrementHPEffect eff = (IncrementHPEffect) character.getPermanentEffect();
					if((eff.getType() == EffectType.INCREMENTHARVESTEFFECT && ph.getType() == ProdHarvType.HARVEST) ||
							(eff.getType() == EffectType.INCREMENTPRODUCTIONEFFECT && ph.getType() == ProdHarvType.PRODUCTION)){
						familyMember.modifyValue(eff.getIncrement());
					}
				}
			}
			
			//I look if I have any excommunication that lowers the value of this action
			for(ExcommunicationTile t : familyMember.getPlayer().getExcommunicationTile()){ 
				if(t.getEffect() instanceof IncrementHPEffect){
					IncrementHPEffect eff = (IncrementHPEffect) t.getEffect();
					if((eff.getType() == EffectType.INCREMENTHARVESTEFFECT && ph.getType() == ProdHarvType.HARVEST) ||
							(eff.getType() == EffectType.INCREMENTPRODUCTIONEFFECT && ph.getType() == ProdHarvType.PRODUCTION)){
						familyMember.modifyValue(eff.getIncrement()); 
					}
				}		
			}
		}
		
		if(throughEffect == null){
			space.addPlayer(familyMember);	
			familyMember.setUsed(true);
		}
		space.applyBonus(game, familyMember);
		gameModel.notifyObserver(new Message("Action completed successfully!", true));

	}
}
