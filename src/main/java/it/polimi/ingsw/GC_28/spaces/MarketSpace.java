package it.polimi.ingsw.GC_28.spaces;
import components.Resource;
import it.polimi.ingsw.GC_28.cards.*;


public class MarketSpace extends Space{
	private Resource bonus;

	public MarketSpace(boolean free, int actionValue) {
		super(free, actionValue);
	}

	public Resource getBonus() {
		return bonus;
	}

	public void setBonus(Resource bonus) {
		this.bonus = bonus;
	}
}
