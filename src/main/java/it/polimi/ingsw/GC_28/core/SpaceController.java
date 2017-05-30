package it.polimi.ingsw.GC_28.core;

import it.polimi.ingsw.GC_28.cards.Character;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.effects.GoToHPEffect;
import it.polimi.ingsw.GC_28.effects.IncrementCardEffect;
import it.polimi.ingsw.GC_28.effects.IncrementHPEffect;
import it.polimi.ingsw.GC_28.spaces.ProductionAndHarvestSpace;
import it.polimi.ingsw.GC_28.spaces.Space;

public class SpaceController {
	
	public SpaceController(){	}
	
	
	public boolean check(FamilyMember familyMember, Space space, GoToHPEffect throughEffect){
		int tmp = familyMember.getValue();
		ProductionAndHarvestSpace prodHarv;
		
		if(space instanceof ProductionAndHarvestSpace){
			prodHarv = (ProductionAndHarvestSpace) space;
			for(Character character : familyMember.getPlayer().getBoard().getCharacters()){
				if(character.getPermanentEffect() instanceof IncrementHPEffect){
					IncrementHPEffect eff = (IncrementHPEffect) character.getPermanentEffect();
					if((eff.isHarvest() && prodHarv.isHarvest()) || (eff.isProduction() && !prodHarv.isHarvest())){
						tmp += eff.getIncrement();
					}
				}
			}
		}
			
		if(throughEffect == null){
			if(space instanceof ProductionAndHarvestSpace){
				prodHarv = (ProductionAndHarvestSpace) space;
				if((tmp > prodHarv.getActionValue()) && (prodHarv.isFree() || prodHarv.isSecondarySpace())){
					return true;
				}
			}
			else{
				if(tmp >= space.getActionValue() && space.isFree()){
					return true;
				}
			}
			return false;
		}
		else{
			return true;
		}
	}
}
