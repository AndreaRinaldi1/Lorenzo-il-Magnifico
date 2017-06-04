package it.polimi.ingsw.GC_28.core;

import it.polimi.ingsw.GC_28.cards.Character;
import it.polimi.ingsw.GC_28.cards.ExcommunicationTile;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.effects.EffectType;
import it.polimi.ingsw.GC_28.effects.GoToHPEffect;
import it.polimi.ingsw.GC_28.effects.IncrementCardEffect;
import it.polimi.ingsw.GC_28.effects.IncrementHPEffect;
import it.polimi.ingsw.GC_28.effects.OtherEffect;
import it.polimi.ingsw.GC_28.spaces.MarketSpace;
import it.polimi.ingsw.GC_28.spaces.PrivilegesSpace;
import it.polimi.ingsw.GC_28.spaces.ProductionAndHarvestSpace;
import it.polimi.ingsw.GC_28.spaces.Space;

public class SpaceController {
	
	public SpaceController(){	}
	
	
	public boolean check(FamilyMember familyMember, Space space, GoToHPEffect throughEffect){
		ProductionAndHarvestSpace prodHarv;
		
		if(space instanceof MarketSpace || space instanceof PrivilegesSpace){ //se ha scelto di andare al mercato
			for(ExcommunicationTile t : familyMember.getPlayer().getExcommunicationTile()){ //guardo se tra le scomuniche ha nomarketeff
				if(t.getEffect() instanceof OtherEffect){
					OtherEffect eff = (OtherEffect) t.getEffect();
					if(eff.getType().equals(EffectType.NOMARKETEFFECT)){
						return false; //allora ritorno mossa non valida
					}
				}
			}
		}
		
		if(throughEffect == null){
			if(space instanceof ProductionAndHarvestSpace){
				prodHarv = (ProductionAndHarvestSpace) space;
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
