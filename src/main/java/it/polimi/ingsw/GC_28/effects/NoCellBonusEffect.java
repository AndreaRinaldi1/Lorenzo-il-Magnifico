package it.polimi.ingsw.GC_28.effects;

import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.boards.PlayerBoard;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.model.Game;

public class NoCellBonusEffect extends Effect{
	private boolean presence;
	private final EffectType type = EffectType.NOCELLBONUS;

	public NoCellBonusEffect(){
		super();
	}
	
	public NoCellBonusEffect(boolean presence){
		this.presence = presence;
	}

	public boolean isPresence() {
		return presence;
	}

	public void setPresence(boolean presence) {
		this.presence = presence;
	}
	
	@Override
	public void apply(FamilyMember familyMember, GameBoard gameBoard, Game game) {
		System.out.println("apply di NoCellBonus");
		//Effettivamente questo effetto non ha molto senso perche quando in action faccio apply (dell'azione) se vedo che tra le carte ho un
		//NoCellBonus, basta che chiamo isPresence per farlo, e se ritorna true, non prendo le risorse in 5 e 7
	}

}
