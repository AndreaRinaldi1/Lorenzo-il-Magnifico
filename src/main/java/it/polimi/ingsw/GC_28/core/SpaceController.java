package it.polimi.ingsw.GC_28.core;

import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.effects.GoToHPEffect;
import it.polimi.ingsw.GC_28.spaces.ProductionAndHarvestSpace;
import it.polimi.ingsw.GC_28.spaces.Space;

public class SpaceController {
	
	public SpaceController(){	}
	
	
	public boolean check(FamilyMember familyMember, Space space, GoToHPEffect throughEffect){
		if(throughEffect == null){
			if(space instanceof ProductionAndHarvestSpace){
				ProductionAndHarvestSpace prodHarv = (ProductionAndHarvestSpace) space;
				if((familyMember.getValue() > prodHarv.getActionValue()) && (prodHarv.isFree() || prodHarv.isSecondarySpace())){
					return true;
				}
			}
			else{
				if(familyMember.getValue() >= space.getActionValue() && space.isFree()){
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
