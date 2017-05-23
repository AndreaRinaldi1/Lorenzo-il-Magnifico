package it.polimi.ingsw.GC_28.effects;

import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.boards.PlayerBoard;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.model.Game;

public class NoCellBonusEffect extends Effect{
	private boolean presence;
	public final EffectType type = EffectType.NOCELLBONUS;

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
	public void apply(PlayerBoard p, GameBoard gameBoard, Game game) {
		System.out.println("apply di NoCellBonus");
		
	}

}
