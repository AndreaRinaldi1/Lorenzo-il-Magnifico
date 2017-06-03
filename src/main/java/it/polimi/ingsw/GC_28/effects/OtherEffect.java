package it.polimi.ingsw.GC_28.effects;

import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.boards.PlayerBoard;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.model.Game;

public class OtherEffect extends Effect{
	public final EffectType type = EffectType.OTHEREFFECT;
	public EffectType specificType;


	public OtherEffect(){
		super();
	}
	

	public void setType(EffectType type) {
		this.specificType = type;
	}	
	
	
	
	public EffectType getType() {
		return specificType;
	}


	@Override
	public void apply(FamilyMember familyMember, Game game) {
		System.out.println("apply di OtherEffect");
		//Effettivamente questo effetto non ha molto senso perche quando in action faccio apply (dell'azione) se vedo che tra le carte ho un
		//NoCellBonus, basta che chiamo isPresence per farlo, e se ritorna true, non prendo le risorse in 5 e 7
	}

}
