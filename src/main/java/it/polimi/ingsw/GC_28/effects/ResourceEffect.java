package effects;

import cards.*;
import core.*;
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
			int x = p.res.getResource().get(rt);
			x += resourceBonus.getResource().get(rt);
			p.res.getResource().put(rt, x);
		}
		System.out.println(p.res.toString());
	}
}
