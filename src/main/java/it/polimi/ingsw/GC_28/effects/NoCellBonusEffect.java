package effects;

import cards.ResourceType;
import core.PlayerBoard;

public class NoCellBonusEffect extends Effect{
	private boolean presence;
	public final EffectType type = EffectType.NOCELLBONUS;

	public NoCellBonusEffect(){
		super();
	}
	
	public NoCellBonusEffect(boolean presence){
		this.presence = presence;
	}

	public boolean isPresence() {
		return presence;
	}

	public void setPresence(boolean presence) {
		this.presence = presence;
	}
	
	@Override
	public void apply(PlayerBoard p){
		System.out.println("apply di NoCellBonus");
		if(presence == true){
			p.res.getResource().put(ResourceType.COIN, 8);
		}
		else{
			p.res.getResource().put(ResourceType.COIN, 9);
		}
		System.out.println(p.res.toString());

	}

}
