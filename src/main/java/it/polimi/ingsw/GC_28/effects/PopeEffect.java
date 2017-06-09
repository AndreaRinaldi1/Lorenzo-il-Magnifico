package it.polimi.ingsw.GC_28.effects;

import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.model.Game;
import it.polimi.ingsw.GC_28.model.Player;

public class PopeEffect extends Effect{
	
	private Resource bonus;

	public Resource getBonus() {
		return bonus;
	}

	public void setBonus(Resource bonus) {
		this.bonus = bonus;
	}
	
	@Override
	public void apply(Player player, Game game) {
		player.addResource(bonus);
	}
}
