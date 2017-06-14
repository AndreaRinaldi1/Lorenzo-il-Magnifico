package it.polimi.ingsw.GC_28.effects;

import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.model.Game;

public class IncrementProductionEffect extends Effect{

	private int increment;

	public final EffectType type = EffectType.INCREMENTPRODUCTIONEFFECT;

	public IncrementProductionEffect(){
		super();
	}

	public int getIncrement() {
		return increment;
	}

	public void setIncrement(int increment) {
		this.increment = increment;
	}

	@Override
	public void apply(FamilyMember familyMember, Game game) {
		System.out.println("apply di IncrementProductionEffect");
		
		familyMember.modifyValue(increment);
	}
}
