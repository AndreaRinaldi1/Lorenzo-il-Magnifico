package it.polimi.ingsw.GC_28.effects;

import it.polimi.ingsw.GC_28.cards.*;
import it.polimi.ingsw.GC_28.core.*;

public class ResourceEffect extends Effect{	
	private Resource resourceBonus;
	public final EffectType type = EffectType.RESOURCEEFFECT;

	public ResourceEffect(){
		super();
	}

	public Resource getResourceBonus() {
		return resourceBonus;
	}

	public void setResourceBonus(Resource resourceBonus) {
		this.resourceBonus = resourceBonus;
	}
	
	@Override
	public void apply(PlayerBoard p){
		System.out.println("apply di ResourceEffect");
		for(ResourceType rt : resourceBonus.getResource().keySet()){
			int x = p.getResource().getResource().get(rt);
			x += resourceBonus.getResource().get(rt);
			p.getResource().getResource().put(rt, x);
		}
	}
}
