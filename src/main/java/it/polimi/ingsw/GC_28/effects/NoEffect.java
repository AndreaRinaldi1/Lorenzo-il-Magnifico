package it.polimi.ingsw.GC_28.effects;

import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.view.GameView;
/**
 * This class represent the empty effect.
 * @author andreaRinaldi, nicoloScipione
 * @version 1.0, 07/04/2017
 */
public class NoEffect extends Effect{
	public final EffectType type = EffectType.NOEFFECT;
	
	@Override
	public void apply(FamilyMember familyMember, GameView game) {
		}
}
