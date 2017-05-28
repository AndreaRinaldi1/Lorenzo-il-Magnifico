package it.polimi.ingsw.GC_28.core;

import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.effects.GoToHPEffect;
import it.polimi.ingsw.GC_28.model.Game;
import it.polimi.ingsw.GC_28.spaces.ProductionAndHarvestSpace;
import it.polimi.ingsw.GC_28.spaces.Space;

public class SpaceAction {
	private Game game;
	private SpaceController marketController = new SpaceController();
	
	public SpaceAction(Game game){
		this.game = game;
	}

	public boolean isApplicable(FamilyMember familyMember, Space space, GoToHPEffect throughEffect){
		return marketController.check(familyMember, space, throughEffect);
	}
	
	public void apply(FamilyMember familyMember, Space space, GoToHPEffect throughEffect){
		if(throughEffect == null){
			if(space instanceof ProductionAndHarvestSpace){
				ProductionAndHarvestSpace prodHarv = (ProductionAndHarvestSpace) space;
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
