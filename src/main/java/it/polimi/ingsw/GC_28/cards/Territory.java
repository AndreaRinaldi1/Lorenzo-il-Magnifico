package it.polimi.ingsw.GC_28.cards;
import it.polimi.ingsw.GC_28.effects.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Territory extends Card{
	private List<Effect> immediateEffect = new ArrayList<>();
	private HarvestEffect permanentEffect;
	
	public Territory(String name, int idNumber, int era){
		super(name, idNumber, era);
		this.setColor(Color.green);
	}

	public List<Effect> getImmediateEffect() {
		return immediateEffect;
	}

	public void setImmediateEffect(List<Effect> immediateEffect) {
		this.immediateEffect = immediateEffect;
	}

	public HarvestEffect getPermanentEffect() {
		return permanentEffect;
	}

	public void setPermanentEffect(HarvestEffect permanentEffect) {
		this.permanentEffect = permanentEffect;
	}

}
