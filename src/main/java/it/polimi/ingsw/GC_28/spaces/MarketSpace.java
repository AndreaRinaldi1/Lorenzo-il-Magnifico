package spaces;
import cards.*;


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
