package it.polimi.ingsw.GC_28.effects;

import it.polimi.ingsw.GC_28.client.ClientWriter;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.model.Game;
import it.polimi.ingsw.GC_28.model.Player;

public class ServantEffect  extends Effect{
	private int numberOfServant;
	private int increment;
	public final EffectType type = EffectType.SERVANTEFFECT;
	
	public void setNumberOfServant(int numberOfServant) {
		this.numberOfServant = numberOfServant;
	}

	public void setIncrement(int increment) {
		this.increment = increment;
	}
	
	public int getNumberOfServant() {
		return numberOfServant;
	}

	public int getIncrement() {
		return increment;
	}

	@Override
	public void apply(Player player, ClientWriter writer){

	}
	
	
}
