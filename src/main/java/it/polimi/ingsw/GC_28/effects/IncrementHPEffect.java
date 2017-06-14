package it.polimi.ingsw.GC_28.effects;

import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.model.Game;

public class IncrementHPEffect extends Effect{

	private int increment;
	public final EffectType type = EffectType.INCREMENTHPEFFECT;
	public EffectType specificType;

	public IncrementHPEffect(){
		super();
	}

	public int getIncrement() {
		return increment;
	}

	public void setIncrement(int increment) {
		this.increment = increment;
	}

	public EffectType getType() {
		return specificType;
	}

	public void setType(EffectType type) {
		this.specificType = type;
	}

	@Override
	public void apply(FamilyMember familyMember, Game game) {
		System.out.println("apply di IncrementProductionEffect");
		
		familyMember.modifyValue(increment);
	}
}
