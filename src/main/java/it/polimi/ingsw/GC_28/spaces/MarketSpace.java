package it.polimi.ingsw.GC_28.spaces;
import it.polimi.ingsw.GC_28.cards.*;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.effects.ResourceEffect;
import it.polimi.ingsw.GC_28.view.GameView;


public class MarketSpace extends Space{
	private ResourceEffect bonus;

	public MarketSpace(boolean free, int actionValue) {
		super(free, actionValue);
	}

	public ResourceEffect getBonus() {
		return bonus;
	}

	public void setBonus(ResourceEffect bonus) {
		this.bonus = bonus;
	}

	@Override
	public void applyBonus(GameView game, FamilyMember familyMember){
		bonus.apply(familyMember, game);
	}
}
