package spaces;

import cards.Resource;

public class MarketSpace extends Space{
	private Resource bonus;
	private MarketSpaceType type;

	public MarketSpace(boolean free, int actionValue, MarketSpaceType type) {
		super(free, actionValue);
		this.type = type;
	}

	
	public MarketSpaceType getType() {
		return type;
	}

	public Resource getBonus() {
		return bonus;
	}

	public void setBonus(Resource bonus) {
		this.bonus = bonus;
	}
}
