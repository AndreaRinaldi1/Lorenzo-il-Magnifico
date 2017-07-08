package it.polimi.ingsw.GC_28.spaces;

import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.effects.PrivilegesEffect;
import it.polimi.ingsw.GC_28.effects.ResourceEffect;
import it.polimi.ingsw.GC_28.view.GameView;

import java.util.List;

/**
 * This class represent the council palace space on the gameboard.
 * @author nicoloScipione, andreaRinaldi
 * @version 1.0 , 07/07/2017
 *
 */
public class CouncilPalace extends Space{
	private ResourceEffect bonus1;
	private PrivilegesEffect bonus2;
	private static CouncilPalace instance;
		
	private CouncilPalace(){}

	public static CouncilPalace instance(){
		if(instance == null){
			instance = new CouncilPalace();
		}
		return instance;
	}		
	
	/**
	 * 
	 * @return the first bonus of the council palace.
	 */
	public ResourceEffect getBonus1() {
		return bonus1;
	}
	
	/**
	 * 
	 * @param bonus1 the first bonus that players receive when go in council palace.
	 */
	public void setBonus1(ResourceEffect bonus1) {
		this.bonus1 = bonus1;
	}

	/**
	 * 
	 * @return the second bonus which is always a privileges effect.
	 */
	public PrivilegesEffect getBonus2() {
		return bonus2;
	}
	
	/**
	 * 
	 * @param bonus2 the second bonus which is a privileges effect.
	 */
	public void setBonus2(PrivilegesEffect bonus2) {
		this.bonus2 = bonus2;
	}
	
	/**
	 * 
	 * @return the list of players in council palace.
	 */
	public List<FamilyMember> getPlayerOrder(){
		return this.getPlayer();
	}
	
	/**
	 * add a player to council palace.
	 */
	@Override
	public void addPlayer(FamilyMember player){
		this.getPlayer().add(player);
	}
	
	/**
	 * apply bonus of council palace to the player that go in this place.
	 */
	@Override
	public void applyBonus(GameView game, FamilyMember familyMember){
		bonus1.apply(familyMember, game);
		bonus2.apply(familyMember, game);
	}
	
}
