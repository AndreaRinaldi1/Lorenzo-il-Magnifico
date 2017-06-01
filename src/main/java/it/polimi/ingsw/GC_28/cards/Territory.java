package it.polimi.ingsw.GC_28.cards;
import it.polimi.ingsw.GC_28.effects.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Territory extends Card{
	private List<Effect> immediateEffect = new ArrayList<>();
	private HarvestEffect permanentEffect;
	
	public Territory(String name, int IDNumber, int era){
		super(name, IDNumber, era);
		this.setColor(Color.green);
	}

	public List<Effect> getImmediateEffect() {
		return immediateEffect;
	}

	public void setImmediateEffect(ArrayList<Effect> immediateEffect) {
		this.immediateEffect = immediateEffect;
	}

	public HarvestEffect getPermanentEffect() {
		return permanentEffect;
	}

	public void setPermanentEffect(HarvestEffect permanentEffect) {
		this.permanentEffect = permanentEffect;
	}

}
