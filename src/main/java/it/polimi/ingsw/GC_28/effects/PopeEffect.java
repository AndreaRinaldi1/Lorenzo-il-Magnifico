package it.polimi.ingsw.GC_28.effects;

import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.view.GameView;

public class PopeEffect extends Effect{
	
	private Resource bonus;
	public final EffectType type = EffectType.POPEEFFECT;
	
	public Resource getBonus() {
		return bonus;
	}

	public void setBonus(Resource bonus) {
		this.bonus = bonus;
	}
	
	@Override
	public void apply(Player player, GameView game) {
		player.addResource(bonus);
	}
}
