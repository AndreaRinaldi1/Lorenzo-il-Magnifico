package it.polimi.ingsw.GC_28.effects;

import it.polimi.ingsw.GC_28.client.ClientWriter;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.model.Game;

public class NoEffect extends Effect{
	public final EffectType type = EffectType.NOEFFECT;
	
	@Override
	public void apply(FamilyMember familyMember, ClientWriter writer) {
		System.out.println("apply di NoEffect");
		}
}
