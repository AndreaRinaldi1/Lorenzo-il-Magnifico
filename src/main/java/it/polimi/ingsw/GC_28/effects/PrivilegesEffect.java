package effects;

import cards.ResourceType;
import core.PlayerBoard;

public class PrivilegesEffect extends Effect{
	private int numberOfCouncilPrivileges;
	public final EffectType type = EffectType.PRIVILEGESEFFECT;

	public PrivilegesEffect(){
		super();
	}

	public int getNumberOfCouncilPrivileges() {
		return numberOfCouncilPrivileges;
	}

	public void setNumberOfCouncilPrivileges(int numberOfCouncilPrivileges) {
		this.numberOfCouncilPrivileges = numberOfCouncilPrivileges;
	}
	
	@Override
	public void apply(PlayerBoard p){
		p.res.getResource().put(ResourceType.COIN, 18);
	}
}
