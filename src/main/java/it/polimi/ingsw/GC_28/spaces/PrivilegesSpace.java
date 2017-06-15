package it.polimi.ingsw.GC_28.spaces;
import it.polimi.ingsw.GC_28.client.ClientWriter;
import it.polimi.ingsw.GC_28.components.*;

import it.polimi.ingsw.GC_28.effects.PrivilegesEffect;
import it.polimi.ingsw.GC_28.model.Game;


public class PrivilegesSpace extends Space{
	private PrivilegesEffect bonus;
	
	public PrivilegesSpace(boolean free, int actionValue) {
		super(free, actionValue);
	}

	
	public PrivilegesEffect getBonus() {
		return bonus;
	}

	public void setBonus(PrivilegesEffect bonus) {
		this.bonus = bonus;
	}

	public void applyBonus(ClientWriter writer, FamilyMember familyMember) {
		bonus.apply(familyMember, writer);
	}	
}
