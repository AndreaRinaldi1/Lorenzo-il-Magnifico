package it.polimi.ingsw.GC_28.spaces;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.effects.ResourceEffect;
import it.polimi.ingsw.GC_28.view.GameView;
/**
 * This class represent the market space of the game.
 * @author nicoloscipione,andrearinaldi
 * @see Space,ResourceEffect
 * @version 1.0, 03/07/2017
 */

public class MarketSpace extends Space{
	private ResourceEffect bonus;
	
	/**
	 * Inherit the contructor from super class(see Space).
	 * @param free
	 * @param actionValue
	 */
	public MarketSpace(boolean free, int actionValue) {
		super(free, actionValue);
	}
	
	/**
	 * This method return the specific resource bonus of the space. In particolar the bonus is a resource effect.
	 * @return ResourceEffect.
	 */
	public ResourceEffect getBonus() {
		return bonus;
	}
	
	/**
	 * This method set the bonus for the space.
	 * @param bonus.Bonus must be a ResourceEffect type in this class.
	 */
	public void setBonus(ResourceEffect bonus) {
		this.bonus = bonus;
	}
	/**
	 * This methos apply space's bonus, so it gives the resource to the player which is in the space.
	 */
	@Override
	public void applyBonus(GameView game, FamilyMember familyMember){
		bonus.apply(familyMember, game);
	}
}
