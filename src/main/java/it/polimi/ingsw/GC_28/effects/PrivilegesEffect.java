package it.polimi.ingsw.GC_28.effects;

import it.polimi.ingsw.GC_28.cards.ResourceType;
import it.polimi.ingsw.GC_28.core.PlayerBoard;

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
		System.out.println("apply di PrivilegesEffect");
	}
}
