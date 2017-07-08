package it.polimi.ingsw.GC_28.effects;

import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.view.GameView;
/**
 * This class represent the effect that allows to increment  (or reducing, for excommunications) 
 * the value of an action of going to harvest or production.
 * on the resource cost of the card.
 * @author andreaRinaldi
 * @version 1.0, 07/04/2017
 */
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

	/**
	 * This method modifies the action value of the family member selected of the amount specified in the effect.
	 */
	@Override
	public void apply(FamilyMember familyMember, GameView game) {
		familyMember.modifyValue(increment);
	}
}
