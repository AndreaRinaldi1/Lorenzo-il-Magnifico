package it.polimi.ingsw.GC_28.effects;

import it.polimi.ingsw.GC_28.boards.*;


import it.polimi.ingsw.GC_28.components.FamilyMember;

import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.model.Game;

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
	public void apply(FamilyMember familyMember, Game game) {
		System.out.println("apply di ResourceEffect");
		familyMember.getPlayer().getBoard().addResource(resourceBonus);
	}
}
