package it.polimi.ingsw.GC_28.effects;

import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.model.Game;

public class NoEffect extends Effect{

	@Override
	public void apply(FamilyMember familyMember, Game game) {
		System.out.println("apply di NoEffect");
		}
}
