package it.polimi.ingsw.GC_28.spaces;
import it.polimi.ingsw.GC_28.cards.*;

import java.lang.Character;

public class TwoPrivilegesSpace extends Space{
	private CouncilPrivilege bonus;
	
	public TwoPrivilegesSpace(boolean free, int actionValue, CouncilPrivilege bonus) {
		super(free, actionValue);
		this.bonus = bonus;
	}

	public Resource getBonus(Character choice) {
		return this.bonus.choose(choice);
	}	
}
