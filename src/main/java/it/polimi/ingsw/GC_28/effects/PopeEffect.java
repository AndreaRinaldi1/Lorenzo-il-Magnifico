package it.polimi.ingsw.GC_28.effects;

import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.view.GameView;

/**
 * This method represent the effect of Sisto IV card.
 * @author nicoloScipione
 * @version 1.0 , 08/07/2017
 */

public class PopeEffect extends Effect{
	
	private Resource bonus;
	public final EffectType type = EffectType.POPEEFFECT;
	
	public Resource getBonus() {
		return bonus;
	}

	public void setBonus(Resource bonus) {
		this.bonus = bonus;
	}
	
	/**
	 * This method is similar to resource effect, but it will be applied only in a specific moment of the game.
	 */
	@Override
	public void apply(Player player, GameView game) {
		player.addResource(bonus);
	}
}
