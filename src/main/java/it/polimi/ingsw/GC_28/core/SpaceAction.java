package it.polimi.ingsw.GC_28.core;

import it.polimi.ingsw.GC_28.cards.Character;
import it.polimi.ingsw.GC_28.cards.ExcommunicationTile;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.effects.GoToHPEffect;
import it.polimi.ingsw.GC_28.effects.IncrementHarvestEffect;
import it.polimi.ingsw.GC_28.effects.IncrementProductionEffect;
import it.polimi.ingsw.GC_28.model.Game;
import it.polimi.ingsw.GC_28.model.GameModel;
import it.polimi.ingsw.GC_28.server.Message;
import it.polimi.ingsw.GC_28.spaces.HarvestSpace;
import it.polimi.ingsw.GC_28.spaces.ProductionSpace;
import it.polimi.ingsw.GC_28.spaces.Space;

public class SpaceAction extends Action{
	private Game game;
	private SpaceController spaceController;
	private FamilyMember familyMember;
	private Space space;
	private GoToHPEffect throughEffect;
	private GameModel gameModel;
	
	public SpaceAction(Game game, GameModel gameModel){
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
		HarvestSpace harv;
		ProductionSpace prod;
		
		if(space instanceof HarvestSpace){
			harv = (HarvestSpace) space;
			if(!(harv.isFree()) && harv.isSecondarySpace()){
				familyMember.modifyValue(-3);
			}
			for(Character character : familyMember.getPlayer().getBoard().getCharacters()){
				if(character.getPermanentEffect() instanceof IncrementHarvestEffect){
					IncrementHarvestEffect eff = (IncrementHarvestEffect) character.getPermanentEffect();
						familyMember.modifyValue(eff.getIncrement());
				}
			}
			
			for(ExcommunicationTile t : familyMember.getPlayer().getExcommunicationTile()){ //guardo se tra le scomuniche ha incrementharvesteffect
				if(t.getEffect() instanceof IncrementHarvestEffect){
					IncrementHarvestEffect eff = (IncrementHarvestEffect) t.getEffect();
						familyMember.modifyValue(eff.getIncrement()); // allora applico effetto
				}		
			}
		}
		else if(space instanceof ProductionSpace){
			prod = (ProductionSpace) space;
			if(!(prod.isFree()) && prod.isSecondarySpace()){
				familyMember.modifyValue(-3);
			}
			for(Character character : familyMember.getPlayer().getBoard().getCharacters()){
				if(character.getPermanentEffect() instanceof IncrementProductionEffect){
					IncrementProductionEffect eff = (IncrementProductionEffect) character.getPermanentEffect();
						familyMember.modifyValue(eff.getIncrement());
				}
			}
			
			for(ExcommunicationTile t : familyMember.getPlayer().getExcommunicationTile()){ //guardo se tra le scomuniche ha incrementproductioneffect
				if(t.getEffect() instanceof IncrementProductionEffect){
					IncrementProductionEffect eff = (IncrementProductionEffect) t.getEffect();
						familyMember.modifyValue(eff.getIncrement()); // allora applico effetto
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
