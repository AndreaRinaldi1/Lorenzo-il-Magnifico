package it.polimi.ingsw.GC_28.spaces;
import it.polimi.ingsw.GC_28.components.*;

import it.polimi.ingsw.GC_28.effects.PrivilegesEffect;
import it.polimi.ingsw.GC_28.view.GameView;

/**
 * This class represent a particular space, which gives two different council privilege to whom occupy this space.
 * @author andreaRinaldi,nicoloScipione
 * @see Space,CouncilPrivilege,PrivilegesEffect
 * @version 1.0, 03/07/2017
 */

public class PrivilegesSpace extends Space{
	private PrivilegesEffect bonus;
	
	/**
	 * Inherit the constructor from super class.
	 * @param free
	 * @param actionValue
	 */
	public PrivilegesSpace(boolean free, int actionValue) {
		super(free, actionValue);
	}

	/**
	 * This method return the privileges effect, which is space's bonus. 
	 * @return PrivilegesEffect
	 */
	public PrivilegesEffect getBonus() {
		return bonus;
	}
	
	/**
	 * This method set a Privileges Effect as this space bonus.
	 * @param bonus
	 */
	public void setBonus(PrivilegesEffect bonus) {
		this.bonus = bonus;
	}
	
	/**
	 * This method apply the privileges effect which is set as this space's bonus. 
	 */
	@Override
	public void applyBonus(GameView game, FamilyMember familyMember) {
		bonus.apply(familyMember, game);
	}	
}
