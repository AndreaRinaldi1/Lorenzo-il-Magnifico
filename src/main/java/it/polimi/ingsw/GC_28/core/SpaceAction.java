package it.polimi.ingsw.GC_28.core;

import it.polimi.ingsw.GC_28.cards.Character;
import it.polimi.ingsw.GC_28.cards.ExcommunicationTile;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.effects.GoToHPEffect;
import it.polimi.ingsw.GC_28.effects.IncrementHPEffect;
import it.polimi.ingsw.GC_28.model.Game;
import it.polimi.ingsw.GC_28.spaces.ProductionAndHarvestSpace;
import it.polimi.ingsw.GC_28.spaces.Space;

public class SpaceAction extends Action{
	private Game game;
	private SpaceController marketController = new SpaceController();
	private FamilyMember familyMember;
	private Space space;
	private GoToHPEffect throughEffect;
	
	public SpaceAction(Game game){
		this.game = game;
	}

	public boolean isApplicable(){
		return marketController.check(familyMember, space, throughEffect);
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
		ProductionAndHarvestSpace prodHarv;
		
		if(space instanceof ProductionAndHarvestSpace){
			prodHarv = (ProductionAndHarvestSpace) space;
			for(Character character : familyMember.getPlayer().getBoard().getCharacters()){
				if(character.getPermanentEffect() instanceof IncrementHPEffect){
					IncrementHPEffect eff = (IncrementHPEffect) character.getPermanentEffect();
					if((eff.isHarvest() && prodHarv.isHarvest()) || (eff.isProduction() && !prodHarv.isHarvest())){
						familyMember.modifyValue(eff.getIncrement());
					}
				}
			}
			
			for(ExcommunicationTile t : familyMember.getPlayer().getExcommunicationTile()){ //guardo se tra le scomuniche ha incrementhpeffect
				if(t.getEffect() instanceof IncrementHPEffect){
					IncrementHPEffect eff = (IncrementHPEffect) t.getEffect();
					if((eff.isHarvest() && prodHarv.isHarvest()) || (eff.isProduction() && !prodHarv.isHarvest())){ //se production e ho prod o se harvest e ho har
						familyMember.modifyValue(eff.getIncrement()); // allora applico effetto
					}
				}		
			}
			
		}
		
		
		if(throughEffect == null){
			if(space instanceof ProductionAndHarvestSpace){
				prodHarv = (ProductionAndHarvestSpace) space;
				if(!prodHarv.isFree() && prodHarv.isSecondarySpace()){
					familyMember.modifyValue(-3);
				}
			}
			space.addPlayer(familyMember);	
		}
		space.applyBonus(game, familyMember);
		familyMember.setUsed(true);
	}
}
