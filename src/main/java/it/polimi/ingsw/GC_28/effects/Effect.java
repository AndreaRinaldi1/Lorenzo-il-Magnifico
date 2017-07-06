package it.polimi.ingsw.GC_28.effects;

import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.view.GameView;

public abstract class Effect {
	public void apply(FamilyMember familyMember, GameView game){}
	public void apply(Player player, GameView game){}
	
}
