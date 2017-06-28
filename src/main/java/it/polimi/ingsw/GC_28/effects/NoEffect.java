package it.polimi.ingsw.GC_28.effects;

import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.view.GameView;

public class NoEffect extends Effect{
	public final EffectType type = EffectType.NOEFFECT;
	
	@Override
	public void apply(FamilyMember familyMember, GameView game) {
		}
}
