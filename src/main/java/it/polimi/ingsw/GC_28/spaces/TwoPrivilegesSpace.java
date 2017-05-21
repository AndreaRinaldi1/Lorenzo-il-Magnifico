package spaces;

import cards.CouncilPrivilege;
import cards.Resource;

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
