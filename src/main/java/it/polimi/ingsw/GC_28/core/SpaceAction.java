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

public class SpaceAction extends Action{
	private GameView game;
	private SpaceController spaceController;
	private FamilyMember familyMember;
	private Space space;
	private GoToHPEffect throughEffect;
	private GameModel gameModel;
	
	public SpaceAction(GameView game, GameModel gameModel){
		this.gameModel = gameModel;
		this.game = game;
		spaceController = new SpaceController(gameModel);

	}

	public boolean isApplicable(){
		return spaceController.check(familyMember, space, throughEffect);
	}
	
	public void setFamilyMember(FamilyMember familyMember) {
		this.familyMember = familyMember;
	}

	public void setSpace(Space space) {
		this.space = space;
	}

	public void setThroughEffect(GoToHPEffect throughEffect) {
		this.throughEffect = throughEffect;
	}

	public void apply(){
		ProdHarvSpace ph;
		
		if(space instanceof ProdHarvSpace){
			ph = (ProdHarvSpace) space;
			if(!(ph.isFree()) && ph.isSecondarySpace()){
				familyMember.modifyValue(-3);
			}
			for(Character character : familyMember.getPlayer().getBoard().getCharacters()){
				if(character.getPermanentEffect() instanceof IncrementHPEffect){
					IncrementHPEffect eff = (IncrementHPEffect) character.getPermanentEffect();
					if((eff.getType() == EffectType.INCREMENTHARVESTEFFECT && ph.getType() == ProdHarvType.HARVEST) ||
							(eff.getType() == EffectType.INCREMENTPRODUCTIONEFFECT && ph.getType() == ProdHarvType.PRODUCTION)){
						familyMember.modifyValue(eff.getIncrement());
					}
				}
			}
			
			for(ExcommunicationTile t : familyMember.getPlayer().getExcommunicationTile()){ //guardo se tra le scomuniche ha incrementharvesteffect
				if(t.getEffect() instanceof IncrementHPEffect){
					IncrementHPEffect eff = (IncrementHPEffect) t.getEffect();
					if((eff.getType() == EffectType.INCREMENTHARVESTEFFECT && ph.getType() == ProdHarvType.HARVEST) ||
							(eff.getType() == EffectType.INCREMENTPRODUCTIONEFFECT && ph.getType() == ProdHarvType.PRODUCTION)){
						familyMember.modifyValue(eff.getIncrement()); // allora applico effetto
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
